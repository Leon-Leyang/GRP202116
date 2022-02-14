import Vue from 'vue'
import VueRouter from 'vue-router'
import ProjectManage from '../views/ProjectManage'

Vue.use(VueRouter)

const routes = [{
    path: '/',
    name: 'ProjectManage',
    component: ProjectManage
},
]

const router = new VueRouter({
    routes: routes
})

export default router