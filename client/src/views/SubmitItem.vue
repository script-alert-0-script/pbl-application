<template>
  <div id="submit-item">
    <h2>アイテム出品フォーム</h2>
    <form @submit.prevent="submitItem">
      <input type="text" placeholder="書籍名" v-model="name" />
      <button type="submit">出品</button>
    </form>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { postSubmitItem } from "@/api";

@Component({})
export default class SubmitItem extends Vue {
  name = "";

  submitItem() {
    if (this.name) postSubmitItem(this.name);
  }
}
</script>
