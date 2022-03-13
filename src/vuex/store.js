
import Vue from "vue";
import Vuex from "vuex";
import * as actions from "./actions";
import * as getters from "./getters";
Vue.use(Vuex);

const state = {
  //当前用户
  userInfo: {
    realName: "",
    userName: ""
  },
  caseType: "300",
  volume: 1,
  caseTypeList: [],
  volumeList: []
};

// Mutations.
const mutations = {
  /*设置当前用户信息*/
  SET_USER_INFO(state, userInfo) {
    state.userInfo = userInfo;
  },
  /*设置案件类型*/
  SET_CASE_TYPE(state, caseType) {
    state.caseType = caseType;
  },
  /*设置卷类型*/
  SET_VOLUME(state, volume) {
    state.volume = volume;
  },
  /*设置案件列表*/
  SET_CASE_TYPE_LIST(state, caseTypeList) {
    state.caseTypeList = caseTypeList;
  },
  /*设置卷列表*/
  SET_VOLUME_LIST(state, volumeList) {
    state.volumeList = volumeList;
  }
};

export default new Vuex.Store({
  state,
  mutations,
  actions,
  getters
});
