<template>
  <div class="edit-content">
    <!-- 头部操作区 -->
    <div class="top-operation">
      <span class="top-tip">所含证据如下（证据名称以英文逗号区分）</span>
      <div class="right">
        <el-input
          class="search-input"
          placeholder="输入关键字检索"
          v-model="searchValue"
          maxlength="500"
          @keyup.enter.native="searchChange"
          :disabled="!activeFile">
          <i v-if="searchValue" slot="suffix" class="el-input__icon el-icon-close" @click="cleanSearch()"></i>
          <i slot="suffix" class="icon search-icon" :class="{'disabled': !activeFile}" @click="searchChange"></i>
        </el-input>
        <el-tooltip class="item" effect="light" content="复用规则" placement="bottom" :visible-arrow="false">
          <span class="reuse-icon icon" @click="reuseRules" :class="{'disabled': !activeFile}"></span>
        </el-tooltip>
        <el-tooltip class="item" effect="light" content="复用至" placement="bottom" :visible-arrow="false">
          <span class="copy-icon icon" @click="reuseFiles" :class="{'disabled': !activeFile}"></span>
        </el-tooltip>
      </div>
    </div>
    <!-- 中间编辑区 -->
    <div 
      class="editor-content" 
      :contenteditable="canEditor" 
      v-html="evidenceStr" 
      @blur="editorSave(1)"
      @dblclick="editorFocus"
      ref="editorContent"></div>
    <!-- 底部操作区 -->
    <div class="bottom-operation">
      <div class="right">
        <el-tooltip class="item" effect="light" v-delTabIndex content="导入归目规则" placement="bottom" :visible-arrow="false">
          <div class="import-item">
            <span class="import-icon icon" @click="uploadClick"></span>
            <input type="file" ref="fileImport" name="myfile" @change="uploadExcel" style="display: none" />
          </div>
        </el-tooltip>
        <el-tooltip class="item" v-delTabIndex effect="light" content="导出归目规则" placement="bottom" :visible-arrow="false">
          <span class="share-icon icon" @click="exportRuleClick"></span>
        </el-tooltip>
        <el-tooltip class="item" v-delTabIndex effect="light" content="下载导入模板" placement="bottom" :visible-arrow="false">
          <span class="download-icon icon" @click="exportRuleModelClick"></span>
        </el-tooltip>
      </div>
    </div>
    <!-- 弹框 -->
    <el-dialog title="证据复用" :visible.sync="dialogReuseRules" width="514px" class="evidence-copy" :close-on-click-modal="false">
      <el-form :model="reuseInfo">
        <el-form-item class="form-item" label="案件类型：" label-width="100px">
          <el-select 
            v-model="reuseInfo.caseTypeValue" 
            placeholder="请选择"
            @change="caseTypeChange(1)"
            class="dialog-select"
            :popper-append-to-body="false">
            <el-option
              v-for="(item, index) in caseTypeList"
              :key="item.code + index"
              :label="item.name"
              :value="item.code"
              :title="item.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="form-item" label="正/副卷：" label-width="100px">
          <el-select 
            v-model="reuseInfo.volumeValue" 
            placeholder="请选择"
            @change="volumeChange(1)"
            class="dialog-select"
            :popper-append-to-body="false">
            <el-option
              v-for="item in volumeList"
              :key="item.volumeType"
              :label="item.volumeName"
              :value="item.volumeType"
              :title="item.volumeName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="form-item" label="材料目录：" label-width="100px">
          <el-select 
            v-model="reuseInfo.materialValue" 
            placeholder="请选择"
            class="dialog-select"
            :popper-append-to-body="false">
            <el-option
              v-for="item in reuseInfo.materialOptions"
              :key="item.catalogName"
              :label="item.catalogName"
              :value="item.catalogName"
              :title="item.catalogName">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogReuseRules = false">取消</el-button>
        <el-button type="primary" @click="reuseRuleSure">确定</el-button>
      </div>
    </el-dialog>
    <!-- 弹框 -->
    <el-dialog class="batch-reuse-dialog" title="批量复用" width="930px" :visible.sync="batchReuse" :before-close="cancelBatchReuse" :destroy-on-close="true">
      <div class="batch-reuse-box">
        <div class="batch-reuse-left">
          <div class="left-select">
            <span class="select-name">案件类型：</span>
            <el-select 
              class="case-type dialog-select"
              v-model="batchReuseInfo.caseTypeValue" 
              placeholder="请选择"
              @change="caseTypeChange(2)"
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
              class="case-volume dialog-select"
              v-model="batchReuseInfo.volumeValue" 
              placeholder="请选择"
              @change="volumeChange(2)"
              :popper-append-to-body="false">
              <el-option
                v-for="item in volumeList"
                :key="item.volumeType"
                :label="item.volumeName"
                :value="item.volumeType"
                :title="item.volumeName">
              </el-option>
            </el-select>
          </div>
          <div class="left-input">
            <el-input
              class="search-input"
              placeholder="输入关键字检索"
              v-model="leftSearchValue"
              maxlength="500"
              @keyup.enter.native="handleSearch('left')">
              <i v-if="leftSearchValue" slot="suffix" class="el-input__icon el-icon-close" @click="emptySearch('left')"></i>
              <i slot="suffix" class="icon search-icon" @click="handleSearch('left')"></i>
            </el-input>
          </div>
          <div class="list-box" v-loading="dialogListLoading" ref="leftListBox">
            <el-checkbox-group v-model="leftSelect" @change="handleLeftChange">
              <el-checkbox class="list-check" v-for="item in leftList" :label="item.catalogName" :key="item.catalogName" :title="item.catalogName">{{item.catalogName}}</el-checkbox>
            </el-checkbox-group>
          </div>
        </div>
        <div class="batch-reuse-right">
          <div class="right-select">
            <p>可将归目规则复制到以下目录：</p>
            <el-button size="mini" @click="cancelBatchReuse">取消</el-button>
            <el-button size="mini" type="primary" @click="handleSave">保存</el-button>
          </div>
          <div class="left-input">
            <el-input
              class="search-input"
              placeholder="输入关键字检索"
              v-model="rightSearchValue"
              maxlength="500"
              @keyup.enter.native="handleSearch('right')">
              <i v-if="rightSearchValue" slot="suffix" class="el-input__icon el-icon-close" @click="emptySearch('right')"></i>
              <i slot="suffix" class="icon search-icon" @click="handleSearch('right')"></i>
            </el-input>
          </div>
          <div class="list-box-right">
            <img class="transfer-btn" src="../assets/transfer.png" alt="" @click="transfer">
            <div class="list-box">
              <el-checkbox-group v-model="rightSelect" class="right-list-item">
                <el-checkbox class="list-check" v-for="item in rightList" :label="item.catalogName" :key="item.catalogName" :title="item.catalogName" v-show="item.show">{{item.caseTypeName}}-{{item.volumnName}}-{{item.catalogName}}</el-checkbox>
              </el-checkbox-group>
            </div>
            <div class="list-box-btn">
              <el-button size="mini" @click="handleDelete">删除</el-button>
              <el-button size="mini" @click="handleClean">清空</el-button>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { rulesResource, config } from "@/axios/index"
