<template>
  <div class="row w-100 ps-md-5 ps-lg-5 ps-2">
    <div class="col-2 col-md-1 mb-5">
      <button
        type="button"
        class="btn-close ms-2 fs-4"
        @click="handleReturnToPedidos"
      ></button>
    </div>
    <div class="col-10 col-md-11">
      <button class="btn btn-primary ms-2 fs-4" :disabled="!isOrderChanged">
        Atualizar Pedido
      </button>
    </div>
    <section class="col-12 justify-content-center ps-4 ps-md-5">
      <div class="d-flex">
        <p class="fw-bold fs-2">#{{ orderData.id }}</p>
      </div>
      <div>
        <EditPedidoForm
          :orderData="orderData"
          @ORDER_CHANGED="isOrderChanged = true"
          @ORDER_RESET="isOrderChanged = false"
        ></EditPedidoForm>
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
const isOrderChanged = ref(false);

onMounted(() => {
  fetchOrderData(orderId.value);
});

const handleReturnToPedidos = () => {
  router.push({ path: "/administracao/pedidos" });
};
const fetchOrderData = async (id) => {
  try {
    const response = await orderService.getOrderById(id);
    console.log("Pedido-request: " + response);
    if (response.status == 200) {
      orderData.value = response.data.data;
      if (
        orderData.value.status == false ||
        orderData.value.productsQuantity == 0
      ) {
        handleReturnToPedidos();
      }
      console.log("Pedido-body: " + orderData.value);
    }
  } catch (error) {
    console.log(error);
  }
};
</script>

<style scoped></style>
