<script setup>
import { ref, computed } from "vue";
import AssureActionAlertModal from "../Modals/AssureActionAlertModal.vue";
import { formatCurrency } from "@/Util/Currency";

const props = defineProps({
  data: Array,
  columns: Array,
});

const emit = defineEmits(["CLOSE_ORDER", "CANCEL_ORDER", "EDIT_ORDER"]);

const showConfirmModal = ref(false);
const confirmModalTitle = ref("");
const confirmModalMessage = ref("");
const actionType = ref(0);
const selectedOrderId = ref(null);
const selectedEvent = ref(null);

const sortKey = ref("");
const sortOrders = ref(
  props.columns.reduce((o, key) => ((o[key.key] = 1), o), {})
);

const filteredData = computed(() => {
  let { data } = props;

  const key = sortKey.value;
  if (key) {
    const order = sortOrders.value[key];
    data = data.slice().sort((a, b) => {
      a = a[key];
      b = b[key];
      return (a === b ? 0 : a > b ? 1 : -1) * order;
    });
  }
  return data;
});

function sortBy(key) {
  sortKey.value = key;
  sortOrders.value[key] *= -1;
}

function capitalize(str) {
  return str.charAt(0).toUpperCase() + str.slice(1);
}

// Abre o modal de confirmação antes de enviar o evento ao Pai
const openConfirmationModal = (id, type) => {
  showConfirmModal.value = true;
  selectedOrderId.value = id;
  selectedEvent.value = type;
  console.log("TIPO:" + type + " ORDEM: " + id);
  if (type === "CLOSE_ORDER") {
    actionType.value = 1;
    confirmModalTitle.value = "Fechar Pedido";
    confirmModalMessage.value = "Tem certeza que deseja fechar esse pedido?";
  } else if (type === "CANCEL_ORDER") {
    actionType.value = 0;
    confirmModalTitle.value = "Cancelar Pedido";
    confirmModalMessage.value = "Tem certeza que deseja cancelar esse pedido?";
  }
};

const handleConfirmAction = () => {
  emit(selectedEvent.value, selectedOrderId.value);
  showConfirmModal.value = false;
};

const closeModal = () => {
  showConfirmModal.value = false;
};

const handleEditOrder = (id) => {
  emit("EDIT_ORDER", id);
};
</script>

