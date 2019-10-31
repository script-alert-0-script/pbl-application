<template>
  <div id="send-message">
    <v-textarea class="mx-4" v-model="message" outlined solo placeholder="コメントを入力"></v-textarea>
    <v-layout justify-center>
      <v-btn class="my-0" @click.prevent="sendMessage">コメント送信</v-btn>
    </v-layout>
  </div>
</template>

<script lang="ts">
import { Component, Emit, Vue } from "vue-property-decorator";

@Component
export default class SendMessage extends Vue {
  message: string = "";

  @Emit("send-message")
  sendMessage() {
    return this.message;
  }
}
</script>
