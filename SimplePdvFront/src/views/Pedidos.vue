<template>
  <OrdersBar ref="ordersBarRef" @RELOAD_ORDER_LIST="handleSearch"></OrdersBar>
  <section class="d-flex flex-column justify-content-center section-orders">
    <OrderCard
      v-for="order in orders"
      :key="order.id"
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
      @ORDER_RELOAD="handleSearchAlt"
    ></OrderCard>
  </section>
</template>

<script setup>
import OrdersBar from "../components/OrdersBar.vue";
import OrderCard from "@/components/Cards/OrderCard.vue";
import axiosClient from "@/Client/AxiosClient";
import { ref, onMounted } from "vue";

// Reference for OrdersBar
const ordersBarRef = ref(null);

const orders = ref([]);
onMounted(() => {
  getOrders();
});

const filtersHead = ref();

async function getOrders() {
  const filters = filtersHead.value;
  try {
    const response = await axiosClient.get("/order/search", {
      params: filters,
      timeout: 2000,
    });
    orders.value = response.data.data.content;
    console.log(response.data.data.content);
  } catch (error) {
    console.error(error);
  }
}

// Handle search event from child
function handleSearch(filters) {
  filtersHead.value = filters;
  console.log("Filters from child:", filters);
  getOrders();
}

// Handle alternate search and reset filters if no orders
function handleSearchAlt() {
  console.log("Filters from child:", filtersHead.value);
  getOrders();
  console.log("Quantidade no Array", orders.value.length);
  if (orders.value.length - 1 === 0) {
    // Call resetFilters on OrdersBar
    ordersBarRef.value?.resetFilters();
    filtersHead.value = null;
    getOrders();
  }
}
</script>

<style scoped>
section {
  margin: 4rem 24rem;
  gap: 8rem;
}
@media (max-width: 1200px) {
  section {
    margin: 4rem 2rem;
  }
}
</style>
