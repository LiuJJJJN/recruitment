<template>
  <div>

    <!-- 搜索栏 -->
    <el-row class="searchRow">
      <el-col>
        <el-input v-model="fuzzySearch.username" placeholder="请输入用户名" clearable class="searchInput"/>
        <el-input v-model="fuzzySearch.name" placeholder="请输入姓名" clearable class="searchInput"/>
        <el-input v-model="fuzzySearch.company" placeholder="请输入所属公司" clearable class="searchInput"/>
        <el-input v-model="fuzzySearch.dept" placeholder="请输入所属部门" clearable class="searchInput"/>
        <el-button type="primary" @click="flushTableData">
          <el-icon>
            <Search/>
          </el-icon>
        </el-button>
        <el-button type="warning" @click="refreshTableData">
          <el-icon>
            <Refresh/>
          </el-icon>
        </el-button>
      </el-col>
    </el-row>

    <el-row justify="space-between">
      <!-- 添加用户按钮 -->
      <el-col :span="6">
        <el-button type="primary" plain @click="addUserBtn">
          <el-icon>
            <Plus/>
          </el-icon>
        </el-button>
        <el-button type="success" plain @click="editUserStatusEnableBatch">
          批量启用
        </el-button>
        <el-popconfirm title="请确认没有锁定全部用户！！" width="230px" @confirm="editUserStatusLockBatch">
          <template #reference>
            <el-button type="warning" plain>
              批量锁定
            </el-button>
          </template>
        </el-popconfirm>
      </el-col>
    </el-row>

    <!-- 用户信息表 -->
    <el-table ref="multipleTableRef" :data="userTableData" style="margin-top:10px"
              @selection-change="handleSelectionChange" v-loading="isUserTableLoading">
      <!--多选列表-->
      <el-table-column type="selection" width="55"/>
      <el-table-column property="username" label="用户名" show-overflow-tooltip width="110" sortable/>
      <el-table-column property="name" label="姓名" show-overflow-tooltip width="90" sortable/>
      <el-table-column property="company" label="所属公司" show-overflow-tooltip width="170" sortable/>
      <el-table-column property="dept" label="所属部门" show-overflow-tooltip width="110" sortable/>
      <el-table-column property="status" label="状态" width="120px" sortable>
        <template #default="scope">
          <el-radio-group v-model="scope.row.status">
            <el-radio-button size="small"
                             v-for="item in userStatus"
                             :key="item.id"
                             :label="item.valueKey"
                             @click="changeUserStatus(scope.row, item, $event)"
            >
              {{ item.valueCode }}
            </el-radio-button>
          </el-radio-group>
        </template>
      </el-table-column>
      <el-table-column property="role" label="角色">
        <template #default="scope">
          <el-tag class="ml-2"
                  effect="dark"
                  type="info"
                  size="default"
                  v-for="(item, index) in scope.row.roleList"
                  :key="index"
                  v-text="item"
                  :color="smartRoleColor(item)"
                  round
          ></el-tag>
        </template>
      </el-table-column>
      <!--operations的edit和delete按钮-->
      <el-table-column label="动作" align="right" width="120px">
        <template #default="scope">
          <el-button size="small" @click="handleEditUser(scope.$index, scope.row)">编辑用户</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页插件 -->
    <el-pagination
        v-model:currentPage="pageNo"
        v-model:page-size="pageSize"
        :page-sizes="[10, 15, 20, 50]"
        :disabled="false"
        :background="true"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalSize"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        class="page-pagination"
    />

    <!-- 添加用户对话框 -->
    <el-dialog v-model="addUserDialogFormVisible" title="添加用户" width="600px">
      <el-form :model="addUserForm" ref="addUserFormRef">
        <el-form-item
            label="用户名"
            :label-width="formLabelWidth"
            prop="username"
            :rules="[
              { required: true, message: '用户名是必填项'},
              { min: 6, max:20, message: '用户名的长度为 6 到 20 个字符'}
            ]"
        >
          <el-input v-model="addUserForm.username" placeholder="请输入用户名" autocomplete="off"/>
        </el-form-item>
        <el-form-item
            label="密码"
            :label-width="formLabelWidth"
            prop="password"
            :rules="[
              { required: true, message: '密码是必填项'},
              { min: 6, max:20, message: '密码的长度为 6 到 20 个字符'}
            ]"
        >
          <el-input type="password"
                    show-password
                    v-model="addUserForm.password"
                    placeholder="请输入密码"
                    autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="addUserForm.name" placeholder="请输入姓名" autocomplete="off"/>
        </el-form-item>
        <el-form-item
            label="邮箱"
            :label-width="formLabelWidth"
            prop="email"
            :rules="[
              { required: true, message: '邮箱是必填项'},
              {
                pattern: '^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*',
                message: '请输入正确的邮箱格式',
                trigger: 'change'
              }
            ]"
        >
          <el-input v-model="addUserForm.email" placeholder="请输入邮箱" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="部门" :label-width="formLabelWidth">
          <el-select v-model="addUserForm.dept"
                     placeholder="请选择部门"
                     class="deptSelect"
                     v-loading="isDeptSelectLoading"
          >
            <el-option
                v-for="item in deptList"
                :key="item"
                :label="item.name"
                :value="item.id"
            >
              <span style="float: left">{{ item.name }}</span>
              <span class="typeCodeComment">{{ item.company }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            label="角色"
            :label-width="formLabelWidth"
            prop="role"
            :rules="[
              { required: true, message: '角色是必选项'},
            ]"
        >
          <el-checkbox-group v-model="addUserForm.role" v-loading="isRoleListLoading">
            <el-checkbox-button v-for="item in roleList" :key="item" :label="item.id">
              {{ item.name }}
            </el-checkbox-button>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addUserDialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="submitAddUserForm(addUserFormRef)">
            提交
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 编辑用户对话框 -->
    <el-dialog v-model="editUserDialogFormVisible" title="编辑用户" width="600px">
      <el-form :model="editUserForm" ref="editUserFormRef">
        <el-form-item
            label="用户名"
            :label-width="formLabelWidth"
            prop="username"
            :rules="[
              { required: true, message: '用户名是必填项'},
              { min: 6, max:20, message: '用户名的长度为 6 到 20 个字符'}
            ]"
        >
          <el-input v-model="editUserForm.username"
                    autocomplete="off"
                    placeholder="请输入用户名"
                    v-loading="editUserForm.id === ''"
                    clearable
          />
        </el-form-item>
        <el-form-item
            label="密码"
            :label-width="formLabelWidth"
            prop="password"
            :rules="[
              { min: 6, max:20, message: '密码的长度为 6 到 20 个字符'}
            ]"
        >
          <el-input type="password"
                    show-password
                    v-model="editUserForm.password"
                    autocomplete="off"
                    placeholder="请输入密码"
                    v-loading="editUserForm.id === ''"
          />
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="editUserForm.name"
                    autocomplete="off"
                    placeholder="请输入姓名"
                    v-loading="editUserForm.id === ''"
                    clearable
          />
        </el-form-item>
        <el-form-item
            label="邮箱"
            :label-width="formLabelWidth"
            prop="email"
            :rules="[
              { required: true, message: '邮箱是必填项'},
              {
                pattern: '^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*',
                message: '请输入正确的邮箱格式',
                trigger: 'change'
              }
            ]"
        >
          <el-input v-model="editUserForm.email"
                    autocomplete="off"
                    placeholder="请输入邮箱"
                    v-loading="editUserForm.id === ''"
                    clearable
          />
        </el-form-item>
        <el-form-item label="部门" :label-width="formLabelWidth">
          <el-select v-model="editUserForm.dept"
                     placeholder="请选择部门"
                     class="deptSelect"
                     v-loading="isDeptSelectLoading"
          >
            <el-option
                v-for="item in deptList"
                :key="item"
                :label="item.name"
                :value="item.id"
            >
              <span style="float: left">{{ item.name }}</span>
              <span class="typeCodeComment">{{ item.company }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            label="状态"
            :label-width="formLabelWidth"
            prop="status"
            :rules="[
              { required: true, message: '状态是必选项'},
            ]"
        >
          <el-radio-group v-model="editUserForm.status">
            <el-radio-button size="small" v-for="item in userStatus" :key="item.id" :label="item.valueKey">
              {{ item.valueCode }}
            </el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item
            label="角色"
            :label-width="formLabelWidth"
            prop="role"
            :rules="[
              { required: true, message: '角色是必选项'},
            ]"
        >
          <el-checkbox-group v-model="editUserForm.role" v-loading="isRoleListLoading">
            <el-checkbox-button v-for="item in roleList" :key="item" :label="item.id">
              {{ item.name }}
            </el-checkbox-button>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editUserDialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="submitEditUserForm(editUserFormRef)">
            提交
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
import {onMounted, reactive, ref} from 'vue';
import {ElMessage, ElTable, FormInstance} from 'element-plus';
import $axios from "../plugins/axiosInstance.js";

