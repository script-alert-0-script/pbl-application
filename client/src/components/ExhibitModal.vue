<template>
  <v-dialog v-model="dialog" max-width="600px">
    <template v-slot:activator="{ on }">
      <v-btn
        color="accent"
        class="d-none d-sm-flex"
        @click.stop="open"
        v-on="on"
        >出品</v-btn
      >
    </template>

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
                v-on:change="getFileContent"
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
export default class ExhibitModal extends Vue {
  name = "";
  author = "";
  description = "";
  image: File | null = null;
  dialog = false;

  async exhibit() {
    if (this.name) {
      await postSubmitItem(this.name, this.author, this.description, this.image);
      this.close();
    }
  }

  open() {
    this.dialog = true;
  }

  close() {
    this.dialog = false;
  }

  async getFileContent(file: File) {
    try {
      this.image = file;
      const content = await this.readFileAsync(file);
      console.log(new Uint8Array(content.slice(0, 3)));
    } catch (e) {
      console.log(e)
    }
  }

  readFileAsync(file: File) :Promise<ArrayBuffer> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = () => (reader.result instanceof ArrayBuffer)?resolve(reader.result) : reject('not ArrayBuffer');
      reader.onerror = reject;
      reader.readAsArrayBuffer(file);
    })
  }
}
</script>
