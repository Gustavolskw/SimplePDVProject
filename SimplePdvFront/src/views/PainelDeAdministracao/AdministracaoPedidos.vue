<template>
  <section class="form-header-content align-items-center">
    <form class="row" id="search">
      <div class="col-lg-4 col-md-4 col-12">
        Consumidor
        <input
          class="form-control"
          name="consumidor"
          v-model="consumerQuery"
          @change.prevent="handleSearchOrders"
        />
      </div>
      <div class="col-lg-4 col-md-4 col-12">
        Guia
        <input
          class="form-control"
          name="guia"
          v-model="guideQuery"
          @change.prevent="handleSearchOrders"
        />
      </div>
      <div class="col-lg-2 col-md-2 col-6">
        Mesa
        <input
          class="form-control"
          name="mesa"
          type="number"
          v-model="tableQuery"
          @change.prevent="handleSearchOrders"
        />
      </div>
      <div class="col-lg-2 col-md-2 col-6 align-content-center">
        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="flexRadioDefault"
            id="statusQueryActive"
            value="true"
            v-model="statusQuery"
            @change.prevent="handleSearchOrders"
          />
          <label class="form-check-label" for="statusQueryActive">
            Ativos
          </label>
        </div>
        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="flexRadioDefault"
            id="statusQueryInactive"
            value="false"
            v-model="statusQuery"
            @change.prevent="handleSearchOrders"
          />
          <label class="form-check-label" for="statusQueryInactive">
            Inativos
          </label>
        </div>
        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="flexRadioDefault"
            id="statusQueryAll"
            value="all"
            v-model="statusQuery"
            @change.prevent="handleSearchOrders"
          />
          <label class="form-check-label" for="statusQueryAll"> Todos </label>
        </div>
      </div>
    </form>
    <div class="mt-4">
      <button @click.prevent="handleSearchOrders" class="btn btn-primary bottt">
        Buscar
      </button>
    </div>
  </section>

  <section class="d-flex justify-content-center w-100">
    <div v-if="!error" class="conteudo-grid">
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
        @CANCEL_ORDER="cancelOrder"
        @CLOSE_ORDER="endOrder"
        @EDIT_ORDER="handleEditOrder"
      />
    </div>
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
import { onMounted, ref } from "vue";
import AlertModal from "@/components/Alerts/AlertModal.vue";
import OrdersGrid from "@/components/Grids/OrdersGrid.vue";
import { orderService } from "@/services/orderService";
import { useRouter } from "vue-router";

const router = useRouter();
const orders = ref([]);
const consumerQuery = ref("");
const guideQuery = ref("");
const tableQuery = ref("");
const statusQuery = ref("all");

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

const handleEditOrder = (orderId) => {
  console.log("Redirecting to edit page with orderId:", orderId);
  // Redirect to the Edicao-pedido route and pass the orderId as a parameter
  router.push({ name: "Edicao-pedido", params: { id: orderId } });
};

const getOrders = async () => {
  error.value = false;

  const filters = {
    ...(consumerQuery.value ? { consumer: consumerQuery.value } : {}),
    ...(guideQuery.value ? { guide: guideQuery.value } : {}),
    ...(tableQuery.value ? { table: tableQuery.value } : {}),
    ...(statusQuery.value !== "all" ? { status: statusQuery.value } : {}),
  };
  try {
    const response = await orderService.getOrders(filters);
    if (response.data && response.data.data && response.data.data.content) {
      const newOrders = response.data.data.content;
      orders.value = response.data.data.content;
    }
  } catch (err) {
    console.error("Erro ao carregar produtos:", err);
    error.value = true;
  } finally {
    loading.value = false;
  }
};

const endOrder = async (id) => {
  try {
    const response = await orderService.closeOrder(id);
    handleOrdersReload({
      message: response.data.message,
      status: response.status,
    });
  } catch (error) {
    handleOrdersReload({
      message: error.response.data.message,
      status: error.response.status,
    });
  }
};

const cancelOrder = async (id) => {
  try {
    const response = await orderService.cancelOrder(id);
    handleOrdersReload({
      message: response.data.message,
      status: response.status,
    });
  } catch (error) {
    handleOrdersReload({
      message: error.response.data.message,
      status: error.response.status,
    });
  }
};

const handleOrdersReload = (data) => {
  if (data.status === 200 || data.status === 201) {
    getOrders();
  }

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
  scale: 103%;
  rotate: 1deg;
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
