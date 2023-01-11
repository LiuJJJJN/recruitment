import {defineStore} from 'pinia'

// 你可以对 `defineStore()` 的返回值进行任意命名，但最好使用 store 的名字，同时以 `use` 开头且以 `Store` 结尾。
// (比如 `useUserStore`，`useCartStore`，`useProductStore`)

/**
 * 定义并导出容器
 * ‘user’: 容器 ID (必须唯一)
 * @type {StoreDefinition<"user", {name: null, token: null}, {}, {}>}
 */
export const useUserStore = defineStore('user', {
    // 用来存储全局状态
    state: () => {
        return {
            name: sessionStorage.getItem('name'),
            token: sessionStorage.getItem('token')
        };
    },
    // 用来封装计算属性, 有缓存功能
    getters: {
        getName() {
            return this.name;
        },
        getToken() {
            return this.token;
        }
    },
    // 封装业务逻辑, 修改 state
    actions: {
        setName(name) {
            this.name = name;
            sessionStorage.setItem('name', name);
        },
        setToken(token) {
            this.token = token;
            sessionStorage.setItem('token', token);
        },
        removeAll() {
            this.name = null;
            sessionStorage.setItem('name', null);
            this.token = null;
            sessionStorage.setItem('token', null);
        }
    }
})
