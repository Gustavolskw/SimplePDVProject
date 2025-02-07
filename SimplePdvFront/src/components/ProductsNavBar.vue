<template>
  <nav class="pb-2 pt-4 navBar-bg-color">
    <div class="container">
      <div class="row align-items-center justify-content-center gap-5">
        <!-- Input 1 -->
        <div class="col-md-4 col-lg-3 mb-3">
          <div class="input-group">
            <span class="input-group-text" id="inputGroup2">
              <i class="bi bi-search"></i>
            </span>
            <input
              type="search"
              v-model="productName"
              @change.prevent="handleSearch"
              class="form-control"
              placeholder="Produto"
              aria-label="produto"
              aria-describedby="inputGroup2"
            />
          </div>
        </div>

        <!-- Dropdown -->
        <div class="col-md-4 col-lg-3 mb-2">
          <div class="input-group">
            <span class="input-group-text" id="inputGroup2">
              <i class="bi bi-search"></i>
            </span>
            <select
              id="types"
              class="form-select"
              v-model="productType"
              @change.prevent="handleSearch"
            >
              <option class="fw-bold" value="" selected>Todos</option>
              <option
                :value="pType.id"
                v-for="pType in productTypes"
                :key="pType.id"
              >
                {{ pType.name }}
              </option>
            </select>
          </div>
        </div>

        <!-- Continue Order Button -->
        <div class="col-md-3 col-lg-2 mb-2" v-if="props.hasToken">
          <button
            @click="continueOrder"
            class="btn w-100 text-white fw-bold add-pedido-btn"
          >
            Continuar
          </button>
        </div>

        <div class="col-md-3 col-lg-2 mb-2" v-else>
          <button
            @click="showModal = true"
            class="btn w-100 text-white fw-bold add-pedido-btn"
          >
            Abrir novo pedido
          </button>
        </div>

        <!-- Search Button -->
        <div class="col-md-2 col-lg-2 mb-2">
          <button
            @click="handleSearch"
            class="btn w-100 text-white fw-bold btn-outline-info"
          >
            Buscar Produtos
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
import { ref, onMounted } from "vue";
import OrderModal from "./Modals/OrderOpenModal.vue";
import axiosClient from "@/Client/AxiosClient";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

const props = defineProps({
  hasToken: {
    type: Boolean,
    required: true,
  },
});

const emit = defineEmits([
  "ORDER_OPENED_FROM_NAV",
  "SEARCH_PRODUCTS",
  "CONTINUE_ORDER",
]);

const productName = ref();
const productType = ref("");

const store = useStore();
const router = useRouter();
const productTypes = ref([]);
const showModal = ref(false);
onMounted(() => {
  getProductTypes();
});

async function getProductTypes() {
  try {
    const response = await axiosClient.get("/product/type", {
      timeout: 2000,
    });
    productTypes.value = response.data.data;
  } catch (error) {
    console.error(error);
  }
}
function handleOrderOpened(orderSpecs) {
  store.dispatch("saveToken", {
    tokenName: "orderToken",
    tokenValue: orderSpecs.orderId,
  });
  showModal.value = false;
  emit("ORDER_OPENED_FROM_NAV");
}
function continueOrder() {
  store.dispatch("removeToken", "orderToken");
  emit("CONTINUE_ORDER");
  router.push("/pedidos");
}

function resetFilters() {
  productName.value = "";
  productType.value = "";
}

// Emit search filters
function handleSearch() {
  emit("SEARCH_PRODUCTS", {
    name: productName.value,
    type: productType.value,
  });
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
  background-color: #06ac00ee !important;
}
.add-pedido-btn:hover {
  background-color: rgba(8, 255, 140, 0.63) !important;
}
</style>
