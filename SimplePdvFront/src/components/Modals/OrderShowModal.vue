<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask" @click="$emit('close')">
      <div class="modal-container" @click.stop v-if="order">
        <div class="d-flex justify-content-between">
          <div class="modal-header fw-bolder">#{{ order.id }}</div>
          <button class="border-0 text-black bg-body" @click="$emit('close')">
            <i class="bi bi-x-lg fw-bolder fs-4"></i>
          </button>
        </div>
        <div class="my-2 px-4">
          <p class="text-center">
            <span class="fw-bolder">Consumidor:</span> {{ order.consumerName }}
          </p>
        </div>
        <div class="d-flex flex-wrap my-1 px-4 gap-3">
          <p class="text-center">
            <span class="fw-bolder">Guia:</span> {{ order.guide }}
          </p>
          <p class="text-center">
            <span class="fw-bolder">Qauntidade de Produtos:</span>
            {{ order.productsQuantity }}
          </p>
          <p class="text-center">
            <span class="fw-bolder">Qauntidade de itens:</span>
            {{ order.itemsQuantity }}
          </p>
          <p class="text-center">
            <span class="fw-bolder">Mesa:</span> {{ order.tableId }}
          </p>
          <p class="text-center">
            <span class="fw-bolder">Preço do Pedido:</span>
            {{ formatCurrency(order.totalPrice) }}
          </p>
        </div>

        <!-- Scrollspy Navigation -->
        <div
          class="my-2 d-flex flex-wrap"
          id="list-example"
          v-if="order.products.length != 0"
        >
          <a
            v-for="product in order.products"
            :key="product.productId"
            class="me-2 text-decoration-none text-black product-header"
            :href="'#product-' + product.productId"
          >
            {{ product.productName }}
          </a>
        </div>
        <div v-else>
          <p>Nenhum Produto adicionado ao Pedido!</p>
        </div>

        <!-- Scrollable Section -->
        <div
          class="modal-body overflow-auto scrollspy-own"
          data-bs-spy="scroll"
          data-bs-target="#list-example"
          data-bs-smooth-scroll="true"
          tabindex="0"
        >
          <div
            v-for="product in order.products"
            :key="product.productId"
            :id="'product-' + product.productId"
            class="mb-4"
          >
            <h4>{{ product.productName }}</h4>
            <div class="d-flex flex-column">
              <p>
                <span class="fw-bold">Valor UN:</span>
                {{ formatCurrency(product.value) }}
              </p>
              <p>
                <span class="fw-bold">Quantidade:</span> {{ product.quantity }}
              </p>
              <p>
                <span class="fw-bold">Valor Total:</span>
                {{ formatCurrency(product.total) }}
              </p>
            </div>
          </div>
        </div>

        <div class="modal-footer d-flex justify-content-center flex-column">
          <p class="text-center">
            <span class="fw-bolder">Data de Criação:</span>
            {{ order.createdAt }}
          </p>
          <p class="text-center" v-if="order.updatedAt !== null">
            <span class="fw-bolder">Ultima atualização:</span>
            {{ order.updatedAt }}
          </p>
        </div>
      </div>
      <div v-else class="modal-header">Erro ao Buscar Dados da Ordem...</div>
    </div>
  </Transition>
</template>

<script setup>
import { orderService } from "@/services/orderService";
import { formatCurrency } from "@/Util/Currency";
import { ref } from "vue";

const props = defineProps({
  show: {
    type: Boolean,
  },
});

const order = ref(null);

// Fetch order details by ID
async function getOrderById(id) {
  try {
    const response = await orderService.getOrderById(id);
    order.value = response.data.data;
    console.log("Order loaded:", response.data.data);
  } catch (error) {
    console.error("Error fetching order:", error);
  }
}

// Expose the getOrderById function
defineExpose({
  getOrderById,
});
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.158);
  display: flex;
  justify-content: flex-end; /* Align the modal container to the right */
  transition: opacity 0.3s ease;
}

.modal-container {
  width: 400px; /* Adjust the width as needed */
  height: 100vh; /* Make it full height if required */
  margin: 0; /* Remove default centering */
  padding: 5px 30px;
  background-color: #fff;
  border-radius: 0; /* Optional: Remove border radius for a clean edge */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  overflow-y: auto;
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}

.modal-default-button {
  float: right;
}

.modal-enter-from {
  opacity: 0;
}

.modal-leave-to {
  opacity: 0;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  -webkit-transform: translateX(20px); /* Slide-in animation from the right */
  transform: translateX(20px);
}

.scrollspy-own {
  max-height: 500px;
}

.product-header {
  border: 1px solid black;
  padding: 0 5px;
  border-radius: 4px;
}
</style>
