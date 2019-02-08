import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Service from '@/components/Service'
import User from '@/components/User'
import Login from '@/components/Login'

Vue.use(Router)

export default new Router({
  mode: 'history', //eliminates # from url
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/callservice',
      name: 'Service',
      component: Service
    },
    {
      path: '/user',
      name: 'User',
      component: User
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    }
  ]
})
