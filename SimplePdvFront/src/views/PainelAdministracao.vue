<template>
  <div class="painel-container">
    <!-- Barra Lateral -->
    <PainelAdministracaoNavBar />

    <!-- Área Principal onde os componentes internos serão renderizados -->
    <div class="painel-conteudo">
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup>
import PainelAdministracaoNavBar from "@/components/PainelAdministracaoNavBar.vue";
</script>

<style scoped>
.painel-container {
  display: flex;
  height: 100%;
  width: 100%;
}

.painel-conteudo {
  flex: 1; /* Ocupa o espaço restante */
  padding: 20px;
  margin-left: 5rem;
}

@media (max-width: 1680px) {
  .painel-conteudo {
    padding-left: 8rem;
  }
}

@media (max-width: 1400px) {
  .painel-conteudo {
    padding-left: 6rem;
  }
}

@media (max-width: 600px) {
  .painel-conteudo {
    margin-left: 5rem;
  }
}
</style>
