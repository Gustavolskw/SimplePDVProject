<template>
  <nav class="pb-2 pt-4 navBar-bg-color">
    <div class="container">
      <div class="row align-items-center justify-content-center gap-5">
        <!-- Input 1 -->
        <div class="col-md-4 col-lg-3 mb-3">
          <div class="input-group">
            <span class="input-group-text" id="inputGroup1">
              <i class="bi bi-search"></i>
            </span>
            <input
              type="search"
              class="form-control"
              placeholder="Guia"
              aria-label="guia"
              aria-describedby="inputGroup1"
            />
          </div>
        </div>

        <!-- Input 2 -->
        <div class="col-md-4 col-lg-3 mb-3">
          <div class="input-group">
            <span class="input-group-text" id="inputGroup2">
              <i class="bi bi-search"></i>
            </span>
            <input
              type="search"
              class="form-control"
              placeholder="Consumidor"
              aria-label="consumidor"
              aria-describedby="inputGroup2"
            />
          </div>
        </div>

        <!-- Input 2 -->
        <div class="col-md-2 col-lg-2 mb-2">
          <div class="input-group">
            <span class="input-group-text" id="inputGroup2">
              <i class="bi bi-search"></i>
            </span>
            <input
              type="number"
              class="form-control"
              placeholder="Mesa"
              aria-label="mesa"
              aria-describedby="inputGroup2"
            />
          </div>
        </div>

        <!-- Button -->
        <div class="col-md-3 col-lg-2 mb-2">
          <button
            @click="showModal = true"
            class="btn w-100 text-white fw-bold add-pedido-btn"
          >
            Adicionar Pedido
          </button>
        </div>
      </div>
    </div>
  </nav>
  <Teleport to="body">
    <!-- use the modal component, pass in the prop -->
    <OrderModal
      @ORDER_OPENED="handleOrderOpened"
      :show="showModal"
      @close="showModal = false"
    >
    </OrderModal>
  </Teleport>
</template>

<script setup>
import OrderModal from "./Modals/OrderModal.vue";
import { ref } from "vue";

const showModal = ref(false);
const emit = defineEmits(["ORDER_OPENED_RELOAD"]);
function handleOrderOpened() {
  $emit("ORDER_OPENED_RELOAD");
}
</script>

<style>
.navBar-bg-color {
  background-color: #6d6d6d;
}
.add-pedido-btn {
  background-color: #17a2b8 !important;
}
.add-pedido-btn:hover {
  background-color: rgba(8, 255, 140, 0.63) !important;
}
</style>
