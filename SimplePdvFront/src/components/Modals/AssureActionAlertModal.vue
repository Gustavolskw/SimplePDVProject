<template>
  <Transition name="modal">
    <div v-if="show" class="modal-mask" @click="$emit('CANCEL')">
      <div class="modal-container" @click.stop>
        <div class="modal-header d-flex justify-content-center">
          <h1
            class="text-center"
            :class="{
              'text-danger': action === 0,
              'text-success': action === 1,
            }"
          >
            {{ titleMessage }}
          </h1>
        </div>
        <div class="modal-body">
          <p
            :class="{
              'text-danger': action === 0,
              'text-success': action === 1,
            }"
          >
            {{ subMessage }}
          </p>
        </div>
        <div class="d-flex flex-wrap justify-content-center gap-md-5 gap-3">
          <button class="btn btn-warning" @click="$emit('CANCEL')">
            Cancelar
          </button>
          <button
            class="btn"
            :class="{
              'btn-danger': action === 0,
              'btn-success': action === 1,
            }"
            @click="handleConfirm"
          >
            Confirmar
          </button>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script setup>
defineProps({
  show: Boolean,
  titleMessage: String,
  subMessage: String,
  action: {
    type: Number,
    required: true, // Garante que a prop sempre seja passada
  },
});
const emit = defineEmits(["CANCEL", "CONFIRM"]);

const handleConfirm = () => {
  emit("CONFIRM");
};
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9994 !important; /* Essa parte pode ser ajustada */
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.089);
  display: flex;
  transition: opacity 0.3s ease;
  backdrop-filter: blur(1px);
}

.modal-container {
  width: 30rem;
  margin: auto;
  padding: 20px 40px;
  background-color: #ffffff;
  border-radius: 0; /* Optional: Remove border radius for a clean edge */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  overflow-y: auto; /* Optional: Enable scrolling if content overflows */
  border-radius: 15px;
}

.modal-header h1 {
  margin-top: 0;
  font-weight: bolder;
}

.modal-body {
  margin: 30px 0;
  padding-bottom: 1rem;
}

.modal-body p {
  font-weight: bold;
  text-align: center;
  font-size: 1.2rem;
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

@media (max-width: 600px) {
  .modal-container {
    width: 20rem;
  }
}
</style>
