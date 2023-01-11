<template>
  <div id="back">
    <!-- 面试信息描述列表-->
    <el-descriptions class="margin-top" :title="$t('面试信息')" :column="2" border>
      <!-- 面试信息功能展示-->
      <template #extra>
        <el-button @click="routerTo('/index/interviewOverview')" v-if="id === undefined" type="primary"
                   v-text="$t('选择面试候选人')"></el-button>
        <!-- 查看简历按钮-->
        <el-button type="primary" class="right-button" @click="previewResume(interviewResumeInformation.filePath)"
                   v-if="id !== undefined">
          {{ $t("预览简历") }}
        </el-button>
        <!-- 查看评论按钮-->
        <el-button type="primary" class="right-button" @click="dialogVisible = true" v-if="id !== undefined">
          {{ $t("查看评论") }}
        </el-button>
      </template>
      <el-descriptions-item min-width="100px">
        <template #label>
          <div class="cell-item">
            {{ $t("面试信息") }}
          </div>
        </template>
        {{ interviewResumeInformation.date }}
      </el-descriptions-item>
      <el-descriptions-item min-width="120px">
        <template #label>
          <div class="cell-item">
            {{ $t("面试官1") }}
          </div>
        </template>
        {{ interviewResumeInformation.interviewer1.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            {{ $t("被面试人") }}
          </div>
        </template>
        {{ interviewResumeInformation.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            {{ $t("面试官2") }}
          </div>
        </template>
        <span v-if="interviewResumeInformation.interviewer2 != undefined">{{
            interviewResumeInformation.interviewer2.name
          }}</span>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            {{ $t("毕业时间") }}
          </div>
        </template>
        <span v-if="interviewResumeInformation.gradDate != undefined">{{ interviewResumeInformation.gradDate }}</span>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            {{ $t("面试官3") }}
          </div>
        </template>
        <span v-if="interviewResumeInformation.interviewer3 != undefined">{{
            interviewResumeInformation.interviewer3.name
          }}</span>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            {{ $t("主要技术") }}
          </div>
        </template>
        <span v-if="interviewResumeInformation.techAbility != undefined">{{
            interviewResumeInformation.techAbility
          }}</span>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            {{ $t("面试官4") }}
          </div>
        </template>
        <span v-if="interviewResumeInformation.interviewer4 != undefined">{{
            interviewResumeInformation.interviewer4.name
          }}</span>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            {{ $t("语言水平") }}
          </div>
        </template>
        {{ interviewResumeInformation.langAbility }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
          </div>
        </template>
      </el-descriptions-item>
    </el-descriptions>
    <!-- 面试结果描述列表-->
    <div>
      <el-descriptions class="margin-top" :title="$t('面试结果')" :column="2" border style="margin:20px 0;">
      </el-descriptions>
      <el-form ref="formRef" :model="form" :rules="FeedBackRules" label-width="120px">
        <!-- 面试结果表单-->
        <el-form-item :label="$t('面试结果')" prop="region1">
          <el-select v-model="form.region1" :placeholder="$t('未选择')">
            <el-option v-for="item in resultList" :label="item.valueCode" :value="item.valueKey"/>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('参考部门')" v-show="form.region1 === 1">
          <el-autocomplete :model-value="form.internalDept.value" :fetch-suggestions="querySearch" clearable
                           @clear="form.internalDept = { value: '' }" :placeholder="$t('请选择意向部门')"
                           @select="handleSelect"
                           style="width: 280px; margin-right: 30px"/>

        </el-form-item>
        <el-form-item :label="$t('面试综合评价')">
          <!-- 参考模板按钮-->
          <el-button type="primary" style="margin-left: 578px; margin-bottom: 10px">{{ $t("参考模板") }}</el-button>
          <!-- 保存为模板按钮-->
          <el-button type="primary" style="margin-bottom: 10px">{{ $t("保存为模板") }}</el-button>
          <el-input v-model="form.desc" type="textarea" rows="4"/>
        </el-form-item>
      </el-form>
    </div>
    <!-- 评价打分表单内容-->
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="valueKey" label="No" width="100"/>
      <el-table-column prop="valueCode" :label="$t('考察能力')" width="450"></el-table-column>
      <el-table-column prop="score" :label="$t('评分')" width="200">
        <template #default="scope">
          <el-rate v-model="scope.row['score']"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('评价')">
        <!-- 打分表单中评论按钮-->
        <template #default="scope">
          <el-button size="small">{{ $t("评价") }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <br>
    <!-- 确认事项表单内容-->
    <el-table :data="tableData1" style="width: 100%">
      <el-table-column prop="valueKey" label="No" width="100"/>
      <el-table-column prop="valueCode" :label="$t('确认事项')" width="500">
      </el-table-column>
      <!-- 确认事项结果 Switch 滑块-->
      <el-table-column :label="$t('结果')" width="150">
        <template #default="scope">
          <el-switch v-model="scope.row['score']" style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                     active-value="100" inactive-value="0"/>
        </template>
      </el-table-column>
      <!-- 确认事项评论按钮-->
      <el-table-column :label="$t('评价')">
        <template #default="scope">
          <el-button size="small" @click="openComment(scope.$index, scope.row)">{{ $t("评价") }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 简历的提交，返回按钮-->
    <div class="submit">
      <el-button type="primary" @click="onSubmit(formRef)"> {{ $t("提交") }}</el-button>
      <!-- 简历返回时提醒未提交面试信息-->
      <el-popconfirm :title="$t('尚未提交面试信息，是否要返回面试一览画面吗？')" width="300px">
        <template #reference>
          <el-button style="margin-left: 50px">{{ $t("返回") }}</el-button>
        </template>
      </el-popconfirm>
    </div>

    <!-- 查看评论对话框-->
    <el-dialog v-model="dialogVisible" :title="$t('评论')" width="550px">
      <!-- 每条评论主体 -->
      <el-timeline>
        <!-- 时间标签 -->
        <el-timeline-item :timestamp="item.creatDate" placement="top" v-for="item in commentList" class="timelineItem">
          <el-card>
            <!-- 评论内容 -->
            <h4>{{ item.comment }}</h4>
            <!-- 评论人 -->
            <p style="float: right; color: gray; margin-bottom: 8px"> -- {{ item.creater.name }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
      <!-- dialog的页脚 -->
      <template #footer>
        <span class="dialog-footer">
          <!-- dialog的确认和取消按钮 -->
          <el-button @click="dialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 查看评论对话框-->
    <el-dialog v-model="itemDialogVisible" :title="$t('评论')" width="550px" append-to-body>
      <el-input v-model="comment" type="textarea" placeholder="Please input"
                :autosize="{ minRows: 4, maxRows: 10 }"/>
      <!-- dialog的页脚 -->
      <template #footer>
              <span class="dialog-footer">
                <!-- dialog的确认和取消按钮 -->
                <el-button @click="itemDialogVisible = false">关闭</el-button>
              </span>
      </template>
    </el-dialog>

  </div>
</template>


<script setup lang="ts">
/**
 * 引入依赖
 */
import {onMounted, reactive, ref} from 'vue';
import {
  ElButton,
  ElCard,
  ElDescriptions,
  ElDescriptionsItem,
  ElDialog,
  ElForm,
  ElFormItem,
  ElInput, ElMessage,
  ElOption,
  ElPopconfirm,
  ElRate,
  ElSelect,
  ElSwitch,
  ElTable,
  ElTableColumn,
  ElTimeline,
  ElTimelineItem,
  FormInstance,
  FormRules
} from 'element-plus';
import i18n from "../locales";
import {useRouter, useRoute} from 'vue-router';
import $axios from "../plugins/axiosInstance.js";
import {Base64} from "js-base64";
// 路由工具
const router = useRouter();
const route = useRoute();
// 获取部门列表
const loadAll = () => {
  let deptList = [];
  $axios.post("deptManage/getDeptList").then((resp) => {
    for (let data of resp.data) {
      deptList.push({
        id: data.id,
        value: data.name,
        company: data.company
      });
    }
  })
  return deptList;
}
const restaurants = ref<RestaurantItem[]>([])
const querySearch = (queryString: string, cb: any) => {
  const results = queryString
      ? restaurants.value.filter(createFilter(queryString))
      : restaurants.value
  // call callback function to return suggestions
  cb(results)
}

const createFilter = (queryString: string) => {
  return (restaurant: RestaurantItem) => {
    return (
        restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
    )
  }
}

interface RestaurantItem {
  value: string
}

const handleSelect = (item: RestaurantItem) => {
  form.internalDept = item;
}
// 反馈结果列表
const resultList = reactive([]);
// 评论列表
const commentList = reactive([]);
// 当前操作面试 ID
const id = ref();

const comment = ref();

interface User {
  id: string
  valueKey: string
  valueCode: string
  score?: any
  comment: string
}

interface User1 {
  id: string
  valueKey: string
  valueCode: string
  score?: any
  comment: string
}

/**
 * 变量部分
 */
const {t} = i18n.global

// 表单格式检查引用
const formRef = ref<FormInstance>()
const interviewResumeInformation = ref(
    {
      id: '',
      date: '',
      status: '',
      resumeId: '',
      interviewer1: '',
      interviewer2: '',
      interviewer3: '',
      interviewer4: '',
      reviewer: '',
      createDate: '',
      modifyDate: '',
      filePath: '',
      name: '',
      sex: '',
      birthday: '',
      email: '',
      tel: '',
      education: '',
      gradDate: '',
      gradSchool: '',
      major: '',
      techAbility: '',
      langAbility: '',
      otherAbility: '',
      workExperience: '',
      selfAssessment: '',
      source: '',
      internalRecommender: '',
      internalDept: '',
      obey: ''
    }
)
//面试结果
const FeedBackRules = reactive<FormRules>({
  region1: [
    {required: true, message: t('请选择面试结果'), trigger: 'blur'},
  ],
})

/**
 * 路由跳转工具
 */
const routerTo = (path) => {
  router.push(path);
}
//面试时间
const viewTime = {
  a1: '2022/11/11 12:00'
}
//面试能力信息内容
const form = reactive({
  region1: '',
  region2: '',
  region3: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
  internalDept: {
    value: ''
  }
})

const submitForm = reactive({
  id: '',
  result: '',
  comment: '',
  internalDept: '',
  interviewId: '',
  abilityList: [],
  confirmList: []
});

//评论列表
const dialog = [
  {
    comments: '这个小伙技术不错',
    time: '2018/4/12 20:46',
    commenter: 'Tom',
  },
  {
    comments: '这个小伙技术好',
    time: '2018/4/02 20:46',
    commenter: 'Jerry',
  },
  {
    comments: '这个小伙性格不太行',
    time: '2018/4/00 20:46',
    commenter: 'Bob',
  },
]

//控制对话框显示
const dialogVisible = ref(false)
const itemDialogVisible = ref(false)


/**
 * 关闭评论
 * @param done
 */
const handleClose = (done: () => void) => {
  done();
}

/**
 * 提交按钮
 * @param formEl  formEl
 */
const onSubmit = (formEl: FormInstance | undefined) => {

  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      submitForm.id = null;
      submitForm.result = form.region1;
      submitForm.comment = form.desc;
      submitForm.internalDept = form.internalDept.id;
      submitForm.interviewId = <string>route.query.id;
      for (let tableDatum of tableData) {
        submitForm.abilityList.push({
          id: tableDatum.id,
          comment: tableDatum.comment,
          item: tableDatum.valueKey,
          score: tableDatum.score
        })
      }
      for (let tableDatum of tableData1) {
        submitForm.confirmList.push({
          id: tableDatum.id,
          comment: tableDatum.comment,
          item: tableDatum.valueKey,
          score: tableDatum.score
        })
      }
      $axios.post("/feedBack/addInterviewFeedback", submitForm).then((resp) => {
        // 成功提示
        ElMessage({
          type: 'success',
          message: resp["msg"],
        });
      }).catch((error) => {

      });

    } else {
      console.log('error submit!')
      return false
    }
  })
}


/**
 * 确认事项中评论按钮
 * @param index  评论下标
 * @param row  单行数据内容
 */
const openComment = (index: number, row: User) => {
  itemDialogVisible.value = true;
}

//评分内容
const tableData: User[] = reactive([])

//确认事项的内容
const tableData1: User1[] = reactive([])
/**
 * 加载面试信息
 */
const loadInterviewResumeInformation = () => {
  if (id.value != undefined) {
    $axios.post("/feedBack/getInterviewResumeInformation", {
      id: id.value
    }).then((resp) => {
      // 赋值
      interviewResumeInformation.value = resp.data;
    });
  }
}
/**
 * 加载简历信息
 */
const previewResume = (path) => {
  let url = "http://www.rms.cool:9000" + path;
  console.log(url);
  window.open('http://www.rms.cool:8012/onlinePreview?url=' + encodeURIComponent(Base64.encode(url)));
}
/**
 * 加载反馈结果列表
 */
const loadResultList = () => {
  $axios.post("/dicValue/getResultList").then((resp) => {
    // 赋值
    resultList.push(...resp.data);
  });
}
/**
 * 加载评论列表
 */
const loadCommentList = () => {
  $axios.post("/comment/getCommentList", {
    id: id.value
  }).then((resp) => {
    // 赋值
    commentList.push(...resp.data);
  });
}
/**
 * 加载考察能力列表
 */
const dictionaryValueList = () => {
  $axios.post("/dicValue/getAbility_item").then((resp) => {
    // 赋值
    tableData.push(...resp.data);
  });
}
/**
 * 加载确认事项列表
 */
const dictionaryValueList1 = () => {
  $axios.post("/dicValue/getConfirm_item").then((resp) => {
    // 赋值
    tableData1.push(...resp.data);
  });
}
/**
 * 在组件挂载完成后执行
 */
onMounted(() => {
  // 获取路由携带的参数: 面试 ID
  id.value = route.query.id;
  //加载评论列表
  loadCommentList();
  // 加载面试信息
  loadInterviewResumeInformation();
  // 加载反馈结果列表
  loadResultList();
  // 加载部门列表
  restaurants.value = loadAll();
  // 加载考察能力列表
  dictionaryValueList();
  // 加载确认事项列表
  dictionaryValueList1();
})


</script>


<style scoped>
/*面试信息表单按钮*/
.dialog-footer button:first-child {
  margin-right: 10px;
}

/*页面布局*/
#back {
  width: 900px;
  margin: 0 auto;
}

/* 简历的上传提交 */
.submit {
  width: 300px;
  margin: 20px auto 0;
}

.timelineItem {
  width: 430px;
}
</style>