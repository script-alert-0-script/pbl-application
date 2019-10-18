import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";
import Signup from "@/views/Signup.vue"
import Signin from "@/views/Signin.vue"

Vue.use(VueRouter);

export default new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "home",
      component: Home
    },
    {
      path: "/item/:id",
      name: "item-page",
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () =>
        import(/* webpackChunkName: "item" */ "@/views/ItemPage.vue")
    },
    {
      path: "/signup",
      name: "signup",
      component: Signup
    },
    {
      path: "/signin",
      name: "signin",
      component: Signin
    }
  ]
});
