<template>
  <div>

    <el-row justify="space-between" class="operateRow">
      <!-- 添加权限按钮 -->
      <el-col :span="6">
        <el-button type="primary" plain @click="addPermission">
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

    <el-table :data="filterTableData" style="width: 100%" v-loading="isPermissionTableLoading">
      <!-- 序号 -->
      <el-table-column type="index" width="50"/>
      <el-table-column label="权限名" prop="name" width="170px" sortable/>
      <el-table-column label="权限代码" prop="code" width="220px" sortable/>
      <el-table-column label="是否是菜单" prop="isMenu" width="130px" sortable/>
      <el-table-column label="前端路径" prop="path" sortable/>
      <el-table-column label="排序依据" prop="orderBy" width="110px" sortable/>
      <el-table-column align="right" width="200px">
        <template #header>
          <el-input v-model="search" size="small" placeholder="根据权限名过滤表格"/>
        </template>
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
            编辑
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

    <!-- 添加权限对话框 -->
    <el-dialog v-model="addDialogFormVisible" title="添加权限" width="600px">
      <el-form :model="addForm" ref="addFormRef">
        <el-form-item
            label="权限名"
            :label-width="formLabelWidth"
            prop="name"
            :rules="[
              { required: true, message: '权限名是必填项'},
              { max:30, message: '权限名不得超过 30 个字符'},
            ]"
        >
          <el-input v-model="addForm.name" placeholder="请输入权限名" autocomplete="off"/>
        </el-form-item>
        <el-form-item
            label="权限代码"
            :label-width="formLabelWidth"
            prop="code"
            :rules="[
              { required: true, message: '权限代码是必填项'},
              { max:30, message: '权限代码最多 30 个字符'}
            ]"
        >
          <el-input v-model="addForm.code" placeholder="请输入权限代码" autocomplete="off"/>
        </el-form-item>
        <el-form-item
            label="是否前端路径"
            :label-width="formLabelWidth"
            prop="isMenu"
            :rules="[
              { required: true, message: '是否是前端路径是必填项'},
              { type: 'number', message: '是否是前端路径必须是数值类型' },
            ]"
        >
          <el-input v-model.number="addForm.isMenu" placeholder="1: 是, 0: 否" autocomplete="off"/>
        </el-form-item>
        <el-form-item
            label="前端路径"
            :label-width="formLabelWidth"
            prop="path"
            :rules="[
              { max:50, message: '前端路径最多 50 个字符'}
            ]"
        >
          <el-input v-model="addForm.path" placeholder="请输入前端路径" autocomplete="off"/>
        </el-form-item>
        <el-form-item
            label="排序依据"
            :label-width="formLabelWidth"
            prop="orderBy"
            :rules="[
              { required: true, message: '排序依据是必填项'},
              { type: 'number', message: '排序依据必须是数值类型' },
            ]"
        >
          <el-input v-model.number="addForm.orderBy" placeholder="请输入排序依据" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addDialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="submitAddPermissionForm(addFormRef)">
            提交
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 编辑权限对话框 -->
    <el-dialog v-model="editDialogFormVisible" title="编辑权限" width="600px">
      <el-form :model="editForm" ref="editFormRef">
        <el-form-item
            label="权限名"
            :label-width="formLabelWidth"
            prop="name"
            :rules="[
              { required: true, message: '权限名是必填项'},
              { max:30, message: '权限名不得超过 30 个字符'},
            ]"
        >
          <el-input v-model="editForm.name"
                    placeholder="请输入权限名"
                    autocomplete="off"
                    v-loading="editForm.id === ''"
          />
        </el-form-item>
        <el-form-item
            label="权限代码"
            :label-width="formLabelWidth"
            prop="code"
            :rules="[
              { required: true, message: '权限代码是必填项'},
              { max:30, message: '权限代码最多 30 个字符'}
            ]"
        >
          <el-input v-model="editForm.code"
                    placeholder="请输入权限代码"
                    autocomplete="off"
                    v-loading="editForm.id === ''"
          />
        </el-form-item>
        <el-form-item
            label="是否前端路径"
            :label-width="formLabelWidth"
            prop="isMenu"
            :rules="[
              { required: true, message: '是否是前端路径是必填项'},
              { type: 'number', message: '是否是前端路径必须是数值类型' },
            ]"
        >
          <el-input v-model.number="editForm.isMenu"
                    placeholder="1: 是, 0: 否"
                    autocomplete="off"
                    v-loading="editForm.id === ''"
          />
        </el-form-item>
        <el-form-item
            label="前端路径"
            :label-width="formLabelWidth"
            prop="path"
            :rules="[
              { max:50, message: '前端路径最多 50 个字符'}
            ]"
        >
          <el-input v-model="editForm.path"
                    placeholder="请输入前端路径"
                    autocomplete="off"
                    v-loading="editForm.id === ''"
          />
        </el-form-item>
        <el-form-item
            label="排序依据"
            :label-width="formLabelWidth"
            prop="orderBy"
            :rules="[
              { required: true, message: '排序依据是必填项'},
              { type: 'number', message: '排序依据必须是数值类型' },
            ]"
        >
          <el-input v-model.number="editForm.orderBy"
                    placeholder="请输入排序依据"
                    autocomplete="off"
                    v-loading="editForm.id === ''"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="submitEditPermissionForm(editFormRef)">
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

