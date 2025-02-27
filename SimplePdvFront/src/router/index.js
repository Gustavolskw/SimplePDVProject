import PainelAdministracao from '@/views/PainelAdministracao.vue'
import AdministracaoPedidos from '@/views/PainelDeAdministracao/AdministracaoPedidos.vue'
import EdicaoPedido from '@/views/PainelDeAdministracao/Pedido/EdicaoPedido.vue'
import AdministracaoProdutos from '@/views/PainelDeAdministracao/AdministracaoProdutos.vue'
import AdministracaoTipoDeProdutos from '@/views/PainelDeAdministracao/AdministracaoTipoDeProdutos.vue'
import AdministracaoUsuarios from '@/views/PainelDeAdministracao/AdministracaoUsuarios.vue'
import Pedidos from '@/views/Pedidos.vue'
import Produtos from '@/views/Produtos.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "",
      redirect: "/produtos",
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
    {
      path: '/administracao',
      name: 'administracao',
      component: PainelAdministracao,
      children: [
        {
          path: "/administracao",
          redirect: "/administracao/produtos",
        },
        {
          path: '/administracao/produtos',
          name: 'administracao-produtos',
          component: AdministracaoProdutos,
        },
        {
          path: '/administracao/usuarios',
          name: 'administracao-usuarios',
          component: AdministracaoUsuarios,
        },
        {
          path: '/administracao/tipo-de-produto',
          name: 'administracao-tipo-de-produto',
          component: AdministracaoTipoDeProdutos,
        },
        {
          path: '/administracao/pedidos',
          name: 'administracao-pedidos',
          component: AdministracaoPedidos,
        },
        {
          path: "/administracao/pedidos/:id",
          name: 'Edicao-pedido',
          component: EdicaoPedido,
        }

      ]
    },

  ],
})

export default router
