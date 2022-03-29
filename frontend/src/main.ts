import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.js";
import "bootstrap-icons/font/bootstrap-icons.css";
import { FontAwesomeIcon } from "../src/plugins/font-awesome";
// window.Kakao.init("cd8e8cfb48d45ac31b7c93bbe5eada61");
createApp(App)
.use(store)
.use(router)
.component('fa', FontAwesomeIcon)
.mount("#app");
