<template>
    <div id="send-message">
        <input type='text' v-model='message'>
        <button v-on:click='sendMessage'>送信</button>
    </div>
</template>

<script lang="ts">
    import {Component, Emit, Vue} from "vue-property-decorator";

    @Component
    export default class SendMessage extends Vue {
        message: string = ''

        @Emit('send-message')
        sendMessage() {
        }
    }
</script>
