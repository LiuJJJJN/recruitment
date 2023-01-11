<template>
  <!-- 整个布局 -->
  <div id="back">

    <el-row class="searchRow" justify="space-between">
      <!--删除和添加按钮-->
      <el-col :span="3">
        <el-button type="primary" plain @click="addDictionaryValueBtn">
          <el-icon>
            <Plus/>
          </el-icon>
        </el-button>
        <el-popconfirm
            title="确认批量删除这些数据吗?"
            width="220px"
            @confirm="removeDictionaryValueBatch"
        >
          <template #reference>
            <el-button type="danger" plain>
              <el-icon>
                <Delete/>
              </el-icon>
            </el-button>
          </template>
        </el-popconfirm>
      </el-col>
      <el-col :span="10" class="searchCol">
        <!--搜索栏-->
        <el-input v-model="searchTypeCode" placeholder="请输入 company" clearable class="code-input"/>
        <el-input v-model="searchValueCode" placeholder="请输入 deptName" clearable class="code-input"/>
        <el-button type="primary" @click="reFlushTableData">
          <el-icon>
            <Search/>
          </el-icon>
        </el-button>
      </el-col>
    </el-row>

    <!-- 用户信息表 -->
    <el-table ref="multipleTableRef"
              :data="dictionaryValueTableData"
              @selection-change="handleSelectionChange"
              v-loading="isDicValueDataLoading"
    >
      <!-- 多选列表 -->
      <el-table-column type="selection" width="55"/>
      <el-table-column property="company" label="公司" width="200" sortable/>
      <el-table-column property="name" label="部门" width="200" show-overflow-tooltip sortable/>
      <el-table-column property="comment" label="备注" width="380" show-overflow-tooltip/>
      <!-- operations 的 edit 和 delete 按钮 -->
      <el-table-column label="动作" align="right">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-popconfirm title="确认删除此条数据吗?"
                         @confirm="handleDelete(scope.$index, scope.row)"
                         width="180px"
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
        class="page-pagination"/>

    <!-- 添加数据字典值对话框 -->
    <el-dialog v-model="addDialogVisible" title="数据字典值添加" width="600px">
      <el-form :model="addForm" ref="addFormRef">
        <el-form-item
            label="typeCode"
            :label-width="formLabelWidth"
            prop="typeId"
            :rules="[
              { required: true, message: '数据字典类型是必选项'},
            ]"
        >
          <el-select
              v-model="addForm.typeId"
              placeholder="请选择类型代码"
              style="width: 460px"
              v-loading="typeCodeList.arr.length === 0"
          >
            <el-option
                v-for="item in typeCodeList.arr"
                :key="item"
                :label="item.typeCode"
                :value="item.id"
            >
              <span style="float: left">{{ item.typeCode }}</span>
              <span class="typeCodeComment">{{ formatTypeCodeComment(item.comment) }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            label="valueKey"
            :label-width="formLabelWidth"
            prop="valueKey"
            :rules="[
              { required: true, message: '数据字典值 key 是必填项' },
              { type: 'number', message: '数据字典值 key 必须是数值类型' },
            ]"
        >
          <el-input v-model.number="addForm.valueKey" placeholder="请输入 valueKey" autocomplete="off" clearable/>
        </el-form-item>
        <el-form-item
            label="valueCode"
            :label-width="formLabelWidth"
            prop="valueCode"
            :rules="[
              { required: true, message: '数据字典值 code 是必填项'},
              { max:200, message: '数据字典值 code 最多 200 字符'}
            ]"
        >
          <el-input v-model="addForm.valueCode" placeholder="请输入 valueCode" autocomplete="off" clearable/>
        </el-form-item>
        <el-form-item
            label="备注"
            :label-width="formLabelWidth"
            prop="comment"
            :rules="[
              { max:500, message: '数据字典值备注最多 500 字符'}
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
        <el-button type="primary" @click="addDictionaryValue(addFormRef)">
          提交
        </el-button>
      </span>
      </template>
    </el-dialog>

    <!-- 编辑数据字典类型对话框 -->
    <el-dialog v-model="editDialogVisible" title="数据字典值修改" width="600px">
      <el-form :model="editForm" ref="editFormRef">
        <el-form-item
            label="typeCode"
            :label-width="formLabelWidth"
            prop="typeId"
            :rules="[
            { required: true, message: '数据字典类型是必选项'},
          ]"
        >
          <el-select
              v-model="editForm.typeId"
              placeholder="请选择类型代码"
              style="width: 460px"
              v-loading="editForm.id === ''"
          >
            <el-option
                v-for="item in typeCodeList.arr"
                :key="item"
                :label="item.typeCode"
                :value="item.id"
            >
              <span style="float: left">{{ item.typeCode }}</span>
              <span class="typeCodeComment">{{ formatTypeCodeComment(item.comment) }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            label="valueKey"
            :label-width="formLabelWidth"
            prop="valueKey"
            :rules="[
            { required: true, message: '数据字典值 key 是必填项' },
            { type: 'number', message: '数据字典值 key 必须是数值类型' },
          ]"
        >
          <el-input v-model.number="editForm.valueKey"
                    placeholder="请输入 valueKey"
                    autocomplete="off"
                    clearable
                    v-loading="editForm.id === ''"
          />
        </el-form-item>
        <el-form-item
            label="valueCode"
            :label-width="formLabelWidth"
            prop="valueCode"
            :rules="[
            { required: true, message: '数据字典值 code 是必填项'},
            { max:200, message: '数据字典值 code 最多 200 字符'}
          ]"
        >
          <el-input v-model="editForm.valueCode"
                    placeholder="请输入 valueCode"
                    autocomplete="off"
                    clearable
                    v-loading="editForm.id === ''"
          />
        </el-form-item>
        <el-form-item
            label="备注"
            :label-width="formLabelWidth"
            prop="comment"
            :rules="[
              { max:500, message: '数据字典值备注最多 500 字符'}
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
        <el-button type="primary" @click="editDictionaryValue(editFormRef)">
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

