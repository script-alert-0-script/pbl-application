<template>
  <v-dialog v-model="dialog" max-width="480">
    <template v-slot:activator="{ on }">
      <v-btn color="red" dark @click.stop="open" v-on="on"
        >出品キャンセル</v-btn
      >
    </template>

    <v-card>
      <v-card-title class="headline">本当にキャンセルしますか？</v-card-title>

      <v-card-text>
        出品をキャンセルすると、取引中でも元に戻すことはできません。
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text @click="this.close">
          いいえ
        </v-btn>

        <v-btn color="red" dark text @click="this.cancel">
          はい
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import { Component, Vue, Prop } from "vue-property-decorator";
import { postCancel } from "@/api";
import { Item } from "libermo";

@Component({})
export default class ConfirmModal extends Vue {
  @Prop({ required: true })
  private item!: Item;

  dialog = false;
  message = "";

  open() {
    this.dialog = true;
  }

  close() {
    this.dialog = false;
  }

  async cancel() {
    try {
      if (this.item) await postCancel(this.item.id);
      this.$router.push("/");
    } catch (e) {
      this.message = e.message;
    }
  }
}
</script>
