<template>
  <section class="d-flex flex-wrap justify-content-evenly mx-5 my-5">
    <ProductCard
      v-for="product in products"
      :key="product.id"
      :id="product.id"
      :name="product.name"
      :description="product.description"
      :type="product.type"
      :value="product.value"
      :imageUrl="product.imageUrl"
    ></ProductCard>
  </section>
</template>
<script setup>
import axiosClient from "@/Client/AxiosClient";
import ProductCard from "@/components/Cards/ProductCard.vue";
import { ref, onMounted } from "vue";

const products = ref([]);
const filtersHead = ref();

onMounted(() => {
  getProducts();
});
async function getProducts() {
  const filters = filtersHead.value;
  try {
    const response = await axiosClient.get("/product", {
      params: filters,
      timeout: 2000,
    });
    products.value = response.data.data;
    console.log(response.data.data);
  } catch (error) {
    console.error(error);
  }
}
</script>
