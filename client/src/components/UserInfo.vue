<template>
  <p>出品者 : {{ owner.id }}</p>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class UserInfo extends Vue {
  @Prop() private owner!: any;
}
</script>
