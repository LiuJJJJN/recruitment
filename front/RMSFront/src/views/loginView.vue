<template>
  <div class="loginPanel">
    <div class="picturePanel">
      <el-carousel height="500px" arrow="never" interval="3000">
        <el-carousel-item v-for="item in pictures" :key="item">
          <!-- 走马灯图片 -->
          <el-image style="width: 600px; height: 500px" :src="item" fit="cover" />
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="loginForm">
      <!-- 标题 -->
      <div class="logoDiv">
        <el-image class="logoImg" src="/logo.png"/>
        <div class="logoTitle">
          <span class="titleEn">Recruitment Management System</span>
          <span class="title">{{ $t('招聘管理系统') }}</span>
        </div>
      </div>
      <!-- 登录表单 -->
      <el-form ref="ruleFormRef" :model="loginForm" :rules="loginRules" label-width="120px">
        <!-- 语言切换 -->
        <el-form-item>
          <el-radio-group v-model="language" class="lang-change">
            <el-radio-button label="中文" @click="langChange('zhCn')"/>
            <el-radio-button label="日本語" @click="langChange('ja')"/>
          </el-radio-group>
        </el-form-item>
        <!-- 用户名输入 -->
        <el-form-item :label="$t('用户名')" prop="username">
          <el-input class="my-input" v-model="loginForm.username" :placeholder="$t('请输入用户名')" clearable
                    size="large"/>
        </el-form-item>
        <!-- 密码输入 -->
        <el-form-item :label="$t('密码')" prop="password">
          <el-input type="password" class="my-input" v-model="loginForm.password" :placeholder="$t('请输入密码')"
                    show-password
                    clearable size="large"/>
        </el-form-item>
        <!-- 验证码 -->
        <el-form-item :label="$t('验证码')" prop="code">
          <el-input class="code-input" v-model="loginForm.code" :placeholder="$t('请输入验证码')" clearable size="large"
                    @keyup.enter="submitForm(ruleFormRef)"/>
          <SIdentify :identifyCode="identifyCode" @click="refreshCode" class="identifyCode"/>
        </el-form-item>
        <!-- 登录、重置 -->
        <el-form-item label="">
          <el-button size="large" type="primary" @click="submitForm(ruleFormRef)" :disabled="isLoginLoading">
            <el-icon style="margin-left: -5px; margin-right: 5px" class="is-loading" v-show="isLoginLoading">
              <Loading/>
            </el-icon>
            {{ $t('登录') }}
          </el-button>
          <el-button size="large" @click="resetForm(ruleFormRef)">{{ $t('重置') }}</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts" setup>
/**
 * 导入依赖
 */
import {onMounted, reactive, ref} from 'vue';
import type {FormInstance, FormRules} from 'element-plus';
import {ElMessage} from "element-plus";
import i18n from "../locales";
import {useRouter} from "vue-router";
import SIdentify from '../components/Sidentify.vue';
import $axios from "../plugins/axiosInstance.js";
import {useUserStore} from "../store";

// 语言切换用法 `t('中文')`
const {t} = i18n.global;

// 定义路由跳转工具
const router = useRouter();

// 定义状态管理工具
const userStore = useUserStore();

// 页面刷新后的语言
const language = ref(localStorage.getItem("lang") == 'zhCn' ||
localStorage.getItem("lang") == null ? '中文' : '日本語');

// 登录中标记
const isLoginLoading = ref(false);

// 表单格式检查引用
const ruleFormRef = ref<FormInstance>();

// 登录表单
const loginForm = reactive({
  username: "",
  password: "",
  code: ""
});

// 表单格式检查
const loginRules = reactive<FormRules>({
  username: [
    {required: true, message: t('请输入用户名'), trigger: 'blur'},
    {min: 6, max: 20, message: t('长度应在 6 到 20 之间'), trigger: 'change'},
  ],
  password: [
    {required: true, message: t('请输入密码'), trigger: 'blur'},
    {min: 6, max: 20, message: t('长度应在 6 到 20 之间'), trigger: 'change'},
  ],
  code: [
    {required: true, message: t('请输入验证码'), trigger: 'blur'},
  ]
});

// 图形验证码
let identifyCodes = "1234567890";
let identifyCode = ref('');