// 表格数据加载动画开启标记
const isDicValueDataLoading = ref(true);

// 类型 code 自动补全输入框加载动画开启标记
const isAutocompleteLoading = ref(true);

// 模糊搜索 类型 code
const searchTypeCode = ref('');

// 模糊搜索 值 code
const searchValueCode = ref('');

//分页相关:
//页标
const pageNo = ref(1);

//每页记录数
const pageSize = ref(10);

//页标总大小
const totalSize = ref(0);

// 表格 ref
const multipleTableRef = ref<InstanceType<typeof ElTable>>();

// 添加表单 ref
const addFormRef = ref<FormInstance>()

// 编辑表单 ref
const editFormRef = ref<FormInstance>()

// 表格中内容的接口
interface dictionaryValue {
  canmpany: string
  name: string
  comment: string
}

// 表格多选参数
const multipleSelection = ref<dictionaryValue[]>([]);

// 数据字典值表格数据
const dictionaryValueTableData = reactive<dictionaryValue[]>([]);

//自动输入补全内容接口
interface typeCodeItem {
  value: string
}

//自动补全内容列表
const typeCodes = ref<typeCodeItem[]>([]);

// 对话框相关
// 添加对话框是否显示
const addDialogVisible = ref(false);

// 修改对话框是否显示
const editDialogVisible = ref(false);

// 对话框内表单宽度
const formLabelWidth = '100px';

// 添加对话框内表单内容
const addForm = reactive({
  typeId: '',
  valueKey: '',
  valueCode: '',
  comment: ''
})

// 修改对话框内表单内容
const editForm = reactive({
  id: '',
  typeId: '',
  valueKey: '',
  valueCode: '',
  comment: ''
})

// 类型代码列表
const typeCodeList = reactive({
  arr: []
})

/**
 * 显示现在的每页显示记录数量
 * @param val 每页显示记录数量
 */
const handleCurrentChange = (val: number) => {
  // 获取数据字典值数据列表
  loadDictionaryValueList();
  // 获取数据字典值数据总量
  loadDictionaryValueTotalSize();
}

/**
 * 修改每页显示记录数量
 * @param val 每页显示记录数量
 */
const handleSizeChange = (val: number) => {
  // 获取数据字典值数据列表
  loadDictionaryValueList();
  // 获取数据字典值数据总量
  loadDictionaryValueTotalSize();
}

