<template>
  <div class="card" style="width: 18rem">
    <img
      v-if="props.imageUrl !== null"
      :src="`http://localhost:8080/api/image/${props.imageUrl}`"
      class="image-card-own"
      alt="Imagem do Produto"
    />
    <img v-else src="../../../public/NullImage.png" alt="" class="image-card-own" />
    <div class="card-body">
      <h5 class="card-title">#{{ props.id }} {{ props.name }}</h5>
      <div>
        <p>{{ props.description }}</p>
        <p class="fw-bold">{{ props.type }}</p>
        <p>{{ formatCurrency(props.value) }}</p>
      </div>
      <a href="#" class="btn btn-primary">Go somewhere</a>
    </div>
  </div>
</template>
<script setup>
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
  value: {
    type: Number,
    required: true,
  },
  imageUrl: {
    type: String,
    required: true,
  },
});

const imgFile = props.image ? `http://localhost:8080/api/image/${props.image}` : "";
console.log(props.imageUrl);
function formatCurrency(value) {
  return new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(value);
}
</script>

<style scoped>
.image-card-own {
  width: 100%;
  height: 250px;
}
</style>