<template>
  <div v-if="filteredData.length" class="content-table">
    <div class="wrapper-table">
      <table>
        <thead>
          <tr>
            <th
              v-for="key in columns"
              @click="sortBy(key.key)"
              :class="{ active: sortKey == key.key }"
              :key="key.key"
            >
              {{ capitalize(key.name) }}
              <span
                class="arrow"
                :class="sortOrders[key.key] > 0 ? 'asc' : 'dsc'"
              >
              </span>
            </th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="entry in filteredData" :key="entry.id">
            <td v-for="col in columns" :key="col.key">
              <span v-if="col.type === 1">
                {{ formatCurrency(entry[col.key]) }}</span
              >
              <span
                v-else-if="col.key == 'status'"
                class="fw-bold"
                :class="{
                  'text-danger': entry[col.key] == false,
                  'text-success': entry[col.key] == true,
                }"
              >
                {{ entry[col.key] == true ? "Aberto" : "Fechado" }}</span
              >

              <span v-else> {{ entry[col.key] }}</span>
            </td>
            <td>
              <div class="d-flex justify-content-end">
                <button
                  class="Buttton"
                  @click.prevent="
                    openConfirmationModal(entry.orderId, 'CLOSE_ORDER')
                  "
                  v-if="entry.status == true && entry.productsInOrder > 0"
                >
                  <i class="bi bi-check-circle text-success"></i>
                </button>
                <button
                  class="Buttton"
                  disabled
                  v-else-if="entry.status == false && entry.productsInOrder > 0"
                >
                  <i class="bi bi-check-circle-fill text-success"></i>
                </button>

                <button
                  class="Buttton"
                  v-if="entry.status == true"
                  @click.prevent="
                    openConfirmationModal(entry.orderId, 'CANCEL_ORDER')
                  "
                >
                  <i class="bi bi-trash-fill fw-bolder fs-4 text-danger"></i>
                </button>

                <button
                  class="Buttton"
                  v-if="entry.status == true && entry.productsInOrder > 0"
                  @click.prevent="handleEditOrder(entry.orderId)"
                >
                  <i
                    class="bi bi-pencil-square fw-bolder fs-4 text-secondary"
                  ></i>
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <section class="small-screen-table">
      <div class="header-small-screen">
        <div
          class=""
          v-for="key in columns"
          @click="sortBy(key.key)"
          :class="{ active: sortKey == key.key }"
          :key="key.key"
        >
          {{ capitalize(key.name) }}
          <span class="arrow" :class="sortOrders[key.key] > 0 ? 'asc' : 'dsc'">
          </span>
        </div>
      </div>
      <div
        v-for="entry in filteredData"
        :key="entry.id"
        class="card-section-small-screen"
      >
        <div class="row">
          <p class="col-12 col-md-12 fw-bolder">
            <span class="fw-bold">ID:</span> #{{ entry.id }}
          </p>
          <p class="col-12 col-md-6">
            <span class="fw-bold">Mesa:</span> {{ entry.tableNumber }}
          </p>
          <p class="col-12 col-md-6">
            <span class="fw-bold">Cosumidor:</span> {{ entry.consumerName }}
          </p>
          <p class="col-12 col-md-6">
            <span class="fw-bold">Guia:</span> {{ entry.guideName }}
          </p>
          <p class="col-12 col-md-6">
            <span class="fw-bold">Valor Total:</span>
            {{ formatCurrency(entry.totalValue) }}
          </p>
          <p class="col-12 col-md-6">
            <span class="fw-bold">Status:</span>
            <span
              class="fw-bold"
              :class="{
                'text-danger': entry.status == false,
                'text-success': entry.status == true,
              }"
            >
              {{ entry.status == true ? "Aberto" : "Fechado" }}
            </span>
          </p>
          <p class="col-12">
            <span class="fw-bold">Total de produtos:</span>
            {{ entry.productsInOrder }}
          </p>
          <p class="col-12">
            <span class="fw-bold">Total de Itens:</span>
            {{ entry.itemsQuantity }}
          </p>
          <p
            class="col-12"
            v-if="entry.updatedAt == null && entry.createdAt != null"
          >
            <span class="fw-bold">Data de Inicio:</span>
            {{ entry.createdAt }}
          </p>
          <p class="col-12" v-else>
            <span class="fw-bold">Data de Inicio:</span>
            {{ entry.updatedAt }}
          </p>
          <div class="col-12 d-flex justify-content-center gap-5">
            <button
              class="Buttton"
              @click.prevent="
                openConfirmationModal(entry.orderId, 'CLOSE_ORDER')
              "
              v-if="entry.status == true && entry.productsInOrder > 0"
            >
              <i class="bi bi-check-circle text-success"></i>
            </button>
            <button
              class="Buttton"
              disabled
              v-else-if="entry.status == false && entry.productsInOrder > 0"
            >
              <i class="bi bi-check-circle-fill text-success"></i>
            </button>

            <button
              class="Buttton"
              v-if="entry.status == true"
              @click.prevent="
                openConfirmationModal(entry.orderId, 'CANCEL_ORDER')
              "
            >
              <i class="bi bi-trash-fill fw-bolder fs-4 text-danger"></i>
            </button>

            <button
              class="Buttton"
              v-if="entry.status == true && entry.productsInOrder > 0"
              @click.prevent="handleEditOrder(entry.orderId)"
            >
              <i class="bi bi-pencil-square fw-bolder fs-4 text-secondary"></i>
            </button>
          </div>
        </div>
      </div>
    </section>
  </div>

  <p v-else>No matches found.</p>

  <Teleport to="body">
    <AssureActionAlertModal
      v-if="showConfirmModal"
      :show="showConfirmModal"
      :titleMessage="confirmModalTitle"
      :subMessage="confirmModalMessage"
      :action="actionType"
      @CANCEL="closeModal"
      @CONFIRM="handleConfirmAction"
    />
  </Teleport>
</template>

<style scoped>
table {
  border: 2px solid #42b983;
  border-radius: 3px;
  background-color: #fff;
  display: table;
  width: 100%;
  table-layout: auto;
}

th {
  background-color: #42b983;
  color: rgba(255, 255, 255, 0.66);
  cursor: pointer;
  user-select: none;
}

td {
  background-color: #f9f9f9;
  border-bottom: 1px solid #42b983;
}

th,
td {
  white-space: nowrap;
  padding: 10px 15px;
  border-bottom: 1px solid #42b983;
}

th.active {
  color: #fff;
}

th.active .arrow {
  opacity: 1;
}

.arrow {
  display: inline-block;
  vertical-align: middle;
  width: 0;
  height: 0;
  margin-left: 5px;
  opacity: 0.66;
}

.arrow.asc {
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
  border-bottom: 4px solid #fff;
}

.arrow.dsc {
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
  border-top: 4px solid #fff;
}

.small-screen-table {
  display: none;
  margin: auto;
  padding: 1rem;
}

.card-section-small-screen {
  border: 2px solid#42b983;
  padding: 1.5rem;
  margin-bottom: 2rem;
  border-radius: 10px;
}

.header-small-screen {
  gap: 2rem;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-bottom: 2rem;
}
.header-small-screen > div {
  background-color: #42b983;
  padding: 0.3rem 0.5rem;
  border: 1px solid #42b983;
  border-radius: 10px;
}

.img-preview {
  max-width: 80px;
  max-height: 80px;
  border-radius: 5px;
  border: 1px solid #00a058;
  margin-top: 10px;
}
.wrapper-table {
  overflow-x: auto;
  display: block;
}
.content-table {
  display: flex;
  justify-content: center;
  overflow-x: auto;
  margin: 0 1rem;
}
.Buttton:hover {
  scale: 115% !important;
}

.Buttton {
  border: 0px;
  background-color: transparent;
}

th,
td {
  min-width: 100px; /* Define um tamanho mínimo para não colapsar */
}
@media (max-width: 800px) {
  table {
    display: none;
  }
  .small-screen-table {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 90%;
    font-size: 0.8rem;
  }
}
</style>
