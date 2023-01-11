<template>
  <div id="back">
    <el-tabs type="border-card">
      <el-tab-pane :label="$t('所有')">
        <div class="cardDiv">
          <el-card class="cardRolSpan" shadow="hover" @click="routerTo('interviewOverview',{valueKey:'1'})">
            <div class="cardNum">{{ (mission.allMissions[1] || 0) }}</div>
            <span class="cardSpan" >{{$t("等待分配Reviewer")}}</span>
          </el-card>
          <el-card class="cardRolSpan" shadow="hover" @click="routerTo('interviewOverview',{valueKey:'2'})">
            <div class="cardNum">{{ (mission.allMissions[2] || 0) }}</div>
            <span class="cardSpan">{{$t("等待Review")}}</span>
          </el-card>
          <el-card class="cardRolSpan" shadow="hover" @click="routerTo('interviewOverview',{valueKey:'3'})">
            <div class="cardNum">{{ (mission.allMissions[3] || 0) }}</div>
            <span class="cardSpan">{{$t("等待分配面试官")}}</span>
          </el-card>
          <el-card class="cardRolSpan" shadow="hover" @click="routerTo('interviewOverview',{valueKey:'5'})">
            <div class="cardNum">{{ ((mission.allMissions[4] || 0) + (mission.allMissions[5] || 0)) }}</div>
            <span class="cardSpan">{{$t("等待面试")}}</span>
          </el-card>
          <el-card class="cardRolSpan" shadow="hover" @click="routerTo('interviewOverview',{valueKey:'8'})">
            <div class="cardNum">{{ (mission.allMissions[8] || 0) }}</div>
            <span class="cardSpan">{{'offer'}}</span>
          </el-card>
        </div>
      </el-tab-pane>
      <el-tab-pane :label="$t('我负责的')">
        <div class="cardDiv">
          <el-card class="cardRolSpan" shadow="hover" @click="routerTo('interviewOverview',{valueKey:'1'})">
            <div class="cardNum">{{ (mission.userMissions[1] || 0) }}</div>
            <span class="cardSpan">{{$t("等待分配Reviewer")}}</span>
          </el-card>
          <el-card class="cardRolSpan" shadow="hover" @click="routerTo('interviewOverview',{valueKey:'2'})">
            <div class="cardNum">{{ (mission.userMissions[2] || 0) }}</div>
            <span class="cardSpan">{{$t("等待Review")}}</span>
          </el-card>
          <el-card class="cardRolSpan" shadow="hover" @click="routerTo('interviewOverview',{valueKey:'3'})">
            <div class="cardNum">{{ (mission.userMissions[3] || 0) }}</div>
            <span class="cardSpan">{{$t("等待分配面试官")}}</span>
          </el-card>
          <el-card class="cardRolSpan" shadow="hover" @click="routerTo('interviewOverview',{valueKey:'5'})">
            <div class="cardNum">{{ (mission.userMissions[4] || 0)+(mission.userMissions[5] || 0) }}</div>
            <span class="cardSpan">{{$t("等待面试")}}</span>
          </el-card>
          <el-card class="cardRolSpan" shadow="hover" @click="routerTo('interviewOverview',{valueKey:'8'})">
            <div class="cardNum">{{ (mission.userMissions[8] || 0) }}</div>
            <span class="cardSpan">{{'offer'}}</span>
          </el-card>
        </div>
      </el-tab-pane>
    </el-tabs>
    <el-card shadow="never" class="myDDL">
      <h4>{{$t("我的任务")}}</h4>
      <el-calendar v-model="date" class="calendar">
        <template #date-cell="{ data }" style="height: 20px" >
          <!-- 日历项的日期和当前选中提示 -->
          <p :class="data.isSelected ? 'is-selected' : ''">
            {{ data.day.split('-').slice(2).join('-') }}
            {{ data.isSelected ? '✔️' : '' }}
            
          </p>
          <div v-if="data.isSelected">
            <!-- 徽章: 当剩余任务为 0 则显示为绿色, 否则为红色  -->      
            <el-badge :value="calendarBadgeText()" class="item" :type="calendarBadgeType()">
            <span style="width: 5px; float: left"></span>
          </el-badge>
          </div>
          
        </template>
      </el-calendar>
      <div class="rightDiv">
        <el-table :data="tableData" class="table">
          <el-table-column prop="date" label="Date" width="180" />
          <el-table-column prop="name" label="Name" width="180" />
          <el-table-column prop="message" label="Message" />
        </el-table>
        <!-- 分页插件 -->
        <el-pagination v-model:currentPage="pageNo" v-model:page-size="pageSize" :disabled="false" :background="true"
          layout="total, prev, pager, next, jumper" :total="totalSize" @size-change="handleSizeChange"
          @current-change="handleCurrentChange" class="page-pagination" />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
/**
 * 导入依赖
 */
