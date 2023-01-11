<template>
  <!-- 整个布局 -->
  <div id="back">

    <el-row justify="space-between">
      <!-- 添加按钮 -->
      <el-col :span="3">
        <el-button type="primary" plain @click="addDialogVisible = true">
          <el-icon>
            <Plus/>
          </el-icon>
        </el-button>
      </el-col>
      <!-- 搜索栏 -->
      <el-col :span="6">
        <el-input v-model="searchTypeCode" placeholder="请输入代码" class="typeCodeSearchField" clearable/>
        <el-button type="primary" class="searchBtn" @click="handleSelect">
          <el-icon>
            <Search/>
          </el-icon>
        </el-button>
      </el-col>
    </el-row>

    <!-- 数据字典类型表 -->
    <el-table :data="dictionaryTypeTableData"
              class="DictionaryTypeTable"
              @selection-change="handleSelectionChange"
              v-loading="isDicTypeDataLoading"
    >
      <!-- 多选 -->
      <el-table-column type="selection" width="55"/>
      <!-- typeCode -->
      <el-table-column property="typeCode" label="typeCode" width="200" sortable/>
      <!-- comment -->
      <el-table-column property="comment" label="备注,【半角逗号前为备注主要内容，后为详细内容】" width="420"/>
      <!-- operations 的修改和删除按钮 -->
      <el-table-column label="动作" align="right">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-popconfirm title="删除前请确保数据字典值表中已无对应数据"
                         @confirm="handleDelete(scope.$index, scope.row)"
                         width="200px"
          >
            <template #reference>
              <el-button size="small" type="danger" plain>删除</el-button>
            </template>
          </el-popconfirm>
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

    <!-- 添加数据字典类型对话框 -->
    <el-dialog v-model="addDialogVisible" title="数据字典类型添加" width="600px">
      <el-form :model="addForm" ref="addFormRef">
        <el-form-item
            label="code"
            :label-width="formLabelWidth"
            prop="typeCode"
            :rules="[
            { required: true, message: '数据字典类型 code 是必选项'},
            { max:50, message: '数据字典类型 code 最多 50 字符'}
          ]"
        >
          <el-input v-model="addForm.typeCode" placeholder="请输入类型代码" autocomplete="off" clearable/>
        </el-form-item>
        <el-form-item
            label="备注"
            :label-width="formLabelWidth"
            prop="comment"
            :rules="[
            { max:100, message: '数据字典类型备注最多 100 字符'}
          ]"
        >
          <el-input
              v-model="addForm.comment"
              :rows="3"
              type="textarea"
              placeholder="请输入备注"
              autocomplete="off"
          />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addDictionaryType(addFormRef)">
          提交
        </el-button>
      </span>
      </template>
    </el-dialog>

    <!-- 编辑数据字典类型对话框 -->
    <el-dialog v-model="editDialogVisible" title="数据字典类型修改" width="600px">
      <el-form :model="editForm" ref="editFormRef">
        <el-form-item
            label="code"
            :label-width="formLabelWidth"
            prop="typeCode"
            :rules="[
            { required: true, message: '数据字典类型 code 是必选项'},
            { max:50, message: '数据字典类型 code 最多 50 字符'}
          ]"
        >
          <el-input v-model="editForm.typeCode" placeholder="请输入类型代码" autocomplete="off"
                    v-loading="editForm.id === ''" clearable/>
        </el-form-item>
        <el-form-item
            label="备注"
            :label-width="formLabelWidth"
            prop="comment"
            :rules="[
            { max:100, message: '数据字典类型备注最多 100 字符'}
          ]"
        >
          <el-input
              v-model="editForm.comment"
              :rows="3"
              type="textarea"
              placeholder="请输入备注"
              autocomplete="off"
              v-loading="editForm.id === ''"
          />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="editDictionaryType(editFormRef)">
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
import {ElMessage, ElTable, FormInstance} from 'element-plus';
import {onMounted, reactive, ref} from 'vue';
import $axios from "../plugins/axiosInstance.js";

// 表格数据加载标记
const isDicTypeDataLoading = ref(true);

// 添加表单 ref
const addFormRef = ref<FormInstance>()

// 编辑表单 ref
const editFormRef = ref<FormInstance>()

// 表格中内容的接口
interface DictionaryType {
  id: string
  typeCode: string
  comment: string
}

// 表格多选参数
const multipleSelection = ref<DictionaryType[]>([]);

// 数据字典类型表格数据
const dictionaryTypeTableData = reactive<DictionaryType[]>([]);

// 代码模糊搜索输入内容
const searchTypeCode = ref('');

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

// 对话框相关
// 添加对话框是否显示
const addDialogVisible = ref(false);

// 编辑对话框是否显示
const editDialogVisible = ref(false);

// 对话框内表单宽度
const formLabelWidth = '100px';

// 添加对话框内表单内容
const addForm = reactive({
  typeCode: '',
  comment: '',
})

// 编辑对话框内表单内容
const editForm = reactive({
  id: '',
  typeCode: '',
  comment: '',
})