// 模糊搜索内容
const fuzzySearch = reactive({
  username: '',
  name: '',
  company: '',
  dept: ''
})

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
const totalSize = ref(0);

// 用户状态列表
const userStatus = reactive([]);

// 表格编号
const multipleTableRef = ref<InstanceType<typeof ElTable>>();

// 表格中内容的接口
interface User {
  id: string
  username: string
  name: string
  signInDate: string
  company: string
  dept: string
  email: string
  status: number
  roleList: any
}

//表格多选参数
const multipleSelection = ref<User[]>([]);

// 用户表加载动画标记
const isUserTableLoading = ref(true);

// 部门选择器加载动画标记
const isDeptSelectLoading = ref(true);

// 角色单选按钮加载动画标记
const isRoleListLoading = ref(true);

//用户数据
const userTableData = reactive<User[]>([]);

// 部门列表
const deptList = reactive([]);

// 角色列表
const roleList = reactive([]);

// 对话框相关
// 添加用户表单 ref
const addUserFormRef = ref<FormInstance>();

// 编辑用户表单 ref
const editUserFormRef = ref<FormInstance>();

// 添加用户对话框是否显示
const addUserDialogFormVisible = ref(false);

// 编辑用户对话框是否显示
const editUserDialogFormVisible = ref(false);

