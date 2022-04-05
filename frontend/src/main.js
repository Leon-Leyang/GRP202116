import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router/index.js'
import axios from 'axios'
import Vuex from 'vuex'
import store from './vuex/store'

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


// Vue.prototype.$moment = moment;


Vue.use(ElementUI);
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

import uploader from 'vue-simple-uploader'

Vue.use(uploader)

/* eslint-disable no-new */
// new Vue({
//   render(createElement) {
//     return createElement(App)
//   }
// }).$mount('#app')

new Vue({
  el:'#app',
  vuetify,
  store,
  router,
  render: h => h(App),
}).$mount('#app')