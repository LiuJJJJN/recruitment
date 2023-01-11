<template>
  <div>
    <div>
      <!-- 第一行: 模糊搜索 -->
      <el-form label-position="left">
        <el-form-item :label="$t('模糊搜索')">
          <el-input v-model="vagueInput" :placeholder="$t('请输入')" class="input-with-select" clearable>
            <template #prepend>
              <el-select
                  v-model="vagueSelect"
                  :placeholder="$t('请选择')"
                  class="prepend-select"
                  v-loading="isVagueSelectItemLoading"
              >
                <el-option v-for="item in vagueSelectItem" :label="$t(item.valueCode)" :value="item.comment"/>
              </el-select>
            </template>
            <template #append>
              <el-button @click="refreshData">
                <el-icon>
                  <Search/>
                </el-icon>
              </el-button>
            </template>
          </el-input>
          <!-- 折叠按钮 -->
          <el-button @click="showCollapse = !showCollapse"
                     size="small"
                     style="margin-bottom: -7px"
                     type="primary"
                     text
          >
            <el-icon>
              <ArrowUpBold v-show="showCollapse"/>
              <ArrowDownBold v-show="!showCollapse"/>
            </el-icon>
            {{ showCollapse ? $t('收起') : $t('更多') }}
          </el-button>
        </el-form-item>
      </el-form>

      <!-- 折叠面板 -->
      <el-collapse-transition>
        <!-- 模糊查询表单 -->
        <el-form v-show="showCollapse" label-position="left">
          <!-- 第二行: 面试状态选择 -->
          <el-form-item :label="$t('面试状态')">
            <div v-loading="isIVStatusLoading">
              <el-check-tag class="check-tag"
                            v-for="item in IVStatus"
                            :key="item"
                            :checked="item.checked"
                            @change="onChange(item)"
              >
                {{ $t(item.valueCode) }}
              </el-check-tag>
            </div>
          </el-form-item>
          <!-- 第三行: 录入时间选择 -->
          <el-form-item :label="$t('录入时间')">
            <div v-loading="isEntryTimeLoading">
              <el-check-tag class="check-tag"
                            v-for="item in entryTime"
                            :key="item"
                            :checked="item.checked"
                            @change="onChange(item)"
              >
                {{ $t(item.valueCode) }}
              </el-check-tag>
            </div>
          </el-form-item>
          <!-- 第四行: 毕业时间选择 -->
          <el-form-item :label="$t('毕业时间')">
            <div v-loading="isGradTimeLoading">
              <el-check-tag class="check-tag"
                            v-for="item in gradTime"
                            :key="item"
                            :checked="item.checked"
                            @change="onChange(item)"
              >
                {{ item.valueCode }}
              </el-check-tag>
            </div>
            <el-date-picker
                v-model="startYear"
                type="year"
                style="margin-right: 5px; width: 120px"
                value-format="YYYY"
            />
            <span>-</span>
            <el-date-picker
                v-model="endYear"
                type="year"
                style="margin-left: 5px; margin-right: 10px; width: 120px"
                value-format="YYYY"
            />
            <el-button circle @click="refreshData">
              <el-icon>
                <Search/>
              </el-icon>
            </el-button>
          </el-form-item>
        </el-form>
      </el-collapse-transition>
    </div>

    <div>
      <!-- 批量动作 -->
      <el-dropdown placement="bottom-start">
        <el-button plain>
          {{ $t('批量动作') }}
          <el-icon class="el-icon--right">
            <arrow-down/>
          </el-icon>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="toAutoAssignReviewer(multipleSelection)">{{
                $t('自动分配 Reviewer')
              }}
            </el-dropdown-item>
            <el-dropdown-item @click="toEditStatus(multipleSelection)">{{ $t('更新面试状态') }}</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>

      <!-- 批量导出 -->
      <el-button
          type="primary"
          class="right-button"
          @click="exportExcel"
      >
        {{ $t('导出数据') }}
        <el-icon class="el-icon--right">
          <Download/>
        </el-icon>
      </el-button>
    </div>

    <div>
      <!-- 表格 -->
      <el-table
          :data="filterTableData"
          ref="multipleTableRef"
          stripe
          v-loading="isIVTableDataLoading"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"/>
        <el-table-column :label="$t('面试编号')" prop="id" width="160px" sortable>
          <template #default="scope">
            <span v-text="formatId(scope.row.id)"></span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('姓名')" prop="name" width="100px" sortable/>
        <el-table-column :label="$t('性别')" prop="sex" sortable>
          <template #default="scope">
            <span v-text="formatDicValueKey2Code(sexList, scope.row.sex)"></span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('录入时间')" prop="createDate" width="160px" sortable/>
        <el-table-column :label="$t('状态')" prop="status" width="300px" sortable>
          <template #default="scope">
            <el-progress
                :text-inside="true"
                :stroke-width="23"
                :percentage="scope.row.status / 9 * 100"
                status="exception"
                :color="customColors"
                v-if="scope.row.status"
            >
              <span v-text="formatDicValueKey2Code(IVStatus, scope.row.status)" class="statusSpan"></span>
            </el-progress>
          </template>
        </el-table-column>
        <el-table-column :label="$t('学历')" prop="education" width="100px" sortable>
          <template #default="scope">
            <span v-text="formatDicValueKey2Code(educationList, scope.row.education)"></span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('毕业日期')" prop="gradDate" width="160px" sortable/>
        <el-table-column :label="$t('毕业学校')" prop="gradSchool" width="180px" sortable/>
        <el-table-column :label="$t('专业')" prop="major" width="180px" sortable/>
        <el-table-column :label="$t('技术能力')" prop="techAbility" width="180px" sortable>
          <template #default="scope">
            <span v-text="formatDicValueKey2Code(techAbilityList, scope.row.techAbility)"></span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('语言能力')" prop="langAbility" width="180px" sortable>
          <template #default="scope">
            <span v-text="formatDicValueKey2Code(langAbilityList, scope.row.langAbility)"></span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('其它能力')" prop="otherAbility" width="210px" sortable/>
        <el-table-column :label="$t('简历来源')" prop="source" width="140px" sortable>
          <template #default="scope">
            <span v-text="formatDicValueKey2Code(sourceList, scope.row.source)"></span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('内推人')" prop="internalRecommender" width="180px" sortable/>
        <el-table-column :label="$t('意向部门')" prop="internalDept" width="285px" sortable/>
        <el-table-column :label="$t('是否服从')" prop="obey" width="180px" sortable>
          <template #default="scope">
            <span v-text="formatDicValueKey2Code(obeyList, scope.row.obey)"></span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('工作经验')" prop="workExperience" width="280px" show-overflow-tooltip sortable/>
        <el-table-column :label="$t('自我评价')" prop="selfAssessment" width="280px" show-overflow-tooltip sortable/>
        <el-table-column :label="$t('生日')" prop="birthday" width="150px" sortable/>
        <el-table-column :label="$t('邮箱')" prop="email" width="180px" sortable/>
        <el-table-column :label="$t('电话')" prop="tel" width="180px" sortable/>
        <!--<el-table-column :label="$t('简历编号')" prop="resumeId" width="285px" sortable/>-->
        <el-table-column :label="$t('面试时间')" prop="date" width="160px" sortable/>
        <el-table-column :label="$t('面试官1')" prop="interviewer1" width="160px" sortable>
          <template #default="scope">
            <el-popover
                placement="top-start"
                :width="450"
                trigger="hover"
                v-if="scope.row.interviewer1"
            >
              <template #reference>
                <el-tag class="roleTag">{{ scope.row.interviewer1.name }}</el-tag>
              </template>
              <template #default>
                <el-descriptions
                    :title="scope.row.interviewer1.name"
                    :border="true"
                    column="2"
                >
                  <el-descriptions-item label="公司">{{ scope.row.interviewer1.company }}</el-descriptions-item>
                  <el-descriptions-item label="部门">{{ scope.row.interviewer1.dept }}</el-descriptions-item>
                  <el-descriptions-item label="邮箱">{{ scope.row.interviewer1.email }}</el-descriptions-item>
                  <el-descriptions-item label="角色">
                    <el-tag
                        size="small"
                        v-for="item in scope.row.interviewer1.roleList"
                        :key="item"
                        style="margin-right: 3px"
                    >
                      {{ item }}
                    </el-tag>
                  </el-descriptions-item>
                </el-descriptions>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column :label="$t('面试官2')" prop="interviewer2" width="160px" sortable>
          <template #default="scope">
            <el-popover
                placement="top-start"
                :width="450"
                trigger="hover"
                v-if="scope.row.interviewer2"
            >
              <template #reference>
                <el-tag class="roleTag">{{ scope.row.interviewer2.name }}</el-tag>
              </template>
              <template #default>
                <el-descriptions
                    :title="scope.row.interviewer2.name"
                    :border="true"
                    column="2"
                >
                  <el-descriptions-item label="公司">{{ scope.row.interviewer2.company }}</el-descriptions-item>
                  <el-descriptions-item label="部门">{{ scope.row.interviewer2.dept }}</el-descriptions-item>
                  <el-descriptions-item label="邮箱">{{ scope.row.interviewer2.email }}</el-descriptions-item>
                  <el-descriptions-item label="角色">
                    <el-tag
                        size="small"
                        v-for="item in scope.row.interviewer2.roleList"
                        :key="item"
                        style="margin-right: 3px"
                    >
                      {{ item }}
                    </el-tag>
                  </el-descriptions-item>
                </el-descriptions>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column :label="$t('面试官3')" prop="interviewer3" width="160px" sortable>
          <template #default="scope">
            <el-popover
                placement="top-start"
                :width="450"
                trigger="hover"
                v-if="scope.row.interviewer3"
            >
              <template #reference>
                <el-tag class="roleTag">{{ scope.row.interviewer3.name }}</el-tag>
              </template>
              <template #default>
                <el-descriptions
                    :title="scope.row.interviewer3.name"
                    :border="true"
                    column="2"
                >
                  <el-descriptions-item label="公司">{{ scope.row.interviewer3.company }}</el-descriptions-item>
                  <el-descriptions-item label="部门">{{ scope.row.interviewer3.dept }}</el-descriptions-item>
                  <el-descriptions-item label="邮箱">{{ scope.row.interviewer3.email }}</el-descriptions-item>
                  <el-descriptions-item label="角色">
                    <el-tag
                        size="small"
                        v-for="item in scope.row.interviewer3.roleList"
                        :key="item"
                        style="margin-right: 3px"
                    >
                      {{ item }}
                    </el-tag>
                  </el-descriptions-item>
                </el-descriptions>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column :label="$t('面试官4')" prop="interviewer4" width="160px" sortable>
          <template #default="scope">
            <el-popover
                placement="top-start"
                :width="450"
                trigger="hover"
                v-if="scope.row.interviewer4"
            >
              <template #reference>
                <el-tag class="roleTag">{{ scope.row.interviewer4.name }}</el-tag>
              </template>
              <template #default>
                <el-descriptions
                    :title="scope.row.interviewer4.name"
                    :border="true"
                    column="2"
                >
                  <el-descriptions-item label="公司">{{ scope.row.interviewer4.company }}</el-descriptions-item>
                  <el-descriptions-item label="部门">{{ scope.row.interviewer4.dept }}</el-descriptions-item>
                  <el-descriptions-item label="邮箱">{{ scope.row.interviewer4.email }}</el-descriptions-item>
                  <el-descriptions-item label="角色">
                    <el-tag
                        size="small"
                        v-for="item in scope.row.interviewer4.roleList"
                        :key="item"
                        style="margin-right: 3px"
                    >
                      {{ item }}
                    </el-tag>
                  </el-descriptions-item>
                </el-descriptions>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column :label="$t('简历 Reviewer')" prop="reviewer" width="150px" sortable>
          <template #default="scope">
            <el-popover
                placement="top-start"
                :width="450"
                trigger="hover"
                v-if="scope.row.reviewer"
            >
              <template #reference>
                <el-tag>{{ scope.row.reviewer.name }}</el-tag>
              </template>
              <template #default>
                <el-descriptions
                    :title="scope.row.reviewer.name"
                    :border="true"
                    column="2"
                >
                  <el-descriptions-item label="公司">{{ scope.row.reviewer.company }}</el-descriptions-item>
                  <el-descriptions-item label="部门">{{ scope.row.reviewer.dept }}</el-descriptions-item>
                  <el-descriptions-item label="邮箱">{{ scope.row.reviewer.email }}</el-descriptions-item>
                  <el-descriptions-item label="角色">
                    <el-tag style="margin-right: 3px"
                            size="small"
                            v-for="item in scope.row.reviewer.roleList"
                            :key="item"
                    >{{ item }}
                    </el-tag>
                  </el-descriptions-item>
                </el-descriptions>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column :label="$t('文件路径')" prop="filePath" width="200px" show-overflow-tooltip sortable/>
        <el-table-column fixed="right" :width="lang==='ja'?'500px':'300px'">
          <template #header>
            <el-input v-model="tableFilter" :placeholder="$t('过滤搜索')"/>
          </template>
          <template #default="scope">
            <el-button @click="previewResume(scope.row.filePath)">
              {{ $t('预览简历') }}
            </el-button>
            <el-button @click="downloadResume(scope.row.filePath)">
              {{ $t('下载简历') }}
            </el-button>
            <el-dropdown placement="bottom-end" class="tableDropdown">
              <el-button plain>
                {{ $t('动作') }}
                <el-icon class="el-icon--right">
                  <arrow-down/>
                </el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu v-loading="isOverviewPathLoading">
                  <el-dropdown-item
                      v-for="(item, index) in overviewPathList"
                      :key="index"
                      :disabled="item.path == 'cvReview' && scope.row.status != 2
                      || item.path == 'interviewFeedback' && scope.row.status != 5"
                      @click="routerTo(item.path, {id: scope.row.id})"
                  >
                    {{ $t(item.name) }}
                  </el-dropdown-item>
                  <el-dropdown-item @click="toEditInfo(scope.row)" v-if="isEditInfoExist">
                    {{ $t('更新面试信息') }}
                  </el-dropdown-item>
                  <el-dropdown-item @click="toEditStatus([scope.row])" v-if="isEditStatusExist">
                    {{ $t('更新面试状态') }}
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div>
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
    </div>

    <!-- 更新面试状态对话框 -->
    <el-dialog v-model="editStatusDialogFormVisible" title="更新面试状态" width="500px">
      <el-form :model="editStatusForm" ref="editStatusFormRef">
        <el-form-item label="当前选中" :label-width="formLabelWidth">
          <el-tag class="nameTag" v-for="item in editStatusForm.targetList" :key="item" v-text="item.name"></el-tag>
        </el-form-item>
        <el-form-item
            label="状态"
            :label-width="formLabelWidth"
            prop="interviewStatus"
            :rules="[
              { required: true, message: '状态是必选项'},
            ]"
        >
          <el-select v-model="editStatusForm.interviewStatus" placeholder="请选择要更改的状态" style="width: 360px">
            <el-option v-for="item in IVStatus" :key="item" :label="item.valueCode" :value="item.valueKey"/>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="editStatusDialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="editStatus(editStatusFormRef)">提交</el-button>
      </span>
      </template>
    </el-dialog>

    <!-- 更新面试信息对话框 -->
    <el-dialog v-model="editInterviewInfoDialogFormVisible" title="更新面试信息" width="600px">
      <el-form :model="editStatusForm" ref="editInfoFormRef">
        <el-form-item label="简历 Reviewer" :label-width="infoFormLabelWidth">
          <el-select
              v-model="editInfoForm.reviewer"
              filterable
              placeholder="请选择"
              style="width: 309px; margin-right: 5px"
              clearable
              v-loading="isEditInfoFormLoading"
          >
            <el-option
                v-for="item in reviewerList"
                :key="item"
                :value="item.id"
                :label="item.name"
            >
              <span style="float: left">{{ item.name }}</span>
              <span class="typeCodeComment">{{ item.username }}</span>
            </el-option>
          </el-select>
          <el-tooltip :content="$t('自动分配 Reviewer')" placement="top">
            <el-button type="primary" @click="getReviewerAutoAssign" v-loading="isAutoAssignReviewerBtnLoading">
              <el-icon>
                <MagicStick/>
              </el-icon>
            </el-button>
          </el-tooltip>
        </el-form-item>
        <el-form-item
            label="面试官1"
            :label-width="infoFormLabelWidth"
        >
          <el-select
              v-model="editInfoForm.interviewer1"
              filterable
              clearable
              placeholder="请选择"
              style="width: 360px"
              v-loading="isEditInfoFormLoading"
          >
            <el-option
                v-for="item in interviewerList"
                :key="item"
                :value="item.id"
                :label="item.name"
            >
              <span style="float: left">{{ item.name }}</span>
              <span class="typeCodeComment">{{ item.username }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            label="面试官2"
            :label-width="infoFormLabelWidth"
        >
          <el-select
              v-model="editInfoForm.interviewer2"
              filterable
              clearable
              placeholder="请选择"
              style="width: 360px"
              v-loading="isEditInfoFormLoading"
          >
            <el-option
                v-for="item in interviewerList"
                :key="item"
                :value="item.id"
                :label="item.name"
            >
              <span style="float: left">{{ item.name }}</span>
              <span class="typeCodeComment">{{ item.username }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            label="面试官3"
            :label-width="infoFormLabelWidth"
        >
          <el-select
              v-model="editInfoForm.interviewer3"
              filterable
              clearable
              placeholder="请选择"
              style="width: 360px"
              v-loading="isEditInfoFormLoading"
          >
            <el-option
                v-for="item in interviewerList"
                :key="item"
                :value="item.id"
                :label="item.name"
            >
              <span style="float: left">{{ item.name }}</span>
              <span class="typeCodeComment">{{ item.username }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            label="面试官4"
            :label-width="infoFormLabelWidth"
        >
          <el-select
              v-model="editInfoForm.interviewer4"
              filterable
              clearable
              placeholder="请选择"
              style="width: 360px"
              v-loading="isEditInfoFormLoading"
          >
            <el-option
                v-for="item in interviewerList"
                :key="item"
                :value="item.id"
                :label="item.name"
            >
              <span style="float: left">{{ item.name }}</span>
              <span class="typeCodeComment">{{ item.username }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
            label="面试时间"
            :label-width="infoFormLabelWidth"
            v-loading="isEditInfoFormLoading"
            prop="date"
            :rules="[
              { validator: checkDate, trigger: 'blur' }
            ]"
        >
          <el-date-picker
              v-model="editInfoForm.date"
              type="datetime"
              placeholder="请选择"
              :shortcuts="shortcuts"
              :disabled-date="disableDate"
              style="width: 360px"
              clearable
          />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="editInterviewInfoDialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="editInfo(editInfoFormRef)">提交</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup lang="ts">
/**
 * 导入依赖
 */
import {computed, onMounted, reactive, ref} from 'vue';
import $axios from "../plugins/axiosInstance.js";
import {ElMessage, ElMessageBox, ElTable, FormInstance} from "element-plus";
import {Base64} from "js-base64";
import axios from "axios";
import {useRoute, useRouter} from 'vue-router';
import i18n from "../locales";

// 路由工具
const router = useRouter();
const route = useRoute();

// 当前语言
const lang = localStorage.getItem('lang');

// 模糊搜索选择器加载动画开启标记
const isVagueSelectItemLoading = ref(true);

// 面试状态加载动画开启标记
const isIVStatusLoading = ref(true);

// 录入时间加载动画开启标记
const isEntryTimeLoading = ref(true);

// 毕业时间加载动画开启标记
const isGradTimeLoading = ref(true);

// 简历表格加载动画开启标记
const isIVTableDataLoading = ref(true);

// 自动分配 Reviewer 按钮加载动画开启标记
const isAutoAssignReviewerBtnLoading = ref(false);

// 更新面试信息表单加载动画开启标记
const isEditInfoFormLoading = ref(true);

// 表格动作列表加载动画开启标记
const isOverviewPathLoading = ref(true);

// 更新面试状态对话框显示标记
const editStatusDialogFormVisible = ref(false);

// 更新面试信息对话框显示标记
const editInterviewInfoDialogFormVisible = ref(false);

// 更新状态表单 ref
const editStatusFormRef = ref<FormInstance>();

// 更新面试信息表单 ref
const editInfoFormRef = ref<FormInstance>();

// 对话框表单 label 的宽度
const formLabelWidth = '100px';

// 更新面试信息对话框表单 label 的宽度
const infoFormLabelWidth = '160px';

// 更新面试状态对话框表单内容
const editStatusForm = reactive({
  targetList: [],
  interviewStatus: null
})

// 更新面试信息对话框表单内容
const editInfoForm = reactive({
  id: null,
  reviewer: null,
  interviewer1: null,
  interviewer2: null,
  interviewer3: null,
  interviewer4: null,
  date: null,
  modifyDate: null
})

// reviewer 列表
const reviewerList = reactive([]);

// 面试官 列表
const interviewerList = reactive([]);

// 日期时间组件快捷选择
const shortcuts = [
  {
    text: 'Today',
    value: new Date(),
  },
  {
    text: 'Tomorrow',
    value: () => {
      const date = new Date()
      date.setTime(date.getTime() + 3600 * 1000 * 24)
      return date
    },
  },
  {
    text: 'A week late',
    value: () => {
      const date = new Date()
      date.setTime(date.getTime() + 3600 * 1000 * 24 * 7)
      return date
    },
  },
]

// 模糊查询的输入内容
const vagueInput = ref('');

// 模糊查询的搜索项
const vagueSelect = ref('');

// 默认展开的折叠面板
const activeNames = ref(['1']);

// 是否展开 Dialog
const showCollapse = ref(true);

// 状态百分比对应颜色
const customColors = [
  {color: '#f38d8d', percentage: 30},
  {color: '#efbf7d', percentage: 80},
  {color: '#8ce8aa', percentage: 100},
]

// 面试对应的动作列表
const options = [
  {
    value: '下载简历',
    label: '下载简历',
  },
  {
    value: '简历Review',
    label: '简历Review',
  },
  {
    value: '面试反馈',
    label: '面试反馈',
  },
  {
    value: '更新面试信息',
    label: '更新面试信息',
  },
];

// 面试表格内容接口
interface InterViewResume {
  id: string
  date: string
  status: string
  resumeId: string
  interviewer1: string
  interviewer2: string
  interviewer3: string
  interviewer4: string
  createDate: string
  reviewer: string
  filePath: string
  name: string
  sex: string
  birthday: string
  email: string
  tel: string
  education: string
  gradDate: string
  gradSchool: string
  major: string
  techAbility: string
  langAbility: string
  otherAbility: string
  workExperience: string
  selfAssessment: string
  source: string
  internalRecommender: string
  internalDept: string
  obey: string
}

// 表格过滤内容
const tableFilter = ref('');

// 面试表格内容
const IVTableData = reactive<InterViewResume[]>([]);

// 表格 ref
const multipleTableRef = ref<InstanceType<typeof ElTable>>()

// 当前选中内容
const multipleSelection = ref<InterViewResume[]>([]);

// 性别列表
const sexList = reactive([]);

// 学历列表
const educationList = reactive([]);

// 技术能力列表
const techAbilityList = reactive([]);

// 语言能力列表
const langAbilityList = reactive([]);

// 简历来源列表
const sourceList = reactive([]);

// 是否服从列表
const obeyList = reactive([]);

// 动作列表
const overviewPathList = reactive([]);

// 动作列表中更新面试信息是否存在
const isEditInfoExist = ref(false);

// 动作列表中更新面试状态是否存在
const isEditStatusExist = ref(false);

// 分页相关: 当前页码
const pageNo = ref(1);

// 分页相关: 页面大小
const pageSize = ref(10);

// 分页相关: 总数据量
const totalSize = ref(0);

// 面试状态
const IVStatus = reactive([]);

// 录入时间
const entryTime = reactive([]);

// 毕业时间
const gradTime = reactive([]);

// 毕业时间开始时间
const startYear = ref('');

// 毕业时间结束时间
const endYear = ref('');

// 模糊搜索项
const vagueSelectItem = reactive([]);

/**
 * 表格选中的回调方法
 * @param val 当前选中的列
 */
const handleSelectionChange = (val: InterViewResume[]) => {
  multipleSelection.value = val
}

/**
 * 表格过滤回调方法
 */
const filterTableData = computed(() =>
    IVTableData.filter(
        (data) => {
          return !tableFilter.value ||
              formatFilterData(data.id).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.resumeId).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.createDate).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.name).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.birthday).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.email).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.tel).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.gradDate).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.gradSchool).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.major).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.otherAbility).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.selfAssessment).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.internalDept).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.date).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(formatDicValueKey2Code(sexList, data.sex)).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(formatDicValueKey2Code(educationList, data.education)).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(formatDicValueKey2Code(techAbilityList, data.techAbility)).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(formatDicValueKey2Code(langAbilityList, data.langAbility)).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.workExperience).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(formatDicValueKey2Code(sourceList, data.source)).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(data.internalRecommender).toLowerCase().includes(tableFilter.value.toLowerCase()) ||
              formatFilterData(formatDicValueKey2Code(obeyList, data.obey)).toLowerCase().includes(tableFilter.value.toLowerCase())
        }
    )
)

