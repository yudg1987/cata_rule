import axios from "axios";
import { Message, Loading } from "element-ui";
/*
 *  这里写一个读取配置文件的方法，原生ajax，同步读取文件
 */
const getConfigration = function () {
  let ajax = null;
  //判断ajax对浏览器支持情况
  if (window.XMLHttpRequest) {
    ajax = new XMLHttpRequest();
  }
  else if (window.ActiveXObject) {
    ajax = new window.ActiveXObject();
  }
  else {
    console.log("您的浏览器不支持ajax");
  }
  if (ajax != null) {
    ajax.open("GET", "/config.json?t=" + Date.now(), false)
    ajax.send(null);
    ajax.onreadystatechange = function () {

    };
    return JSON.parse(ajax.responseText);
  }
};

export const config = getConfigration();

//let baseUrl = "http://" + config.baseUrl + "/evidence-manage-web/";
let baseUrl = "http://" + config.baseUrl + "/eds-catarule/";
//判断当前的运行环境，使用devServer代理后台服务;
/* if (process && process.env.NODE_ENV === "development") {
  baseUrl = "/api";
} */

//axios全局配置项
axios.defaults.baseURL = baseUrl;
//axios.defaults.timeout = 500;
//axios.defaults.headers.common["token"] = "token";
axios.defaults.withCredentials = false;

// axios拦截器
//请求拦截器
axios.interceptors.request.use(config => {
  // 在发送请求之前做些什么
  if (config.options) {
    if (!config.options.loading) {
      config.options.timeOutFn = setTimeout(() => {
        if (document.querySelector(config.options.el)) {
          config.options.loading = Loading.service({
            target: config.options.el,
            lock: true,
            text: "加载中..."
          });
        }
      }, config.options.timeout);
    }
  }
  return config;
}, error => {
  // 对请求错误做些什么
  return Promise.reject(error);
});

//添加响应拦截器
axios.interceptors.response.use(response => {
  // 对响应数据做点什么
  if (response.config.options && response.config.options.el) {
    response.config.options.timeOutFn && clearTimeout(response.config.options.timeOutFn);

    if (response.config.options.loading) {
      response.config.options.loading.close();
      response.config.options.loading = null;
    }
  }
  if(response.data.code == "0" || response.config.options && response.config.options.directReturn) {
  //if (response.data.flag || (!response.data.flag && response.data.level && response.data.level == "3")) {
    return Promise.resolve(response.data);
  }
  else {
    if(response.data.message) {
      Message.error(response.data.message);
    }
    else if(response.data.msg) {
      Message.error(response.data.msg);
    }
    else {
      Message.error("后台服务异常，请联系管理员！");
    }
    // 如果判断用户未登录，则跳转到登录页面
    // if(response.data.level == "0") {
    //   //路由跳转
    //   router.push({ name: "Login" });
    // }
    return Promise.reject(response);
  }
}, error => {
  // 对响应错误做点什么
  if (error.config.options && error.config.options.el) {
    error.config.options.timeOutFn && clearTimeout(error.config.options.timeOutFn);
    if (error.config.options.loading) {
      error.config.options.loading.close();
      error.config.options.loading = null;
    }
  }
  let errorMsg = "后台服务异常，请联系管理员！";
  Message.error(errorMsg);
  return Promise.reject(error);
});

//写一个函数来通过接口地址配置项分发请求
const getResources = actions => {
  let resource = {};
  for (let key in actions) {
    resource[key] = params => {
      let axiosOptions = {
        options: actions[key].options,
        method: actions[key].method
      };
      let regex = new RegExp("\\{[^}]*\\}", "g");
      let getParams = actions[key].url.match(regex);
      let url = actions[key].url;
      if (getParams) {
        getParams.map(item => {
          url = url.replace(item, params[item.substring(1, item.length - 1)]);
          return item;
        });
      }
      else {
        axiosOptions.data = params;
      }
      if(key == "header") {
        /* axiosOptions.headers = {
          "Content-Type": "application/x-www-form-urlencoded"
        } */
        axiosOptions.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      }
      // 文件流相关接口
      if(actions[key] && actions[key].responseType) {
        axiosOptions.responseType = 'blob'
      }
      if(axiosOptions.options && axiosOptions.options.url) {
        axios.defaults.baseURL = "http://" + config.baseUrl + axiosOptions.options.url;
      }
      else {
        axios.defaults.baseURL = baseUrl;
      }
      axiosOptions.url = url;
      return axios(axiosOptions);
    }
  }
  return resource;
};

export { baseUrl };
/*
 * 接口地址统一在此处配置，GET、DELETE请求请将参数名称配置在url中，用{}包住
 */

// 登录相关接口
const rulesActions = {
  // 获取全量案件类型
  getAllCaseType:{ url: "/catalog/rule/getAllCaseType",method:"post"},
  // 获取全量卷
  getAllVolume: { url: "/catalog/rule/getAllVolume", method: "post" },
  // 根据案件类型、卷获取材料目录
  getCatalog: { url: "/catalog/rule/getCatalog", method: "post" },
  // 根据ID查询规则详情
  getCatalogInfo: { url: "/catalog/rule/detail", method: "POST" },
  // 编辑证据名称
  editCatalog: { url: "/catalog/rule/edit", method: "PUT" },
  // 复用至/复用接口共用
  copyTo: {url: "/catalog/rule/copyTo", method: "post"},
  // 导出归目规则EXCEL
  exportRule: { url: "/catalog/rule/exportRule", method: "GET", options: {directReturn: true}},
  // 下载归目规则模板
  exportRuleModel: { url: "/catalog/rule/exportRuleModel", method: "GET", options: {directReturn: true}},
  // 导入归目规则EXCEL
  importRule: { url: "/catalog/rule/importRule", method: "post", options: {directReturn: true}}
}

export const rulesResource = getResources(rulesActions);




