import Vue from 'vue'
import VueRouter from 'vue-router'
import ProjectManage from '../views/ProjectManage'
import Home from '../views/Home'
import PerProject from '../views/PerProject'

Vue.use(VueRouter)

const routes = [{
    path: '/',
    name: 'Home',
    component: Home,
    },{
    path: '/project-manage',
    name: 'ProjectManage',
    component: ProjectManage
},
{
    path: '/per-project',
    name: 'PerProject',
    component: PerProject,
  },
]

const router = new VueRouter({
    routes: routes
})

export default router