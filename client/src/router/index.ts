import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";
import ItemPage from "@/views/ItemPage.vue";
import SignUp from "@/views/SignUp.vue";
import SignIn from "@/views/SignIn.vue";
import SubmitItem from "@/views/SubmitItem.vue";

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
      component: ItemPage
    },
    {
      path: "/exhibit",
      name: "submit-item",
      component: SubmitItem
    },
    {
      path: "/signup",
      name: "signup",
      component: SignUp
    },
    {
      path: "/signin",
      name: "signin",
      component: SignIn
    }
  ]
});
