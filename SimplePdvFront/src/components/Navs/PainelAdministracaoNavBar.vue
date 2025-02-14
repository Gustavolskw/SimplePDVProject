<template>
  <div
    class="barra-lateral"
    :class="{
      'barra-lateral-col-on': !colapseNav,
      'barra-lateral-col-off': colapseNav,
    }"
  >
    <div
      class="header-barra mb-2"
      :class="{
        'd-flex justify-content-between': !colapseNav,
      }"
    >
      <h5
        class="text-center fw-bold text-white texto-header"
        v-if="!colapseNav"
      >
        Painel de Administração
      </h5>
      <button
        class="botao-colapse"
        :class="{
          'text-center': colapseNav,
        }"
        @click.prevent="toggleCollapse"
      >
        <i v-if="!colapseNav" class="bi bi-chevron-double-left fw-bold"></i>
        <i v-else class="bi bi-chevron-double-right fw-bold"></i>
      </button>
    </div>

    <div class="d-flex flex-column">
      <ul
        class="navbar-nav"
        :class="{
          'd-flex flex-column gap-3': colapseNav,
        }"
      >
        <li class="nav-item">
          <RouterLink
            :class="{
              active: route.path.startsWith('/administracao/produtos'),
            }"
            :to="`/administracao/produtos`"
          >
            <i
              class="bi bi-boxes"
              data-bs-toggle="tooltip"
              data-bs-title="Produtos"
            ></i>
            <span v-if="!colapseNav">Produtos</span>
          </RouterLink>
        </li>

        <li class="nav-item">
          <RouterLink
            :class="{
              active: route.path.startsWith('/administracao/tipo-de-produto'),
            }"
            :to="`/administracao/tipo-de-produto`"
          >
            <i
              class="bi bi-bookmarks"
              data-bs-toggle="tooltip"
              data-bs-title="Tipo de Produtos"
            ></i>
            <span v-if="!colapseNav">Tipo de Produtos</span>
          </RouterLink>
        </li>

        <li class="nav-item">
          <RouterLink
            :class="{ active: route.path.startsWith('/administracao/pedidos') }"
            :to="`/administracao/pedidos`"
          >
            <i
              class="bi bi-basket"
              data-bs-toggle="tooltip"
              data-bs-title="Pedidos"
            ></i>
            <span v-if="!colapseNav">Pedidos</span>
          </RouterLink>
        </li>

        <li class="nav-item">
          <RouterLink
            :class="{
              active: route.path.startsWith('/administracao/usuarios'),
            }"
            :to="`/administracao/usuarios`"
          >
            <i
              class="bi bi-people"
              data-bs-toggle="tooltip"
              data-bs-title="Usuarios"
            ></i>
            <span v-if="!colapseNav">Usuarios</span>
          </RouterLink>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { RouterLink, useRoute } from "vue-router";
import { ref, onMounted, onUnmounted, nextTick } from "vue";
import * as bootstrap from "bootstrap"; // Ensure Bootstrap is imported

// Function to initialize Bootstrap tooltips
const initializeTooltips = () => {
  nextTick(() => {
    document.querySelectorAll('[data-bs-toggle="tooltip"]').forEach((el) => {
      new bootstrap.Tooltip(el);
    });
  });
};

const route = useRoute();
const colapseNav = ref(false);

const emit = defineEmits(["HANDLE_NAV-BAR"]);

// Function to toggle the sidebar
const toggleCollapse = () => {
  colapseNav.value = !colapseNav.value;
  emit("HANDLE_NAV-BAR", { state: colapseNav.value });
  initializeTooltips(); // Reinitialize tooltips when collapsing
};

// Function to check screen width
const checkScreenSize = () => {
  if (window.innerWidth < 700) {
    colapseNav.value = true;
    emit("HANDLE_NAV-BAR", { state: colapseNav.value });
  } else {
    colapseNav.value = colapseNav.value;
    emit("HANDLE_NAV-BAR", { state: colapseNav.value });
  }
};

// Detect screen size on mount and on resize
onMounted(() => {
  checkScreenSize();
  initializeTooltips(); // Reinitialize tooltips when collapsing
  window.addEventListener("resize", checkScreenSize);
});

// Cleanup event listener on unmount
onUnmounted(() => {
  window.removeEventListener("resize", checkScreenSize);
});
</script>

<style scoped>
.barra-lateral {
  background-color: #006f7ef3;
  padding: 20px;
  transition: all 0.3s ease;
  position: fixed;
  top: 10;
  left: 0;
  transition: transform 0.3s ease-in-out;
}

.barra-lateral-col-on {
  width: 12%;
  height: 100vh;
}

.barra-lateral-col-off {
  width: 4.75rem;
  height: 100vh;
}

.botao-colapse {
  font-size: 1.3rem;
  color: white;
  background: none;
  border: none;
  cursor: pointer;
  transition: transform 0.3s ease-in-out;
}

.botao-colapse:hover {
  color: black;
}

.navbar-nav {
  margin-top: 1rem;
  padding-left: 0;
  gap: 0.5rem;
}

.nav-item {
  display: flex;
  align-items: center;
}

.nav-item a {
  font-weight: bold;
  color: white;
  text-decoration: none;
  padding: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
  width: 100%;
}

.nav-item a i {
  font-size: 1.2rem;
}

.nav-item > a > span {
  font-size: 1.3rem;
}

.nav-item a.active {
  transition: all 0.3s ease-in;
  color: black;
  text-shadow: none;
  background-color: #dadada;
  border-radius: 5px;
}
@media (max-width: 1400px) {
  .barra-lateral-col-on {
    width: 20%;
    height: 100vh;
  }
}

@media (max-width: 1200px) {
  .barra-lateral-col-on {
    width: 20%;
    height: 100vh;
  }
}

@media (max-width: 1000px) {
  .barra-lateral-col-on {
    width: 25%;
    height: 100vh;
  }
}

@media (max-width: 800px) {
  .barra-lateral-col-on {
    width: 30%;
    height: 100vh;
  }
}

@media (max-width: 600px) {
  .barra-lateral {
    position: fixed;
    background-color: #0092a5; /* Slightly darker for better visibility */
    transition: transform 0.3s ease-in-out;
  }

  .barra-lateral-col-on {
    width: 50%;
    height: 100vh;
  }

  .barra-lateral-col-off {
    width: 4.75rem;
    height: 100vh;
  }

  .nav-item a {
    font-size: 1.2rem; /* Larger text for mobile */
  }

  .nav-item a {
    font-weight: bold;
    color: white;
    text-decoration: none;
    padding: 10px;
    display: flex;
    align-items: center;
    gap: 10px;
    width: 4rem;
  }
}

@media (max-width: 450px) {
  .barra-lateral-col-on {
    width: 57%;
    height: 100vh;
  }
  .barra-lateral-col-off {
    width: 4.3rem;
    height: 100vh;
  }
}
</style>