/**
 * 格式化过滤数据
 */
const formatFilterData = (value) => {
  if (value == null) {
    return "";
  }
  return value;
}

/**
 * 模糊搜索中 tag 的 change 事件
 * @param item 当前选择项
 */
const onChange = (item) => {
  // 设置选中状态: 点击选中点击取消选中
  item.checked = !item.checked;
  // 重新加载表格数据
  loadIVTableData();
  // 重新加载数据总量
  loadIVTotalSize();
}

/**
 * 分页插件更改页面大小时触发的事件
 * @param val 更改后的页面大小
 */
const handleSizeChange = (val: number) => {
  refreshData();
}

/**
 * 分压插件更改当前页码触发的事件
 * @param val 更改的页码
 */
const handleCurrentChange = (val: number) => {
  refreshData();
}

/**
 * 加载模糊搜索项列表
 */
const loadVagueSelectItemList = () => {
  $axios.post("/dicValue/getVagueSelectItemList").then((resp) => {
    // 赋值
    vagueSelectItem.push(...resp.data);
    // 关闭加载动画
    isVagueSelectItemLoading.value = false;
  }).catch(() => {
    // 关闭加载动画
    isVagueSelectItemLoading.value = false;
  });
}

/**
 * 加载模糊搜索项列表
 */
