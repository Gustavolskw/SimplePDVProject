import Main from '@/views/Main.vue'
import Pedidos from '@/views/Pedidos.vue'
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
  ],
})

export default router
