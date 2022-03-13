<template>
  <div class="main-content">
    <navHeader class="nav-head" @changeList="changeList" />
    <div class="show-Content">
      <materialList
        class="material-list"
        :materialListData="materialListData"
        :activeFile="activeFile"
        @activeFileChange="activeFileChange" />
      <editContent
        ref="editContent"
        class="edit-content"
        :activeFile="activeFile"
        @changeList="changeList"
        @updataMenuFile="activeFileChange" />
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import { rulesResource } from "@/axios/index"
import navHeader from "@/components/navHeader.vue";
import materialList from "@/components/materialList.vue"
import editContent from "@/components/editContent.vue"
import { mapGetters, mapActions } from "vuex";

export default {
  name: "Main",
  components: {
    navHeader,
    materialList,
    editContent
  },
  data() {
    return {
      // 材料目录列表
      materialListData: [],
      // 材料选中项
      activeFile: ""
    }
  },
  methods: {
    ...mapActions([
      "setUserInfo"
    ]),
    // 更新材料列表
    changeList(caseTypeValue, volumeValue) {
      this.activeFile = "";
      this.getCatalog(caseTypeValue, volumeValue)
    },
    // 切换材料文件
    activeFileChange(item) {
      if(!item || !item.catalogName) {
        this.$refs.editContent.evidenceStr = '';
        this.$refs.editContent.$refs.editorContent.innerHTML = '';
        return
      }
      this.activeFile = item.catalogName;
      this.$refs.editContent.getEditorContent(this.caseTypeValue, this.volumeValue, item.catalogName, true);
    },
    // 获取材料目录
    async getCatalog(caseTypeValue, volumeValue) {
      let request = {
        caseTypeCode: caseTypeValue,
        keyword: "",
        volumn: volumeValue
      }
      let res = await rulesResource.getCatalog(request)
      if (res.code === "0") {
        this.materialListData = res.data
        this.activeFileChange(this.materialListData[0])
      } else {
        this.$message.warning(res.msg)
      }
    }
  },
  created() {
  },
  computed: {
    ...mapGetters({
      caseTypeValue: "getCaseType",
      volumeValue:"getVolume"
    })
  }
}
</script>
<style lang="scss" scoped>
  .main-content {
    width: 100%;
    height: 100%;
    background-color: #fff;
    .nav-head {
      width: 100%;
      height: 60px;
      line-height: 60px;
    }
    .show-Content {
      width: 100%;
      height: calc(100% - 60px);
      .material-list {
        float: left;
        width: 270px;
        height: 100%;
      }
      .edit-content {
        float: left;
        width: calc(100% - 270px);
        height: 100%;
      }
    }
  }
</style>
