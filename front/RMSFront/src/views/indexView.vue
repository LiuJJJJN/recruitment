<template>
  <div class="common-layout">
    <!-- 内容面板 -->
    <el-container>
      <!-- 页头 -->
      <el-header class="pageHeader">
        <!-- logo 和 项目名标题 -->
        <el-tooltip
            effect="dark"
            :content="$t('点击返回工作台页面')"
            placement="right"
        >
          <div class="logoDiv" @click="toWorkbench">
            <el-image class="logoImg" src="/logo.png"/>
            <div class="logoTitle">
              <span class="titleEn">Recruitment Management System</span>
              <span class="title">{{ $t('招聘管理系统') }}</span>
            </div>
          </div>
        </el-tooltip>
        <div class="header-right">
          <!-- 语言切换 -->
          <el-radio-group v-model="language" class="lang-change">
            <el-radio-button label="中文" @click="langChange('zhCn')"/>
            <el-radio-button label="日本語" @click="langChange('ja')"/>
          </el-radio-group>
          <!-- 用户下拉菜单按钮 -->
          <el-dropdown class="userDropdown">
            <span>
              <el-button text bg>Hi, {{ userStore.getName }}</el-button>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="dialogVisible = true">
                  <el-icon>
                    <EditPen/>
                  </el-icon>
                  {{ $t('修改密码') }}
                </el-dropdown-item>
                <el-dropdown-item :icon="SwitchButton" @click="logout">
                  <el-icon>
                    <SwitchButton/>
                  </el-icon>
                  {{ $t('退出登录') }}
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <!-- 内容面板 -->
      <el-container>
        <!-- 侧边栏 -->
        <el-aside class="my-aside" width="200px">
          <el-menu
              :default-active="$route.path"
              class="asideMenu"
              :router=true
              v-loading="isMenuLoading"
          >
            <el-menu-item
                v-for="(item, index) in menuList"
                :key="index"
                :index="item.path"
            >
              <span v-text="$t(item.name)"></span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <!-- 内容路由 -->
        <el-main class="my-main">
          <!-- 过度动画, appear: 一开始就显示动画 -->
          <transition appear>
            <RouterView/>
          </transition>
        </el-main>
      </el-container>
    </el-container>
    <!-- 返回顶部按钮 -->
    <el-backtop :right="100" :bottom="100"/>
  </div>

  <!-- 修改密码Dialog -->
  <el-dialog
      v-model="dialogVisible"
      :title="$t('修改密码')"
      width="540px"
  >
    <el-form :model="form" label-width="190px" style="margin-right: 30px" ref="formRef">
      <el-form-item
          :label="$t('请输入新密码')"
          prop="password"
          :rules="[
            { required: true, message: '请填写密码'},
            { min: 6, max: 20, message: '密码的长度为 6 到 20 个字符'}
          ]"
      >
        <el-input type="password" v-model="form.password" show-password clearable/>
      </el-form-item>
      <el-form-item
          :label="$t('再次输入新密码')"
          prop="rePassword"
          :rules="[
            { required: true, message: '请再次填写密码'},
            { min: 6, max: 20, message: '密码的长度为 6 到 20 个字符'}
          ]"
      >
        <el-input type="password" v-model="form.rePassword" show-password clearable/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">{{ $t('取消') }}</el-button>
        <el-button type="primary" @click="editPassword(formRef)">{{ $t('提交') }}</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script lang="ts" setup>
/**
 * 导入依赖
 */
import {RouterView, useRouter} from 'vue-router';
import {onMounted, reactive, ref} from "vue";
import {ElMessage, ElMessageBox, FormInstance} from "element-plus";
import {useUserStore} from "../store";
import $axios from "../plugins/axiosInstance.js";
import {ElLoading} from 'element-plus'
import i18n from "../locales";

// 语言切换用法 `t('中文')`
const {t} = i18n.global;

// 路由工具
const router = useRouter();

// 定义状态管理工具
const userStore = useUserStore();

// 菜单加载动画开启标记
const isMenuLoading = ref(true);

