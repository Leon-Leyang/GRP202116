/**
 * This file is used to control page jumps
 * @link https://router.vuejs.org/zh/
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home'
import PerProject from '../views/PerProject'
import Data from '@/views/Data'

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

]

const router = new VueRouter({
    routes: routes
})

export default router