const loadIVStatusList = () => {
  $axios.post("/dicValue/getIVStatusList").then((resp) => {
    // 赋值
    IVStatus.push(...resp.data);
    IVStatus.forEach((value, index) => {
      if (value.valueKey == route.query.valueKey) {
        IVStatus[index].checked = true;
        // value.checked = true;
      }
    })
    // 关闭加载动画
    isIVStatusLoading.value = false;
  }).catch(() => {
    // 关闭加载动画
    isIVStatusLoading.value = false;
  });
}

/**
 * 加载模糊搜索项列表
 */
const loadEntryTimeList = () => {
  $axios.post("/dicValue/getEntryTimeList").then((resp) => {
    // 赋值
    entryTime.push(...resp.data);
    // 关闭加载动画
    isEntryTimeLoading.value = false;
  }).catch(() => {
    // 关闭加载动画
    isEntryTimeLoading.value = false;
  });
}

/**
 * 加载模糊搜索项列表
 */
const loadGradTimeList = () => {
  $axios.post("/dicValue/getGradTimeList").then((resp) => {
    // 赋值
    gradTime.push(...resp.data);
    // 关闭加载动画
    isGradTimeLoading.value = false;
  }).catch(() => {
    // 关闭加载动画
    isGradTimeLoading.value = false;
  });
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
 * 加载技术能力列表
 */
const loadTechAbilityList = () => {
  $axios.post("/dicValue/getTechAbilityList").then((resp) => {
    // 赋值
    techAbilityList.push(...resp.data);
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
 * 加载简历来源列表
 */
const loadSourceList = () => {
  $axios.post("/dicValue/getSourceList").then((resp) => {
    // 赋值
    sourceList.push(...resp.data);
  });
}

/**
 * 加载是否服从列表
 */
const loadObeyList = () => {
  $axios.post("/dicValue/getObeyList").then((resp) => {
    // 赋值
    obeyList.push(...resp.data);
  });
}

/**
 * 加载面试表格数据
 */
const loadIVTableData = () => {
  // 开启加载动画
  isIVTableDataLoading.value = true;

  $axios.post("/interviewOverview/getInterviewOverviewListByPageCondition", {
    vagueSelect: vagueSelect.value,
    vagueInput: vagueInput.value,
    IVStatusList: IVStatus.filter((value) => {
      return value.checked == true
    }),
    entryTimeList: entryTime.filter((value) => {
      return value.checked == true
    }),
    gradTimeList: gradTime.filter((value) => {
      return value.checked == true
    }),
    startGradYear: startYear.value,
    endGradYear: endYear.value,
    pageNo: (pageNo.value - 1) * pageSize.value,
    pageSize: pageSize.value
  }).then((resp) => {
    // 截断表格旧数据
    IVTableData.length = 0;
    // 赋值新数据
    IVTableData.push(...resp.data);
    // 关闭加载动画
    isIVTableDataLoading.value = false;
  }).catch(() => {
    // 关闭加载动画
    isIVTableDataLoading.value = false;
  });

}

/**
 * 加载面试表格数据总量
 */
const loadIVTotalSize = () => {
  $axios.post("/interviewOverview/getInterviewOverviewTotalSize", {
    vagueSelect: vagueSelect.value,
    vagueInput: vagueInput.value,
    IVStatusList: IVStatus.filter((value) => {
      return value.checked == true
    }),
    entryTimeList: entryTime.filter((value) => {
      return value.checked == true
    }),
    gradTimeList: gradTime.filter((value) => {
      return value.checked == true
    }),
    startGradYear: startYear.value,
    endGradYear: endYear.value,
    pageNo: (pageNo.value - 1) * pageSize.value,
    pageSize: pageSize.value
  }).then((resp) => {
    // 赋值
    totalSize.value = resp.data;
  });
}

/**
 * 根据状态的 valueKey 返回对应的 valueCode
 */
const formatDicValueKey2Code = (dicList, status) => {
  if (status == null) {
    return ""
  }
  return dicList.filter((value) => {
    return value.valueKey == status
  })[0]["valueCode"];
}

/**
 * 格式化 id, 只显示前 18 位
 */
const formatId = (id) => {
  return id.substring(0, 18);
}

/**
 * 预览简历
 */
const previewResume = (path) => {
  let url = "http://www.rms.cool:9000" + path;
  console.log(url);
  window.open('http://www.rms.cool:8012/onlinePreview?url=' + encodeURIComponent(Base64.encode(url)));
}

/**
 * 导出数据按钮点击事件
 */
const exportExcel = () => {
  // 获取当前选中的 id 列表
  let idList = [];
  multipleSelection.value.forEach((value) => {
    idList.push(value.id);
  });
  // 发送请求
  $axios.post("/interviewOverview/getExcelByIdList", idList, {
        responseType: "arraybuffer"
      }
  ).then((resp) => {
    console.log(resp)
    let blob = new Blob([resp.data]);
    let url = window["URL"].createObjectURL(blob); // 创建 url 并指向 blob
    let a = document.createElement('a');
    a.href = url;
    a.download = '面试及简历数据.xlsx';
    a.click();
    window["URL"].revokeObjectURL(url); // 释放该 url
  }).catch(() => {
    ElMessage({
      type: 'error',
      message: '文件下载失败, 请联系管理员',
    });
  });
}

/**
 * 动作中下载简历点击事件
 */
const downloadResume = (path) => {
  // 在当前页面打开下载文件
  window.location = "http://www.rms.cool:9000" + path;
}

/**
 * 动作中路由跳转点击事件
 */
const routerTo = (name, query) => {
  // 路由跳转
  router.push({
    name: name,
    query: query
  });
}

/**
 * 更新状态
 */
const editStatus = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      // 发送请求更改状态
      $axios.post("/interviewOverview/editStatusBatch", editStatusForm).then((resp) => {
        // 成功提示
        ElMessage({
          type: 'success',
          message: resp["msg"],
        });
        // 关闭对话框
        editStatusDialogFormVisible.value = false;
        // 更新表格数据
        refreshData();
      }).catch(() => {
        // 关闭对话框
        editStatusDialogFormVisible.value = false;
      });
    } else {
      return false
    }
  })
}

