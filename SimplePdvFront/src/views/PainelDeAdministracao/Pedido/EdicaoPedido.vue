<template>
  <div class="row w-100">
    <div class="col-1">
      <button
        type="button"
        class="btn-close ms-2 fs-4"
        @click.prevent="handleReturnToPedidos"
      ></button>
    </div>
    <section class="col-11">
      <div class="d-flex">
        <p class="fw-bold fs-2">#{{ orderData.id }}</p>
      </div>
      <div>
        <EditPedidoForm :orderData="orderData"></EditPedidoForm>
      </div>
    </section>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import { orderService } from "@/services/orderService";
import EditPedidoForm from "@/components/Forms/EditPedidoForm.vue";

const router = useRouter();

const route = useRoute();
const orderId = ref(route.params.id);
const orderData = ref([]);

onMounted(() => {
  fetchOrderData(orderId.value);
});

const handleReturnToPedidos = () => {
  router.push({ name: "administracao-pedidos" });
};
const fetchOrderData = async (id) => {
  try {
    const response = await orderService.getOrderById(id);

    console.log(response);
    if (response.status == 200) {
      orderData.value = response.data.data;
      if (
        orderData.value.status == false ||
        orderData.value.productsQuantity == 0
      ) {
        handleReturnToPedidos();
      }
    }
  } catch (error) {
    console.log(error);
  }
};
</script>

<style scoped></style>
