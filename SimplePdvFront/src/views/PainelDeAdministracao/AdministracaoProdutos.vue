<template>
  <section class="form-header-content align-items-center">
    <form id="search">
      Buscar <input class="form-control" name="query" v-model="searchQuery" />
    </form>
    <div class="mt-4">
      <button
        class="btn btn-outline-primary"
        @click.prevent="showCreationModal = true"
      >
        Adicionar Produto
      </button>
    </div>
  </section>

  <section class="d-flex justify-content-center w-100">
    <div v-if="!loading && !error" class="conteudo-grid">
      <ProductsGrid
        @DELETE_ACTION="handleProductInactivation"
        @UPDATE_ACTION="handleProductUpdate"
        @REACTIVATE_ACTION="handleReactivation"
        :data="products"
        :columns="[
          { key: 'id', name: 'ID' },
          { key: 'name', name: 'Nome' },
          { key: 'description', name: 'Descrição' },
          { key: 'status', name: 'Status' },
          { key: 'type', name: 'Tipo' },
          { key: 'value', name: 'Valor', type: 1 },
          { key: 'imageUrl', name: 'Imagem' },
        ]"
        :filter-key="searchQuery"
      />
    </div>
    <p v-else-if="loading">Carregando produtos...</p>
    <p v-else class="text-danger">Erro ao carregar produtos.</p>
  </section>

  <Teleport to="body">
    <AlertModal
      @close="showAlertModal = false"
      :alertShow="showAlertModal"
      :message="alertModalMessage"
      :status="alertModalStatus"
    >
    </AlertModal>
  </Teleport>

  <Teleport to="body">
    <EditProductModal
      v-if="showEditModal"
      @close="showEditModal = false"
      :show="showEditModal"
      :productRefer="updateProduct"
      @PRODUCT_UPDATED="handleProductRelaod"
    >
    </EditProductModal>
  </Teleport>

  <Teleport to="body">
    <CreateProductModal
      v-if="showCreationModal"
      @close="showCreationModal = false"
      :show="showCreationModal"
      @PORDUCT_CREATED="handleProductRelaod"
    >
    </CreateProductModal>
  </Teleport>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import { productService } from "@/services/productService";
import ProductsGrid from "@/components/Grids/ProductsGrid.vue";
import AlertModal from "@/components/Alerts/AlertModal.vue";
import EditProductModal from "@/components/Modals/EditProductModal.vue";
import CreateProductModal from "@/components/Modals/CreateProductModal.vue";

const products = ref([]);
const searchQuery = ref("");
const loading = ref(false);
const error = ref(false);
const updateProduct = ref(null);

const showEditModal = ref(false);

const showCreationModal = ref(false);

const alertModalMessage = ref("");
const alertModalStatus = ref();
const showAlertModal = ref();

watch(
  searchQuery,
  () => {
    fetchProducts();
  },
  { immediate: true }
);

onMounted(() => {
  fetchProducts();
});

function handleProductInactivation(id) {
  inactivateProduct(id.id);
}

function handleReactivation(id) {
  reactivateProduct(id.id);
}

function handleProductUpdate(id) {
  updateProduct.value = searchProductByIdOnArray(id.id);
  showEditModal.value = true;
}

function searchProductByIdOnArray(id) {
  return products.value.find((product) => product.id === id) || null;
}

async function reactivateProduct(id) {
  try {
    const response = await productService.reactiveProduct(id);
    // Verificando se a resposta contém dados válidos
    console.log(response);
    showAlertModal.value = true;
    if (response.data && response.data.message && response.status == 200) {
      alertModalMessage.value = response.data.message;
      alertModalStatus.value = response.status;
      fetchProducts();
    } else {
      alertModalMessage.value = response.data.message;
      alertModalStatus.value = response.status;
    }
  } catch (err) {
    showAlertModal.value = true;
    console.error("Erro ao carregar produtos:", err);
    alertModalMessage.value = "Erro Fatal ao Excluir produtos";
    alertModalStatus.value = 500;
  }
}

async function inactivateProduct(id) {
  try {
    const response = await productService.inactivateProduct(id);

    console.log(response);
    showAlertModal.value = true;
    if (response.data && response.data.message && response.status == 200) {
      alertModalMessage.value = response.data.message;
      alertModalStatus.value = response.status;
      fetchProducts();
    } else {
      alertModalMessage.value = response.data.message;
      alertModalStatus.value = response.status;
    }
  } catch (err) {
    showAlertModal.value = true;
    console.error("Erro ao carregar produtos:", err);
    alertModalMessage.value = "Erro Fatal ao Excluir produtos";
    alertModalStatus.value = 500;
  }
}

async function fetchProducts() {
  loading.value = true;
  error.value = false;

  try {
    const response = await productService.getProducts({
      query: searchQuery.value,
    });

    if (response.data && response.data.data && response.data.data.content) {
      products.value = response.data.data.content;
    } else {
      products.value = [];
      error.value = true;
    }
  } catch (err) {
    console.error("Erro ao carregar produtos:", err);
    error.value = true;
    products.value = [];
  } finally {
    loading.value = false;
  }
}

const handleProductRelaod = (data) => {
  fetchProducts();
  showEditModal.value = false;
  showCreationModal.value = false;
  showAlertModal.value = true;
  alertModalMessage.value = data.message;
  alertModalStatus.value = data.status;
};
</script>

<style scoped>
.form-header-content {
  display: flex;
  justify-content: center;
  align-content: center;
  align-items: center;
  gap: 2rem;
  margin: 2rem 1rem;
}
.conteudo-grid {
  width: 100%;
}

@media (max-width: 600px) {
  .conteudo-grid {
    display: flex;
    justify-content: end;
    width: 100%;
  }
}
</style>