// 菜单列表
const menuList = reactive([]);

// 表单 ref
const formRef = ref<FormInstance>();

// 重置密码表单
const form = reactive({
  password: '',
  rePassword: ''
});

// 重置密码Dialog是否显示
const dialogVisible = ref(false);

// 页面刷新后的语言
const language = ref(localStorage.getItem("lang") == 'zhCn' ||
localStorage.getItem("lang") == null ? '中文' : '日本語');

/**
 * 退出登录点击事件
 */
const logout = () => {
  ElMessageBox.confirm(
      t('确认要退出登录吗?'),
      t('退出提示'),
      {
        confirmButtonText: t('确认'),
        cancelButtonText: t('取消'),
        type: 'warning',
      }
  ).then(() => {
    // 开启全屏加载动画
    const loading = ElLoading.service();
    // 发送登出请求
    $axios.post('/user/logout').then((resp) => {
      // 清除用户信息
      userStore.removeAll();
      // 跳转到登录页面
      router.push("/login");
      // 关闭加载动画
      loading.close();
      // 成功提示
      ElMessage({
        message: t(resp["msg"]),
        type: 'success',
      });
    }).catch((error) => {
      // 关闭加载动画
      loading.close();
    });
  });
};

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

/**
 * 加载权限路径列表
 */
const loadMenuPathList = () => {
  $axios.post("/permissionManage/getPermissionPathList").then((resp) => {
    // 阶段菜单列表
    menuList.length = 0;
    // 菜单列表赋值
    menuList.push(...resp.data);
    // 关闭加载动画
    isMenuLoading.value = false;
  }).catch(() => {
    // 关闭加载动画
    isMenuLoading.value = false;
  });
}

/**
 * 点击 logo 区域触发的事件
 * 跳转到工作台页面
 */
const toWorkbench = () => {
  router.push("/index/workbench");
}

/**
 * 修改密码
 */
const editPassword = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if (valid) {
      if (form.password !== form.rePassword) {
        // 给出提示
        ElMessage({
          message: '密码不一致, 请重新输入',
          type: 'warning',
        });
        // 置空表单
        form.password = '';
        form.rePassword = '';
        return;
      }
      $axios.post("/userManage/editPassword", {
        password: form.password
      }).then((resp) => {
        // 成功提示
        ElMessage({
          type: 'success',
          message: resp["msg"],
        });
        // 跳转到登录页面
        router.push('/login');
      }).catch(() => {
        // 置空表单
        form.password = '';
        form.rePassword = '';
        // 关闭对话框
        dialogVisible.value = false;
      });
    }
  })
}

/**
 * 在组件挂载完成后执行
 */
onMounted(() => {
  // 加载权限路径列表
  loadMenuPathList();
})
</script>

<style>
/* 页头 */
.pageHeader {
  background-color: #FFFFFF;
  border-bottom: #E4E7ED 1px solid;
  min-width: 590px;
}

/* logo 区域 */
.logoDiv {
  display: inline-block;
  cursor: pointer;
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

/* 用户名下拉菜单按钮 */
.userDropdown {
  margin-top: 14px;
}

/* 侧边栏 */
.my-aside {
  background-color: #FFFFFF;
}

/* 侧边栏链接 */
.asideMenu {
  background-color: #FCFCFC;
  height: calc(100vh - 60px);
}

/* 内容区 */
.my-main {
  background-color: #FAFAFA;
  /* 设置内容区高度为:100%视图区高度 - header的高度 */
  height: calc(100vh - 60px);
  min-width: 390px;
}

/* 页头右侧功能区 */
.header-right {
  float: right;
}

/* 语言切换 */
.lang-change {
  margin-top: 14px;
  margin-right: 10px;
}

/* 进入动画 */
.v-enter-active {
  animation: move 0.3s;
}

/* 离开动画 */
.v-leave-active {
  animation: move 0.3s reverse;
}

@keyframes move {
  from {
    transform: translateX(-100%);
  }
  to {
    transform: translate(0);
  }
}

</style>
