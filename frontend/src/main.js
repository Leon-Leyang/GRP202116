/**
 * Global configuration
 * @link https://vuetifyjs.com/zh-Hans/
 * @link https://vuex.vuejs.org/
 * @link https://element.eleme.cn/
 * @link https://www.npmjs.com/package/vue-codemirror
 */
import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router/index.js'
import axios from 'axios'
import Vuex from 'vuex'
import store from './vuex/store'
import locale from 'element-ui/lib/locale/lang/en'
import VCharts from 'v-charts'
Vue.use(VCharts)

Vue.prototype.$axios = axios
axios.defaults.baseURL = '/api'
axios.defaults.headers.post['Content-Type'] = 'application/json';

Vue.config.productionTip = false
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
// import moment from "moment";
// import http from './api/config'  //axios
// import './mock'   // mockjs

import "./assets/icon/iconfont.css"
// import icon in the topbar

Vue.use(Vuex)

// Vue.forceUpdate()

Vue.use(ElementUI, { locale })


// Vue.prototype.$moment = moment;


// Vue.prototype.$http = http

// Vue.prototype.$axios = axios;

Vue.config.productionTip = false;


//vue codemirror
import VueCodemirror from 'vue-codemirror'

// import base style
import 'codemirror/lib/codemirror.css'

// import more codemirror resource...

// you can set default global options and events when Vue.use
Vue.use(VueCodemirror, /* {
  options: { theme: 'base16-dark', ... },
  events: ['scroll', ...]
} */)


new Vue({
  el:'#app',
  vuetify,
  store,
  router,
  render: h => h(App),
}).$mount('#app')