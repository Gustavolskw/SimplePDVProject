<template>
  <form class="row g-3 justify-content-center">
    <div class="col-md-8">
      <label for="productTypeName" class="form-label">Tipo de Produto</label>
      <input
        type="text"
        class="form-control"
        id="productTypeName"
        v-model="name"
        required
      />
      <div class="fw-bold text-danger">{{ nameValidation }}</div>
    </div>
  </form>
  <div class="modal-footer d-flex justify-content-center gap-5 mt-5">
    <button
      class="btn btn-success text-dark fw-bold px-3 py-2"
      @click.prevent="handleProductTypeCreation"
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
</template>

<script setup>
import { ref } from "vue";

const emit = defineEmits(["PRODUCT_CREATE", "CLOSE"]);

const name = ref();

const nameValidation = ref("");

function closeModal() {
  resetForm();
  emit("CLOSE");
}

const resetForm = () => {
  name.value = "";
};

// **Correção para evitar erro de trim() em valores indefinidos**
const validateFormInputs = () => {
  let isValid = true;

  // Valida o nome do produto
  if (!name.value || !name.value.trim()) {
    nameValidation.value = "O nome do tipo de produto é obrigatório.";
    isValid = false;
  } else {
    nameValidation.value = "";
  }
  return isValid;
};

const handleProductTypeCreation = () => {
  if (validateFormInputs()) {
    emit("PRODUCT_CREATE", {
      name: name.value,
    });
  }
};
</script>
