<template>
  <div id="signup">
    <h2>ユーザー登録</h2>
    <!-- TODO: メールアドレスがmアドレスだけだと分かりやすいように -->
    <form @submit.prevent="signUp">
      <input type="email" placeholder="メールアドレス" v-model="email" />
      <input type="password" placeholder="パスワード" v-model="password" />
      <button type="submit">登録</button>
    </form>
    <p>
      アカウントをお持ちの方は
      <router-link to="/signin">ログイン</router-link>
    </p>
    <p>
      {{ message }}
    </p>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import firebase from "firebase";
import { signUp } from "@/auth";

@Component({})
export default class SignUp extends Vue {
  email: string = "";
  password: string = "";
  message = "";

  signUp() {
    signUp(this.email, this.password)
      .then(() => {
        this.message = "メールを確認してください";
      })
      .catch(e => {
        if (e instanceof Error) {
          this.message = e.message;
        }
      });
  }
}
</script>