// 走马灯图片
const pictures = [
    "/mixkit-co-workers-doing-a-high-five-after-a-planning-meeting-396-original.png",
    "/mixkit-exhausted-man-in-front-of-a-computer-with-his-head-69-original.png",
    "/mixkit-desks-in-a-cool-co-working-space-with-an-office-dog-399-original.png",
    "/mixkit-team-members-working-side-by-side-on-laptops-379-original.png",
    "/mixkit-two-co-workers-having-a-drink-in-an-office-breakout-room-275-original.png",
    "/mixkit-job-interview-in-a-modern-office-378-original.png",
]

/**
 * 生成随机数
 * @param min 最小值
 * @param max 最大值
 */
const randomNum = (min, max) => {
  return Math.floor(Math.random() * (max - min) + min);
}

/**
 * 生成验证码
 * @param obj 生成对象
 * @param len 验证码长度
 */
const makeCode = (obj, len) => {
  for (let i = 0; i < len; i++) {
    identifyCode.value += obj[
        randomNum(0, obj.length)
        ];
  }
}

/**
 * 刷新验证码
 */
const refreshCode = () => {
  identifyCode.value = "";
  makeCode(identifyCodes, 4);
}

/**
 * 生命周期钩子: 组件挂载完成后执行
 */
onMounted(() => {
  // 刷新验证码
  refreshCode();
})

/**
 * 登录表单提交
 * @param formEl 登录表单
 */
const submitForm = async (formEl: FormInstance | undefined) => {
  // 验证表单内容格式是否正确, 不正确 return
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      // 判断验证码是否正确
      if (loginForm.code != identifyCode.value) {
        ElMessage({
          message: t('验证码错误, 请重新输入'),
          type: 'warning',
        });
        loginForm.code = '';
        return;
      }
      // 修改登录标记: 显示加载动画并设置登录按钮为不可点击
      isLoginLoading.value = true;
      // 登录操作
      $axios.post("/user/login", loginForm).then((resp) => {
        // 将 姓名、token 存储在 store 中
        userStore.setName(resp.data.name);
        userStore.setToken(resp.data.token);
        // 页面跳转
        router.push("/index/workbench");
        // 成功提示
        ElMessage({
          message: t(resp["msg"]),
          type: 'success',
        });
      }).catch(() => {
        // 重置登录状态: 隐藏加载动画并设置登录按钮为可点击
        isLoginLoading.value = false;
      });
    } else {
      // 表单不符合格式要求时执行
      ElMessage({
        message: t('请检查表单内容是否符合要求'),
        type: 'error',
      });
    }
  });
}

/**
 * 重置登录表单
 * @param formEl 登录表单
 */
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl)
    return;
  formEl.resetFields();
}

/**
 * 语言切换
 * @param language 当前选择语言
 */
const langChange = (language) => {
  // 设置浏览器localStorage的内容
  localStorage.setItem("lang", language);
  // 重新加载页面
  location.reload();
}
</script>

<style scoped>
/* 登录功能区 */
.loginPanel {
  font-size: 0;
  width: 1000px;
  margin: calc((100vh - 500px) / 2) auto 0;
  box-shadow: #d9d9d9 2px 2px 30px 10px;
}

/* 图片走马灯区域 */
.picturePanel {
  width: 600px;
  display: inline-block;
}

/* 走马灯右侧登录表单区域 */
.loginForm {
  width: 400px;
  height: 500px;
  display: inline-block;
  vertical-align: top;
}

/* logo 区域 */
.logoDiv {
  display: block;
  width: 310px;
  margin: 30px auto;
}

/* logo 图片 */
.logoImg {
  width: 70px;
  height: 60px;
  vertical-align: top;
}

/* logo 标题区域 */
.logoTitle {
  display: inline-block;
  width: 230px;
  margin-left: 6px;
  margin-top: 3px
}

/* 英文标题 */
.titleEn {
  display: inline-block;
  font-weight: bolder;
  font-size: 14px;
}

/* 标题 */
.title {
  display: inline-block;
  font-size: 16px;
  font-weight: bolder;
}

/* 账号密码输入框 */
.my-input {
  width: 230px;
}

/* 验证码输入框 */
.code-input {
  width: 145px;
  margin-right: 5px;
}

/* 语言切换掉单选按钮 */
.lang-change {
  padding-left: 98px;
}

/* 验证码组件 */
.identifyCode {
  margin-bottom: -10px;
}

/* 走马灯图片效果 */
.demonstration {
  color: var(--el-text-color-secondary);
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
