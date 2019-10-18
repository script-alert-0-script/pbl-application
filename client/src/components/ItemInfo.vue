<template>
  <div id="item-info">
    <p>
      出品物のid,name,state : {{ item.id }} - {{ item.name }} - {{ item.state }}
    </p>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class ItemInfo extends Vue {
  @Prop() private item!: any;
}
</script>
