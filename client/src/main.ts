import Vue from "vue";
import App from "./App.vue";
import store from "./store";
import router from "./router";
import firebase from "firebase/app";
import "firebase/auth";
import "firebase/analytics";

Vue.config.productionTip = false;

const firebaseConfig = {
  apiKey: "AIzaSyDrFnDTumQBdhER4pAe_Q0sfsAZ0nQiUAY",
  authDomain: "libermo.firebaseapp.com",
  databaseURL: "https://libermo.firebaseio.com",
  projectId: "libermo",
  storageBucket: "libermo.appspot.com",
  messagingSenderId: "375740935092",
  appId: "1:375740935092:web:cb993e3c262ec8bd44e0e1",
  measurementId: "G-JTSBWZ15SD"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);
firebase.analytics();

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount("#app");
