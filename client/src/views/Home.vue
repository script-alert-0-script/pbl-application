<template>
  <v-app>
    <v-app-bar app dark color="black">
      <router-link to="/" class="d-flex align-center">
        <v-avatar tile><v-img src="@/assets/logo.svg"/></v-avatar>
        <v-toolbar-title>
          <v-img
            src="@/assets/logo-type.svg"
            height="36"
            max-width="100"
            contain
            class="d-none d-sm-flex"
          />
        </v-toolbar-title>
      </router-link>
      <v-spacer />
      <v-text-field
        placeholder="商品検索"
        prepend-inner-icon="mdi-magnify"
        single-line
        hide-details
        v-model="param"
      />
      <v-spacer />
      <template v-if="!isAuthenticated">
        <v-btn to="/signin" class="d-none d-sm-flex">
          <v-icon left>mdi-exit-to-app</v-icon>
          ログイン
        </v-btn>
        <v-btn to="/signin" text icon class="d-flex d-sm-none">
          <v-icon>mdi-exit-to-app</v-icon>
        </v-btn>
      </template>
      <exhibit-modal v-else />
    </v-app-bar>
    <v-content>
      <v-container fill-height>
        <v-row>
          <v-col v-for="item in items" :key="item.id" cols="6" sm="4" lg="3">
            <item-card :item="item" />
          </v-col>
        </v-row>
      </v-container>
    </v-content>
  </v-app>
</template>

<script lang="ts">
import { getAllItems, getItemSearch } from "@/api";
import { debounce } from "lodash";
import { Component, Vue, Watch } from "vue-property-decorator";
import { Item } from "libermo";
import auth from "@/auth";
import { user } from "@/store/modules/user";
import ItemCard from "@/components/home/ItemCard.vue";
import ExhibitModal from "@/components/ExhibitModal.vue";

@Component({
  components: {
    ItemCard,
    "exhibit-modal": ExhibitModal
  }
})
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

  get isAuthenticated() {
    return user.isAuthenticated;
  }

  @Watch("param")
  debouncedGetItemSearch: Function = debounce(this.getItemSearch, 500);
}
</script>

<style lang="scss" scoped>
.item-search-box {
  max-width: 24rem;
}
</style>
