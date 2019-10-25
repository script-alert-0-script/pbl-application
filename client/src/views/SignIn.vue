<template>
  <div id="signin">
    <h2>ログイン</h2>
    <!-- TODO: メールアドレスがmアドレスだけだと分かりやすいように -->
    <form @submit.prevent="signIn">
      <input type="email" placeholder="メールアドレス" v-model="email" />
      <input type="password" placeholder="パスワード" v-model="password" />
      <button type="submit">ログイン</button>
    </form>
    <p>
      アカウントをお持ちでない方は
      <router-link to="/signup">新規登録</router-link>
    </p>
    <p>
      {{ message }}
    </p>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { signIn } from "@/auth";

@Component({})
export default class SignIn extends Vue {
  email = "";
  password = "";
  message = "";

  signIn() {
    signIn(this.email, this.password)
      .then(async () => {
        // TODO: GET /api/user/me
        this.$router.push("/");
      })
      .catch(e => {
        if (e instanceof Error) {
          this.message = e.message;
        }
      });
  }
}
</script>
