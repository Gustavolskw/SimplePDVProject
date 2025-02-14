<template>
  <div class="card d-flex flex-column">
    <div class="card-header fw-bold">#{{ props.id }}</div>
    <div
      class="card-body d-flex flex-wrap justify-content-center text-center align-items-center header-comp"
    >
      <h5 class="card-title">Consumidor: {{ props.consumerName }}</h5>
      <h5 class="card-title">Guia: {{ props.guideName }}</h5>
    </div>
    <div class="card-body row align-items-center justify-content-center">
      <p class="card-text col-md-4 col-lg-4 mb-4">
        Mesa:{{ props.tableNumber }}
      </p>
      <p class="card-text col-md-4 col-lg-4 mb-4">
        Status:{{ props.status === true ? "Ativo" : "Fechado" }}
      </p>
      <p class="card-text col-md-4 col-lg-4 mb-4">
        Total de Produtos:{{ props.productsInOrder }}
      </p>
      <p class="card-text col-md-4 col-lg-4 mb-4">
        Total de itens:{{ props.itemsQuantity }}
      </p>
      <p class="card-text col-md-4 col-lg-4 mb-4">
        Valor Total:{{ formatCurrency(props.totalValue) }}
      </p>
      <p
        v-if="props.updatedAt != null"
        class="card-text col-md-4 col-lg-4 mb-4"
      >
        Ultima Atualização:{{ props.updatedAt }}
      </p>
      <p
        v-if="props.updatedAt == null"
        class="card-text col-md-4 col-lg-4 mb-4"
      >
        Aberto em:{{ props.createdAt }}
      </p>
    </div>
    <div class="card-body d-flex flex-wrap justify-content-evenly body-btn">
      <button
        @click.prevent="endOrder(props.id)"
        v-if="props.productsInOrder > 0 && props.status === true"
        class="btn btn-success text-white fw-bold"
      >
        Finalizar Pedido
      </button>
      <button
        @click="cancelOrder(props.id)"
        v-if="props.status === true"
        class="btn btn-danger text-white fw-bold"
      >
        Cancelar
      </button>

      <button
        v-if="props.status === true"
        class="btn add-pedido-btn text-white fw-bold"
        @click.prevent="enterInAOrder"
      >
        Adicionar Produto
      </button>

      <button class="btn btn-outline-info fw-bold" @click="openOrderModal">
        Ver Pedido
      </button>
    </div>
  </div>

  <Teleport to="body">
    <!-- use the modal component, pass in the prop -->
    <OrderShowModal
      ref="orderShowModal"
      :show="showOrderModal"
      @close="showOrderModal = false"
    ></OrderShowModal>
  </Teleport>
</template>

<script setup>
import OrderShowModal from "../Modals/OrderShowModal.vue";
import { formatCurrency } from "@/Util/Currency";
import axiosClient from "@/Client/AxiosClient";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

import { ref } from "vue";

const emit = defineEmits(["ORDER_RELOAD"]);

const showOrderModal = ref(false);
const orderShowModal = ref(null);
const store = useStore();
const router = useRouter();

function openOrderModal() {
  showOrderModal.value = true;
  orderShowModal.value.getOrderById(props.id); // Call the method on the child component
}

const props = defineProps({
  id: {
    type: Number,
    required: true,
  },
  tableNumber: {
    type: Number,
    required: true,
  },
  consumerName: {
    type: String,
    required: true,
  },
  guideName: {
    type: String,
    required: true,
  },
  status: {
    type: Boolean,
    required: true,
  },
  totalValue: {
    type: Number,
    required: true,
  },
  productsInOrder: {
    type: Number,
    required: true,
  },
  itemsQuantity: {
    type: Number,
    required: true,
  },
  createdAt: {
    type: String,
    required: true,
  },
  updatedAt: {
    type: String,
  },
});

async function cancelOrder(id) {
  console.log("cancelar" + id);
  try {
    const response = await axiosClient.delete(`/order/${id}/cancel`, null, {
      timeout: 2000,
    });
    emit("ORDER_RELOAD");
    console.log(response.data.message);
  } catch (error) {
    console.error(error);
  }
}

async function endOrder(id) {
  console.log("finalizar" + id);
  try {
    const response = await axiosClient.put(`/order/${id}/close`, null, {
      timeout: 2000,
    });
    emit("ORDER_RELOAD");
    console.log(response.data.message);
  } catch (error) {
    console.log(error.response.data.message);
  }
}

function enterInAOrder() {
  store.dispatch("saveToken", {
    tokenName: "orderToken",
    tokenValue: props.id,
  });
  router.push("/produtos");
}
</script>

<style scoped>
.card {
  border: 2px solid lightgrey !important;
}
.card-text {
  font-size: 1rem;
  text-align: center !important;
}
.header-comp {
  gap: 10rem;
  border-bottom: 1px solid lightgray;
}
.body-btn {
  gap: 2rem;
}

.add-pedido-btn {
  background-color: #17a2b8 !important;
}
.add-pedido-btn:hover {
  background-color: rgba(39, 173, 111, 0.63) !important;
}
@media (max-width: 1000px) {
  .header-comp {
    gap: 2rem;
  }
  .body-btn > button {
    font-size: 0.8rem;
  }
}
</style>
