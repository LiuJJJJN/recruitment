<template>
    <div id="back">
        <!-- 简历上传表单 -->
        <el-form :model="form" :rules="rules" label-width="170px" ref="ruleFormRef" class="demo-ruleForm">
            <!-- 第一行 简历文件上传 -->
            <el-row>
                <el-form-item :label="$t('上传简历')">
                    <!-- action="http://localhost:8080/cvUpload/resumeUpload" -->
                    <el-upload class="upload-demo"
                               ref="upload"
                               :headers="{token: userStore.getToken}"
                               action="http://www.rms.cool:8080/cvUpload/resumeUpload"
                               multiple
                               :limit="1"
                               :auto-upload="false"
                               :on-remove="handleRemove"
                               :on-success="handleSuccess"
                               :on-error="handleError"
                               :before-upload="beforeUpload"
                               :on-exceed="handleExceed"
                    >
                        <el-button type="primary">{{ $t('选择简历') }}</el-button>
                        <template #tip>
                            <div class="el-upload__tip" style="float: right; margin-left: 5px">
                                {{ $t('只能选择一个简历文件(类型包括 txt、doc、docx、pdf且文件大小不超过5MB)') }}
                            </div>
                        </template>
                    </el-upload>
                </el-form-item>
            </el-row>
            <!-- 第二行 姓名 性别 -->
            <el-row>
                <el-form-item :label="$t('姓名')" prop="name">
                    <el-input v-model="form.name" :placeholder="$t('请输入姓名')" class="small-form"/>
                </el-form-item>
                <el-form-item :label="$t('性别')" prop="sex">
                    <el-radio-group v-model="form.sex" class="small-form">
                        <el-radio v-for="item in sexList" :label="item.valueKey">{{ item.valueCode }}</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-row>
            <!-- 第三行 生日 邮箱 -->
            <el-row>
                <el-form-item :label="$t('生日')" style="margin-right:30px" prop="birthday">
                    <el-date-picker v-model="form.birthday" type="date" :placeholder="$t('请选择生日')"
                                    class="small-form"
                                    style="width:280px"/>
                </el-form-item>
                <el-form-item :label="$t('邮箱')" prop="email">
                    <el-input v-model="form.email" :placeholder="$t('请输入邮箱')" class="small-form"/>
                </el-form-item>
            </el-row>
            <!-- 第四行 联系电话 最高学历 -->
            <el-row>
                <el-form-item :label="$t('联系电话')" prop="tel">
                    <el-input v-model="form.tel" :placeholder="$t('请输入号码')" class="small-form"/>
                </el-form-item>
                <el-form-item :label="$t('最高学历')" prop="education">
                    <el-select v-model="form.education" :placeholder="$t('请选择最高学历')" class="small-form">
                        <el-option v-for="item in educationList" :label="item.valueCode" :value="item.valueKey"/>
                    </el-select>
                </el-form-item>
            </el-row>
            <!-- 第五行 毕业时间 毕业学校 -->
            <el-row>
                <el-form-item :label="$t('毕业时间')" style="margin-right:30px" prop="gradDate">
                    <el-date-picker v-model="form.gradDate" type="date" :placeholder="$t('请选择毕业年月')"
                                    class="small-form"
                                    style="width:280px"/>
                    <span class="text-gray-500"></span>
                </el-form-item>
                <el-form-item :label="$t('毕业学校')" prop="gradSchool">
                    <el-input v-model="form.gradSchool" :placeholder="$t('请输入校名')" class="small-form"/>
                </el-form-item>
            </el-row>
            <!-- 第六行 专业 其它能力 -->
            <el-row>
                <el-form-item :label="$t('专业')" prop="major">
                    <el-input v-model="form.major" :placeholder="$t('请输入所学专业')" class="small-form"/>
                </el-form-item>
                <el-form-item :label="$t('其它能力')" prop="otherAbility">
                    <el-input v-model="form.otherAbility" :placeholder="$t('请输入其它能力')" class="small-form"/>
                </el-form-item>
            </el-row>
            <!-- 第七行 主要技术 语言水平 -->
            <el-row>
                <el-form-item :label="$t('主要技术')" prop="techAbility">
                    <el-select v-model="form.techAbility" :placeholder="$t('请选择主要技术')" class="small-form">
                        <el-option v-for="item in techAbilityList" :label="item.valueCode" :value="item.valueKey"/>
                    </el-select>
                </el-form-item>
                <el-form-item :label="$t('语言水平')" prop="langAbility">
                    <el-select v-model="form.langAbility" :placeholder="$t('请选择语言水平')" class="small-form">
                        <el-option v-for="item in langAbilityList" :label="item.valueCode" :value="item.valueKey"/>
                    </el-select>
                </el-form-item>
            </el-row>
            <!-- 第八行 工作经验 -->
            <el-row>
                <el-form-item :label="$t('工作经验')" prop="workExperience">
                    <el-input v-model="form.workExperience" type="textarea" :placeholder="$t('请输入工作经验')"
                              class="base-input"
                              rows="3"/>
                </el-form-item>
            </el-row>
            <!-- 第九行 自我评价 -->
            <el-row>
                <el-form-item :label="$t('自我评价')" prop="selfAssessment">
                    <el-input v-model="form.selfAssessment" type="textarea" :placeholder="$t('请输入自我评价')"
                              class="base-input"
                              rows="3"/>
                </el-form-item>
            </el-row>
            <!-- 第十行 意向部门 服从分配 -->
            <el-row>
                <el-form-item :label="$t('意向部门')" prop="intendedDept">
                    <el-autocomplete
                            :model-value="form.intendedDept.value"
                            :label="form.intendedDept.value"
                            :fetch-suggestions="querySearch"
                            :trigger-on-focus="false"
                            clearable
                            @clear="form.intendedDept = {}"
                            :placeholder="$t('请选择意向部门')"
                            @select="handleSelect"
                            style="width: 280px; margin-right: 30px"
                    />
                </el-form-item>
                <el-form-item :label="$t('服从分配')" prop="obey">
                    <el-radio-group v-model="form.obey" class="small-form"
                                    :disabled="form.intendedDept.id == undefined">
                        <el-radio v-for="item in obeyList" :label="item.valueKey">{{ item.valueCode }}</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-row>
            <!-- 第十一行 简历来源 内推人 -->
            <el-row>
                <el-form-item :label="$t('简历来源')" prop="source">
                    <el-select v-model="form.source" :placeholder="$t('请选择简历来源')" class="small-form">
                        <el-option v-for="item in sourceList" :label="item.valueCode" :value="item.valueKey"/>
                    </el-select>
                </el-form-item>
                <el-form-item :label="$t('内推人')" prop="internalRecommender">
                    <el-input v-model="form.internalRecommender"
                              :placeholder="$t('请输入内推人')"
                              class="small-form"
                              :disabled="form.source != 2"
                              clearable
                    />
                </el-form-item>
            </el-row>
            <!-- 第十二行 简历Reviewer 备注 -->
            <el-row>
                <el-form-item :label="$t('简历Reviewer')" prop="reviewer">
                    <el-autocomplete
                            :model-value="form.reviewer.value"
                            :label="form.reviewer.name"
                            :fetch-suggestions="querySearch2"
                            :trigger-on-focus="false"
                            clearable
                            @clear="form.reviewer = {}"
                            :placeholder="$t('请选择简历Reviewer')"
                            @select="handleReviewerSelect"
                            style="width: 280px;margin-right: 30px"
                    />
                    <el-tooltip :content="$t('一键分配Reviewer')" placement="right" effect="light">
                        <el-button type="primary" style="margin-left: -25px" @click="autoAssign">
                            <el-icon>
                                <MagicStick/>
                            </el-icon>
                        </el-button>
                    </el-tooltip>
                </el-form-item>
            </el-row>
            <el-row>
                <el-form-item label="Comment" prop="comment">
                    <el-input v-model="form.comment" type="textarea" :placeholder="$t('请输入备注内容')"
                              class="base-input"
                              rows="4"/>
                </el-form-item>
            </el-row>
            <!-- 第十三行 上传简历 保存 按钮 -->
            <el-row>
                <el-form-item>
                    <el-button class="ml-3" type="success" @click="submitUpload">{{ $t('上传简历') }}</el-button>
                    <el-button type="primary" @click="submitForm(ruleFormRef)" :disabled="!form.filePath">
                        {{ $t('保存数据') }}
                    </el-button>
                    <el-button @click="resetForm(ruleFormRef)">{{ $t('重置') }}</el-button>
                </el-form-item>
            </el-row>
        </el-form>
    </div>