/**
 * 编辑函数
 * @param index  下标
 * @param row  内容
 */
const handleEdit = (index: number, row: dictionaryValue) => {
  // // 清空孤儿数据
  // editForm.id = '';
  // editForm.typeId = '';
  // editForm.valueKey = '';
  // editForm.valueCode = '';
  // editForm.comment = '';
  // // 打开编辑对话框
  // editDialogVisible.value = true;
  // // 加载数据字典类型 code 列表
  // $axios.post("/dicType/getDicTypeList").then((resp) => {
  //   // 赋值
  //   typeCodeList.arr = resp.data;
  // }).catch(() => {
  //   // 关闭添加对话框
  //   editDialogVisible.value = false;
  // });
  // // 获取对应数据内容
  // $axios.post("/dicValue/getDicValueById", {
  //   id: row.id
  // }).then((resp) => {
  //   // 赋值到修改表单
  //   editForm.id = resp.data.id;
  //   editForm.typeId = resp.data.typeId;
  //   editForm.valueKey = resp.data.valueKey;
  //   editForm.valueCode = resp.data.valueCode;
  //   editForm.comment = resp.data.comment;
  // }).catch(() => {
  //   // 关闭添加对话框
  //   editDialogVisible.value = false;
  // });
}

/**
 * 删除函数
 * @param index  下标
 * @param row  内容
 */
const handleDelete = (index: number, row: dictionaryValue) => {
  // $axios.post("/dicValue/removeDicValueById", {
  //   id: row.id
  // }).then((resp) => {
  //   // 删除成功提示
  //   ElMessage({
  //     type: 'success',
  //     message: resp["msg"]
  //   });
  //   // 重新获取表格数据
  //   loadDictionaryValueList();
  //   // 重新获取数据字典表数据总量
  //   loadDictionaryValueTotalSize();
  // });
}

/**
 * 选择记录的函数
 * @param val 被选择的用户列表
 */
const handleSelectionChange = (val: dictionaryValue[]) => {
  multipleSelection.value = val;
}

/**
 * 获取输入建议的方法
 * @param queryString 搜索参数
 * @param cb 回调用参数
 */
const querySearch = (queryString: string, cb: any) => {
  const results = queryString
      ? typeCodes.value.filter(createFilter(queryString))
      : typeCodes.value
  // call callback function to return suggestions
  cb(results)
}

/**
 * 创建过滤器
 * @param queryString 需要过滤的参数
 */
const createFilter = (queryString: string) => {
  return (item: typeCodeItem) => {
    return (
        item.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
    )
  }
}

/**
 * 载入自动补全的内容
 */
const loadAll = () => {
  let typeCodeList = [];
  // 加载数据字典类型列表
  $axios.post("/dicType/getDicTypeList").then((resp) => {
    resp.data.forEach(item => {
      typeCodeList.push({
        value: item.typeCode
      })
    });
    // 结束自动补全输入框加载动画
    isAutocompleteLoading.value = false;
  }).catch(() => {
    // 结束自动补全输入框加载动画
    isAutocompleteLoading.value = false;
  });
  return typeCodeList;
}

/**
 * 手动触发选中建议事件
 * @param item 搜索关键字
 */
const handleSelect = (item: typeCodeItem) => {
  console.log(item);
}

/**
 * 获取数据字典值数据列表
 */
const loadDictionaryValueList = () => {
  // 开启加载状态
  isDicValueDataLoading.value = true;
  // 发送请求获取数据字典值列表
  $axios.post("/deptManage/getDeptListByPageCondition", {
    pageNo: (pageNo.value - 1) * pageSize.value,
    pageSize: pageSize.value,
    company: searchTypeCode.value,
    deptName: searchValueCode.value
  }).then((resp) => {
    // 清空列表内容
    dictionaryValueTableData.length = 0;
    // 更新数据字典值表格数据
    dictionaryValueTableData.push(...resp.data);
    // 关闭加载动画
    isDicValueDataLoading.value = false;
  }).catch(() => {
    // 清空列表内容
    dictionaryValueTableData.length = 0;
    // 关闭加载动画
    isDicValueDataLoading.value = false;
  });
}

/**
 * 获取数据字典值数据总量
 */
