<template>
  <div v-if="item" id="item-page">
    <item-info :item="item"></item-info>
    <user-info :owner="item.owner"></user-info>
    <button v-if="item.state == 'AVAILABLE'" v-on:click="request">
      request
    </button>
    <button v-if="item.state == 'PENDING'" v-on:click="cancel">cancel</button>
    <button v-if="item.state == 'PENDING'" v-on:click="allow">allow</button>
    <!-- TODO: manage chats -->
    <chat
      :message="log.message"
      :user-name="log.name"
      v-for="(log, i) in logs"
      :key="i"
    ></chat>
    <send-message @send-message="pushLog"></send-message>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { getItem, postAllow, postCancel, postRequest } from "@/api";
import ItemInfo from "@/components/ItemInfo.vue";
import UserInfo from "@/components/UserInfo.vue";
import Chat from "@/components/Chat.vue";
import SendMessage from "@/components/SendMessage.vue";
import { Item } from "libermo";
import VueRouter from "vue-router";

@Component({
  components: {
    "item-info": ItemInfo,
    "user-info": UserInfo,
    chat: Chat,
    "send-message": SendMessage
  }
})
export default class ItemPage extends Vue {
  logs = [
    { name: "name1", message: "mes1" },
    { name: "name2", message: "mes2" }
  ];
  item?: Item;

  async created() {
    this.item = await getItem(parseInt(this.$route.params.id));
  }

  pushLog(message: string) {
    this.logs.push({
      name: "my name",
      message: message
    });
  }

  request() {
    if (this.item) postRequest(this.item.id);
  }

  cancel() {
    if (this.item) postCancel(this.item.id);
  }

  allow() {
    if (this.item) postAllow(this.item.id);
  }
}
</script>
