<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask" @click="$emit('close')">
      <div class="modal-container" @click.stop>
        <div class="d-flex justify-content-between">
          <div class="modal-header fw-bolder">#</div>
          <button class="border-0 text-black" @click="$emit('close')">
            <i class="bi bi-x-lg fw-bolder fs-4"></i>
          </button>
        </div>

        <div class="row justify-content-center">
          <p class="col-12 text-center">Produto: {{ props.productName }}</p>
          <p class="col-12 text-center">
            Descrição: {{ props.productDescription }}
          </p>
          <p class="col-12 text-center">
            Valor UN: {{ formatCurrency(props.value) }}
          </p>
          <p class="col-12 text-center">
            <span class="fw-bold">Valor Total: </span
            >{{ formatCurrency(props.value * quantity) }}
          </p>
        </div>

        <form class="row justify-content-center align-items-center g-2">
          <!-- Minus Button -->
          <div class="col-auto">
            <button
              type="button"
              class="btn btn-danger"
              @click="decreaseQuantity"
            >
              -
            </button>
          </div>

          <!-- Input Field -->
          <div class="col-auto">
            <input
              type="number"
              class="form-control text-center"
              v-model.number="quantity"
            />
            <p class="text-danger fw-bold" v-if="qtdErrorMessage">
              {{ qtdErrorMessage }}
            </p>
          </div>

          <!-- Plus Button -->
          <div class="col-auto">
            <button
              type="button"
              class="btn btn-success"
              @click="increaseQuantity"
            >
              +
            </button>
          </div>

          <div class="col-8">
            <button
              class="btn btn-outline-success w-100"
              @click.prevent="includeProductOnOrder"
              :disabled="qtdErrorMessage !== null"
            >
              Adicionar ao Pedido
            </button>
          </div>
        </form>
      </div>
    </div>
  </Transition>
</template>

<script setup>
import axiosClient from "@/Client/AxiosClient";
import { formatCurrency } from "@/Util/Currency";
import { ref, watch } from "vue";

const props = defineProps({
  show: Boolean,
  productId: Number,
  productName: String,
  productDescription: String,
  value: Number,
  orderId: Number,
});

const emit = defineEmits(["PRODUCT_INCLUDED_ACTION", "close"]);

const quantity = ref(1);
const qtdErrorMessage = ref(null);

// Observa mudanças na quantidade e valida automaticamente
watch(quantity, (newVal) => {
  if (newVal <= 0 || isNaN(newVal)) {
    qtdErrorMessage.value = "A quantidade deve ser maior que 0";
  } else {
    qtdErrorMessage.value = null;
  }
});

// Funções para aumentar e diminuir a quantidade com validação
function increaseQuantity() {
  quantity.value++;
}

function decreaseQuantity() {
  if (quantity.value > 1) {
    quantity.value--;
  }
}

async function includeProductOnOrder() {
  if (qtdErrorMessage.value) return; // Bloqueia envio se houver erro

  const payload = {
    productId: props.productId,
    quantity: quantity.value,
  };

  try {
    const response = await axiosClient.post(
      `/order/${props.orderId}/include`,
      payload,
      {
        timeout: 2000,
      }
    );
    resetForm();
    emit("PRODUCT_INCLUDED_ACTION", {
      message: response.data.message,
      status: response.status,
    });
  } catch (error) {
    resetForm();
    emit("PRODUCT_INCLUDED_ACTION", {
      message: error.response?.data?.message || "Erro desconhecido",
      status: error.response?.status || 500,
    });
  }
}

function resetForm() {
  quantity.value = 1;
}
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9994 !important; /* Essa parte pode ser ajustada */
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.158);
  display: flex;
  transition: opacity 0.3s ease;
  backdrop-filter: blur(1px);
}

.modal-container {
  width: 500px;
  margin: auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 0; /* Optional: Remove border radius for a clean edge */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  overflow-y: auto; /* Optional: Enable scrolling if content overflows */
}

.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}

.modal-body {
  margin: 20px 0;
}

.modal-default-button {
  float: right;
}

.modal-enter-from {
  opacity: 0;
}

.modal-leave-to {
  opacity: 0;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  -webkit-transform: translateX(20px); /* Slide-in animation from the right */
  transform: translateX(20px);
}
@media (max-width: 450px) {
  .modal-container {
    width: 250px;
  }
}
</style>
