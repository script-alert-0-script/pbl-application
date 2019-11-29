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
            max-width="300"
            max-height="300"
            src="@/assets/no-image.png"
            class="mb-6"
          />
          <!--buttons-->
          <!--available-->
          <v-btn
            v-if="item.state == 'AVAILABLE' && !isLoggedIn"
            rounded
            disabled
            >ログインして取引希望</v-btn
          >
          <v-btn
            v-else-if="item.state == 'AVAILABLE' && !isOwner"
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
            v-if="item.state == 'PENDING' && isTrader"
            color="blue-grey"
            dark
            rounded
            @click.prevent="cancel"
            >取引キャンセル</v-btn
          >
          <v-btn v-if="item.state == 'PENDING' && !isTrader" rounded disabled
            >他のユーザが取引中</v-btn
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
          <v-card-subtitle class="black--text">
            {{ item.author }}
          </v-card-subtitle>
          <v-card-text class="black--text">
            <user-info :owner="item.owner"></user-info>
            <div>{{ item.description }}</div>
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
import { user } from "@/store/modules/user";

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

  get isLoggedIn() {
    return user.user != null;
  }

  // TODO: get me
  get isOwner() {
    if (user.user && this.item) {
      return user.user.id === this.item.owner.id;
    }
    return false;
  }

  get isBuyer() {
    if (user.user && this.item && this.item.buyer) {
      return user.user.id === this.item.buyer.id;
    }
    return false;
  }

  // owner or buyer
  get isTrader() {
    if (user.user && this.item) {
      return this.isOwner || this.isBuyer;
    }
    return false;
  }

  async created() {
    this.item = await getItem(parseInt(this.$route.params.id));
  }

  pushLog(message: string) {
    this.logs.push({
      name: "my name",
      message: message
    });
  }

  async request() {
    if (this.item) this.item = await postRequest(this.item.id);
  }

  async cancel() {
    if (this.item) this.item = await postCancel(this.item.id);
  }

  async allow() {
    if (this.item) this.item = await postAllow(this.item.id);
  }

  close() {
    this.$router.push("/");
  }
}
</script>