</template>

<script setup lang="ts">
import {ref, reactive, onMounted} from 'vue'
import {
    ElMessage,
    UploadInstance,
    UploadProps,
    FormInstance,
    FormRules,
    UploadRawFile
} from 'element-plus'
import {Awaitable} from "@vueuse/core";
import $axios from "../plugins/axiosInstance.js";
import {useUserStore} from "../store";

// 定义状态管理工具
const userStore = useUserStore();

interface RestaurantItem {
    value: string
}

const restaurants = ref<RestaurantItem[]>([])
const restaurants2 = ref<RestaurantItem[]>([])
const querySearch = (queryString: string, cb: any) => {
    const results = queryString
        ? restaurants.value.filter(createFilter(queryString))
        : restaurants.value
    // call callback function to return suggestions
    cb(results)
}

const createFilter = (queryString: string) => {
    return (restaurant: RestaurantItem) => {
        return (
            restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        )
    }
}

const querySearch2 = (queryString: string, cb: any) => {
    const results = queryString
        ? restaurants2.value.filter(createFilter(queryString))
        : restaurants2.value
    // call callback function to return suggestions
    cb(results)
}

const loadAll = () => {
    let deptList = [];
    // 获取部门列表
    $axios.post("deptManage/getDeptList").then((resp) => {
        for (let data of resp.data) {
            deptList.push({
                id: data.id,
                value: data.name,
                company: data.company
            });
        }
    })
    return deptList;
}

