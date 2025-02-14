<template>
  <div class="card border-2" style="width: 18rem">
    <div class="h-100 align-items-center justify-content-center">
      <img
        v-if="props.imageUrl"
        :src="`${api.apiBaseUrl}/image/${props.imageUrl}`"
        class="image-card-own"
        alt="Imagem do Produto"
      />
      <img
        v-else
        src="../../../public/NullImage.png"
        alt=""
        class="image-card-own"
      />
    </div>

    <div class="card-body border-top">
      <h5 class="card-title">{{ props.name }}</h5>
      <div>
        <p>{{ props.description }}</p>
        <p><span class="fw-bold">Cassificação: </span>{{ props.type }}</p>
        <p>
          <span class="fw-bold">Valor: </span>{{ formatCurrency(props.value) }}
        </p>
      </div>
      <div class="d-flex justify-content-center" v-if="props.orderId">
        <button
          class="btn btn-primary fw-bold"
          @click="openOrderModal"
          v-if="props.status == true"
        >
          Adicionar a Ordem
        </button>
        <p v-else class="text-danger fs-5 fw-bold">Produto inativo</p>
      </div>
    </div>
  </div>
  <Teleport to="body">
    <!-- use the modal component, pass in the prop -->
    <ProductIncludeOnOrder
      :show="showOrderModal"
      @close="showOrderModal = false"
      @PRODUCT_INCLUDED_ACTION="handleProductInclusion"
      :productName="props.name"
      :productDescription="props.description"
      :value="props.value"
      :productId="props.id"
      :orderId="props.orderId"
    ></ProductIncludeOnOrder>
  </Teleport>
</template>
<script setup>
import ProductIncludeOnOrder from "../Modals/ProductIncludeOnOrder.vue";
import { formatCurrency } from "@/Util/Currency";
import api from "@/Client/api";
import { ref } from "vue";
const props = defineProps({
  id: {
    type: Number,
    required: true,
  },
  name: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
  type: {
    type: String,
    required: true,
  },
  status: {
    type: Boolean,
    required: true,
  },
  value: {
    type: Number,
    required: true,
  },
  imageUrl: {
    type: [String, null], // Accept String or null
    required: false, // Mark as optional
  },
  orderId: {
    type: [Number, null], // Accept String or null
    required: false, // Mark as optional
  },
});

const emit = defineEmits(["EMIT_iNCLUSION_ALERT"]);

const showOrderModal = ref(false);

function handleProductInclusion(data) {
  showOrderModal.value = false;
  emit("EMIT_iNCLUSION_ALERT", data);
}

function openOrderModal() {
  showOrderModal.value = true;
}
</script>

<style scoped>
.image-card-own {
  width: 100%;
  max-height: 250px;
  border-radius: 12px;
  margin-top: 10px;
}
</style>
