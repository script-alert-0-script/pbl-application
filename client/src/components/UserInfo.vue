<template>
    <div id='user-info'>
        <p>ownerのid : {{ owner.id }}</p>
    </div>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from "vue-property-decorator";

    @Component
    export default class UserInfo extends Vue {
        @Prop() private owner!: any;
    }
</script>
