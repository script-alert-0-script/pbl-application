<template>
  <v-app>
    <div id="item-page">
      <modal />
    </div>
  </v-app>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import ItemPageModal from "@/components/ItemPageModal.vue";
@Component({
  components: {
    modal: ItemPageModal
  }
})
export default class ItemPage extends Vue {}
</script>