// 对话框内表单宽度
const formLabelWidth = '100px';

// 添加用户对话框内表单内容
const addUserForm = reactive({
  username: '',
  name: '',
  password: '',
  dept: '',
  email: '',
  role: [],
})

// 编辑用户对话框内表单内容
const editUserForm = reactive({
  id: '',
  username: '',
  name: '',
  password: '',
  dept: '',
  email: '',
  status: 0,
  role: [],
})

/**
 * 显示现在的每页显示记录数量
 * @param val 每页显示记录数量
 */
const handleCurrentChange = (val: number) => {
  // 获取用户数据列表
  loadUserListByPageCondition();
  // 获取用户数据总量
  loadUserListByPageConditionTotalSize();
}

/**
 * 修改每页显示记录数量
 * @param val 每页显示记录数量
 */
const handleSizeChange = (val: number) => {
  // 获取用户数据列表
  loadUserListByPageCondition();
  // 获取用户数据总量
  loadUserListByPageConditionTotalSize();
}

/**
 * 编辑函数，编辑用户信息
 * @param index  用户下标
 * @param row  用户信息
 */
const handleEditUser = (index: number, row: User) => {
  // 清空表单数据
  editUserForm.id = '';
  editUserForm.status = null;
  editUserForm.dept = '';
  editUserForm.username = '';
  editUserForm.name = '';
  editUserForm.email = '';
  editUserForm.role = [];
  // 打开编辑用户对话框
  editUserDialogFormVisible.value = true;
  // 加载部门和角色列表
  LoadDeptAndRoleList();
  // 获取用户详细信息
  $axios.post("/userManage/getUserById", {
    id: row.id
  }).then((resp) => {
    // 刷新页面数据
    editUserForm.id = resp.data.id;
    editUserForm.status = resp.data.status;
    editUserForm.dept = resp.data.dept;
    editUserForm.username = resp.data.username;
    editUserForm.name = resp.data.name;
    editUserForm.email = resp.data.email;
    editUserForm.role = resp.data.roleList;
  });
}