const handleSelect = (item: RestaurantItem) => {
    form.intendedDept = item;
}

const handleReviewerSelect = (item: RestaurantItem) => {
    form.reviewer = item;
}

// 简历文件上传 ref
const upload = ref<UploadInstance>()

// 文件上传成功标记
const uploadSuccessFlag = ref(false)

// 表单
const form = reactive({
    name: '',
    filePath: '',
    sex: 0,
    birthday: '',
    email: '',
    tel: '',
    education: '',
    gradDate: '',
    gradSchool: '',
    major: '',
    techAbility: '',
    langAbility: '',
    otherAbility: '',
    workExperience: '',
    selfAssessment: '',
    source: '',
    internalRecommender: '',
    intendedDept: {},
    obey: 0,
    reviewer: {},
    comment: '',
})

const sexList = reactive([])

const educationList = reactive([])

const techAbilityList = reactive([])

const langAbilityList = reactive([])

const sourceList = reactive([])

const obeyList = reactive([])

// 允许的文件类型
const fileTypeList = reactive([
    "application/pdf",
    "text/plain",
    "application/msword",
    "application/x-excel",
    "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
    "image/jpeg"
])

/**
 * 上传简历按钮点击事件
 */
const submitUpload = () => {
    upload.value!.submit();
}

/**
 * 选择简历文件数量超出限制时执行的钩子函数
 * @param files 文件信息
 * @param uploadFiles 上传文件列表
 */
const handleExceed: UploadProps['onExceed'] = (files, uploadFiles) => {
    ElMessage.warning(
        `最多支持上传1个文件`
    )
}

/**
 * 文件列表移除文件时的钩子
 * @param file
 * @param uploadFiles
 */
const handleRemove: UploadProps['onRemove'] = (file, uploadFiles) => {
    ElMessage({
        message: `已删除`,
        type: 'warning',
    })
}

/**
 * 上传前验证文件信息
 * @param uploadFiles
 */
const beforeUpload: (rawFile: UploadRawFile) => Awaitable<void | undefined | null | boolean> = (uploadFiles) => {
    // 验证文件大小
    if (uploadFiles.size > 1024 * 1024 * 5) {
        ElMessage({
            message: `文件过大: ${uploadFiles.name}`,
            type: 'error',
        })
        return false;
    }
    // 验证文件格式
    let flag = false;
    fileTypeList.forEach((value) => {
        if (uploadFiles.type == value) {
            flag = true;
        }
    })
    if (!flag) {
        ElMessage({
            message: `格式不正确: ${uploadFiles.name}`,
            type: 'error',
        })
        return false;
    }
    // 通过验证, 上传文件
    ElMessage({
        message: `正在上传文件:${uploadFiles.name}`,
        type: 'warning',
    })
}

/**
 * 上传成功处理
 * @param response
 * @param uploadFile
 * @param uploadFiles
 */
const handleSuccess: UploadProps['onSuccess'] = (response, uploadFile, uploadFiles) => {
    ElMessage({
        message: `上传文件成功:${uploadFile.name}`,
        type: 'success',
    })
    // 改变标记
    form.filePath = response;
}

/**
 * 上传失败处理
 * @param response
 * @param uploadFile
 * @param uploadFiles
 */
const handleError: UploadProps['onError'] = (response, uploadFile, uploadFiles) => {
    ElMessage({
        message: `上传文件失败:${uploadFile.name}`,
        type: 'error',
    })
}

