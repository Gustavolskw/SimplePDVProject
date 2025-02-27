<template>
  <section>
    <ProductsNavBar
      @ORDER_OPENED_FROM_NAV="updateAfterOrderOpened"
      :hasToken="hasToken"
      ref="filtersFromNav"
      @SEARCH_PRODUCTS="handleProductsSearch"
      @CONTINUE_ORDER="cleanFilters"
    ></ProductsNavBar>
  </section>
  <section
    class="d-flex flex-wrap justify-content-evenly mx-5 my-5 card-section"
    v-if="!errorOnProductData"
  >
    <ProductCard
      @EMIT_iNCLUSION_ALERT="handleAlertPopUp"
      v-for="product in products"
      :key="product.id"
      :id="product.id"
      :name="product.name"
      :description="product.description"
      :status="product.status"
      :type="product.type"
      :value="product.value"
      :imageUrl="product.imageUrl"
      :orderId="parseInt(orderToken)"
    ></ProductCard>
  </section>
  <section v-else class="d-flex justify-content-center align-items-center">
    <h4 class="fw-bold text-danger text-center mt-5">
      Nenhum Produto Encontrado!
    </h4>
  </section>

  <Teleport to="body">
    <AlertModal
      @close="alertModal = false"
      :alertShow="alertModal"
      :message="alertMessage"
      :status="alertMessageStatus"
    >
    </AlertModal>
  </Teleport>
</template>

<script setup>
import ProductsNavBar from "@/components/Navs/ProductsNavBar.vue";
import ProductCard from "@/components/Cards/ProductCard.vue";
import AlertModal from "@/components/Alerts/AlertModal.vue";
import { useStore } from "vuex";
import { ref, onMounted } from "vue";
import { productService } from "@/services/productService";

const alertModal = ref(false);
const alertMessage = ref("");
const alertMessageStatus = ref();

const store = useStore();

const filtersFromNav = ref();

const products = ref([]);
const filtersHead = ref();
const orderToken = ref();
const hasToken = ref(false);
const errorOnProductData = ref(false);

onMounted(() => {
  initializeState();
});

function initializeState() {
  orderToken.value = store.getters.getToken("orderToken");
  hasToken.value = !!orderToken.value;
  handleProductsCleanSearch();
}

async function handleProductsSearch(filters) {
  filtersHead.value = filters;
  fecthProducts(filtersHead.value);
}

const fecthProducts = async (data) => {
  console.log(data);
  try {
    const response = await productService.getProducts(data, true);
    products.value = response.data.data.content;
  } catch (err) {
    console.log(err);
    products.value = null;
    errorOnProductData.value = true;
  }
};

async function handleProductsCleanSearch() {
  fecthProducts(null);
}
function cleanFilters() {
  filtersHead.value = null;
  filtersFromNav.value?.resetFilters();
}
function updateAfterOrderOpened() {
  console.log("Order token added, updating parent state...");
  initializeState(); // Reinitialize state to fetch token and update `hasToken`
}

function handleAlertPopUp(data) {
  alertMessage.value = data.message;
  alertMessageStatus.value = data.status;
  alertModal.value = true;
}
</script>

<style scoped>
.card-section {
  gap: 2rem;
}
</style>