/**
 * 更新面试信息
 */
const editInfo = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      // 发送请求更改状态
      $axios.post("/interviewOverview/editInterviewInfo", editInfoForm).then((resp) => {
        // 成功提示
        ElMessage({
          type: 'success',
          message: resp["msg"],
        });
        // 关闭对话框
        editInterviewInfoDialogFormVisible.value = false;
        // 更新表格数据
        refreshData();
      }).catch(() => {
        // 关闭对话框
        editInterviewInfoDialogFormVisible.value = false;
      });
    } else {
      return false
    }
  })
}

/**
 * 更新面试状态按钮点击事件
 */
const toEditStatus = (objs) => {
  // 置空表单
  editStatusForm.interviewStatus = null;
  editStatusForm.targetList = [];
  // 判断是否有选中
  if (objs.length === 0) {
    ElMessage({
      type: 'info',
      message: '请选择至少一项后重试',
    });
    return;
  }
  // 更新当前面试者列表
  editStatusForm.targetList = objs;
  // 打开更新状态对话框
  editStatusDialogFormVisible.value = true;
  // 如果只选中了一条则直接选中他的当前状态
  if (objs.length === 1) {
    editStatusForm.interviewStatus = objs[0].status;
  }
}

/**
 * 更新面试信息按钮点击事件
 */
