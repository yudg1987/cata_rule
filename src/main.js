import Vue from "vue"
import App from "./App.vue"
import router from "./router"
import store from "./vuex/store"
import { Button, Select, Option, Input, Dialog, Form, FormItem, CheckboxGroup, Checkbox, Message, Tooltip, Loading, MessageBox } from "element-ui"
import "./styles/reset.css"
import "./styles/base.css"
import "./styles/element.scss"
import clearTabIndex from './utils/clearTabIndex.js' // 删除el-tooltip tabindex

Vue.config.productionTip = false

Vue.prototype.$ELEMENT = { size: "small", zIndex: 3000 }
Vue.prototype.$message = Message
// 按需引入element组件
Vue.use(Button)
Vue.use(Select)
Vue.use(Option)
Vue.use(Input)
Vue.use(Dialog)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(CheckboxGroup)
Vue.use(Checkbox)
Vue.use(Tooltip)
Vue.use(Loading)
Vue.prototype.$confirm = MessageBox.confirm

clearTabIndex.install(Vue)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app")
