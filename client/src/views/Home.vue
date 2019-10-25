<template>
  <v-app>
    <div id="home">
      <v-app-bar color="black" dark flat class="px-5">
        <v-toolbar-title>libermo</v-toolbar-title>
        <div class="flex-grow-1"></div>
        <!-- search form -->
        <v-flex xs10 md6>
          <v-text-field v-model="param" filled></v-text-field>
        </v-flex>

        <v-toolbar-items>
          <v-btn icon>
            <v-icon>mdi-magnify</v-icon>
          </v-btn>
        </v-toolbar-items>
      </v-app-bar>

      <router-link to="/signin" tag="button">ログイン</router-link>
      <router-link to="/exhibit" tag="button">出品</router-link>

      <ul>
        <li v-for="item in items" :key="item.id">
          <router-link v-bind:to="`/item/${item.id}`">
            id : {{ item.id }} - name : {{ item.name }} by {{ item.owner.id }} -
            state : {{ item.state }}
          </router-link>
        </li>
      </ul>
    </div>
  </v-app>
</template>

<script lang="ts">
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
</script>
