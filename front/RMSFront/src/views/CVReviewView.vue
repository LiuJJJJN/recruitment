<template>
  <!-- 整个布局 -->
  <div id="back">

    <!-- 表单 -->
    <el-form
        :model="form"
        label-width="120px"
        class="form-set"
        :rules="rules"
        ref="ruleFormRef"
    >
      <!-- 第一个表单头 -->
      <el-descriptions :title="$t('简历信息')"></el-descriptions>
      <!-- 表单第一行 -->
      <el-form-item :label="$t('候选人')">
        <div class="top-line">
          <span class="grid-text" v-if="id !== undefined">
            <el-tag class="ml-2" type="info" size="large">{{ form.data.name }}</el-tag>
          </span>
          <span class="grid-text" v-if="id !== undefined">
            <el-tag class="ml-2" type="info" size="large"
                    v-text="formatDicValueKey2Code(sexList, form.data.sex)"></el-tag>
          </span>
          <!-- 如果不是从面试一览画面跳转过来的话显示这个按钮 -->
          <span v-if="id === undefined">
            <el-button @click="routerTo('/index/interviewOverview')" type="primary"
                       v-text="$t('选择 Review 候选人')"></el-button>
          </span>
          <!-- 查看简历和查看评论的按钮 -->
          <el-button
              type="primary"
              class="right-button"
              :size="size"
              @click="previewResume(form.data.filePath)"
              v-loading="isFormLoading"
              v-if="id !== undefined"
          >
            {{ $t("预览简历") }}
          </el-button>
          <el-button
              type="primary"
              class="right-button"
              :size="size"
              @click="dialogVisible = true"
              v-loading="isFormLoading"
              v-if="id !== undefined"
          >
            {{ $t("查看评论") }}
          </el-button>
        </div>
      </el-form-item>
      <!-- 表单第二行 -->
      <el-form-item :label="$t('毕业时间')" v-loading="isFormLoading">
        <!-- 日期选择器 -->
        <el-col>
          <el-date-picker v-model="form.data.gradDate"
                          type="date"
                          :placeholder="$t('请选择日期')"
                          :shortcuts="shortcuts"
                          :size="size"
                          class="small-form small-form-right"
          />
          <span class="grid-content">{{ $t('最高学历') }}</span>
          <!-- 学历的选择框 -->
          <el-select
              v-model="form.data.education"
              :placeholder="$t('未选择')"
              class="small-form"
              :size="size"
              v-loading="isFormLoading"
          >
            <el-option v-for="item in educationList" :key="item" :label="item.valueCode" :value="item.valueKey"/>
          </el-select>
        </el-col>
      </el-form-item>
      <!-- 表单第三行 -->
      <el-form-item :label="$t('毕业学校')" v-loading="isFormLoading">
        <!-- 毕业学校的输入框 -->
        <el-input v-model="form.data.gradSchool" :placeholder="$t('请输入学校')" class="base-input" :size="size"/>
      </el-form-item>
      <!-- 表单第四行 -->
      <el-form-item :label="$t('专业')" v-loading="isFormLoading">
        <!-- 专业的输入框 -->
        <el-input v-model="form.data.major" :placeholder="$t('请输入专业')" class="base-input" :size="size"/>
      </el-form-item>
      <!-- 表单第五行 -->
      <el-form-item :label="$t('语言能力')" v-loading="isFormLoading">
        <!-- 语言能力的选择框 -->
        <el-select v-model="form.data.langAbility" :placeholder="$t('未选择')" class="small-form small-form-right"
                   :size="size">
          <el-option v-for="item in langAbilityList" :key="item" :label="item.valueCode" :value="item.valueKey"/>
        </el-select>
        <span class="grid-content">{{ $t('技术能力') }}</span>
        <!-- 技术能力的选择框 -->
        <el-select v-model="form.data.techAbility" :placeholder="$t('未选择')" class="small-form" :size="size">
          <el-option v-for="item in techAbilityList" :key="item" :label="item.valueCode" :value="item.valueKey"/>
        </el-select>
      </el-form-item>
      <!-- 表单第六行 -->
      <el-form-item :label="$t('其他能力')" v-loading="isFormLoading">
        <!-- 其他能力的输入框 -->
        <el-input v-model="form.data.otherAbility" :placeholder="$t('请输入其他能力')" class="base-input" :size="size"/>
      </el-form-item>
      <!-- 表单第七行 -->
      <el-form-item :label="$t('工作经验')" v-loading="isFormLoading">
        <!-- 工作经验的输入框 -->
        <el-input v-model="form.data.workExperience" :placeholder="$t('请描述工作经验，最多200字')" type="textarea"
                  class="base-input"
                  maxlength="200" :rows="3" show-word-limit/>
      </el-form-item>
      <!-- 评审分割线 -->
      <el-descriptions :title="$t('评审结果')"></el-descriptions>

      <!-- 表单第九行 -->
      <el-form-item :label="$t('是否通过')" prop="result">
        <!-- 是否通过的开关 -->
        <el-radio-group v-model="form.result">
          <el-radio-button v-for="item in resultList" :label="item.valueKey">{{ item.valueCode }}</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <!-- 表单第十行 -->
      <el-form-item :label="$t('评论')" prop="comment" :rules="{
        required: form.result === 0,
        message: $t('请输入NG原因'),
        trigger: 'blur',
      }">
        <!-- 评论的输入框 -->
        <el-input v-model="form.comment" type="textarea"
                  :placeholder="$t('请发表你对该简历的看法，若不通过则必须填写原因，最多200字')"
                  class="base-input" :rows="4" maxlength="200" show-word-limit/>
      </el-form-item>
      <!-- 表单提交和取消按钮 -->
      <el-form-item>
        <el-button type="primary" @click="submitForm(ruleFormRef)" :size="size">{{ $t('提交') }}</el-button>
        <el-button :size="size" @click="resetForm(ruleFormRef)">{{ $t('重置') }}</el-button>
      </el-form-item>
    </el-form>

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

  </div>