import { mapGetters, mapActions } from "vuex";
import axios from "axios";
import { formatDate } from "@/utils/util";
import { Loading } from 'element-ui';
export default {
  name: "editContent",
  components: {
  },
  props: {
    evidenceContent: String,
    activeFile: String
  },
  data() {
    return {
      // 搜索内容
      searchValue: "",
      leftSearchValue: "",
      rightSearchValue: "",
      evidenceStr: "",
      // 定时保存定时器
      scheduledSave: null,
      // 控制复用规则弹框显示隐藏
      dialogReuseRules: false,
      // 控制批量复用弹框
      batchReuse: false,
      // 复用规则选项
      reuseInfo: {
        caseTypeValue: "",
        volumeValue: "",
        materialValue: "",
        // 材料目录选项
        materialOptions: []
      },
      // 批量复用选项
      batchReuseInfo: {
        caseTypeValue: "",
        volumeValue: ""
      },
      // 复用框左侧列表
      leftList: [],
      leftSelect: [],
      // 复用框右侧列表
      rightList: [],
      rightSelect: [],
      canEditor: false,
      // 复用弹框加载
      dialogListLoading: false,
      // 原始证据
      folderName: '',
      // 冲突数据
      conflictTip: null
    }
  },
  watch: {
    evidenceContent(val) {
      this.evidenceStr = val
    },
    activeFile() {
      this.searchValue = "";
    }
  },
  methods: {
    // 搜索
    searchChange() {
      if(!this.activeFile) {
        return;
      }
      this.arrangeContent(this.folderName, this.conflictTip)
    },
    // 清空搜索
    cleanSearch(content) {
      this.searchValue = "";
      this.arrangeContent(this.folderName, this.conflictTip)
    },
    // 批量复用搜索
    handleSearch(str) {
      if (str === "left") {
        this.leftSelect = []
        this.getCatalog(this.batchReuseInfo.caseTypeValue, this.batchReuseInfo.volumeValue, this.leftSearchValue, 1)
      } else {
        this.rightList.forEach(item => {
          if(!this.rightSearchValue.trim()) {
            item.show = true;
          }
          else {
            item.show = item.catalogName.indexOf(this.rightSearchValue) >= 0;
          }
        })
        this.$forceUpdate();
      }
    },
    // 批量复用清空搜索
    emptySearch(str) {
      if (str == "left") {
        this.leftSearchValue = ""
        this.getCatalog(this.batchReuseInfo.caseTypeValue, this.batchReuseInfo.volumeValue, this.leftSearchValue, 1)
      } else {
        this.rightSearchValue = ""
        this.rightList.forEach(item => {
          if(!this.rightSearchValue.trim()) {
            item.show = true;
          }
          else {
            item.show = item.catalogName.indexOf(this.rightSearchValue) >= 0;
          }
        })
        this.$forceUpdate();
      }
    },
    // 获取材料目录
    async getCatalog(caseTypeValue, volumeValue, keyword, num) {
      if(num === 1) {
        this.dialogListLoading = true;
        this.$refs.leftListBox && (this.$refs.leftListBox.scrollTop = 0);
      }
      let request = {
        caseTypeCode: caseTypeValue,
        keyword: keyword,
        volumn: volumeValue
      }
      let res = await rulesResource.getCatalog(request)
      if (res.code === "0") {
        if (num === 1) {
          this.leftList = res.data
        } else {
          this.reuseInfo.materialOptions = res.data || [];
          this.reuseInfo.materialValue = res.data && res.data.length > 0 ? res.data[0].catalogName : '';
        }
      } else {
        this.$message.warning(res.msg)
      }
      this.dialogListLoading = false;
    },
    // 选取批量复用的目录
    transfer() {
      if (this.leftSelect.length > 0) {
        this.leftSelect.forEach(val => {
          this.leftList.forEach(item => {
            if (val === item.catalogName) {
              if (!this.rightList.some((obj => { return obj.catalogName === item.catalogName }))) {
                item.show = !this.rightSearchValue.trim() || item.catalogName.indexOf(this.rightSearchValue) >= 0;
                this.rightList.push(item)
              }
            }
          })
        })
      }
    },
    // 删除选中项
    handleDelete() {
      this.$confirm('此操作将删除已选目录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        closeOnClickModal: false
      }).then(() => {
        let arr = []
        this.rightList.forEach(item => {
          if (this.rightSelect.indexOf(item.catalogName) == -1) {
            arr.push(item)
          }
        })
        this.rightList = arr
        this.rightSelect = []
      }).catch(() => {

      });
    },
    // 清空选中项
    handleClean() {
      this.$confirm('此操作将清空所有已选目录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        closeOnClickModal: false
      }).then(() => {
        this.rightList = []
        this.rightSelect = []
        // 清空右侧搜索条件
        this.rightSearchValue = '';
      }).catch(() => {

      });
    },
    // 保存选中项
    handleSave() {
      if(this.rightList && this.rightList.length > 0) {
        rulesResource
        .copyTo({
          caseTypeCode: this.caseTypeValue,
          volumn: this.volumeValue,
          mapingName: this.activeFile,
          toEdFolders: this.rightList.map(item => {return {
            caseTypeCode: item.caseTypeCode,
            volumn: item.volumn,
            mapingName: item.catalogName,
          }})
        })
        .then(res => {
          this.batchReuse = false;
          this.$message.success('归目规则已复用成功！');
        })
      }
      else {
        this.batchReuse = false;
        this.$message.success('归目规则已复用成功！');
      }
      this.reuseEmpty();
    },
    // 取消复用
    cancelBatchReuse(done) {
      this.$confirm('此操作将放弃所有操作，不做保存, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        closeOnClickModal: false
      }).then(() => {
        this.batchReuse = false;
        this.reuseEmpty();
      }).catch(() => {
      });
    },
    // 编辑区失焦保存
    editorSave(manual, upload) {
      if(!this.activeFile) {
        return;
      }
      this.canEditor = false;
      // 手动保存，计时器重新计时
      if(manual) {
        clearInterval(this.scheduledSave);
        this.scheduledSave = null;
        this.scheduledSave = setInterval(this.editorSave, 60000);
      }
      let paramsEvidenceName = '';
      if(!upload)  {
        // 中文逗号转英文逗号
        let evidenceName = this.$refs.editorContent.innerText.replace(/，/ig,",");
        // 去重
        let str_arr = evidenceName.split(',');
        str_arr = str_arr.filter((item, index) => {return str_arr.slice(index + 1, str_arr.length).indexOf(item) < 0 && item.trim().length > 0});
        if(str_arr.length > 200) {
          this.$message.error('证据名称不能大于200个');
          return;
        }
        paramsEvidenceName = str_arr.join(',')
      }
      rulesResource
      .editCatalog({
        caseTypeCode: this.caseTypeValue,
        volumn: this.volumeValue,
        evidenceName: paramsEvidenceName,
        mapingName: this.activeFile
      }).then(res => {
        if (res.code === "0") {
          this.arrangeContent(res.data.folderName, res.data.conflictTip);
        }
      })
    },
    // 整理编辑区展示样式
    arrangeContent(folderName, conflictTip) {
      this.folderName = folderName;
      this.conflictTip = conflictTip;
      this.evidenceStr = '';
      let evidenceList = folderName ? folderName.split(',') : [];
      evidenceList.forEach((item, index) => {
        let itemValue = item;
        if(this.searchValue) {
          itemValue = itemValue.replace(new RegExp(this.searchValue,"g"), `<span class="search-text">${this.searchValue}</span>`)
        }
        if(conflictTip && conflictTip[item]) {
          this.evidenceStr += '<span  class="high-text" title="' + conflictTip[item] + '">'+ itemValue +'</span>' + ',';
        }
        else {
          this.evidenceStr += itemValue + ',';
        }
      });
      this.$refs.editorContent.innerHTML = this.evidenceStr;
    },
    // 获取编辑区内容
    getEditorContent(caseTypeCode, volumn, mapingName, changeFile) {
      this.evidenceStr = '';
      this.$refs.editorContent.innerHTML = '';
      if(changeFile) {
        this.searchValue = '';
      }
      rulesResource
      .getCatalogInfo({
        caseTypeCode: caseTypeCode,
        volumn: volumn,
        mapingName: mapingName
      }).then(res => {
        if (res.code === "0") {
          this.arrangeContent(res.data.folderName, res.data.conflictTip);
        }
      })
    },
    // 复用规则
    reuseRules() {
      if(!this.activeFile) {
        return;
      }
      this.dialogReuseRules = true;
      this.reuseInfo.caseTypeValue = this.caseTypeList[0].code;
      this.reuseInfo.volumeValue = this.volumeList[0].volumeType;
      this.reuseInfo.materialValue = '';
      this.getCatalog(this.reuseInfo.caseTypeValue, this.reuseInfo.volumeValue, "", 2);
    },
    // 复检规则到其他文件
    reuseFiles() {
      if(!this.activeFile) {
        return;
      }
      this.batchReuse = true
      this.batchReuseInfo.caseTypeValue = this.caseTypeList[0].code
      this.batchReuseInfo.volumeValue = this.volumeList[0].volumeType
      this.getCatalog(this.batchReuseInfo.caseTypeValue, this.batchReuseInfo.volumeValue, this.leftSearchValue, 1)
    },
    reuseEmpty() {
      this.leftSelect = []
      this.rightSelect = []
      this.rightList = []
      this.leftSearchValue = ""
    },
    // 切换案件类型
    caseTypeChange(num) {
      if (num == 1) {
        this.reuseInfo.volumeValue = this.volumeList[0].volumeType;
        this.reuseInfo.materialValue = "";
        this.reuseInfo.materialOptions = [];
        this.getCatalog(this.reuseInfo.caseTypeValue, this.reuseInfo.volumeValue, '', 2)
      } else {
        this.batchReuseInfo.volumeValue = this.volumeList[0].volumeType;
        this.leftSearchValue = "";
        this.leftSelect = [];
        this.getCatalog(this.batchReuseInfo.caseTypeValue, this.batchReuseInfo.volumeValue, '', 1)
      }
    },
    // 切换卷
    volumeChange(num) {
      if (num == 1) {
        this.reuseInfo.materialValue = "";
        // 获取材料目录选项
        this.getCatalog(this.reuseInfo.caseTypeValue, this.reuseInfo.volumeValue);
      } else {
        this.leftSearchValue = "";
        this.leftSelect = [];
        // this.rightSearchValue = "";
        // this.rightSelect = [];
        // this.rightList = [];
        this.getCatalog(this.batchReuseInfo.caseTypeValue, this.batchReuseInfo.volumeValue, '', 1)
      }
    },
    // 左侧列表选择
    handleLeftChange(val) {
      console.log(val,this.leftSelect)
    },
    editorFocus() {
      if(!this.activeFile) {
        return;
      }
      this.canEditor = true;
      this.$nextTick(() => {
        this.$refs.editorContent.focus();
      })
    },
    // 复用规则确认
    reuseRuleSure() {
      if(!this.reuseInfo.materialValue) {
        this.$message.error('未选择目录');
        return
      }
      this.dialogReuseRules = false;
      if(this.activeFile === this.reuseInfo.materialValue) {
        this.$message.success(res.msg);
        return
      }
      rulesResource
      .copyTo({
        caseTypeCode: this.reuseInfo.caseTypeValue,
        volumn: this.reuseInfo.volumeValue,
        mapingName: this.reuseInfo.materialValue,
        toEdFolders: [{
          caseTypeCode: this.caseTypeValue,
          volumn: this.volumeValue,
          mapingName: this.activeFile
        }]
      })
      .then(res => {
        this.$message.success(res.msg);
        // 更新当前编辑区
        this.getEditorContent(this.caseTypeValue, this.volumeValue, this.activeFile);
      })
    },
    // 导入规则点击
    uploadClick() {
      this.$refs.fileImport.click();
    },
    // 上传规则
    uploadExcel(e) {
      let fileName = e.target.files[0];
      if(fileName !== undefined){
        let file_typename = fileName.name.substring(fileName.name.lastIndexOf('.'));
        if (file_typename === '.xlsx') {
          // 上传
          let  form = new FormData(); // FormData 对象
          form.append("file", fileName); 
          let loadingInstance = Loading.service({ fullscreen: true,background: 'rgba(0, 0, 0, 0.8)', text: '导入中，请稍后'  });
          rulesResource
          .importRule(form)
          .then(res => {
            if(res.code === '0') {
              this.$message.success('导入成功');
              // 更新当前目录证据
              this.getEditorContent(this.caseTypeValue, this.volumeValue, this.activeFile);
              loadingInstance.close();
            }
            else {
              this.$message.error('和默认模板不一致，请在模板中编辑');
              loadingInstance.close();
            }
          })
        }
        else {
          this.$message.error('只允许上传excel表格');
        } 
      }
      this.$refs.fileImport.value = "";
    },
    // 导出规则
    exportRuleClick() {
      window.open(
        'http://' + config.baseUrl + '/eds-catarule/catalog/rule/exportRule',
        '_self'
      );
      // let loadingInstance = Loading.service({ fullscreen: true,background: 'rgba(0, 0, 0, 0.8)', text: '导出中，请稍后'  });
      // rulesResource
      // .exportRule()
      // .then(res => {
      //   const content = res;
      //   const blob = new Blob([content]);
      //   let url = window.URL.createObjectURL(blob);
      //   let link = document.createElement("a");
      //   link.style.display = "none";
      //   link.href = url;
      //   link.download = '归目规则-'+ formatDate(new Date(new Date()), 'yyyymmddhm') +'.xlsx';
      //   document.body.appendChild(link);
      //   link.click();
      //   loadingInstance.close();
      // })
    },
    // 下载导入规则
    exportRuleModelClick() {
      window.open(
        'http://' + config.baseUrl + '/eds-catarule/catalog/rule/exportRuleModel',
        '_self'
      );
      // let loadingInstance = Loading.service({ fullscreen: true,background: 'rgba(0, 0, 0, 0.8)',text: '下载中，请稍后' });
      // rulesResource
      // .exportRuleModel()
      // .then(res => {
      //   const content = res;
      //   const blob = new Blob([content]);
      //   let url = window.URL.createObjectURL(blob);
      //   let link = document.createElement("a");
      //   link.style.display = "none";
      //   link.href = url;
      //   link.download = '诉讼卷宗材料目录模板.xlsx';
      //   document.body.appendChild(link);
      //   link.click();
      //   loadingInstance.close();
      // })
    }
  },
  mounted() {
    this.scheduledSave = setInterval(this.editorSave, 60000);
  },
  beforeDestroy() {
    clearInterval(this.scheduledSave);
    this.scheduledSave = null;
  },
  computed: {
    ...mapGetters({
      caseTypeList: "getCaseTypeList",
      volumeList: "getVolumeList",
      caseTypeValue: "getCaseType",
      volumeValue:"getVolume"
    })
  }
}
</script>

