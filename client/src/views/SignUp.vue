<template>
  <v-app>
    <div id="signup">
      <sign-up-modal />
    </div>
  </v-app>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import SignUpModal from "@/components/SignUpModal.vue";
@Component({
  components: {
    "sign-up-modal": SignUpModal
  }
})
export default class SignUp extends Vue {}
</script>
