<template>
  <div id="home">
    <input v-model="param">
    <ul>
      <li v-for="item in items">
        <router-link v-bind:to="`/item/${item.id}`">id : {{ item.id }} - name : {{ item.name }} by {{item.owner.id}} -
          state : {{ item.state }}
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
  import {getAllItems, getItemSearch} from "../fetch";
  import {debounce} from "lodash";
  import {Component, Vue, Watch} from "vue-property-decorator";

  @Component({})
  export default class Home extends Vue {
    items: Array<any> = []
    param: string = ''

    async created() {
      this.items = await getAllItems();
    }

    async getItemSearch() {
      if (this.param == '') return;
      this.items = await getItemSearch(this.param);
    }

    @Watch('param')
    debouncedGetItemSearch: Function = debounce(this.getItemSearch, 500)

  }
</script>