const toEditInfo = (row) => {
  // 清空表单内容
  editInfoForm.id = null;
  editInfoForm.reviewer = null;
  editInfoForm.interviewer1 = null;
  editInfoForm.interviewer2 = null;
  editInfoForm.interviewer3 = null;
  editInfoForm.interviewer4 = null;
  editInfoForm.date = null;
  editInfoForm.modifyDate = null;
  // 打开更新面试信息对话框
  editInterviewInfoDialogFormVisible.value = true;
  // 开启加载动画
  isEditInfoFormLoading.value = true;
  // 加载 reviewer 列表
  loadReviewerList();
  // 加载 面试官 列表
  loadInterviewerList();
  $axios.post("/interviewOverview/getInterviewById", {
    id: row.id
  }).then((resp) => {
    editInfoForm.id = resp.data.id;
    editInfoForm.reviewer = resp.data.reviewer;
    editInfoForm.interviewer1 = resp.data.interviewer1;
    editInfoForm.interviewer2 = resp.data.interviewer2;
    editInfoForm.interviewer3 = resp.data.interviewer3;
    editInfoForm.interviewer4 = resp.data.interviewer4;
    editInfoForm.date = resp.data.date;
    editInfoForm.modifyDate = resp.data.modifyDate;
    // 关闭加载动画
    isEditInfoFormLoading.value = false;
  }).catch(() => {
    // 关闭对话框
    editInterviewInfoDialogFormVisible.value = false;
  });
}

