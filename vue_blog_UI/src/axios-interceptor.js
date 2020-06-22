import axios from 'axios'
import Element from 'element-ui'
import router from './router/router'
import store from './store/store'

/**
 * axios 拦截器
 */
axios.defaults.baseURL = "http://localhost"

// 前置拦截
axios.interceptors.request.use(config => config)

// 后置拦截
axios.interceptors.response.use(response => {
  const res = response.data;
  // console.log(res)

  if (res.code === 200) {
    return response;
  } else {

    console.log(response.data)
    Element.Message.error(res.msg);

    return Promise.reject(res.msg)
  }
}, error => {
  // console.log(error.response.data)
  if (error.response.data) {
    error.message = error.response.data.msg || error.response.data.message
  }

  if (error.response.status === 401) {
    store.commit("REMOVE_INFO");
    error.message = '登录失效,请重新登录';
    router.push("/login").then()
  }

  if (error.response.status === 403) {
    error.message = '权限不足，无法访问';
  }

  if (error.message === "该博客不存在" || error.message === "权限不足，无法访问") {
    router.push("/blogs").then()
  }

  Element.Message.error(error.message)
  return Promise.reject(error)
})