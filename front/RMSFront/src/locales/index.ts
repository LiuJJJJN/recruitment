import { createI18n } from 'vue-i18n'
import zhCn from './zh-cn'
import ja from "./ja";

// 创建 i18n
const i18n = createI18n({
    legacy: false,
    globalInjection: true, // 全局模式，可以直接使用 $t
    locale: localStorage.getItem('lang') || 'zhCn',
    messages: {
        zhCn,
        ja
    }
})

export default i18n