/**
 * 加载部门和角色列表
 */
const LoadDeptAndRoleList = () => {
  isDeptSelectLoading.value = true;
  $axios.post("/deptManage/getDeptList").then((resp) => {
    // 刷新部门列表数据
    deptList.length = 0;
    deptList.push(...resp.data);
    // 关闭加载动画
    isDeptSelectLoading.value = false;
  }).catch(() => {
    // 清空部门列表数据
    deptList.length = 0;
    // 关闭加载动画
    isDeptSelectLoading.value = false;
  });
  // 角色列表加载动画开启
  isRoleListLoading.value = true;
  $axios.post("/roleManage/getRoleList").then((resp) => {
    // 刷新角色列表数据
    roleList.length = 0;
    roleList.push(...resp.data);
    // 关闭加载动画
    isRoleListLoading.value = false;
  }).catch(() => {
    // 清空角色列表数据
    roleList.length = 0;
    // 关闭加载动画
    isRoleListLoading.value = false;
    // 关闭添加用户对话框
    addUserDialogFormVisible.value = false;
    // 关闭编辑用户对话框
    editUserDialogFormVisible.value = false;
  });
}

/**
 * 删除函数，删除用户信息
 * @param index  用户下标
 * @param row  用户行号
 */
const handleDelete = (index: number, row: User) => {
  console.log(index, row);
}

/**
 * 选择记录的函数
 * @param val 被选择的用户列表
 */
const handleSelectionChange = (val: User[]) => {
  multipleSelection.value = val;
}

/**
 * 自动切换角色标签背景色
 * @param item 当前角色字符
 */
const smartRoleColor = (item) => {
  switch (item) {
    case 'Pmo':
      return "#95d475";
      break;
    case 'Reviewer':
      return "#eebe77";
      break;
    case 'Leader':
      return "#79bbff";
      break;
    case 'Admin':
      return "#f89898";
      break;
  }
}

/**
 * 根据分页和模糊查询数据获取用户数据列表
 */
const loadUserListByPageCondition = () => {
  // 开启加载动画
  isUserTableLoading.value = true;
  $axios.post("/userManage/getUserListByPageCondition", {
    pageNo: (pageNo.value - 1) * pageSize.value,
    pageSize: pageSize.value,
    username: fuzzySearch.username,
    name: fuzzySearch.name,
    company: fuzzySearch.company,
    dept: fuzzySearch.dept
  }).then((resp) => {
    // 清空列表内容
    userTableData.length = 0;
    // 更新数据字典值表格数据
    userTableData.push(...resp.data);
    // 关闭加载动画
    isUserTableLoading.value = false;
  }).catch(() => {
    // 清空列表内容
    userTableData.length = 0;
    // 关闭加载动画
    isUserTableLoading.value = false;
  });
}

/**
 * 根据模糊查询数据获取用户数据总量
 */
const loadUserListByPageConditionTotalSize = () => {
  $axios.post("/userManage/getUserTotalSizeByPageCondition", {
    username: fuzzySearch.username,
    name: fuzzySearch.name,
    company: fuzzySearch.company,
    dept: fuzzySearch.dept
  }).then((resp) => {
    // 更新数据字典值表格数据
    totalSize.value = resp.data;
  });
}

/**
 * 根据模糊查询数据获取用户数据总量
 */
const loadUserStatusList = () => {
  $axios.post("/dicValue/getUserStatusList").then((resp) => {
    // 更新用户状态列表数据
    userStatus.push(...resp.data);
  });
}

/**
 * 模糊查询重置按钮点击事件
 */
const refreshTableData = () => {
  // 重置模糊查询输入框数据
  fuzzySearch.username = '';
  fuzzySearch.name = '';
  fuzzySearch.company = '';
  fuzzySearch.dept = '';
  // 获取用户数据列表
  loadUserListByPageCondition();
  // 获取用户数据总量
  loadUserListByPageConditionTotalSize();
}

/**
 * 用户状态单选按钮点击事件
 */
