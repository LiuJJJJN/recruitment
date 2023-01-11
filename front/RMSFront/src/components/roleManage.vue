<template>
  <div>

    <el-row justify="space-between" class="operateRow">
      <!-- 添加角色按钮 -->
      <el-col :span="6">
        <el-button type="primary" plain @click="addRole">
          <el-icon>
            <Plus/>
          </el-icon>
        </el-button>
        <el-button type="warning" plain @click="refreshTableData">
          <el-icon>
            <Refresh/>
          </el-icon>
        </el-button>
      </el-col>
    </el-row>

    <el-table :data="filterTableData" style="width: 100%" v-loading="isRoleTableLoading">
      <!-- 序号 -->
      <el-table-column type="index" width="50"/>
      <el-table-column label="角色名" prop="name" width="250px" sortable/>
      <el-table-column label="备注" show-overflow-tooltip prop="comment"/>
      <el-table-column align="right">
        <template #header>
          <el-input v-model="search" size="small" placeholder="根据角色名过滤表格"/>
        </template>
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
            编辑
          </el-button>
          <el-button size="small" @click="handleEditPermission(scope.$index, scope.row)">
            权限管理
          </el-button>
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

    <!-- 添加角色对话框 -->
    <el-dialog v-model="addDialogFormVisible" title="添加角色" width="600px">
      <el-form :model="addForm" ref="addFormRef">
        <el-form-item
            label="角色名"
            :label-width="formLabelWidth"
            prop="name"
            :rules="[
              { required: true, message: '角色名是必填项'},
              { min: 2, max:20, message: '角色名应在 2 到 20 个字符之间'},
              { pattern: '^[a-zA-Z]+[a-zA-Z0-9_]+$', message: '角色名只能为英文开头的英文数字下划线组成', trigger: 'change'}
            ]"
        >
          <el-input v-model="addForm.name" placeholder="请输入角色名" autocomplete="off"/>
        </el-form-item>
        <el-form-item
            label="备注"
            :label-width="formLabelWidth"
            prop="comment"
            :rules="[
              { max:50, message: '备注最多 50 个字符'}
            ]"
        >
          <el-input type="textarea" rows="3" v-model="addForm.comment" placeholder="请输入备注" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addDialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="submitAddRoleForm(addFormRef)">
            提交
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 编辑角色对话框 -->
    <el-dialog v-model="editDialogFormVisible" title="编辑角色" width="600px">
      <el-form :model="editForm" ref="editFormRef">
        <el-form-item
            label="角色名"
            :label-width="formLabelWidth"
            prop="name"
            :rules="[
              { required: true, message: '角色名是必填项'},
              { min: 2, max:20, message: '角色名应在 2 到 20 个字符之间'},
              { pattern: '^[a-zA-Z]+[a-zA-Z0-9_]+$', message: '角色名只能为英文开头的英文数字下划线组成', trigger: 'change'}
            ]"
        >
          <el-input v-model="editForm.name"
                    placeholder="请输入角色名"
                    autocomplete="off"
                    v-loading="editForm.id === ''"
          />
        </el-form-item>
        <el-form-item
            label="备注"
            :label-width="formLabelWidth"
            prop="comment"
            :rules="[
              { max:50, message: '备注最多 50 个字符'}
            ]"
        >
          <el-input type="textarea"
                    rows="3"
                    v-model="editForm.comment"
                    placeholder="请输入备注"
                    autocomplete="off"
                    v-loading="editForm.id === ''"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="submitEditRoleForm(editFormRef)">
            提交
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 权限管理对话框 -->
    <el-dialog v-model="permissionManageDialogVisible" title="权限管理" top="100px" width="1020px">
      <el-transfer
          v-model="rightValue"
          style="text-align: left; display: inline-block"
          filterable
          :titles="['剩余权限', '已有权限']"
          :button-texts="['移除', '添加']"
          :format="{
            noChecked: '${total}',
            hasChecked: '${checked}/${total}',
          }"
          :data="data"
      />
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="permissionManageDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitPermissionManage">
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
import {computed, onMounted, reactive, ref} from 'vue'
import {ElMessage, FormInstance} from "element-plus";
import $axios from "../plugins/axiosInstance.js";

// 角色表格数据加载动画标记
const isRoleTableLoading = ref(true);

//分页相关:
//页标
const pageNo = ref(1);

//每页记录数
const pageSize = ref(10);

//页标总大小
const totalSize = ref(0);

// 对话框相关
// 添加角色表单 ref
const addFormRef = ref<FormInstance>();

// 编辑角色表单 ref
const editFormRef = ref<FormInstance>();

// 添加对话框是否显示
const addDialogFormVisible = ref(false);

// 权限管理对话框是否显示
const permissionManageDialogVisible = ref(false);

// 编辑对话框是否显示
const editDialogFormVisible = ref(false);

// 对话框内表单宽度
const formLabelWidth = '100px';

// 添加对话框内表单内容
const addForm = reactive({
  name: '',
  comment: ''
})

// 添加对话框内表单内容
const editForm = reactive({
  id: '',
  name: '',
  comment: ''
})

// 权限内容接口
interface Option {
  key: string
  label: string
}

// 权限内容列表
const data = reactive<Option[]>([])

// 当前 / 已有的权限列表
const rightValue = ref(['1'])

// 角色表格数据接口
interface Role {
  id: string
  name: string
  comment: string
}

// 角色表格内容
const roleTableData = reactive<Role[]>([]);

