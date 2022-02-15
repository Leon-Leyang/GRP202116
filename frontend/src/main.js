import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router/index.js'
import axios from 'axios'

Vue.prototype.$axios = axios

Vue.config.productionTip = false
// import axios from 'axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
// import moment from "moment";
import Vuex from 'vuex'
import http from './api/config'  //axios
import './mock'   // mockjs


Vue.use(Vuex)


// Vue.prototype.$moment = moment;


Vue.use(ElementUI);
Vue.prototype.$http = http

// Vue.prototype.$axios = axios;

Vue.config.productionTip = false;

new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount('#app')
