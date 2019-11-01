<template>
  <v-dialog v-model="dialog" max-width="600px">
    <!--<v-btn slot="activator" color="black" dark flat outlined @click.stop="open"-->
    <v-btn
      slot="activator"
      color="accent"
      class="d-none d-sm-flex"
      @click.stop="open"
      >出品</v-btn
    >

    <v-card>
      <v-card-title class="headline black white--text">
        本を出品する
        <v-spacer />
        <v-btn icon dark @click.stop="close">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>

      <v-container>
        <v-card-text>
          <v-row no-gutters>
            <v-col cols="8">
              本のタイトル
              <v-text-field
                type="text"
                v-model="name"
                placeholder="線形代数基礎"
                outlined
              ></v-text-field>
            </v-col>
            <v-col cols="4">
              <v-file-input
                class="ml-6 mt-5"
                accept="image/png, image/jpeg, image/bmp"
                prepend-icon="mdi-camera"
                single-line
                outlined
                label="写真"
              ></v-file-input>
            </v-col>

            <v-col cols="12">
              著者名
              <v-text-field
                type="text"
                v-model="author"
                placeholder="東工太郎"
                outlined
              ></v-text-field>
            </v-col>

            <v-col cols="12">
              説明
              <v-textarea
                v-model="description"
                outlined
                placeholder="本の状態や、受け渡し希望キャンパスなど"
              ></v-textarea>
            </v-col>
          </v-row>

          <!--TODO: センタリング -->
          <v-btn color="grey darken-3" dark @click.prevent="exhibit"
            >出品</v-btn
          >
        </v-card-text>
      </v-container>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { postSubmitItem } from "@/api";

@Component({})
export default class SignInModal extends Vue {
  name = "";
  author = "";
  description = "";
  dialog = false;

  exhibit() {
    if (this.name) {
      postSubmitItem(this.name, this.author, this.description).then(async () => {
        this.$router.push("/");
        close();
      });
    }
  }

  open() {
    this.dialog = true;
  }

  close() {
    this.dialog = false;
  }
}
</script>
