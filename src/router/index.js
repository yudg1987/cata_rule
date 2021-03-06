import Vue from "vue"
import VueRouter from "vue-router"
import Main from "../views/main.vue"

Vue.use(VueRouter)

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main
  }
]

const router = new VueRouter({
  routes
})

export default router
