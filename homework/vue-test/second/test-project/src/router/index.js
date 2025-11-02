import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/user',
        name: 'user',
        component: () => import('../views/element/UserView.vue')
    },
    {
        path: '/question',
        name: 'question',
        component: () => import('../views/element/QuestionView.vue')
    },
    {
        path: '/',
        redirect: '/user'
    }
]

const router = new VueRouter({
    routes
})

export default router
