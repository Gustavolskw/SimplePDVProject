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
              v-model="guide"
              @change.prevent="handleSearch"
              aria-label="guia"
              aria-describedby="inputGroup1"
            />
          </div>
        </div>

        <!-- Input 2 -->
        <div class="col-md-4 col-lg-2 mb-3">
          <div class="input-group">
            <span class="input-group-text" id="inputGroup2">
              <i class="bi bi-search"></i>
            </span>
            <input
              type="search"
              v-model="consumer"
              @change.prevent="handleSearch"
              class="form-control"
              placeholder="Consumidor"
              aria-label="consumidor"
              aria-describedby="inputGroup2"
            />
          </div>
        </div>

        <!-- Input 2 -->
        <div class="col-md-3 col-lg-2 mb-2">
          <div class="input-group">
            <span class="input-group-text" id="inputGroup2">
              <i class="bi bi-search"></i>
            </span>
            <input
              type="number"
              class="form-control"
              @change.prevent="handleSearch"
              v-model="table"
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
        <!-- Button -->
        <div class="col-md-2 col-lg-1 mb-2">
          <button
            @click="handleSearch"
            class="btn w-100 text-white fw-bold btn-outline-info"
          >
            Buscar
          </button>
        </div>
      </div>
    </div>
  </nav>
  <Teleport to="body">
    <!-- use the modal component, pass in the prop -->
    <OrderModal
      @ORDER_OPENED="handleOrderOpened()"
      :show="showModal"
      @close="showModal = false"
    >
    </OrderModal>
  </Teleport>
</template>

<script setup>
import OrderModal from "./Modals/OrderOpenModal.vue";
import { ref } from "vue";

// Reactive variables
const guide = ref("");
const consumer = ref("");
const table = ref(null);

const showModal = ref(false);
const emit = defineEmits(["RELOAD_ORDER_LIST"]);

function handleOrderOpened() {
  console.log(guide.value);
  showModal.value = false;
  emit("RELOAD_ORDER_LIST", {
    guide: guide.value,
    table: table.value,
    consumer: consumer.value,
  });
}

// Emit search filters
function handleSearch() {
  emit("RELOAD_ORDER_LIST", {
    guide: guide.value,
    consumer: consumer.value,
    table: table.value,
  });
}

// Reset all filters
function resetFilters() {
  guide.value = "";
  consumer.value = "";
  table.value = null;
  console.log("Filters reset!");
}

// Expose methods to parent
defineExpose({
  resetFilters,
});
</script>

<style scoped>
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
