<template>

  <div id="signin">
    <h2>ログイン</h2>
    <!-- TODO: メールアドレスがmアドレスだけだと分かりやすいように -->
    <input type="email" placeholder="メールアドレス" v-model="email">

    <input type="password" placeholder="パスワード" v-model="password">

    <button>ログイン</button>

    <p>アカウントをお持ちでない方は 
      <router-link to="/signup">新規登録</router-link>
    </p>
  </div>

</template>

<script lang="ts">
/*
import { getAllItems, getItemSearch } from "@/api";
import { debounce } from "lodash";
import { Component, Vue, Watch } from "vue-property-decorator";
import { Item } from "libermo";

@Component({})
export default class Home extends Vue {
  items: Item[] = [];
  param: string = "";

  async created() {
    this.items = await getAllItems();
  }

  async getItemSearch() {
    if (this.param == "") return;
    this.items = await getItemSearch(this.param);
  }

  @Watch("param")
  debouncedGetItemSearch: Function = debounce(this.getItemSearch, 500);
}
*/
</script>