/**
 * 加载 reviewer 列表
 */
const loadReviewerList = () => {
  // 开启加载动画
  isEditInfoFormLoading.value = true;
  $axios.post("/userManage/getReviewerList").then((resp) => {
    // 截断原有数据
    reviewerList.length = 0;
    // 添加新数据
    reviewerList.push(...resp.data);
    // 关闭加载动画
    isEditInfoFormLoading.value = false;
  }).catch(() => {
    // 关闭对话框
    editInterviewInfoDialogFormVisible.value = false;
  });
}

/**
 * 加载 面试官 列表
 */
const loadInterviewerList = () => {
  // 开启加载动画
  isEditInfoFormLoading.value = true;
  $axios.post("/userManage/getInterviewerList").then((resp) => {
    // 截断原有数据
    interviewerList.length = 0;
    // 添加新数据
    interviewerList.push(...resp.data);
    // 关闭加载动画
    isEditInfoFormLoading.value = false;
  }).catch(() => {
    // 关闭对话框
    editInterviewInfoDialogFormVisible.value = false;
  });
}

/**
 * 自动分配 Reviewer
 */
const toAutoAssignReviewer = (objs) => {
  // 判断是否有选中
  if (objs.length === 0) {
    ElMessage({
      type: 'info',
      message: '请选择至少一项后重试',
    });
    return;
  }
  // 确认提示框
  ElMessageBox.confirm(
      '确认为 ' + objs.length + ' 位面试者自动分配 Reviewer',
      '提示',
      {
        type: 'warning',
      }
  ).then(() => {
    // 执行分配功能
    $axios.post("/interviewOverview/editReviewerAutoAssignByIdList", objs).then((resp) => {
      // 成功提示
      ElMessage({
        type: 'success',
        message: resp["msg"],
      });
      // 刷新表格数据
      refreshData();
    });
  })
}

