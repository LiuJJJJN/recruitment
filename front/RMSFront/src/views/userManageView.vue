<template>
  <!-- 整个布局 -->
  <div id="back">

    <!-- 标签页 -->
    <el-tabs
        v-model="activeName"
        type="card"
        @tab-click="handleClick"
    >
      <!-- 用户管理标签页 -->
      <el-tab-pane label="用户" name="user" lazy>
        <UserManage/>
      </el-tab-pane>

      <!-- 角色管理标签页 -->
      <el-tab-pane label="角色" name="role" lazy>
        <roleManage/>
      </el-tab-pane>

      <!-- 权限管理标签页 -->
      <el-tab-pane label="权限" name="permission" lazy>
        <permissionManage/>
      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script lang="ts" setup>
/**
 * 导入依赖
 */
import {ref} from 'vue';
import type {TabsPaneContext} from 'element-plus';

// 导入用户管理组件
import UserManage from '../components/userManage.vue';

// 导入角色管理组件
import RoleManage from '../components/roleManage.vue';

// 导入权限管理组件
import PermissionManage from '../components/permissionManage.vue';

// 标签相关
// 默认显示的标签
const activeName = ref('user');

// 切换被选中时的回调函数
const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event)
}
</script>

<style scoped>
/* 整体布局样式 */
#back {
  width: 1100px;
  margin: 0 auto;
}
</style>
