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
      <ProductTypeGrid
        @DELETE_ACTION="handleProductInactivation"
        :data="productTypes"
        :columns="[
          { key: 'id', name: 'ID' },
          { key: 'name', name: 'Nome' },
        ]"
        :filter-key="searchQuery"
      />
    </div>
    <p v-else-if="loading">Carregando produtos...</p>
    <p v-else class="text-danger">Erro ao carregar produtos.</p>
  </section>

  <Teleport to="body">
    <CreateProductTypeModal
      v-if="showCreationModal"
      @close="showCreationModal = false"
      :show="showCreationModal"
      @PRODUCT_TYPE_CREATED="handleProductRelaod"
    >
    </CreateProductTypeModal>
  </Teleport>

  <Teleport to="body">
    <AlertModal
      @close="showAlertModal = false"
      :alertShow="showAlertModal"
      :message="alertModalMessage"
      :status="alertModalStatus"
    >
    </AlertModal>
  </Teleport>
</template>
<script setup>
import { onMounted, ref, watch } from "vue";
import ProductTypeGrid from "@/components/Grids/ProductTypeGrid.vue";
import CreateProductTypeModal from "@/components/Modals/CreateProductTypeModal.vue";
import AlertModal from "@/components/Alerts/AlertModal.vue";
import { prodcutTypeServcie } from "@/services/productTypeService";

const productTypes = ref([]);
const searchQuery = ref("");
const loading = ref(false);
const error = ref(false);
const alertModalMessage = ref("");
const alertModalStatus = ref();
const showAlertModal = ref();
const showCreationModal = ref(false);

watch(
  searchQuery,
  () => {
    getProductTypes();
  },
  { immediate: true }
);
onMounted(() => {
  getProductTypes();
});
function handleProductInactivation(id) {
  inactivateProduct(id.id);
}
async function getProductTypes() {
  loading.value = true;
  error.value = false;

  try {
    const response = await prodcutTypeServcie.getProductTypes({
      query: searchQuery.value,
    });

    // Verificando se a resposta contém dados válidos
    if (response.data && response.data.data && response.data.data) {
      productTypes.value = response.data.data;
    } else {
      productTypes.value = [];
      error.value = true;
    }
  } catch (err) {
    console.error("Erro ao carregar produtos:", err);
    error.value = true;
    productTypes.value = [];
  } finally {
    loading.value = false;
  }
}

async function inactivateProduct(id) {
  try {
    const response = await prodcutTypeServcie.deleteProductTypes(id);
    // Verificando se a resposta contém dados válidos
    showAlertModal.value = true;
    if (response.data && response.data.message && response.status == 200) {
      alertModalMessage.value = response.data.message;
      alertModalStatus.value = response.status;
      getProductTypes();
    } else {
      alertModalMessage.value = response.data.message;
      alertModalStatus.value = response.status;
    }
  } catch (err) {
    showAlertModal.value = true;
    console.error("Erro ao carregar produtos:", err);
    if (err.status === 422) {
      alertModalMessage.value = "Tipo de Produto possui produtos vinculados!";
      alertModalStatus.value = 422;
    } else {
      alertModalMessage.value = "Erro Fatal ao Excluir produtos";
      alertModalStatus.value = 500;
    }
  }
}

const handleProductRelaod = (data) => {
  getProductTypes();
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
  .form-header-content {
    flex-direction: column;
  }
}
</style>
