<template>
  <div class="row">
    <div class="col-12">
      <p class="fw-bold">#{{ productId }}</p>
    </div>
    <div
      class="col-md-6 col-lg-2 col-6 align-content-center align-items-center"
      v-if="imageUrl != '' || imageUrl != null"
    >
      <img
        class="img-preview"
        :src="`${api.apiBaseUrl}/image/${imageUrl}`"
        alt="Imagem do produto"
      />
    </div>
    <div class="col-md-6 col-lg-3 col-6">
      <span class="fw-bold"> Produto:</span>
      <p class="bg-body-tertiary rounded border-1 px-3 py-2">
        {{ productName }}
      </p>
    </div>
    <div class="col-md-6 col-lg-2 col-12 justify-content-center">
      <div class="d-flex flex-column">
        <p class="text-center text-lg-start">
          <span class="fw-bold">Valor Un:</span
          >{{ formatCurrency(props.value) }}
        </p>
        <p
          class="text-center text-lg-start"
          :class="{
            'text-primary': quantityChanged == true,
          }"
        >
          <span class="fw-bold">Valor Total:</span
          >{{ formatCurrency(totalValue) }}
        </p>
      </div>
    </div>

    <div class="col-md-12 col-lg-5 col-12">
      <div class="text-center w-100">
        <label for="inputCosumerName" class="form-label">Quantidade</label>
      </div>

      <div class="row px-md-5">
        <div class="col-md-3 col-3">
          <button
            class="btn btn-danger w-100 px-3"
            @click.prevent="handleQuantityDecrease"
          >
            -
          </button>
        </div>
        <div class="col-3 col-6">
          <input
            type="text"
            id="inputCosumerName"
            class="form-control text-center"
            v-model="productQuantity"
            @change.prevent="handletotalValueChange"
          />
          <span class="form-text fw-bold text-danger is-invalid">{{
            errorProductQuantity
          }}</span>
        </div>
        <div class="col-3 col-md-3">
          <button
            class="btn btn-success w-100"
            @click.prevent="handleQuantityIncrease"
          >
            +
          </button>
        </div>
        <div class="col-12 mt-2">
          <button
            class="btn btn-secondary w-100"
            @click.prevent="resetQuantity"
          >
            Resetar Quantidade
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import api from "@/Client/api";
import { ref, watch } from "vue";
import { formatCurrency } from "@/Util/Currency";
const props = defineProps({
  productId: {
    type: Number,
    required: true,
  },
  productName: {
    type: String,
    required: true,
  },
  imageUrl: {
    type: String,
    required: true,
  },
  value: {
    type: Number,
    required: true,
  },
  quantity: {
    type: Number,
    required: true,
  },
  total: {
    type: Number,
    required: true,
  },
});

const emit = defineEmits([
  "PRODUCT_QUANTITY_CHANGED",
  "PRODUCT_QUANTITY_UPDATED",
]);

const productQuantity = ref();
const errorProductQuantity = ref();

const quantityChanged = ref(false);

const totalValue = ref();

watch(
  () => [props.quantity, props.total], // Watch both `quantity` and `value`
  ([quantity, total]) => {
    if (quantity && total) {
      productQuantity.value = quantity;
      totalValue.value = total; // Assuming total = value * quantity
    }
  },
  { immediate: true }
);

const resetQuantity = () => {
  console.log("Incial-props:" + props.quantity);
  productQuantity.value = props.quantity;
  handletotalValueChange();
};

const handletotalValueChange = () => {
  quantityChanged.value = true;
  totalValue.value = productQuantity.value * props.value;
  if (productQuantity.value == props.quantity) {
    quantityChanged.value = false;
  }
  emit("PRODUCT_QUANTITY_CHANGED", {
    id: props.productId,
    quantity: productQuantity.value,
  });
};

const handleQuantityDecrease = () => {
  if (productQuantity.value - 1 >= 0) {
    if (productQuantity - 1 === 0) {
      /*will ask for drop the product */
    }
    productQuantity.value -= 1;
    handletotalValueChange();
  }
};
const handleQuantityIncrease = () => {
  productQuantity.value += 1;
  handletotalValueChange();
};
</script>

<style scoped>
.img-preview {
  max-width: 80px;
  max-height: 80px;
  border-radius: 5px;
  margin-top: 10px;
}
@media (max-width: 800px) {
  .img-preview {
    max-width: 120px;
    max-height: 120px;
    border-radius: 5px;
  }
}

@media (max-width: 576px) {
  .img-preview {
    max-width: 60px;
    max-height: auto;
    border-radius: 5px;
  }
}
</style>
