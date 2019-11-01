<template>
  <v-card :to="`/item/${item.id}`" max-width="16em" class="mx-auto">
    <v-img src="@/assets/no-image.png" />
    <v-card-title>{{ item.name }}</v-card-title>
    <v-card-subtitle>by {{ item.owner.name }}</v-card-subtitle>
  </v-card>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import { Item } from "libermo";

@Component
export default class ItemCard extends Vue {
  @Prop({ required: true })
  private item!: Item;
}
</script>
