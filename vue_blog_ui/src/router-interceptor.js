import router from "./router/router";
import Element from 'element-ui'

/**
 * router 拦截,bug 登陆页面会跳转两次
 */
// 路由判断登录 根据路由配置文件的参数
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requireAuth)) { // 判断该路由是否需要登录权限
    const token = localStorage.getItem("token")
    if (token) {
      next()
    } else {
      Element.Message.warning("您还没有登录");
      next({name: 'Login'})
    }
  } else {
    next()
  }
})