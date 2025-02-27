<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask" @click="$emit('close')">
      <div class="modal-container" @click.stop>
        <div class="modal-header d-flex justify-content-center">
          <h2>Adição de Produto</h2>
        </div>
        <ProductTypeCreationForm
          @CLOSE="$emit('close')"
          @PRODUCT_CREATE="handleProductTypeCreation"
        >
        </ProductTypeCreationForm>
      </div>
    </div>
  </Transition>
</template>

<script setup>
import { onMounted } from "vue";
import ProductTypeCreationForm from "@/components/Forms/ProductTypeCreationForm.vue";
import { prodcutTypeServcie } from "@/services/productTypeService";
const props = defineProps({
  show: {
    type: Boolean,
  },
});

onMounted(() => {});

// Define emitted events
const emit = defineEmits(["PRODUCT_TYPE_CREATED", "close"]);

async function sendProductTypeInsert(data) {
  try {
    const response = await prodcutTypeServcie.createProductType(data.name);

    emit("PRODUCT_TYPE_CREATED", {
      status: response.status,
      message: response.data.message,
    });
  } catch (error) {
    console.error("Erro ao atualizar produto:", error);
    emit("PRODUCT_TYPE_CREATED", {
      status: error.status,
      message: error.response.data.message,
    });
  }
}

// Chamada correta
function handleProductTypeCreation(data) {
  sendProductTypeInsert(data);
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
  width: 40rem;
  margin: auto;
  padding: 20px 40px;
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