// 表单格式验证
const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
    name: [
        {required: true, message: "请输入姓名", trigger: 'blur'},
        {min: 2, max: 5, message: "字符长度2-5", trigger: 'blur'},
    ],
    email: [
        {required: true, message: "请输入邮件", trigger: 'blur'},
        {
            pattern: '^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*',
            message: "请输入正确的邮件格式",
            trigger: 'change'
        },

    ],
    sex: [
        {required: true, message: "请输入性别", trigger: 'blur'},
    ],
    tel: [
        {required: true, message: "请输入号码", trigger: "blur"},
        {
            pattern: /(^((\+86)|(86))?(1[3-9])\d{9}$)|(^(0\d{2,3})-?(\d{7,8})$)/,
            message: "输入的电话号码格式不正确，请重新输入",
            trigger: "blur"
        },
    ],
    education: [
        {required: true, message: "请输入最高学历", trigger: 'change'},
    ],
    birthday: [
        {type: 'date', required: true, message: "请输入生日", trigger: 'change'},
    ],
    resource: [
        {required: true, message: "请输入简历来源", trigger: 'blur'},
    ],
    desc: [
        {required: true, message: 'Please input activity form', trigger: 'blur'},
    ],
})

/**
 * 保存按钮点击事件
 * @param formEl
 */
const submitForm = async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    await formEl.validate((valid, fields) => {
        if (valid) {
            // 格式化数据
            form.reviewer = form.reviewer.id;
            form.intendedDept = form.intendedDept.id;
            // 发送请求
            $axios.post("/cvUpload/addResumeInterview", form).then((resp) => {
                // 成功提示
                ElMessage({
                    type: 'success',
                    message: resp["msg"],
                });
            }).catch((error) => {

            });
        } else {
            console.log('error submit!', fields)
        }
    })
}

/**
 * 重置按钮点击事件
 * @param formEl
 */
const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    form.internalRecommender = "";
    formEl.resetFields()
}

const loadEducationList = () => {
    $axios.post("dicValue/getEducationList").then((resp) => {
        // 赋值
        educationList.push(...resp.data);
        // 关闭加载动画
    })
}

const loadLangAbilityList = () => {
    $axios.post("dicValue/getLangAbilityList").then((resp) => {
        // 赋值
        langAbilityList.push(...resp.data);
        // 关闭加载动画
    })
}

const loadSexList = () => {
    $axios.post("dicValue/getSexList").then((resp) => {
        // 赋值
        sexList.push(...resp.data);
        // 关闭加载动画
    })
}

const loadTechAbilityList = () => {
    $axios.post("dicValue/getTechAbilityList").then((resp) => {
        // 赋值
        techAbilityList.push(...resp.data);
        // 关闭加载动画
    })
}

const loadObeyList = () => {
    $axios.post("dicValue/getObeyList").then((resp) => {
        // 赋值
        obeyList.push(...resp.data);
        // 关闭加载动画
    })
}

const loadSourceList = () => {
    $axios.post("dicValue/getSourceList").then((resp) => {
        // 赋值
        sourceList.push(...resp.data);
        // 关闭加载动画
    })
}

const loadReviewerList = () => {
    let deptList = [];
    // 获取部门列表
    $axios.post("userManage/getReviewerList").then((resp) => {
        for (let data of resp.data) {
            deptList.push({
                id: data.id,
                value: data.name
            });
        }
    })
    restaurants2.value = deptList;
}

const autoAssign = () => {
    $axios.post("userManage/getReviewerAutoAssign").then((resp) => {
        // 赋值
        form.reviewer.id = resp.data.id;
        form.reviewer.value = resp.data.name;
        // 关闭加载动画
    })
}

/**
 * 在组件挂载完成后执行的内容
 */
onMounted(() => {
    // 加载学历列表
    loadEducationList();
    // 加载语言能力列表
    loadLangAbilityList();
    // 加载性别列表
    loadSexList();
    // 加载技术能力列表
    loadTechAbilityList();
    // 加载简历来源列表
    loadSourceList();
    // 加载是否服从列表
    loadObeyList();
    // 加载简历Reviewer列表
    loadReviewerList();
    // 加载部门列表
    restaurants.value = loadAll();
});
</script>

<style scoped>
#back {
    width: 970px;
    margin: 0 auto;
}

.small-form {
    width: 280px;
    margin-right: 30px;
}

.base-input {
    width: 760px;
}

</style>
