import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'defaultView',
      component: () => import("@/views/loginView.vue")
    },
    {
      path: '/index',
      name: 'indexView',
      component: () => import("@/views/indexView.vue"),
      children:[
        {
          path: 'workbench',
          name: 'workbench',
          component: () => import("@/views/workbenchView.vue")
        },
        {
          path: 'cvUpload',
          name: 'cvUpload',
          component: () => import("@/views/CVUploadView.vue")
        },
        {
          path: 'cvReview',
          name: 'cvReview',
          component: () => import('@/views/CVReviewView.vue')
        },
        {
          path: 'interviewFeedback',
          name: 'interviewFeedback',
          component: () => import("@/views/interviewFeedbackView.vue")
        },
        {
          path: 'interviewOverview',
          name: 'interviewOverview',
          component: () => import("@/views/interviewOverviewView.vue")
        },
        {
          path: 'dictionaryTypeManage',
          name: 'dictionaryTypeManage',
          component: () => import("@/views/dictionaryTypeManageView.vue")
        },
        {
          path: 'dictionaryValueManage',
          name: 'dictionaryValueManage',
          component: () => import("@/views/dictionaryValueManageView.vue")
        },
        {
          path: 'userManage',
          name: 'userManage',
          component: () => import("@/views/userManageView.vue")
        },
        {
          path: 'historyInterview',
          name: 'historyInterview',
          component: () => import("@/views/historyInterviewView.vue")
        },
        {
          path: 'deptManage',
          name: 'deptManage',
          component: () => import("@/views/deptManageView.vue")
        },
        {
          path: ':pathMatch(.*)',
          name: 'innerErrorView',
          component: () => import("@/views/errorView.vue")
        },
      ]
    },
    {
      path: '/login',
      name: 'loginView',
      component: () => import("@/views/loginView.vue")
    },
    {
      path: '/:pathMatch(.*)',
      name: 'errorView',
      component: () => import("@/views/errorView.vue")
    },
  ]
})

export default router
