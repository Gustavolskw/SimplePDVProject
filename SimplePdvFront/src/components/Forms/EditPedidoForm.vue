<template>
  <div class="row">
    <div class="col-md-4 col-6">
      <label for="inputCosumerName" class="form-label">Consumidor</label>
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
    <div class="col-md-12 col-12">
      <div v-for="product in products" v-bind:key="product.productId">
        <EditProdutoDoPedido
          :productId="product.productId"
          :productName="product.productName"
          :imageUrl="product.imageUrl"
          :value="product.value"
          :quantity="product.quantity"
          :total="product.total"
        ></EditProdutoDoPedido>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import EditProdutoDoPedido from "./EditProdutoDoPedido.vue";

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
const products = ref([]);

watch(
  () => props.orderData,
  (editOrder) => {
    if (editOrder) {
      consumerName.value = editOrder.consumerName || "";
      products.value = editOrder.products;
    }
  },
  { immediate: true }
);
</script>

<style scoped></style>
