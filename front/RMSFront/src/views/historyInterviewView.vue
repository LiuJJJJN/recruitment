<template>
  <!-- 整个布局 -->
  <div id="back">
    <el-row justify="space-between">
      <!--筛选按钮-->
      <el-col :span="5">
        <div>
          <el-radio-group v-model="filterRadio"  >
            <el-radio-button :label="1" @click="onRaidoClicked($event)">{{$t('只显示NG')}}</el-radio-button>
            <el-radio-button :label="2" @click="onRaidoClicked($event)">{{$t('只显示OK')}}</el-radio-button>
            
          </el-radio-group>
        </div>

      </el-col>
      <!--搜索栏-->
      <el-col :span="8">
        <el-autocomplete v-model="searchNameCode" :fetch-suggestions="querySearch" :trigger-on-focus="false" clearable
          class="inline-input w-50" :placeholder="$t('按名字查找')" @select="handleSelect" />
        <el-button type="primary" style="margin-left:10px">
          <el-icon>
            <Search />
          </el-icon>
        </el-button>
      </el-col>
    </el-row>
    <!-- 用户信息表 -->
    <el-table ref="multipleTableRef" :data="historyInterviewData.filter((x) => {
        if(!filterRadio)
          return true
        else if(filterRadio==1)
          return x.status==7||x.status==9
        else if(filterRadio==2)
          return x.status==8
      })" style="margin-top:10px"
      v-loading="isDicTypeDataLoading" @selection-change="handleSelectionChange">
      <!--多选列表-->
      <el-table-column type="selection" width="55" />
      <el-table-column label="date" width="120" sortable>
        <template #default="scope">{{ scope.row.date }}</template>
      </el-table-column>
      <el-table-column property="name" label="name" width="120" sortable />
      <el-table-column property="statusCode" label="status" show-overflow-tooltip sortable />
      <!--operations的按钮-->
      <el-table-column :label="$t('动作')" align="right">
        <template #default="scope">
          <el-button v-if="scope.row.status == 7 || scope.row.status == 9" size="small"
            @click="revoke(scope.$index, scope.row)">{{ $t("撤销NG") }}</el-button>
          <el-button v-if="scope.row.status == 8" size="small" type="danger" plain
            @click="revoke(scope.$index, scope.row)">{{ $t("撤销OK") }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页插件 -->
    <el-pagination v-model:currentPage="pageNo" v-model:page-size="pageSize" :page-sizes="[10, 15, 20, 50]"
      :disabled="false" :background="true" layout="total, sizes, prev, pager, next, jumper" :total="totalSize"
      @size-change="handleSizeChange" @current-change="handleCurrentChange" class="page-pagination" />


    <el-dialog v-model="dialogFormVisible" title="Shipping address">
      <el-form :model="form">
        <el-form-item label="Promotion name" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="Zones" :label-width="formLabelWidth">
          <el-select v-model="form.region" placeholder="Please select a zone">
            <el-option label="Zone No.1" value="shanghai" />
            <el-option label="Zone No.2" value="beijing" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible = false">Cancel</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">
            Confirm
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
/**
 * 导入依赖
 */
import i18n from "../locales";
import { ElTable, ElMessage } from 'element-plus';
import { onMounted, reactive, ref } from 'vue';
import $axios from "../plugins/axiosInstance.js";

// 语言切换用法 `t('中文')`
const { t } = i18n.global;


// 表格数据加载标记
const isDicTypeDataLoading = ref(true);

// 表格中内容的接口
interface HistoryData {
  id: string
  date: Date
  name: string
  status: Number
  statusCode: string
}

//筛选器
const filterRadio = ref()

//分页相关:
//页标
const pageNo = ref(1);

//每页记录数
const pageSize = ref(10);

//页标缩小
const small = ref(false);

//页标背景
const background = ref(false);

//页标可选
const disabled = ref(false);

//页标总大小
const totalSize = ref(100);

//表格编号
const multipleTableRef = ref<InstanceType<typeof ElTable>>();

//表格多选参数
const multipleSelection = ref<HistoryData[]>([]);

//用户数据
const historyInterviewData = reactive<HistoryData[]>([]);

//用户原始数据
const historyInterviewOriginalData=reactive<HistoryData[]>([]);

//自动输入补全内容接口
interface RestaurantItem {
  value: string,
  link: string
}

//状态
const searchNameCode = ref('');

//自动补全内容列表
const restaurants = ref<RestaurantItem[]>([]);

// 对话框相关
// 对话框是否显示
const dialogFormVisible = ref(false);

// 对话框内表单宽度
const formLabelWidth = '140px';

// 对话框内表单内容
const form = reactive({
  name: '',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})

/**
 * 显示现在的每页显示记录数量
 * @param val 每页显示记录数量
 */
const handleCurrentChange = (val: number) => {
  console.log(`current page: ${val}`);
}

/**
 * 修改每页显示记录数量
 * @param val 每页显示记录数量
 */
const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`);
}

/**
 * 撤销面试结果函数
 * @param index  用户下标
 * @param row  用户行号
 */
const revoke = (index: number, row: HistoryData) => {
  $axios.post("/history/revokeHistoryInterview", {
    id: row.id
  }).then((resp) => {
    // 撤销成功提示
    ElMessage({
      type: 'success',
      message: resp["msg"]
    });
    // 重新获取表格数据
    loadHisyoryInterviewList();
    // 重新获取数据字典表数据总量
    loadHisyoryInterviewTotalSize();
  });
}


/**
 * 选择记录的函数
 * @param val 被选择的用户列表
 */
const handleSelectionChange = (val: HistoryData[]) => {
  multipleSelection.value = val;
}

/**
 * 搜索自动补全函数
 * @param queryString 搜索参数
 * @param cb 回调用参数
 */
const querySearch = (queryString: string, cb: any) => {
  const results = queryString
    ? restaurants.value.filter(createFilter(queryString))
    : restaurants.value
  // call callback function to return suggestions
  cb(results)
}

/**
 * 创建过滤器
 * @param queryString 需要过滤的参数
 */
const createFilter = (queryString: string) => {
  return (restaurant: RestaurantItem) => {
    return (
      restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
    )
  }
}

/**
 * 载入自动补全的内容
 */
const loadAll = () => {
  return [
    { value: 'vue', link: 'https://github.com/vuejs/vue' },
    { value: 'element', link: 'https://github.com/ElemeFE/element' },
    { value: 'cooking', link: 'https://github.com/ElemeFE/cooking' },
    { value: 'mint-ui', link: 'https://github.com/ElemeFE/mint-ui' },
    { value: 'vuex', link: 'https://github.com/vuejs/vuex' },
    { value: 'vue-router', link: 'https://github.com/vuejs/vue-router' },
    { value: 'babel', link: 'https://github.com/babel/babel' }
  ]
}

/**
 * 选择搜索函数，选择搜索关键字
 * @param item 搜索关键字
 */
const handleSelect = (item: RestaurantItem) => {
  console.log(item);
}


/**
 * 获取历史面试表格数据
 */
const loadHisyoryInterviewList = () => {
  // 开启加载状态
  isDicTypeDataLoading.value = true;
  // 发送请求获取历史面试列表
  $axios.post("/history/getHistoryInterviewByPageCondition", {
    pageNo: (pageNo.value - 1) * pageSize.value,
    pageSize: pageSize.value,
    nameCode: searchNameCode.value
  }).then((resp) => {
    // 清空列表内容
    historyInterviewData.length = 0;
    historyInterviewOriginalData.length=0;
    // 更新历史面试表格数据
    historyInterviewData.push(...resp.data);
    historyInterviewOriginalData.concat(historyInterviewData)
    // 关闭加载动画
    isDicTypeDataLoading.value = false;
  }).catch(() => {
    // 清空列表内容
    historyInterviewData.length = 0;
    historyInterviewOriginalData.length=0;
    // 关闭加载动画
    isDicTypeDataLoading.value = false;
  });
}

/**
 * 获取历史面试数据总量
 */
const loadHisyoryInterviewTotalSize = () => {
  $axios.post("/history/getHistoryInterviewTotalSizeByCondition", {
    nameCode: searchNameCode.value
  }).then((resp) => {
    // 更新历史面试表格数据
    totalSize.value = resp.data;
  });
}

const onRaidoClicked=(event)=>{

  if(event.target.value==filterRadio.value||event.target.value==filterRadio.value){
   filterRadio.value=null
   event.target.checked=false
  }
  
}


/**
 * 在组件挂载完成后执行的方法
 */
onMounted(() => {
  // 获取历史面试表格数据
  loadHisyoryInterviewList();
  // 获取历史面试数据总量
  loadHisyoryInterviewTotalSize();
})
</script>

<style scoped>
/* 整体布局样式 */
#back {
  width: 900px;
  margin: 0 auto;
}

/* 标页码样式 */
.page-pagination {
  margin: 10px;
}
</style>
