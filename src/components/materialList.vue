<template>
  <div class="material-content">
    <div class="material-title">
      <span class="icon material-icon"></span>材料目录
    </div>
    <ul class="list-content">
      <li
        v-for="item in materialListData"
        :key="item.id"
        :title="item.catalogName"
        :class="{'active': activeFile === item.catalogName}"
        @click="$emit('activeFileChange', item)">{{item.catalogName}}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "materialList",
  components: {
  },
  props: {
    materialListData: Array,
    activeFile: String,
    loading: Boolean
  }
}
</script>

<style lang="scss" scoped>
.material-content {
  .material-title {
    width: 100%;
    height: 64px;
    display: flex;
    align-items: center;
    border-bottom: 1px solid #E4E6EB;
    border-right: 1px solid #E4E6EB;
    font-size: 18px;
    color: #677FAA;
    padding-left: 24px;
    .material-icon {
      width: 20px;
      height: 18px;
      background-image: url(../assets/menu-icon.png);
      margin-right: 12px;
    }
  }
  .list-content {
    width: 100%;
    height: calc(100% - 99px);
    overflow: auto;
    margin-top: 35px;
    font-size: 16px;
    color: #646B6F;
    box-shadow: 3px 0px 7px 0px rgba(213, 222, 238, 0.18);
    li {
      width: 100%;
      height: 40px;
      line-height: 40px;
      cursor: pointer;
      margin: 2px 0;
      padding: 0px 22px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    li:hover, li.active {
      background-color: #DBECFD;
      color: #1B74E5;
    }
  }
}
</style>
