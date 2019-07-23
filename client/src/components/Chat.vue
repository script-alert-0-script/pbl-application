<template>
    <div id="chat">
        <p>{{ userName }} : {{ message }}</p>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";

    @Component
    export default class Chat extends Vue {
        @Prop() private userName!: string;
        @Prop() private message!: string;
    }
</script>
