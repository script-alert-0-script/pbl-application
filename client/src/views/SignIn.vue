<template>
  <v-app>
    <div id="signin">
      <sign-in-modal />
    </div>
  </v-app>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import SignInModal from "@/components/SignInModal.vue";
@Component({
  components: {
    "sign-in-modal": SignInModal
  }
})
export default class SignIn extends Vue {}
</script>