/**
 * 获取最闲 Reviewer
 */
const getReviewerAutoAssign = () => {
  // 开启按钮加载动画
  isAutoAssignReviewerBtnLoading.value = true;
  $axios.post("/userManage/getReviewerAutoAssign").then((resp) => {
    // 关闭按钮加载动画
    isAutoAssignReviewerBtnLoading.value = false;
    // 成功提示
    ElMessage({
      type: 'success',
      message: resp["msg"],
    });
    // 将获取到的用户信息赋给表单中
    editInfoForm.reviewer = resp.data.id;
  }).catch(() => {
    // 关闭按钮加载动画
    isAutoAssignReviewerBtnLoading.value = false;
  });
}

/**
 * 加载动作列表
 */
const loadOverviewPathList = () => {
  // 开启按钮加载动画
  isOverviewPathLoading.value = true;
  $axios.post("/permissionManage/getOverviewPathList").then((resp) => {
    // 关闭按钮加载动画
    isOverviewPathLoading.value = false;
    // 截断数据
    overviewPathList.length = 0;
    for (let datum of resp.data) {
      if (datum.path != '') {
        // 将获取到的用户信息赋给表单中
        overviewPathList.push(datum);
      } else if (datum.name == '更新面试信息') {
        isEditInfoExist.value = true;
      } else if (datum.name == '更新面试状态') {
        isEditStatusExist.value = true;
      }
    }
  }).catch(() => {
    // 关闭按钮加载动画
    isOverviewPathLoading.value = false;
  });
}

