<template>

  <div id="signup">
    <h2>ユーザー登録</h2>
    <!-- TODO: メールアドレスがmアドレスだけだと分かりやすいように -->
    <input type="email" placeholder="メールアドレス" v-model="email">

    <input type="text" placeholder="ユーザー名" v-model="name">

    <input type="password" placeholder="パスワード" v-model="password">

    <button v-on:click="signup">登録</button>

    <p>アカウントをお持ちの方は 
      <router-link to="/signin">ログイン</router-link>
    </p>

    <div id="firebaseui-auth-container"></div>
  </div>

</template>

<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.2.1/firebase-app.js"></script>
<!-- TODO: Add SDKs for Firebase products that you want to use
     https://firebase.google.com/docs/web/setup#available-libraries -->
<script src="https://www.gstatic.com/firebasejs/7.2.1/firebase-analytics.js"></script>



<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import firebase from "firebase";

@Component({})
export default class Signup extends Vue {
  email: string = "";
  password: string = "";
  name: string = "";

  const actionCodeSettings = {
    // URL you want to redirect back to. The domain (www.example.com) for this
    // URL must be whitelisted in the Firebase Console.
    //url: 'https://alert0.itsp.club/register.html',
    url: "http://localhost:8080",
    // This must be true.
    handleCodeInApp: true,
    iOS: {
        bundleId: 'com.example.ios'
    },
    android: {
        packageName: 'com.example.android',
        installApp: true,
        minimumVersion: '12'
    },
    dynamicLinkDomain: 'libermo.page.link'
  };

  async signup() {
    var email = this.email;
    firebase.auth().sendSignInLinkToEmail(email, this.actionCodeSettings)
      .then(function(user) {
        alert("success!");
        // The link was successfully sent. Inform the user.
        // Save the email locally so you don't need to ask the user for it again
        // if they open the link on the same device.
        window.localStorage.setItem('emailForSignIn', email);
      })
      .catch(function(error) {
        // TODO: handle error
        alert("error");
      }
    );
  }

}
</script>
