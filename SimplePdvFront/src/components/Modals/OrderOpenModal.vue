<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-container">
        <div class="modal-header d-flex justify-content-center">
          <h2>Formulário de Ordem</h2>
        </div>
        <form class="row g-3 needs-validation" novalidate>
          <div class="col-md-9">
            <label for="validationCustom01" class="form-label"
              >Nome do consumidor</label
            >
            <input
              type="text"
              class="form-control"
              id="validationCustom01"
              v-model="consumerName"
              required
            />
            <div class="valid-feedback">Looks good!</div>
          </div>
          <div class="col-md-3">
            <label for="validationCustom05" class="form-label">Mesa</label>
            <input
              type="number"
              class="form-control"
              id="validationCustomTable"
              v-model="tableNumber"
              required
            />
            <div class="invalid-feedback">
              Escolha uma Mesa para inciar o pedido!
            </div>
          </div>
          <div class="col-md-3">
            <label for="validationCustom01" class="form-label"
              >Id do Guia</label
            >
            <input
              type="number"
              class="form-control"
              id="validationCustomGuideId"
              v-model="guide"
              @input="handleGuideInputChange"
              @change="resetGuideNameAndRequest"
              required
            />
            <div class="valid-feedback">Id Válido!</div>
          </div>
          <div class="col-md-9">
            <label for="validationCustomUsername" class="form-label"
              >Guia</label
            >
            <div class="input-group has-validation">
              <span class="input-group-text" id="inputGroupPrepend">@</span>
              <input
                type="text"
                class="form-control"
                id="validationCustomUsername"
                aria-describedby="inputGroupPrepend"
                v-model="guideName"
                required
                disabled
              />
              <div class="invalid-feedback">
                Insira um Id válido para esolher seu guia
              </div>
            </div>
          </div>
        </form>
        <div class="modal-footer d-flex justify-content-center gap-5 mt-5">
          <button
            class="btn btn-success text-dark fw-bold px-3 py-2"
            @click.prevent="handleOrderOpen()"
          >
            Adicionar
          </button>
          <button
            class="btn btn-warning text-dark fw-bold px-3 py-2"
            @click="closeModal"
          >
            Cancelar
          </button>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script setup>
import { ref } from "vue";
import axiosClient from "@/Client/AxiosClient";
defineProps({
  show: {
    type: Boolean,
  },
});

// Define emitted events
const emit = defineEmits(["ORDER_OPENED", "close"]);

const consumerName = ref("");
const guide = ref();
const tableNumber = ref();
const guideName = ref();

function getGuide(id) {
  axiosClient
    .get(`/user/${id}`)
    .then(({ data }) => {
      console.log(data);
      guideName.value = data.data.username;
    })
    .catch((error) => {
      console.log(error);
    });
}
function handleGuideInputChange(event) {
  guideName.value = "";
  const id = event.target.value;
  if (id) {
    getGuide(id);
  }
}
function closeModal() {
  cleanFormInputs();
  emit("close");
}
function cleanFormInputs() {
  consumerName.value = "";
  tableNumber.value = null;
  guide.value = null;
  guideName.value = "";
}
async function sendOpenOrderRequest() {
  const payload = {
    consumerName: consumerName.value,
    tableNum: tableNumber.value,
    guide: guide.value,
  };

  console.log(payload);

  try {
    const response = await axiosClient.post("/order", payload, {
      timeout: 2000,
    });
    console.log(response.data.data.orderId);
    emit("ORDER_OPENED", { orderId: response.data.data.orderId }); // Emit event only after a successful response
    cleanFormInputs();
  } catch (error) {
    console.error("Failed to open order:", error);
  }
}

function handleOrderOpen() {
  sendOpenOrderRequest();
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
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  transition: opacity 0.3s ease;
  backdrop-filter: blur(1px);
}

.modal-container {
  width: 500px;
  margin: auto;
  padding: 20px 30px;
  background-color: #adadad;
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
</style>