<style lang="scss" scoped>
.edit-content {
  background-color: #EEF4FF;
  .right {
    float: right;
  }
  .top-operation{
    width: 100%;
    height: 64px;
    line-height: 64px;
    box-shadow: 0px 3px 7px 0px rgba(213, 222, 238, 0.18);
    padding: 0px 30px;
    background-color: #fff;
    .top-tip {
      color: #677FAA;
      font-size: 16px;
    }
    .search-input {
      width: 216px;
      .search-icon {
        width: 20px;
        height: 20px;
        background-image: url(../assets/search-icon.png);
        vertical-align: text-top;
        margin-left: 0px;
      }
      .search-icon.disabled {
        cursor: not-allowed;
      }
    }
    .icon {
      cursor: pointer;
      margin-left: 30px;
    }
    .reuse-icon {
      width: 24px;
      height: 28px;
      background-image: url(../assets/copy-icon.png);
    }
    .reuse-icon.disabled {
      cursor: not-allowed;
    }
    .copy-icon {
      width: 24px;
      height: 28px;
      background-image: url(../assets/recovery-icon.png);
    }
    .copy-icon.disabled {
      cursor: not-allowed;
    }
  }
  .editor-content {
    width: calc(100% - 60px);
    height: calc(100% - 180px);
    margin: 30px;
    padding: 30px;
    background-color: #fff;
    font-size: 18px;
    color: #5E666B;
    overflow: auto;
    word-break: break-all;
    line-height: 24px;
    /deep/.high-text{
      // color: #EB3E2B;
      color: #1B74E5;
    }
    /deep/.search-text{
      color: #EB3E2B;
      // color: #E97D00;
    }
  }
  .bottom-operation {
    width: 100%;
    height: 56px;
    line-height: 56px;
    background-color: #fff;
    .icon {
      cursor: pointer;
      height: 24px;
      margin-right: 30px;
    }
    .import-item {
      display: inline-block;
      width: 24px;
      height: 24px;
      margin-right: 30px;
      // position: relative;
      vertical-align: middle;
      .import-icon {
        width: 24px;
        background-image: url(../assets/import-icon.png);
        // z-index: 99;
        // position: absolute;
        // background-color: #fff;
        // pointer-events: none;
        vertical-align: top;
      }
      // .import-input {
      //   display: inline-block;
      //   width: 100%;
      //   height: 100%;
      //   position: absolute;
      //   z-index: 1;
      // }
    }
    .share-icon {
      width: 23px;
      background-image: url(../assets/share-icon.png);
    }
    .download-icon {
      width: 24px;
      background-image: url(../assets/download-icon.png);
    }
  }
  .evidence-copy .dialog-select, .evidence-copy .dialog-select /deep/.el-select-dropdown {
    width: 176px;
  }
  .batch-reuse-box{
    display: flex;
    .batch-reuse-left{
      width: 450px;
      margin-right: 30px;
      padding: 25px;
      box-shadow: 0 0 40px 10px rgba(0,0,0,0.1);
    }
    .batch-reuse-right{
      width: 450px;
      padding: 25px;
    }
    .case-type{
      width: 152px;
      margin-right: 20px;
    }
    .case-type.dialog-select /deep/.el-select-dropdown {
      width: 152px;
      margin-top: -1px;
    }
    .case-volume{
      width: 84px;
    }
    .case-volume.dialog-select /deep/.el-select-dropdown {
      width: 84px;
      margin-top: -1px;
    }
    .select-name{
      font-size: 16px;
      color: #71777b;
    }
    .left-input{
      margin: 16px 0;
      /deep/.el-input__suffix{
        line-height: 32px;
      }
      .search-icon {
        width: 20px;
        height: 20px;
        background-image: url(../assets/search-icon.png);
        vertical-align: text-top;
        cursor: pointer;
      }
    }
    .list-box{
      height: 352px;
      border: 1px solid #CCD7ED;
      padding: 12px 24px;
      overflow: auto;
    }
    .list-check{
      display: flex;
      align-items: center;
      margin-top: 12px;
      margin-right: 0;
      /deep/.el-checkbox__label{
        height: 28px;
        line-height: 28px;
        padding: 0 7px;
        margin-left: 12px;
        display: block;
        flex: 1;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        color: #646B6F;
        font-size: 16px;
      }
      /deep/.el-checkbox__input.is-checked + .el-checkbox__label{
        background: #DBECFD;
        color: #646B6F;
      }
      /deep/.el-checkbox__inner{
        display: block;
        width: 20px;
        height: 20px;
        &::after{
          width: 5px;
          height: 10px;
          left: 5px;
          top: 2px;
          border-width: 2px;
        }
      }
    }
    .list-check:first-child{
      margin-top: 0;
    }
    .list-box-right{
      position: relative;
      // .list-box{
      //   padding-bottom: 50px;
      // }
      .list-box-btn{
        position: absolute;
        bottom: 12px;
        right: 12px;
        background: #fff;
      }
      .transfer-btn{
        position: absolute;
        bottom: 167px;
        left: -38px;
        cursor: pointer;
      }
      .list-box {
        padding: 0px;
        .right-list-item {
          height: 305px;
          overflow: auto;
          padding: 12px 24px;
        }
      }
    }
    .right-select{
      display: flex;
      padding: 2px 0;
      p{
        width: 278px;
        font-size: 16px;
        line-height: 28px;
        color: #71777b;
      }
    }
  }
  .batch-reuse-dialog{
    /deep/.el-dialog__body{
      padding: 0;
    }
    /deep/.el-button--default{
      border-color: #1B74E5;
      width: 64px;
      font-size: 14px;
      color: #1B74E5;
    }
    /deep/.el-button--primary{
      width: 64px;
      font-size: 14px;
      background: #1B74E5;
    }
  }
  /deep/.el-dialog__header{
    padding: 12px 20px;
    background: #1B74E5;
  }
  /deep/.el-dialog__title{
    color: #ffffff;
  }
  /deep/.el-dialog__headerbtn{
    font-size: 24px;
    top: 8px;
    right: 16px;
  }
  /deep/.el-dialog__close{
    color: #fff!important;
  }
  /deep/.el-input--small{
    font-size: 16px;
  }
  /deep/.el-input__inner{
    border-radius: 0;
    border-color: #ccd7ed;
    color: #71777b;
  }
}
</style>