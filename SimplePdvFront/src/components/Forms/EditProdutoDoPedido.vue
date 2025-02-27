<template>
  <div class="row">
    <div class="col-12">
      <p class="fw-bold">#{{ productId }}</p>
    </div>
    <div class="col-md-2 col-2" v-if="imageUrl != '' || imageUrl != null">
      <img
        class="img-preview"
        :src="`${api.apiBaseUrl}/image/${imageUrl}`"
        alt="Imagem do produto"
      />
    </div>
    <div class="col-md-2 col-4">
      <span class="fw-bold"> Produto:</span>
      <p class="bg-body-tertiary rounded border-1 px-3 py-2">
        {{ productName }}
      </p>
    </div>
    <div class="col-md-1 col-1">
      <label for="inputCosumerName" class="form-label">Quantidade</label>
      <input
        type="text"
        id="inputCosumerName"
        class="form-control"
        v-model="productQuantity"
      />
      <span class="form-text fw-bold text-danger is-invalid">{{
        errorConsumerName
      }}</span>
    </div>
  </div>
</template>

<script setup>
import api from "@/Client/api";
import { ref, watch } from "vue";
defineProps({
  productId: {
    type: Number,
    required: true,
  },
  productName: {
    type: String,
    required: true,
  },
  imageUrl: {
    type: String,
    required: true,
  },
  value: {
    type: Number,
    required: true,
  },
  quantity: {
    type: Number,
    required: true,
  },
  total: {
    type: Number,
    required: true,
  },
});

const productQuantity = ref();
const errorProductQuantity = ref();
</script>

<style scoped>
.img-preview {
  max-width: 80px;
  max-height: 80px;
  border-radius: 5px;
  margin-top: 10px;
}
@media (max-width: 800px) {
  .img-preview {
    max-width: 120px;
    max-height: 120px;
    border-radius: 5px;
  }
}
</style>