// 权限表格数据加载动画标记
const isPermissionTableLoading = ref(true);

//分页相关:
//页标
const pageNo = ref(1);

//每页记录数
const pageSize = ref(10);

//页标总大小
const totalSize = ref(0);

// 对话框相关
// 添加权限表单 ref
const addFormRef = ref<FormInstance>();

// 编辑权限表单 ref
const editFormRef = ref<FormInstance>();

// 添加对话框是否显示
const addDialogFormVisible = ref(false);

// 权限管理对话框是否显示
const permissionManageDialogVisible = ref(false);

// 编辑对话框是否显示
const editDialogFormVisible = ref(false);

// 对话框内表单宽度
const formLabelWidth = '110px';

// 添加对话框内表单内容
const addForm = reactive({
  name: '',
  code: '',
  isMenu: null,
  path: '',
  orderBy: null
})

// 添加对话框内表单内容
const editForm = reactive({
  id: '',
  name: '',
  code: '',
  isMenu: null,
  path: '',
  orderBy: null
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

// 权限表格数据接口
interface Permission {
  id: string
  name: string
  code: string
  isMenu: number
  path: string
  orderBy: number
}

// 权限表格内容
const PermissionTableData = reactive<Permission[]>([]);

// 表格过滤搜索相关
const search = ref('')
const filterTableData = computed(() =>
    PermissionTableData.filter(
        (data) =>
            !search.value ||
            data.name.toLowerCase().includes(search.value.toLowerCase())
    )
)

// 当前权限 ID
const PermissionId = ref();

/**
 * 显示现在的每页显示记录数量
 * @param val 每页显示记录数量
 */
const handleCurrentChange = (val: number) => {
  // 获取权限列表
  loadPermissionList();
  // 获取权限总量
  loadPermissionTotalSize();
}

/**
 * 修改每页显示记录数量
 * @param val 每页显示记录数量
 */
const handleSizeChange = (val: number) => {
  // 获取权限列表
  loadPermissionList();
  // 获取权限总量
  loadPermissionTotalSize();
}

/**
 * 编辑按钮点击事件
 *
 * @param index 当前行下标
 * @param row 当前行内容
 */
const handleEdit = (index: number, row: Permission) => {
  // 置空 id , 开启加载动画
  editForm.id = '';
  // 打开编辑权限对话框
  editDialogFormVisible.value = true;
  // 获取权限内容列表
  $axios.post("/permissionManage/getPermissionById", {
    id: row.id
  }).then((resp) => {
    // 刷新编辑表单内容
    editForm.id = resp.data.id;
    editForm.name = resp.data.name;
    editForm.code = resp.data.code;
    editForm.isMenu = resp.data.isMenu;
    editForm.path = resp.data.path;
    editForm.orderBy = resp.data.orderBy;
  }).catch(() => {
    // 关闭编辑权限对话框
    editDialogFormVisible.value = false;
  });
}


/**
 * 获取权限总量
 */
const loadPermissionTotalSize = () => {
  $axios.post("/permissionManage/getPermissionTotalSize").then((resp) => {
    totalSize.value = resp.data;
  });
}

/**
 * 添加权限按钮点击事件
 */
const addPermission = () => {
  // 打开添加权限对话框
  addDialogFormVisible.value = true;
}

/**
 * 刷新按钮点击事件
 */
const refreshTableData = () => {
  loadPermissionList();
  loadPermissionTotalSize();
}

/**
 * 获取表格数据权限列表
 */
const loadPermissionList = () => {
  // 开启加载动画
  isPermissionTableLoading.value = true;
  $axios.post("/permissionManage/getPermissionListByPageCondition", {
    pageNo: (pageNo.value - 1) * pageSize.value,
    pageSize: pageSize.value,
  }).then((resp) => {
    PermissionTableData.length = 0;
    PermissionTableData.push(...resp.data);
    // 关闭加载动画
    isPermissionTableLoading.value = false;
  }).catch(() => {
    // 关闭加载动画
    isPermissionTableLoading.value = false;
  });
}

/**
 * 获取表格数据权限列表
 */
const submitAddPermissionForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if (valid) {
      $axios.post("/permissionManage/addPermission", addForm).then((resp) => {
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
const submitEditPermissionForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if (valid) {
      $axios.post("/permissionManage/editPermission", editForm).then((resp) => {
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
  // 获取表格数据权限列表
  loadPermissionList();
  // 获取数据总量
  loadPermissionTotalSize();
})
</script>

<style scoped>
/* 操作区: 添加权限按钮 */
.operateRow {
  margin-bottom: 10px;
}

/* 标页码样式 */
.page-pagination {
  margin: 10px;
}
</style>
