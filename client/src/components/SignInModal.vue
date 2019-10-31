<template>
  <v-dialog v-model="dialog" max-width="600px">
    <v-btn slot="activator" color="black" dark flat outlined @click.stop="open">ログイン</v-btn>

    <v-card>
      <v-card-title class="headline black white--text">
        libermoにログイン
        <v-spacer />
        <v-btn icon dark @click.stop="close">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>

      <v-container>
        <v-card-text>
          <v-row>
            <v-col cols="12">
              メールアドレス
              <v-text-field
                type="text"
                v-model="email"
                placeholder="libermo.a.aa"
                suffix="@m.titech.ac.jp"
                outlined
              ></v-text-field>
            </v-col>
            <v-col cols="12">
              パスワード
              <v-text-field type="password" v-model="password" placeholder="password" outlined></v-text-field>
            </v-col>
          </v-row>

          <!--TODO: センタリング -->
          <v-btn color="grey darken-3" dark @click.prevent="signIn">ログイン</v-btn>

          <!--TODO: センタリング -->
          <p>{{ message }}</p>
          <p>
            アカウントをお持ちでない方は
            <!--TODO-->
            <router-link to="/signup">新規登録</router-link>
          </p>
        </v-card-text>
      </v-container>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { signIn } from "@/auth";

@Component({})
export default class SignInModal extends Vue {
  readonly domain = "@m.titech.ac.jp";
  email = "";
  password = "";
  message = "";
  dialog = false;

  signIn() {
    signIn(this.email + this.domain, this.password)
      .then(async () => {
        // TODO: GET /api/user/me
        this.$router.push("/");
        close();
      })
      .catch(e => {
        if (e instanceof Error) {
          this.message = e.message;
        }
      });
  }

  open() {
    this.dialog = true;
  }

  close() {
    this.dialog = false;
  }
}
</script>