const changeUserStatus = (row, item, event) => {
  // 解决一次点击执行多次的问题
  event.preventDefault();
  // 发送请求
  $axios.post("/userManage/editUserStatus", {
    id: row.id,
    status: item.valueKey
  }).then((resp) => {
    // 成功提示
    ElMessage({
      type: 'success',
      message: resp["msg"],
    });
    // 刷新表格数据
    flushTableData();
  });
}

/**
 * 模糊查询搜索按钮点击事件
 */
const flushTableData = () => {
  // 获取用户数据列表
  loadUserListByPageCondition();
  // 获取用户数据总量
  loadUserListByPageConditionTotalSize();
}

/**
 * 批量启用按钮点击事件
 */
const editUserStatusEnableBatch = () => {
  // 当没有选中任何数据时提示
  if (multipleSelection.value.length === 0) {
    ElMessage({
      type: 'info',
      message: "请选中要启用的数据后重试"
    });
    return;
  }
  // 获取选中的内容
  let idList = [];
  multipleSelection.value.forEach(item => {
    idList.push(item.id);
  });
  $axios.post("/userManage/editUserStatus0Batch", idList).then((resp) => {
    // 成功提示
    ElMessage({
      type: 'success',
      message: resp["msg"],
    });
    // 刷新页面数据
    flushTableData();
  });
}

/**
 * 批量锁定按钮点击事件
 */
const editUserStatusLockBatch = () => {
  // 当没有选中任何数据时提示
  if (multipleSelection.value.length === 0) {
    ElMessage({
      type: 'info',
      message: "请选中要锁定的数据后重试"
    });
    return;
  }
  // 获取选中的内容
  let idList = [];
  multipleSelection.value.forEach(item => {
    idList.push(item.id);
  });
  $axios.post("/userManage/editUserStatus1Batch", idList).then((resp) => {
    // 成功提示
    ElMessage({
      type: 'success',
      message: resp["msg"],
    });
    // 刷新页面数据
    flushTableData();
  });
}

/**
 * 添加用户按钮点击事件
 */
const addUserBtn = () => {
  // 打开添加用户对话框
  addUserDialogFormVisible.value = true;
  // 加载部门和角色列表
  LoadDeptAndRoleList();
}

/**
 * 添加用户对话框提交按钮点击事件
 */
const submitAddUserForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if (valid) {
      $axios.post("/userManage/addUser", addUserForm).then((resp) => {
        // 成功提示
        ElMessage({
          type: 'success',
          message: resp["msg"],
        });
        // 关闭对话框
        addUserDialogFormVisible.value = false;
        // 刷新表格数据
        flushTableData();
      });
    } else {
      return false
    }
  })
}

/**
 * 编辑用户对话框提交按钮点击事件
 */
const submitEditUserForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if (valid) {
      $axios.post("/userManage/editUserById", editUserForm).then((resp) => {
        // 成功提示
        ElMessage({
          type: 'success',
          message: resp["msg"],
        });
        // 刷新表格数据
        flushTableData();
        // 关闭对话框
        editUserDialogFormVisible.value = false;
      });
    } else {
      return false
    }
  })
}

/**
 * 在组件挂载完成后执行的方法
 */
onMounted(() => {
  // 获取用户数据列表
  loadUserListByPageCondition();
  // 获取用户数据总量
  loadUserListByPageConditionTotalSize();
  // 获取数据字典中用户状态列表
  loadUserStatusList();
})
</script>

<style scoped>
/* 模糊搜索区域 */
.searchRow {
  margin-bottom: 10px;
}

/* 模糊搜索区域输入框 */
.searchInput {
  width: 192px;
  margin-right: 10px;
}

/* 表格中的角色标签 */
.ml-2 {
  margin-right: 5px;
  /* 去除边框颜色 */
  --el-tag-border-color: none;
}

/* 表单中的部门选择下拉菜单 */
.deptSelect {
  width: 460px;
}

/* 标页码样式 */
.page-pagination {
  margin: 10px;
}

/* 添加对话框中 部门下拉菜单 的选项备注 */
.typeCodeComment {
  float: right;
  color: var(--el-text-color-secondary);
  font-size: 13px;
}
</style>
