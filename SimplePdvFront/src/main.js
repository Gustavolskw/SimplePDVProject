import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store"
// Importar estilos do Bootstrap e Bootstrap Icons
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';

// Importar scripts do Bootstrap
import 'bootstrap';

const app = createApp(App)

app.use(router)
app.use(store)

app.mount('#app')