const loadDictionaryValueTotalSize = () => {
  $axios.post("/deptManage/getDeptListTotalSizeByCondition", {
    company: searchTypeCode.value,
    deptName: searchValueCode.value
  }).then((resp) => {
    // 更新数据字典值表格数据
    totalSize.value = resp.data;
  });
}

/**
 * 添加数据字典值数据
 */
const addDictionaryValue = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      $axios.post("/dicValue/addDicValue", addForm).then((resp) => {
        // 添加成功提示
        ElMessage({
          type: 'success',
          message: resp["msg"],
        })
        // 关闭添加对话框
        addDialogVisible.value = false;
        // 获取数据字典值数据列表
        loadDictionaryValueList();
        // 获取数据字典值数据总量
        loadDictionaryValueTotalSize();
      });
    } else {
      return false
    }
  })
}

/**
 * 添加按钮点击事件: 打开添加对话框、加载数据
 */
const addDictionaryValueBtn = () => {
  // 重置类型代码列表
  typeCodeList.arr = [];
  // 打开添加数据字典值对话框
  addDialogVisible.value = true;
  // 加载数据字典类型列表
  $axios.post("/dicType/getDicTypeList").then((resp) => {
    // 赋值
    typeCodeList.arr = resp.data;
  }).catch(() => {
    // 关闭添加对话框
    addDialogVisible.value = false;
  });
}

/**
 * 编辑数据字典值
 */
const editDictionaryValue = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      $axios.post("/dicValue/editDicValue", editForm).then((resp) => {
        // 编辑成功提示
        ElMessage({
          type: 'success',
          message: resp["msg"]
        });
        // 关闭编辑对话框
        editDialogVisible.value = false;
        // 重新获取表格数据
        loadDictionaryValueList();
        // 重新获取数据字典表数据总量
        loadDictionaryValueTotalSize();
      });
    } else {
      return false
    }
  })
}

/**
 * 格式化数据
 */
const formatTypeCodeComment = (str: String) => {
  return str.match('^(?:\\d+,\\d+)?[^,]+').toString();
}

/**
 * 批量删除数据字典值数据
 */
const removeDictionaryValueBatch = () => {
  // // 当没有选中任何数据时提示
  // if (multipleSelection.value.length === 0) {
  //   ElMessage({
  //     type: 'info',
  //     message: "请选中要删除的数据后重试"
  //   });
  //   return;
  // }
  // // 获取选中的 id 列表
  // let idList = [];
  // multipleSelection.value.forEach(item => {
  //   idList.push(item.id);
  // });
  // $axios.post("/dicValue/removeDicValueBatch", idList).then((resp) => {
  //   // 删除成功提示
  //   ElMessage({
  //     type: 'success',
  //     message: resp["msg"]
  //   });
  //   // 重新获取表格数据
  //   loadDictionaryValueList();
  //   // 重新获取数据字典表数据总量
  //   loadDictionaryValueTotalSize();
  // });
}

/**
 * 搜索区域搜索按钮点击事件
 */
const reFlushTableData = () => {
  // 重新获取表格数据
  loadDictionaryValueList();
  // 重新获取数据字典表数据总量
  loadDictionaryValueTotalSize();
}

/**
 * 在组件挂载完成后执行的方法
 */
onMounted(() => {
  // 获取数据字典类型 code 列表
  typeCodes.value = loadAll();
  // 获取数据字典值数据列表
  loadDictionaryValueList();
  // 获取数据字典值数据总量
  loadDictionaryValueTotalSize();
})
</script>

<style scoped>
/* 整体布局样式 */
#back {
  width: 1100px;
  margin: 0 auto;
}

/* 表格上方搜索区域 */
.searchRow {
  margin-bottom: 10px;
}

/* 搜索区组件间距 */
.searchRow > *, .searchCol > * {
  margin-right: 10px;
}

/* 模糊搜索类型 code 外层 div */
.loadingDiv {
  display: inline-block;
}

/* 数据字典值 code 搜索输入框 */
.code-input {
  width: 191px;
}

/* 添加对话框中 typeCode 的选项备注 */
.typeCodeComment {
  float: right;
  color: var(--el-text-color-secondary);
  font-size: 13px;
}

/* 标页码样式 */
.page-pagination {
  margin: 10px;
}
</style>
