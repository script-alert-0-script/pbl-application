<template>
  <v-card v-if="item" class="mx-auto">
    <v-container>
      <v-spacer />
      <v-btn icon style="float: right;" @click.stop="close">
        <v-icon>mdi-close</v-icon>
      </v-btn>

      <v-row>
        <!-- TODO: update when clicked -->
        <v-col cols="12" md="5" align="center">
          <v-img
            max-width="200"
            max-height="200"
            src="@/assets/mokun.png"
          ></v-img>
          <!--buttons-->
          <!--available-->
          <v-btn
            v-if="item.state == 'AVAILABLE' && !isOwner"
            color="orange darken-2"
            dark
            rounded
            @click.prevent="request"
            >取引希望！</v-btn
          >
          <v-btn v-if="item.state == 'AVAILABLE' && isOwner" rounded disabled
            >出品中</v-btn
          >
          <!--pending-->
          <v-btn
            v-if="item.state == 'PENDING'"
            color="blue-grey"
            dark
            rounded
            @click.prevent="cancel"
            >取引キャンセル</v-btn
          >
          <v-btn
            v-if="item.state == 'PENDING' && isOwner"
            color="pink lighten-4"
            dark
            rounded
            @click.prevent="allow"
            >取引承認！</v-btn
          >
          <!--completed-->
          <v-btn v-if="item.state == 'COMPLETED'" rounded disabled
            >取引済み</v-btn
          >
        </v-col>

        <v-col cols="12" md="7">
          <v-card-title class="headline mb-1">{{ item.name }}</v-card-title>
          <!-- TODO: add author to Item -->
          <v-card-subtitle class="black--text">{{
            item.author
          }}</v-card-subtitle>
          <v-card-text class="black--text">
            <user-info :owner="item.owner"></user-info>
            <div>
              {{ item.description }}
            </div>
            <!-- TODO: manage chats -->
            <v-divider />

            <chat
              :message="log.message"
              :user-name="log.name"
              v-for="(log, i) in logs"
              :key="i"
            ></chat>
            <send-message @send-message="pushLog"></send-message>
          </v-card-text>
        </v-col>

        <!-- FIXME: スマホだと変なところに行っちゃう -->
      </v-row>
    </v-container>
  </v-card>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { getItem, postAllow, postCancel, postRequest } from "@/api";
import UserInfo from "@/components/UserInfo.vue";
import Chat from "@/components/Chat.vue";
import SendMessage from "@/components/SendMessage.vue";
import { Item } from "libermo";
import VueRouter from "vue-router";

@Component({
  components: {
    "user-info": UserInfo,
    chat: Chat,
    "send-message": SendMessage
  }
})
export default class ItemPageModal extends Vue {
  logs = [
    { name: "name1", message: "mes1" },
    { name: "name2", message: "mes2" }
  ];
  item: Item | null = null;
  // TODO: get me
  readonly isOwner: Boolean = false;

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

  close() {
    this.$router.push("/");
  }
}
</script>