/**
 * 检查面试时间是否应该填写
 */
const checkDate = (rule: any, value: any, callback: any) => {
  if (editInfoForm.date != null && (editInfoForm.interviewer1 == null || editInfoForm.interviewer1 == "")) {
    return callback(new Error('若选择了 面试时间, 面试官1 则为必须入力项目'))
  } else {
    callback()
  }
}

/**
 * 用来判断该日期是否被禁用的函数
 * 当前日期之前的日期都应被禁用
 */
const disableDate = (date: Date) => {
  return date <= new Date();
}

/**
 * 刷新页面表格数据和数据总量
 */
const refreshData = () => {
  // 重新加载表格数据
  loadIVTableData();
  // 重新加载数据总量
  loadIVTotalSize();
}

/**
 * 在组件挂载完成后执行的内容
 */
onMounted(() => {
  // 加载模糊搜索项列表
  loadVagueSelectItemList();
  // 加载面试状态列表
  loadIVStatusList();
  // 加载录入时间列表
  loadEntryTimeList();
  // 加载毕业时间列表
  loadGradTimeList();
  // 加载性别列表
  loadSexList();
  // 加载学历列表
  loadEducationList();
  // 加载技术能力列表
  loadTechAbilityList();
  // 加载语言能力列表
  loadLangAbilityList();
  // 加载简历来源列表
  loadSourceList();
  // 加载是否服从列表
  loadObeyList();
  // 等待 300 ms 再发送请求
  setTimeout(() => {
    // 加载面试表格数据
    loadIVTableData();
    // 加载面试表格数据总量
    loadIVTotalSize();
  }, 300)
  // 加载动作列表
  loadOverviewPathList();
});
</script>

<style scoped>
/* 筛选标签 */
.check-tag {
  margin-right: 8px;
  margin-bottom: 4px;
  margin-top: 4px;
}

/* 增大增粗表格下方滚动条 */
:deep(.el-scrollbar__bar.is-horizontal>div) {
  height: 10px;
  margin-top: -5px;
}

/* 模糊查询输入框 */
.input-with-select {
  width: 500px;
}

/* 模糊查询前置搜索项 */
.prepend-select {
  width: 155px;
}

/* 批量导出按钮 */
.right-button {
  float: right;
  margin: 10px;
}

/* 表格状态文字 */
.statusSpan {
  mix-blend-mode: difference;
}

/* 分页插件 */
.page-pagination {
  margin: 10px;
}

/* 表格动作按钮 */
.tableDropdown {
  float: right;
}

/* 更新状态中的姓名 tag */
.nameTag {
  margin-right: 5px;
}

/* 更新面试信息对话框中下拉菜单的选项备注 */
.typeCodeComment {
  float: right;
  color: var(--el-text-color-secondary);
  font-size: 13px;
}
</style>
