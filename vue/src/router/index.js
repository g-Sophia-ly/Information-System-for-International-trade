import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home',  // 重定向到主页
    children: [
      { path: '403', name: 'NoAuth', meta: { name: '无权限' }, component: () => import('../views/manager/403') },
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home') },
      { path: 'admin', name: 'Admin', meta: { name: '管理员信息' }, component: () => import('../views/manager/Admin') },
      { path: 'adminPerson', name: 'AdminPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/AdminPerson') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
      { path: 'notice', name: 'Notice', meta: { name: '公告信息' }, component: () => import('../views/manager/Notice') },
      { path: 'staff', name: 'Staff', meta: { name: '员工信息' }, component: () => import('../views/manager/Staff') },
      { path: 'factory', name: 'Factory', meta: { name: '工厂信息' }, component: () => import('../views/manager/Factory') },
      { path: 'staffPerson', name: 'StaffPerson', meta: { name: '员工个人信息' }, component: () => import('../views/manager/StaffPerson') },
      { path: 'factoryPerson', name: 'FactoryPerson', meta: { name: '工厂信息' }, component: () => import('../views/manager/FactoryPerson') },
      { path: 'customer', name: 'Customer', meta: { name: '客户信息' }, component: () => import('../views/manager/Customer') },
      { path: 'order', name: 'Order', meta: { name: '订单信息' }, component: () => import('../views/manager/Order') },
      { path: 'newOrder', name: 'NewOrder', meta: { name: '新增订单信息' }, component: () => import('../views/manager/NewOrder') },
      { path: 'goods', name: 'Goods', meta: { name: '商品信息' }, component: () => import('../views/manager/Goods') },
      { path: 'sizenum', name: 'Sizenum', meta: { name: '配码信息' }, component: () => import('../views/manager/Sizenum') },
      { path: 'newSizenum', name: 'NewSizenum', meta: { name: '新增配码信息' }, component: () => import('../views/manager/NewSizenum') },
      { path: 'packlist', name: 'Packlist', meta: { name: '装箱单' }, component: () => import('../views/manager/Packlist') },
      { path: 'newPacklist', name: 'NewPacklist', meta: { name: '新增装箱单' }, component: () => import('../views/manager/NewPacklist') },
      { path: 'declare', name: 'Declare', meta: { name: '报关资料' }, component: () => import('../views/manager/Declare') },
      { path: 'newDeclare', name: 'NewDeclare', meta: { name: '新增报关资料' }, component: () => import('../views/manager/NewDeclare') },
      { path: 'ship', name: 'Ship', meta: { name: '订船资料' }, component: () => import('../views/manager/Ship') },
      { path: 'newShip', name: 'NewShip', meta: { name: '新增订船资料' }, component: () => import('../views/manager/NewShip') },
      { path: 'succ', name: 'Succ', meta: { name: '完成' }, component: () => import('../views/manager/Succ') },
      { path: 'analyse', name: 'Analyse', meta: { name: '数据统计' }, component: () => import('../views/manager/Analyse') },
      { path: 'supplier', name: 'Supplier', meta: { name: '供应商信息' }, component: () => import('../views/manager/Supplier') },
      { path: 'logs', name: 'Logs', meta: { name: '日志信息' }, component: () => import('../views/manager/Logs') },
    ]
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/Front.vue'),
    children: [
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/front/Home') },
      { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/front/Person') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },
  { path: '*', name: 'NotFound', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 注：不需要前台的项目，可以注释掉该路由守卫
// 路由守卫
// router.beforeEach((to ,from, next) => {
//   let user = JSON.parse(localStorage.getItem("xm-user") || '{}');
//   if (to.path === '/') {
//     if (user.role) {
//       if (user.role === 'USER') {
//         next('/front/home')
//       } else {
//         next('/home')
//       }
//     } else {
//       next('/login')
//     }
//   } else {
//     next()
//   }
// })

export default router
