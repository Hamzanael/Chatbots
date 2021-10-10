import {createApp} from 'vue'
import App from './App.vue'
import router from "./router/router";
import axios from "axios";
import store from "./store/store";

axios.defaults.baseURL = "http://localhost:8080"
let app = createApp(App);
app.use(router)
app.use(store)
app.mount('#app')
