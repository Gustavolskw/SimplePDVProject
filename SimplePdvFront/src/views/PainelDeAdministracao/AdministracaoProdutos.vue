<template>
  <section class="form-header-content">
    <form id="search">
      Buscar <input class="form-control" name="query" v-model="searchQuery" />
    </form>
  </section>

  <section class="conteudo-grid">
    <ProductsGrid
      @DELETE_ACTION="handleProductInactivation"
      @UPDATE_ACTION="handleProductUpdate"
      @REACTIVATE_ACTION="handleReactivation"
      v-if="!loading && !error"
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
    <EditProductModal @close="showEditModal = false" :show="showEditModal">
    </EditProductModal>
  </Teleport>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import axiosClient from "@/Client/AxiosClient";
import ProductsGrid from "@/components/Grids/ProductsGrid.vue";
import AlertModal from "@/components/Alerts/AlertModal.vue";
import EditProductModal from "@/components/Modals/EditProductModal.vue";

const products = ref([]);
const searchQuery = ref("");
const loading = ref(false);
const error = ref(false);
const updateProduct = ref(null);

const showEditModal = ref(false);

const alertModalMessage = ref("");
const alertModalStatus = ref();
const showAlertModal = ref();

watch(
  searchQuery,
  () => {
    getProducts();
  },
  { immediate: true }
);

onMounted(() => {
  getProducts();
});

function handleProductInactivation(id) {
  inactivateProduct(id.id);
}

function handleReactivation(id) {
  reactivateProduct(id.id);
}

function handleProductUpdate(id) {
  showEditModal.value = true;
}

function searchProductByIdOnArray(id) {
  products.value.forEach((product) => {
    if (product.id === id) {
      return product;
    }
  });
  return null;
}

async function reactivateProduct(id) {
  try {
    const response = await axiosClient.put(`/product/${id}/activate`, {
      timeout: 5000,
    });
    // Verificando se a resposta contém dados válidos
    console.log(response);
    showAlertModal.value = true;
    if (response.data && response.data.message && response.status == 200) {
      alertModalMessage.value = response.data.message;
      alertModalStatus.value = response.status;
      getProducts();
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
    const response = await axiosClient.delete(`/product/${id}`, {
      timeout: 5000,
    });
    // Verificando se a resposta contém dados válidos

    console.log(response);
    showAlertModal.value = true;
    if (response.data && response.data.message && response.status == 200) {
      alertModalMessage.value = response.data.message;
      alertModalStatus.value = response.status;
      getProducts();
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

async function getProducts() {
  loading.value = true;
  error.value = false;

  try {
    const response = await axiosClient.get("/product", {
      params: { query: searchQuery.value },
      timeout: 5000,
    });

    // Verificando se a resposta contém dados válidos
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
  display: flex;
  justify-content: center;
}
</style>
