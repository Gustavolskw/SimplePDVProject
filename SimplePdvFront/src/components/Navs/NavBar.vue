<script setup>
import { RouterLink, useRoute } from "vue-router";

const route = useRoute(); // Get the current route
</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
      <RouterLink class="navbar-brand" to="/">
        <i class="bi bi-gem"></i>
      </RouterLink>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <!-- <li class="nav-item">
            <RouterLink
              class="nav-link"
              :class="{ active: route.path === '/' }"
              to="/"
            >
              Home
            </RouterLink>
          </li> -->
          <li class="nav-item">
            <RouterLink
              class="nav-link"
              :class="{ active: route.path === '/produtos' }"
              to="/produtos"
            >
              Produtos
            </RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink
              class="nav-link"
              :class="{ active: route.path === '/pedidos' }"
              to="/pedidos"
            >
              Pedidos
            </RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink
              class="nav-link"
              :class="{ active: route.path.startsWith('/administracao') }"
              to="/administracao"
            >
              Administração
            </RouterLink>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped>
.nav-link {
  font-weight: 600;
  transition: 0.3s ease-in-out;
}
</style>
