import Vue from 'vue'
import VueRouter from 'vue-router'
// import ProjectManage from '../views/ProjectManage'
import Home from '../views/Home'
import PerProject from '../views/PerProject'
// import { LabelStudio } from '@heartexlabs/label-studio'
// import LabelStudio from '@/views/LabelStudio/LabelStudio'
import Data from '@/views/Data'
// import Test from '@/views/Test';
import MLTest from '@/views/PerProject/ML/ML-Test-'
import MLTrain from '@/views/PerProject/ML/ML-Train-'

Vue.use(VueRouter)

const routes = [{
        path: '/',
        name: 'Home',
        component: Home,
    }, {
        path: '/per-project',
        name: 'PerProject',
        component: PerProject,
    },
    {
        path: '/data',
        name: 'Data',
        component: Data,
    },
    {
        path: '/ML-Test',
        name: 'ML-Test',
        component: MLTest,
    },
    {
        path: '/ML-Train',
        name: 'ML-Train',
        component: MLTrain,
    },

]

const router = new VueRouter({
    routes: routes
})

export default router