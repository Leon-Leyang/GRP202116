import Vue from 'vue'
import VueRouter from 'vue-router'
import ProjectManage from '../views/ProjectManage'
import Home from '../views/Home'
import PerProject from '../views/PerProject'
// import { LabelStudio } from '@heartexlabs/label-studio'
import LabelStudio from '@/views/LabelStudio/LabelStudio'

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
  {
    path: '/label-studio',
    name: 'LabelStudio',
    component: LabelStudio,
  },
]

const router = new VueRouter({
    routes: routes
})

export default router