<template>
  <div id="chat">
    <p>
      <v-card color="grey lighten-3" class="ma-4" flat>
        <v-card-title class="pa-1 subtitle-2">{{ userName }}</v-card-title>
        <v-card-text class="px-5 black--text body-2">{{ message }}</v-card-text>
      </v-card>
    </p>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

@Component
export default class Chat extends Vue {
  @Prop() private userName!: string;
  @Prop() private message!: string;
}
</script>