// 表格过滤搜索相关
const search = ref('')
const filterTableData = computed(() =>
    roleTableData.filter(
        (data) =>
            !search.value ||
            data.name.toLowerCase().includes(search.value.toLowerCase())
    )
)

// 当前角色 ID
const roleId = ref();


/**
 * 显示现在的每页显示记录数量
 * @param val 每页显示记录数量
 */
const handleCurrentChange = (val: number) => {
  // 获取角色列表
  loadRoleList();
  // 获取角色总量
  loadRoleTotalSize();
}

/**
 * 修改每页显示记录数量
 * @param val 每页显示记录数量
 */
const handleSizeChange = (val: number) => {
  // 获取角色列表
  loadRoleList();
  // 获取角色总量
  loadRoleTotalSize();
}


/**
 * 权限管理按钮点击事件
 *
 * @param index 当前行下标
 * @param row 当前行内容
 */
const handleEditPermission = (index: number, row: Role) => {
  // 截断权限列表
  data.length = 0;
  // 清空权限内容列表
  rightValue.value = [];
  // 打开权限管理对话框
  permissionManageDialogVisible.value = true;
  // 获取权限内容列表
  $axios.post("/permissionManage/getPermissionVoList").then((resp) => {
    // 刷新权限内容列表
    data.push(...resp.data);
  }).catch(() => {
    // 关闭权限管理对话框
    permissionManageDialogVisible.value = false;
  });
  // 获取当前角色的权限内容列表
  $axios.post("/permissionManage/getPermissionIdListByRoleId", {
    id: row.id
  }).then((resp) => {
    // 刷新权限内容列表
    rightValue.value = resp.data;
  }).catch(() => {
    // 关闭权限管理对话框
    permissionManageDialogVisible.value = false;
  });
  // 保存当前角色的 id
  roleId.value = row.id;
}

/**
 * 编辑按钮点击事件
 *
 * @param index 当前行下标
 * @param row 当前行内容
 */
const handleEdit = (index: number, row: Role) => {
  // 置空 id , 开启加载动画
  editForm.id = '';
  // 打开编辑角色对话框
  editDialogFormVisible.value = true;
  // 获取权限内容列表
  $axios.post("/roleManage/getRoleById", {
    id: row.id
  }).then((resp) => {
    // 刷新编辑表单内容
    editForm.id = resp.data.id;
    editForm.name = resp.data.name;
    editForm.comment = resp.data.comment;
  }).catch(() => {
    // 关闭编辑角色对话框
    editDialogFormVisible.value = false;
  });
}

/**
 * 添加角色按钮点击事件
 */
const addRole = () => {
  // 打开添加角色对话框
  addDialogFormVisible.value = true;
}

/**
 * 刷新按钮点击事件
 */
const refreshTableData = () => {
  loadRoleList();
}

/**
 * 获取表格数据角色列表
 */
const loadRoleList = () => {
  // 开启加载动画
  isRoleTableLoading.value = true;
  $axios.post("/roleManage/getRoleListByPageCondition", {
    pageNo: (pageNo.value - 1) * pageSize.value,
    pageSize: pageSize.value,
  }).then((resp) => {
    roleTableData.length = 0;
    roleTableData.push(...resp.data);
    // 关闭加载动画
    isRoleTableLoading.value = false;
  }).catch(() => {
    // 关闭加载动画
    isRoleTableLoading.value = false;
  });
}

/**
 * 获取表格数据角色列表
 */
const loadRoleTotalSize = () => {
  $axios.post("/roleManage/getRoleTotalSize").then((resp) => {
    totalSize.value = resp.data;
  });
}

/**
 * 获取表格数据角色列表
 */
const submitAddRoleForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if (valid) {
      $axios.post("/roleManage/addRole", addForm).then((resp) => {
        // 成功提示
        ElMessage({
          type: 'success',
          message: resp["msg"],
        });
        // 关闭对话框
        addDialogFormVisible.value = false;
        // 刷新表格数据
        refreshTableData();
      });
    } else {
      return false
    }
  })
}

/**
 * 权限管理提交按钮点击事件
 */
const submitPermissionManage = () => {
  $axios.post("/roleManage/editPermission", {
    roleId: roleId.value,
    idList: rightValue.value
  }).then((resp) => {
    // 成功提示
    ElMessage({
      type: 'success',
      message: resp["msg"],
    });
  });
  // 关闭对话框
  permissionManageDialogVisible.value = false;
}

/**
 * 权限管理提交按钮点击事件
 */
const submitEditRoleForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if (valid) {
      $axios.post("/roleManage/editRole", editForm).then((resp) => {
        // 成功提示
        ElMessage({
          type: 'success',
          message: resp["msg"],
        });
        // 刷新表格数据
        refreshTableData();
      });
      // 关闭对话框
      editDialogFormVisible.value = false;
    } else {
      return false
    }
  })
}

/**
 * 在组件挂载完成后执行的方法
 */
onMounted(() => {
  // 获取表格数据角色列表
  loadRoleList();
  // 获取角色表数据总量
  loadRoleTotalSize();
})
</script>

<style scoped>
/* 操作区: 添加角色按钮 */
.operateRow {
  margin-bottom: 10px;
}

/* 修改穿梭框的宽度和高度 */
.el-transfer {
  --el-transfer-panel-width: 380px;
  --el-transfer-panel-body-height: 500px;
}

/* 修改搜索框宽度 */
:deep(.el-transfer-panel__filter>.el-input__wrapper>.el-input__inner) {
  width: 300px;
}

/* 标页码样式 */
.page-pagination {
  margin: 10px;
}
</style>
