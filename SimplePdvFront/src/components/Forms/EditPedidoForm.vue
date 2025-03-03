<template>
  <div class="row">
    <div class="col-md-4 col-lg-3 col-12">
      <label for="inputCosumerName" class="form-label fw-bold"
        >Consumidor</label
      >
      <input
        type="text"
        id="inputCosumerName"
        class="form-control"
        v-model="consumerName"
      />
      <span class="form-text fw-bold text-danger is-invalid">{{
        errorConsumerName
      }}</span>
    </div>
    <div class="col-md-2 col-lg-1 col-4">
      <label for="inputGuideId" class="form-label fw-bold">Id Guia</label>
      <input
        type="number"
        id="inputGuideId"
        class="form-control"
        v-model="guideId"
        @change.prevent="handleGuideEdition"
      />
      <span class="form-text fw-bold text-danger is-invalid">{{
        errorGuideId
      }}</span>
    </div>
    <div class="col-md-3 col-lg-3 col-6">
      <label for="inputguideName" class="form-label fw-bold">Guia</label>
      <p
        class="border rounded bg-body-secondary border-1 px-3 py-2"
        id="inputguideName"
      >
        {{ guideName }}
      </p>
      <span class="form-text fw-bold text-danger is-invalid">{{
        errorGuideName
      }}</span>
    </div>

    <div class="col-md-2 col-lg-1 col-4">
      <label for="inputTableNumber" class="form-label fw-bold">Mesa:</label>
      <input
        type="number"
        id="inputTableNumber"
        class="form-control"
        v-model="tableNumber"
      />
      <span class="form-text fw-bold text-danger is-invalid">{{
        errorTableNumber
      }}</span>
    </div>

    <div class="col-md-4 col-lg-2 col-6 mt-1">
      <span class="fw-bold"> QTD produtos:</span>
      <p class="border rounded bg-body-secondary border-1 px-3 py-2">
        {{ productQuantity }}
      </p>
    </div>
    <div class="col-md-4 col-lg-2 col-6 mt-1">
      <span class="fw-bold"> QTD Itens:</span>
      <p class="border rounded bg-body-secondary border-1 px-3 py-2">
        {{ itemsQuantity }}
      </p>
    </div>
    <div class="col-md-4 col-lg-2 col-6">
      <span class="fw-bold"> Valor do pedido:</span>
      <p
        class="border rounded bg-body-secondary border-1 px-3 py-2"
        :class="{
          'text-primary fw-bold': isTotalPriceChanged == true,
        }"
      >
        {{ formatCurrency(totalPrice) }}
      </p>
    </div>

    <div class="col-12 justify-content-center">
      <div class="d-flex justify-content-center">
        <button class="btn btn-light w-50" @click="handleResetOrderForm">
          Resetar
        </button>
      </div>
    </div>

    <div class="col-md-12 col-12">
      <div
        v-for="product in products"
        v-bind:key="product.productId"
        class="my-3 border px-md-5 px-2 py-4"
      >
        <EditProdutoDoPedido
          @PRODUCT_QUANTITY_CHANGED="calculateTempTotalOrderValue"
          :productId="product.productId"
          :productName="product.productName"
          :imageUrl="product.imageUrl"
          :value="product.value"
          :quantity="product.quantity"
          :total="product.total"
        ></EditProdutoDoPedido>
      </div>
    </div>

    <div
      class="text-center"
      :class="{
        'col-6': props.orderData.updatedAt != null,
        'col-12': props.orderData.updatedAt == null,
      }"
    >
      <span class="fw-bold"> Criado Em:</span>
      <p class="px-3 py-2">
        {{ props.orderData.createdAt }}
      </p>
    </div>
    <div class="col-6" v-if="props.orderData.updatedAt != null">
      <span class="fw-bold"> Ultima Atualização:</span>
      <p class="px-3 py-2">
        {{ props.orderData.updatedAt }}
      </p>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import EditProdutoDoPedido from "./EditProdutoDoPedido.vue";
import { formatCurrency } from "@/Util/Currency";
import { userService } from "@/services/userService";

const props = defineProps({
  orderData: {
    type: Object,
    required: true,
  },
});

const consumerName = ref("");
const errorConsumerName = ref("");
const tableNumber = ref("");
const errorTableNumber = ref("");
const guideName = ref("");
const errorGuideName = ref("");
const guideId = ref();
const errorGuideId = ref("");
const productQuantity = ref();
const itemsQuantity = ref();
const totalPrice = ref();
const totalPriceOriginal = ref();
const products = ref([]);
const tempProducts = ref([]);

const isTotalPriceChanged = ref(false);

watch(
  () => props.orderData,
  (editOrder) => {
    if (editOrder) {
      consumerName.value = editOrder.consumerName || "";
      guideName.value = editOrder.guide;
      guideId.value = editOrder.guideId;
      tableNumber.value = editOrder.tableId;
      productQuantity.value = editOrder.productsQuantity;
      itemsQuantity.value = editOrder.itemsQuantity;
      totalPrice.value = editOrder.totalPrice;
      totalPriceOriginal.value = editOrder.totalPrice;
      products.value = editOrder.products;
    }
  },
  { immediate: true }
);

const emit = defineEmits(["ORDER_CHANGED", "ORDER_RESET"]);

onMounted(() => {
  verifyTotalPriceValue(totalPriceOriginal.value, totalPrice.value);
});

const handleGuideEdition = () => {
  if (!validateGuideIdInput()) return;
  fecthGuide();
};

const calculateTempTotalOrderValue = (data) => {
  // Clone the original products array into tempProducts if it's empty
  if (tempProducts.value.length === 0) {
    tempProducts.value = products.value.map((p) => ({ ...p }));
  }

  // Find and update the changed product in tempProducts
  let productChanged = tempProducts.value.find(
    (product) => product.productId === data.id
  );

  if (!productChanged) {
    console.warn(`Produto com ID ${data.id} não encontrado!`);
    return;
  }

  productChanged.quantity = data.quantity;

  // Recalculate total value based on the modified tempProducts array
  let newTotalValue = tempProducts.value.reduce((acc, product) => {
    return acc + product.value * product.quantity;
  }, 0);

  totalPrice.value = newTotalValue;
  verifyTotalPriceValue(totalPriceOriginal.value, totalPrice.value);
};

const verifyTotalPriceValue = (originalValue, actualValue) => {
  isTotalPriceChanged.value = false;
  if (originalValue != actualValue) {
    isTotalPriceChanged.value = true;
    emit("ORDER_CHANGED");
  } else {
    emit("ORDER_RESET");
  }
};

const fecthGuide = async () => {
  try {
    const response = await userService.getUserById(guideId.value);
  } catch (error) {
    errorGuideId.value = "Erro na Busca do Guia";
    errorGuideName.value = "Erro na atualização do nome do Guia";
  }
};
const updateGuideData = (name) => {
  guideName.value = name;
};
const validateGuideIdInput = () => {
  if (guideId.value === null || guideId.value == 0) {
    errorGuideId.value = "Valor do Id do Guia inválido!";
    return false;
  }
  return true;
};
const handleResetOrderForm = () => {
  consumerName.value = props.orderData.consumerName;
  guideId.value = props.orderData.guideId;
  guideName.value = props.orderData.guide;
  tableNumber.value = props.orderData.tableId;
  products.value = props.orderData.products;
};

const updateOrder = () => {};

defineExpose({
  updateOrder,
});
</script>

<style scoped></style>
