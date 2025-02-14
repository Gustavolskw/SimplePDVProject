<template>
  <section class="form-header-content align-items-center">
    <form class="row" id="search">
      <div class="col-lg-5 col-md-4 col-12">
        Consumidor
        <input
          class="form-control"
          name="consumidor"
          v-model="consumerQuery"
          @change.prevent="handleSearchOrders"
        />
      </div>
      <div class="col-lg-5 col-md-4 col-12">
        Guia
        <input
          class="form-control"
          name="guia"
          v-model="guideQuery"
          @change.prevent="handleSearchOrders"
        />
      </div>
      <div class="col-lg-2 col-md-2 col-12">
        Mesa
        <input
          class="form-control"
          name="mesa"
          type="number"
          v-model="tableQuery"
          @change.prevent="handleSearchOrders"
        />
      </div>
    </form>
    <div class="mt-4">
      <button @click="handleSearchOrders" class="btn btn-primary bottt">
        Buscar
      </button>
    </div>
  </section>

  <section class="d-flex justify-content-center w-100">
    <div v-if="!loading && !error" class="conteudo-grid">
      <OrdersGrid
        :data="orders"
        :columns="[
          { key: 'orderId', name: 'ID' },
          { key: 'tableNumber', name: 'Mesa' },
          { key: 'consumerName', name: 'Consumidor' },
          { key: 'guideName', name: 'Guia' },
          { key: 'status', name: 'Status' },
          { key: 'totalValue', name: 'Valor Total', type: 1 },
          { key: 'productsInOrder', name: 'Total de produtos' },
          { key: 'itemsQuantity', name: 'Total de Itens' },
          { key: 'createdAt', name: 'Criado em' },
          { key: 'updatedAt', name: 'Ultima Atualização' },
        ]"
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
</template>
<script setup>
import { onMounted, ref, watch } from "vue";
import axiosClient from "@/Client/AxiosClient";
import AlertModal from "@/components/Alerts/AlertModal.vue";
import OrdersGrid from "@/components/Grids/OrdersGrid.vue";

const orders = ref([]);
const consumerQuery = ref("");
const guideQuery = ref("");
const tableQuery = ref("");

const loading = ref(false);
const error = ref(false);
const alertModalMessage = ref("");
const alertModalStatus = ref();
const showAlertModal = ref();

onMounted(() => {
  getOrders();
});

const handleSearchOrders = () => {
  getOrders();
};

const getOrders = async () => {
  loading.value = true;
  error.value = false;
  const filters = {
    ...(consumerQuery.value ? { consumer: consumerQuery.value } : {}),
    ...(guideQuery.value ? { guide: guideQuery.value } : {}),
    ...(tableQuery ? { table: tableQuery.value } : {}),
  };

  try {
    const response = await axiosClient.get("/order", {
      params: filters,
      timeout: 5000,
    });

    if (response.data && response.data.data && response.data.data.content) {
      orders.value = response.data.data.content;
    } else {
      orders.value = [];
      error.value = true;
    }
  } catch (err) {
    console.error("Erro ao carregar produtos:", err);
    error.value = true;
    orders.value = [];
  } finally {
    loading.value = false;
  }
};

const handleOrdersReload = (data) => {
  getOrders();

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

.bottt:hover {
  scale: 105%;
  rotate: 1deg;
  /* animation-duration: 300ms;*/
}

@media (max-width: 600px) {
  .conteudo-grid {
    display: flex;
    justify-content: end;
    width: 100%;
    overflow-x: auto;
  }
}
</style>