/**
 * 显示现在的每页显示记录数量
 * @param val 每页显示记录数量
 */
const handleCurrentChange = (val: number) => {
  // 重新获取表格数据
  loadDictionaryTypeList();
  // 重新获取数据字典表数据总量
  loadDictionaryTypeTotalSize();
}

/**
 * 修改每页显示记录数量
 * @param val 每页显示记录数量
 */
const handleSizeChange = (val: number) => {
  // 重新获取表格数据
  loadDictionaryTypeList();
  // 重新获取数据字典表数据总量
  loadDictionaryTypeTotalSize();
}

/**
 * 编辑数据字典类型信息
 * @param index 下标
 * @param row 对应数据
 */
const handleEdit = (index: number, row: DictionaryType) => {
  // 清空孤儿数据
  editForm.id = '';
  editForm.typeCode = '';
  editForm.comment = '';
  // 打开编辑数据字典类型对话框
  editDialogVisible.value = true;
  $axios.post("/dicType/getDicTypeById", {
    id: row.id
  }).then((resp) => {
    // 更新当前数据字典类型数据
    editForm.id = resp.data.id;
    editForm.typeCode = resp.data.typeCode;
    editForm.comment = resp.data.comment;
  }).catch(() => {
    // 关闭添加对话框
    editDialogVisible.value = false;
  });
}

/**
 * 删除函数，删除用户信息
 * @param index 下标
 * @param row 对象
 */
const handleDelete = (index: number, row: DictionaryType) => {
  $axios.post("/dicType/removeDicTypeById", {
    id: row.id
  }).then((resp) => {
    // 删除成功提示
    ElMessage({
      type: 'success',
      message: resp["msg"]
    });
    // 重新获取表格数据
    loadDictionaryTypeList();
    // 重新获取数据字典表数据总量
    loadDictionaryTypeTotalSize();
  });
}

/**
 * 选择记录的函数
 * @param val 被选择的用户列表
 */
const handleSelectionChange = (val: DictionaryType[]) => {
  multipleSelection.value = val;
}

/**
 * 模糊搜索按钮点击事件
 */
const handleSelect = () => {
  // 重新获取表格数据
  loadDictionaryTypeList();
  // 重新获取数据字典表数据总量
  loadDictionaryTypeTotalSize();
}

/**
 * 获取数据字典类型表格数据
 */
const loadDictionaryTypeList = () => {
  // 开启加载状态
  isDicTypeDataLoading.value = true;
  // 发送请求获取数据字典类型列表
  $axios.post("/dicType/getDicTypeListByPageCondition", {
    pageNo: (pageNo.value - 1) * pageSize.value,
    pageSize: pageSize.value,
    typeCode: searchTypeCode.value
  }).then((resp) => {
    // 清空列表内容
    dictionaryTypeTableData.length = 0;
    // 更新数据字典类型表格数据
    dictionaryTypeTableData.push(...resp.data);
    // 关闭加载动画
    isDicTypeDataLoading.value = false;
  }).catch(() => {
    // 清空列表内容
    dictionaryTypeTableData.length = 0;
    // 关闭加载动画
    isDicTypeDataLoading.value = false;
  });
}

/**
 * 获取数据字典类型数据总量
 */
const loadDictionaryTypeTotalSize = () => {
  $axios.post("/dicType/getDicTypeTotalSizeByCondition", {
    typeCode: searchTypeCode.value
  }).then((resp) => {
    // 更新数据字典类型表格数据
    totalSize.value = resp.data;
  });
}

/**
 * 添加数据字典类型
 */
const addDictionaryType = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      $axios.post("/dicType/addDicType", addForm).then((resp) => {
        // 添加成功提示
        ElMessage({
          type: 'success',
          message: resp["msg"]
        });
        // 关闭添加对话框
        addDialogVisible.value = false;
        // 重新获取表格数据
        loadDictionaryTypeList();
        // 重新获取数据字典表数据总量
        loadDictionaryTypeTotalSize();
      });
    } else {
      return false
    }
  })
}

/**
 * 编辑数据字典类型
 */
const editDictionaryType = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      $axios.post("/dicType/editDicType", editForm).then((resp) => {
        // 编辑成功提示
        ElMessage({
          type: 'success',
          message: resp["msg"]
        });
        // 关闭编辑对话框
        editDialogVisible.value = false;
        // 重新获取表格数据
        loadDictionaryTypeList();
        // 重新获取数据字典表数据总量
        loadDictionaryTypeTotalSize();
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
  // 获取数据字典类型表格数据
  loadDictionaryTypeList();
  // 获取数据字典类型数据总量
  loadDictionaryTypeTotalSize();
})
</script>

<style scoped>
/* 整体布局样式 */
#back {
  width: 1100px;
  margin: 0 auto;
}

/* typeCode 搜索框 */
.typeCodeSearchField {
  width: 205px;
}

/* 搜索按钮 */
.searchBtn {
  margin-left: 10px;
}

/* 数据字典类型表格 */
.DictionaryTypeTable {
  margin-top: 10px;
}

/* 标页码样式 */
.page-pagination {
  margin: 10px;
}
</style>
