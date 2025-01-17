<template>
  <div class="card">
    <div class="card-header fw-bold">#{{ props.id }}</div>
    <div
      class="card-body d-flex justify-content-center text-center align-items-center header-comp"
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
        Valor Total: R${{ props.totalValue }}
      </p>
      <p
        v-if="props.updatedAt != null"
        class="card-text col-md-4 col-lg-4 mb-4"
      >
        Mesa:{{ props.updatedAt }}
      </p>
    </div>
    <div class="card-body d-flex flex-wrap justify-content-between body-btn">
      <button
        v-if="props.status === true"
        class="btn btn-danger text-white fw-bold"
      >
        Cancelar
      </button>
      <button
        v-if="props.status === true"
        class="btn btn-success text-white fw-bold"
      >
        Finalizar Pedido
      </button>
      <button
        v-if="props.status === true"
        class="btn add-pedido-btn text-white fw-bold"
      >
        Adicionoar Produto
      </button>
    </div>
  </div>
</template>

<script setup>
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

function cancelOrder(id) {
  console.log(id);
}

function finalizarPedido(id) {}
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
  margin: 0 10rem;
}

.add-pedido-btn {
  background-color: #17a2b8 !important;
}
.add-pedido-btn:hover {
  background-color: rgba(39, 173, 111, 0.63) !important;
}
</style>
