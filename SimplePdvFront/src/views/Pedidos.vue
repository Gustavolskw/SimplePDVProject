<template>
  <OrdersBar @ORDER_OPENED_RELOAD="getOrders"></OrdersBar>
  <section v-for="order in orders" :key="order.id">
    <OrderCard
      :id="order.orderId"
      :consumerName="order.consumerName"
      :guideName="order.guideName"
      :tableNumber="order.tableNumber"
      :status="order.status"
      :createdAt="order.createdAt"
      :productsInOrder="order.productsInOrder"
      :itemsQuantity="order.itemsQuantity"
      :totalValue="order.totalValue"
      :updatedAt="order.updatedAt"
    ></OrderCard>
  </section>
</template>
<script setup>
import OrdersBar from "../components/OrdersBar.vue";
import OrderCard from "@/components/Cards/OrderCard.vue";
import axiosClient from "@/Client/AxiosClient";
import { ref, onMounted } from "vue";

const orders = ref([]);
onMounted(() => {
  getOrders();
});
async function getOrders() {
  try {
    const response = await axiosClient.get("/order", "", {
      timeout: 2000,
    });
    orders.value = response.data.data.content;
    //console.log(response.data.data.content);
  } catch (error) {
    console.log(error);
  }
}
</script>

<style>
section {
  margin: 4rem 20rem;
}
</style>
