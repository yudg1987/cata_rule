// 设置当前用户信息
export const setUserInfo= function ({ commit }, userInfo) {
  commit("SET_USER_INFO", userInfo);
}
// 设置当前案件类型
export const setCaseType= function ({ commit }, caseType) {
  commit("SET_CASE_TYPE", caseType);
}
// 设置当前正副卷
export const setVolume= function ({ commit }, volume) {
  commit("SET_VOLUME", volume);
}
// 设置案件列表
export const setCaseTypeList= function ({ commit }, caseTypeList) {
  commit("SET_CASE_TYPE_LIST", caseTypeList);
}
// 设置卷列表
export const setVolumeList= function ({ commit }, volumeList) {
  commit("SET_VOLUME_LIST", volumeList);
}