import { onMounted, reactive, ref, watch } from "vue";
import $axios from "../plugins/axiosInstance.js";
import { ElBadge, ElButton, ElCalendar, ElCard, ElDescriptions, ElDescriptionsItem, ElIcon, ElLoading, ElPagination, ElPopover, ElTable, ElTableColumn, ElTabPane, ElTabs, ElTag } from "element-plus";
import i18n from "../locales";
import { useRouter } from "vue-router";
// 语言切换用法 `t('中文')`
const {t} = i18n.global;

// 路由工具
const router = useRouter();

// 日历组件当前日期
const date = ref(new Date())

//当前任务量数据
const mission = reactive({
  allMissions:{
 

},
userMissions:{

}
})

// 表格数据
const tableData:any[] =reactive( [
{
    date: '-',
    name: '-',
    message: '-',
  },
  {
    date: '-',
    name: '-',
    message: '-',
  },
  {
    date: '-',
    name: '-',
    message: '-',
  },
  {
    date: '-',
    name: '-',
    message: '-',
  },
  {
    date: '-',
    name: '-',
    message: '-',
  },
  {
    date: '-',
    name: '-',
    message: '-',
  },
  {
    date: '-',
    name: '-',
    message: '-',
  },
  {
    date: '- ',
    name: '-',
    message: '- ',
  }
])
// 总任务
const totalMission = ref();

//分页管理
const pageNo = ref(1)
const pageSize = ref(10)
const small = ref(false)
const background = ref(false)
const disabled = ref(false)
const totalSize = ref(100);
const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`)
}
const handleCurrentChange = (val: number) => {
  console.log(`current page: ${val}`)
}



/**
 * 日历项徽章的内容显示
 * 
 */
const calendarBadgeText = () => {

  if(totalMission.value)
    return totalMission.value+t('未完成');
  else
    return t('无任务');
}

/**
 * 日历项徽章类型颜色: 绿色无任务, 红色有任务
 */
const calendarBadgeType = () => {

  return totalMission.value == 0 ? 'success' : 'danger';
}

/**
 * 获取总任务和当前用户任务
 */
const getInterviewStates = () => {
  $axios.post("/workbench/getInterviewStates",{
    pageNo: (pageNo.value - 1) * pageSize.value,
    pageSize: pageSize.value,
    date:`${date.value.getFullYear()}-${date.value.getMonth()+1}-${date.value.getDate()}`
  }).then((resp) => {
    mission.allMissions = resp.data["allMissions"];
    mission.userMissions = resp.data["userMissions"];
    if (resp.data["todayMission"]) {
      tableData.length=0;
      for (let i in resp.data["todayMission"]) {
        tableData[i] = resp.data["todayMission"][i]
        tableData[i].date=tableData[i].date||`${date.value.getFullYear()}-${date.value.getMonth()+1}-${date.value.getDate()}`
      }
      totalSize.value=resp.data["todayMission"].length;
      totalMission.value=totalSize.value
    }else{
      totalMission.value=0;
      totalSize.value=1;
      tableData.length=0;
      tableData[0]={date:`${date.value.getFullYear()}-${date.value.getMonth()+1}-${date.value.getDate()}`, message:t("今日无任务") ,name:""}
    }
  })
}






/**
 * 动作中路由跳转点击事件
 */
 const routerTo = (name, query) => {
  // 路由跳转
  router.push({
    name: name,
    query: query
  });
}

/**
 * 在组件挂载完成后执行的方法
 */
onMounted(getInterviewStates)


/**
 * 侦听日期事件
 */
watch(date, (count, prevCount) => {
  
  getInterviewStates();
})

</script>

<style scoped>
#back {
  width: 1200px;
  margin: 0 auto;
}

/* 卡片div */
.cardDiv {
  margin-left: 20px;
  margin-right: 20px;
}

/* 卡片 */
.cardRolSpan {
  display: inline-block;
  width: 150px;
  height: 180px;
  margin-right: 90px;
  cursor: pointer;
}

/* 最后一个卡片 */
.cardRolSpan:nth-last-child(1) {
  margin-right: 0;
}

/* 卡片数值 */
.cardNum {
  color: rgb(64, 158, 255);
  font-size: 45px;
  text-align: center;
}

/* 卡片名 */
.cardSpan {
  display: block;
  margin-top: 20px;
  font-size: 13px;
  text-align: center;
}

/* 我的安排区域 */
.myDDL {
  margin-top: 20px;
}

/* 日历组件 */
.calendar {
  width: 500px;
  display: inline-block;
}

.rightDiv {
  margin-top: 12px;
  width: 658px;
  display: inline-block;
  vertical-align: top;
}

/* 表格组件 */
.table {}

/* 分页组件 */
.page-pagination {
  margin-top: 10px;
}

/* 更改日历组件day高度 */
:deep(.el-calendar-table .el-calendar-day) {
  height: 60px;
}

.taskDescription:not(:last-child) {
  margin-bottom: 10px;
}
</style>