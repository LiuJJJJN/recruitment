import { createApp } from 'vue'
import App from './App.vue'
// ElementPLus 组件依赖
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// ElementPlus 自带的组件国际化
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import ja from 'element-plus/dist/locale/ja.mjs'
// router 路由
import router from './router'
// i18n 国际化
import i18n from './locales'
// axios 异步处理
import axios from '@/plugins/axiosInstance.js'
// pinia 状态管理库
import { createPinia } from 'pinia'
// 主样式
import './assets/main.css'

// 创建 pinia 实例
const pinia = createPinia()
const app = createApp(App)

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(router)
app.use(i18n)
app.use(ElementPlus, {
    locale: !localStorage.getItem('lang') || localStorage.getItem('lang') === 'zhCn'?zhCn:ja,
})
app.use(pinia)

app.provide('$axios', axios)

app.mount('#app')
