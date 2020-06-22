import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import store from './store/store'
import Element from 'element-ui'
import axios from 'axios'
import "element-ui/lib/theme-chalk/index.css"
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

import "./axios-interceptor"
import "./router-interceptor"

Vue.config.productionTip = false

Vue.use(Element)
Vue.use(mavonEditor)
Vue.prototype.$axios = axios

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
