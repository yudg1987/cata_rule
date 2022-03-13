<template>
  <div class="head-content">
    <span class="icon head-logo"></span>
    <span class="icon title-show" v-show="sysPermition.system_name.show">{{sysPermition.system_name.content}}</span>
    <!-- <ul class="menu-list">
      <li 
        v-for="item in navData" 
        :key="item.key" 
        :class="{'active': item.index === activeIndex}">
        {{item.title}}
      </li>
    </ul> -->
    <div class="right-operate">
      <span class="select-name">案件类型：</span>
      <el-select 
        v-model="caseTypeValue" 
        placeholder="请选择"
        @change="caseTypeChange"
        class="head-select"
        :popper-append-to-body="false">
        <el-option
          v-for="(item, index) in caseTypeList"
          :key="item.code + index"
          :label="item.name"
          :value="item.code"
          :title="item.name">
        </el-option>
      </el-select>
      <span class="select-name">选择卷：</span>
      <el-select 
        v-model="volumeValue" 
        placeholder="请选择"
        @change="volumeChange"
        class="head-select"
        :popper-append-to-body="false">
        <el-option
          v-for="item in volumeList"
          :key="item.volumeType"
          :label="item.volumeName"
          :value="item.volumeType"
          :title="item.name">
        </el-option>
      </el-select>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex"
import bus from "@/utils/event-bus"
import { rulesResource } from "@/axios/index"
export default {
  name: "navHeader",
  components: {
  },
  data () {
    return {
      // 系统设置
      sysPermition: window.SYS_PERMITION,
      // 菜单列表
      navData: [
        {
          index: 1,
          title: "普通视图"
        }
      ],
      // 当前选中
      activeIndex: 1,
      // 案件类型选项
      caseTypeOptions: [
        {
          value: "选项1",
          label: "黄金糕"
        }, 
        {
          value: "选项2",
          label: "双皮奶"
        }
      ],
      // 案件类型
      caseTypeValue: "",
      // 卷选项
      volumeOptions: [
        {
          value: "选项1",
          label: "黄金糕"
        }, 
        {
          value: "选项2",
          label: "双皮奶"
        }
      ],
      // 卷
      volumeValue: ""
    }
  },
  created() {
    this.getAllCaseType().then(() => {
      this.getAllVolume().then(() => {
        this.$emit("changeList", this.caseTypeValue, this.volumeValue)
      })
    }
    )
  },
  methods: {
    ...mapActions([
      "setCaseTypeList",
      "setVolumeList",
      "setCaseType",
      "setVolume"
    ]),
    getAllCaseType() {
      return rulesResource
      .getAllCaseType()
      .then(res => {
        if (res.code === "0") {
          this.setCaseTypeList(res.data)
          // this.caseTypeValue = res.data[0].code
          this.caseTypeValue = '0201'
          this.setCaseType(this.caseTypeValue)
        } else {
          this.$message.warning(res.msg)
        }
      })
    },
    getAllVolume() {
      return rulesResource
      .getAllVolume()
      .then(res => {
        if (res.code === "0") {
          this.setVolumeList(res.data)
          // this.volumeValue = res.data[0].volumeType
          this.volumeValue = 1
          this.setVolume(this.volumeValue)
        } else {
          this.$message.warning(res.msg)
        }
      })
    },
    // 切换案件类型
    caseTypeChange() {
      this.volumeValue = this.volumeList[0].volumeType;
      this.setCaseType(this.caseTypeValue);
      // 获取卷选项
      this.$emit("changeList", this.caseTypeValue, this.volumeValue);
    },
    // 切换卷
    volumeChange() {
      this.setVolume(this.volumeValue);
      // 切换目录
      this.$emit("changeList", this.caseTypeValue, this.volumeValue);
    }
  },
  computed: {
    ...mapGetters({
      caseInfo: "getCaseInfo",
      caseTypeList: "getCaseTypeList",
      volumeList: "getVolumeList"
    })
  }
}
</script>

<style lang="scss" scoped>
.head-content {
  color: #fff;
  background: url(../assets/background.png) right no-repeat,linear-gradient(0deg, #124CC1, #0665DA);
  padding: 0 31px 0px 20px;
  .head-logo {
    width: 28px;
    height: 31px;
    background-image: url(../assets/logo.png);
    margin-right: 13px;
  }
  .title-show {
    font-size: 18px;
  }
  .menu-list {
    display: inline-block;
    margin-left: 20px;
    li {
      width: 80px;
      text-align: center;
    }
    li.active {
      background-color: #3147A3;
    }
  }
  .right-operate {
    float: right;
    font-size: 14px;
    .select-name {
      margin-left: 30px;
    }
    .head-select, .head-select /deep/.el-select-dropdown {
      width: 168px;
    }
  }
}
</style>