</template>

<script setup lang="ts">
/**
 * 导入依赖
 */
import {ref, reactive, onMounted, onBeforeMount} from 'vue';
import i18n from "../locales";
import {ElMessage, ElMessageBox, ElNotification, FormInstance, FormRules} from 'element-plus';
import {useRouter, useRoute} from 'vue-router';
import $axios from "../plugins/axiosInstance.js";
import {Base64} from "js-base64";

// 路由工具
const router = useRouter();
const route = useRoute();

// 语言切换用法 `t('中文')`
const {t} = i18n.global;

// 当前操作面试 ID
const id = ref();

// 性别列表
const sexList = reactive([]);

// 学历列表
const educationList = reactive([]);

// 语言能力列表
const langAbilityList = reactive([]);

// 技术能力列表
const techAbilityList = reactive([]);

// 反馈结果列表
const resultList = reactive([]);

// 评论列表
const commentList = reactive([]);

// 控制对话框显示
const dialogVisible = ref(false)

// 简历信息表单加载动画开启标记
const isFormLoading = ref(true);

// 简历信息表单内容
const form = reactive({
  interviewId: null,
  data: {},
  result: null,
  comment: null
})

//表单判断规则
const ruleFormRef = ref<FormInstance>();

//表单标签对齐方式
const labelPosition = ref('right')

//控制表单控件大小
const size = ref<'' | 'large' | 'small'>('')

//时间控件
const shortcuts = [
  {
    text: new Date().getFullYear() - 2,
    value: () => {
      const date = new Date()
      date.setFullYear(date.getFullYear() - 2, 7 - 1, 1);
      return date
    },
  },
  {
    text: new Date().getFullYear() - 1,
    value: () => {
      const date = new Date()
      date.setFullYear(date.getFullYear() - 1, 7 - 1, 1);
      return date
    },
  },
  {
    text: new Date().getFullYear(),
    value: () => {
      const date = new Date()
      date.setFullYear(date.getFullYear(), 7 - 1, 1);
      return date
    },
  },
  {
    text: new Date().getFullYear() + 1,
    value: () => {
      const date = new Date()
      date.setFullYear(date.getFullYear() + 1, 7 - 1, 1);
      return date
    },
  },
  {
    text: new Date().getFullYear() + 2,
    value: () => {
      const date = new Date()
      date.setFullYear(date.getFullYear() + 2, 7 - 1, 1);
      return date
    },
  },
]

// 设置通过开关为必填
const rules = reactive<FormRules>({
  result: [
    {
      required: true,
      message: t('请选择是否通过'),
      trigger: 'blur',
    },
  ],
})

/**
 * 提交函数，用于提交表单里的数据
 * @param formEl  表单数据
 */
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      // 如果未选择 候选人则给出提示
      if (id.value === undefined) {
        ElNotification({
          title: '警告',
          message: '请选择 Review 候选人后重试',
          type: 'warning',
        })
        return;
      }
      // 获取面试 ID
      form.interviewId = id.value;
      $axios.post("/cvReview/submitReviewResult", form).then((resp) => {
        // 成功提示
        ElMessage({
          type: 'success',
          message: resp["msg"],
        });
        // 跳转到面试一览画面
        routerTo("/index/interviewOverview");
      });
    } else {
      console.log('error submit!', fields)
    }
  })
}

