//导入axios
import axios from 'axios'
import {ElMessage, ElNotification} from "element-plus";
import router from "@/router";

//使用 axios 下面的 create([config]) 方法创建 axios 实例，其中 config 参数为 axios 最基本的配置信息
const API = axios.create({
    baseURL: 'http://localhost:8081',     //请求后端数据的基本地址
    // baseURL: 'http://www.rms.cool:8080',     //请求后端数据的基本地址
    timeout: 10 * 1000                     //请求超时设置，单位ms
})

//前置拦截: 设置请求头内容
API.interceptors.request.use(
    config => {
        // 给每个请求都加上 token 请求头
        if (config.url !== '/user/login' && sessionStorage.getItem('token') != null) {
            config.headers.token = sessionStorage.getItem('token');
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

//后置拦截: 根据后端给前端的状态码进行拦截, 这样在其他页面就只需要处理 200 状态码的情况了
API.interceptors.response.use(
    resp => {
        let res = resp.data;
        if (res.code == 200) { // 200 成功 不拦截
            return res;
        } else if (Math.floor(res.code / 10) == 41) {   // 41X 认证 / 授权错误, 提示并跳转登录页面
            console.log("41x 系列错误");
            // 页面跳转
            router.push("/login");
            // 重置前端 token 存储
            sessionStorage.setItem("token", null);
            // 错误提示
            ElMessage({
                type: 'error',
                message: res.msg,
                grouping: true
            });
            return Promise.reject(res.msg);
        } else if (Math.floor(res.code / 10) == 42) {   // 42X 不重要的错误,仅提示
            console.log("42x 系列错误")
            // 错误提示
            ElMessage({
                type: 'warning',
                message: res.msg,
                grouping: true
            });
            return Promise.reject(res.msg);
        } else {                                           // 其它情况: 文件下载、后抛出的其它异常
            console.log("其它情况")
            if (res) {
                ElNotification({
                    title: '文件下载',
                    message: '请注意接收',
                    type: 'info'
                })
            }else{
                ElNotification({
                    title: 'Error',
                    message: '未知错误, 请联系管理员',
                    type: 'error'
                })
            }
            return resp;
        }
    },
    error => {
        // 如果后端有回应
        if (error.response) {
            console.log("服务端 500 错误");
            ElMessage({
                type: 'error',
                message: "服务器抛出异常: " + error.message + ", 请联系管理员",
                grouping: true
            });
        } else {
            // 如果后端没有回应
            console.log('后端没有响应或网络连接异常');
            ElMessage({
                type: 'error',
                message: '请求发送失败, 请联系管理员',
                grouping: true
            });
        }
        return Promise.reject(error);
    }
);

//导出 axios 实例模块，ES6 export 用法
export default API
