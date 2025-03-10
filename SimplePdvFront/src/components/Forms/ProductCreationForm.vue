<template>
  <form class="row g-3" enctype="multipart/form-data">
    <div class="col-md-5">
      <label for="productName" class="form-label">Produto</label>
      <input
        type="text"
        class="form-control"
        id="productName"
        v-model="name"
        required
      />
      <div class="fw-bold text-danger">{{ nameValidation }}</div>
    </div>

    <div class="col-md-3">
      <label for="productValue" class="form-label">Valor</label>
      <div class="input-group has-validation">
        <span class="input-group-text" id="inputGroupPrepend">R$</span>
        <input
          type="text"
          class="form-control"
          id="productValue"
          v-model="value"
          required
        />
        <div class="fw-bold text-danger">{{ valueValidation }}</div>
      </div>
    </div>

    <div class="col-md-4">
      <label for="productType" class="form-label">Tipo</label>
      <select id="productType" v-model="selectedType" class="form-control">
        <option value="" selected>Escolha um Tipo</option>
        <option
          v-for="type in productTypesList"
          :key="type.id"
          :value="type.id"
        >
          {{ type.name }}
        </option>
      </select>
      <div class="fw-bold text-danger">{{ typeValidation }}</div>
    </div>

    <div class="col-md-12">
      <label for="productDesc" class="form-label">Descrição</label>
      <input
        type="text"
        class="form-control"
        id="productDesc"
        v-model="description"
        required
      />
      <div class="fw-bold text-danger">{{ descValidation }}</div>
    </div>

    <div class="col-md-12 container-input-image">
      <div>
        <label for="productDesc" class="form-label">Imagem</label>
        <div class="d-flex">
          <input
            class="form-control w-100"
            id="productDesc"
            ref="image"
            type="file"
            @change="handleFileUpload"
            required
          />
          <button
            v-if="imageUrl && !getProductUrl"
            @click.prevent="handleCleanImageInput"
            class="btn btn-danger ms-2"
          >
            X
          </button>
        </div>

        <div class="fw-bold text-danger">{{ imageValidation }}</div>
      </div>
      <!-- Exibe nova imagem selecionada -->
      <div v-if="imageUrl && !getProductUrl" class="img-container">
        <img :src="imageUrl" alt="Preview" class="img-preview" />
      </div>
    </div>
  </form>

  <div class="modal-footer d-flex justify-content-center gap-5 mt-5">
    <button
      class="btn btn-success text-dark fw-bold px-3 py-2"
      @click.prevent="handleProductCreation"
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
import { onMounted, ref } from "vue";
import api from "@/Client/api";
import { prodcutTypeServcie } from "@/services/productTypeService";

const emit = defineEmits(["PRODUCT_CREATE", "CLOSE"]);

const name = ref();
const value = ref(0.0);
const selectedType = ref("");
const description = ref();
const getProductUrl = ref();

const image = ref();
const imageUrl = ref();
const productTypesList = ref([]);

const nameValidation = ref("");
const valueValidation = ref("");
const typeValidation = ref("");
const descValidation = ref("");
const imageValidation = ref();

// Limpa a imagem do input e reseta a visualização
function handleCleanImageInput() {
  if (image.value) {
    image.value.value = ""; // Reseta o input file corretamente
  }
  imageUrl.value = null;
  getProductUrl.value = props.product.imageUrl
    ? `${api.apiBaseUrl}/image/${props.product.imageUrl}`
    : null;
}

// Corrige onMounted
onMounted(() => {
  productTypes();
});

function closeModal() {
  resetForm();
  emit("CLOSE");
}

const resetForm = () => {
  name.value = "";
  value.value = null;
  selectedType.value = null;
  description.value = "";
  getProductUrl.value = null;
  image.value.value = "";
};

const handleFileUpload = () => {
  getProductUrl.value = null;
  const file = image.value.files[0];

  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      imageUrl.value = e.target.result; // Atualiza o src da imagem para exibição
    };
    reader.readAsDataURL(file); // Converte o arquivo em uma URL para ser exibida
  }
};

// Busca os tipos de produtos
const productTypes = async () => {
  try {
    const response = await prodcutTypeServcie.getProductTypes();
    if (response.status === 200) {
      productTypesList.value = response.data.data;
    }
  } catch (error) {
    console.error("Erro ao buscar tipos de produto:", error);
  }
};

// **Correção para evitar erro de trim() em valores indefinidos**
const validateFormInputs = () => {
  let isValid = true;

  // Valida o nome do produto
  if (!name.value || !name.value.trim()) {
    nameValidation.value = "O nome do produto é obrigatório.";
    isValid = false;
  } else {
    nameValidation.value = "";
  }

  // Valida a descrição do produto
  if (!description.value || !description.value.trim()) {
    descValidation.value = "A descrição é obrigatória.";
    isValid = false;
  } else {
    descValidation.value = "";
  }

  // Valida o tipo do produto
  if (!selectedType.value) {
    typeValidation.value = "Selecione um tipo de produto.";
    isValid = false;
  } else {
    typeValidation.value = "";
  }

  // Valida o valor do produto
  const parsedValue = parseFloat(value.value);
  if (!value.value || isNaN(parsedValue) || parsedValue <= 0) {
    valueValidation.value = "Informe um valor válido maior que zero.";
    isValid = false;
  } else {
    valueValidation.value = "";
  }

  return isValid;
};

const handleProductCreation = () => {
  if (validateFormInputs()) {
    emit("PRODUCT_CREATE", {
      name: name.value,
      description: description.value,
      type: selectedType.value,
      value: value.value,
      status: true,
      image: image.value ? image.value : null,
    });
  }
};
</script>

<style scoped>
.img-preview {
  max-width: 150px;
  max-height: 150px;
  border-radius: 5px;
  margin-top: 10px;
}

.container-input-image {
  display: flex;
  justify-content: space-evenly;
}

@media (max-width: 1000px) {
  .container-input-image {
    justify-content: center;
    flex-direction: column;
  }
  .img-container {
    margin: 0 auto;
  }
}
</style>