/**
 * 重置表单
 * @param formEl 表单
 */
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl)
    return;
  formEl.resetFields();
}

/**
 * 路由跳转工具
 */
const routerTo = (path) => {
  router.push(path);
}

/**
 * 加载简历信息
 */
const loadResumeFormData = () => {
  if (id.value !== undefined) {
    // 等待 200 ms 再发送请求, 因为此请求速度过快的话 formatDicValueKey2Code 方法执行不过来容易导致空数据出现
    setTimeout(() => {
      $axios.post("/cvReview/getResumeById", {
        id: id.value
      }).then((resp) => {
        // 赋值表单数据
        form.data = resp.data;
        // 关闭加载动画
        isFormLoading.value = false;
      }).catch(() => {
        // 关闭加载动画
        isFormLoading.value = false;
      });
    }, 200)
  } else {
    // 关闭加载动画
    isFormLoading.value = false;
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
 * 根据状态的 valueKey 返回对应的 valueCode
 */
const formatDicValueKey2Code = (dicList, status) => {
  return dicList.filter((value) => {
    return value.valueKey == status
  })[0]["valueCode"];
}

/**
 * 加载性别列表
 */
const loadSexList = () => {
  $axios.post("/dicValue/getSexList").then((resp) => {
    // 赋值
    sexList.push(...resp.data);
  });
}

/**
 * 加载学历列表
 */
const loadEducationList = () => {
  $axios.post("/dicValue/getEducationList").then((resp) => {
    // 赋值
    educationList.push(...resp.data);
  });
}

/**
 * 加载语言能力列表
 */
const loadLangAbilityList = () => {
  $axios.post("/dicValue/getLangAbilityList").then((resp) => {
    // 赋值
    langAbilityList.push(...resp.data);
  });
}

/**
 * 加载技术能力列表
 */
const loadTechAbilityList = () => {
  $axios.post("/dicValue/getTechAbilityList").then((resp) => {
    // 赋值
    techAbilityList.push(...resp.data);
  });
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
 * 在组件挂载完成后执行
 */
onMounted(() => {
  // 获取路由携带的参数: 面试 ID
  id.value = route.query.id;
  // 加载性别列表
  loadSexList();
  // 加载学历列表
  loadEducationList();
  // 加载语言能力列表
  loadLangAbilityList();
  // 加载技术能力列表
  loadTechAbilityList();
  // 加载反馈类型列表
  loadResultList();
  //加载评论列表
  loadCommentList();
  // 加载简历信息
  loadResumeFormData();
})
</script>

<style scoped>
/* 布局样式 */
#back {
  width: 800px;
  margin: 0 auto;
}

/* 行样式 */
.el-col {
  border-radius: 4px;
}

/* 表单中其他的 label 样式 */
.grid-content {
  display: inline-block;
  border-radius: 4px;
  min-height: 38px;
  min-width: 100px;
  padding-right: 12px;
  padding-left: 12px;
  text-align: center;
  font-size: 14px;
}

/* 本页文本 label 样式 */
.grid-text {
  display: inline-block;
  border-radius: 4px;
  min-height: 38px;
  min-width: 80px;
  padding-right: 12px;
  padding-left: 12px;
  text-align: center;
  font-size: 16px;
  margin-left: 20px;
}

/* 小号控件的属性 */
.small-form, :deep(.el-date-editor) {
  width: 250px;
}

/* 右侧小号控件属性 */
.small-form-right, :deep(.el-date-editor) {
  margin-right: 70px;
}


/* 日期选择器样式 */
.demo-date-picker {
  display: flex;
  width: 100%;
  flex-wrap: wrap;
}

/* 日期选择器样式 */
.demo-date-picker .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  flex: 1;
}

/* 日期选择器样式 */
.demo-date-picker .block:last-child {
  border-right: none;
}

/* 日期选择器样式 */
.demo-date-picker .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
  margin-right: 30px;
}

/* 查看简历和评论按钮的布局 */
.top-line {
  width: 900px;
  margin-right: 10px;
}

/* 查看简历和评论按钮的属性 */
.right-button {
  margin: 5px;
  float: right;
}

/* 标准输入框的属性 */
.base-input {
  width: 670px;
}

/* 对话框footer样式 */
.dialog-footer button:first-child {
  margin-right: 10px;
}

/* 评论对话框中的时间线 */
.timelineItem {
  width: 430px;
}
</style>