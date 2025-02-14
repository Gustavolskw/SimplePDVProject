<template>
  <div class="painel-container">
    <!-- Barra Lateral -->
    <PainelAdministracaoNavBar @HANDLE_NAV-BAR="handleColapseStateNav" />

    <!-- Área Principal onde os componentes internos serão renderizados -->
    <div
      class="painel-conteudo"
      :class="{
        'colapse-on-painel': navBarState == false,
      }"
    >
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup>
import PainelAdministracaoNavBar from "@/components/Navs/PainelAdministracaoNavBar.vue";
import { ref } from "vue";

const navBarState = ref();

function handleColapseStateNav(data) {
  console.log(data.state);
  navBarState.value = data.state;
}
</script>

<style scoped>
.painel-container {
  display: flex;
  height: 100%;
  width: 100%;
}

.painel-conteudo {
  flex: 1;
  padding: 20px 0;
  padding-left: 8rem;
  width: 100%;
}

.colapse-on-painel {
  padding-left: 17rem;
}

@media (max-width: 1680px) {
  .painel-conteudo {
    padding-left: 6rem;
  }
  .colapse-on-painel {
    padding-left: 15rem;
  }
}

@media (max-width: 1400px) {
  .painel-conteudo {
    padding-left: 5rem;
  }
  .colapse-on-painel {
    padding-left: 16rem;
  }
}

@media (max-width: 1200px) {
  .painel-conteudo {
    padding-left: 4rem;
  }
  .colapse-on-painel {
    padding-left: 12rem;
  }
}

@media (max-width: 800px) {
  .painel-conteudo {
    padding-left: 4rem;
  }
  .colapse-on-painel {
    padding-left: 8rem;
  }
}

@media (max-width: 600px) {
  .painel-conteudo {
    padding-left: 4rem;
  }
  .colapse-on-painel {
    padding-left: 3rem;
  }
}
</style>
