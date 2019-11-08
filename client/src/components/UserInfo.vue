<template>
  <p>出品者 : {{ owner.name }}</p>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import { User } from "libermo";

@Component
export default class UserInfo extends Vue {
  @Prop({ required: true })
  private owner!: User;
}
</script>
