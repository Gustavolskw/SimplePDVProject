import Main from '@/views/Main.vue'
import Pedidos from '@/views/Pedidos.vue'
import Produtos from '@/views/Produtos.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Main,
    },
    {
      path: '/pedidos',
      name: 'pedidos',
      component: Pedidos,
    },
    {
      path: '/produtos',
      name: 'produtos',
      component: Produtos,
    },
  ],
})

export default router
