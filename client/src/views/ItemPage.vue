<template>
    <div id="item-page">
        <item-info :item="item"></item-info>
        <user-info :owner="item.owner"></user-info>
        <button v-if="item.state == 'AVAILABLE'" v-on:click="request">request</button>
        <button v-if="item.state == 'PENDING'" v-on:click="cancel">cancel</button>
        <button v-if="item.state == 'PENDING'" v-on:click="allow">allow</button>
        <!-- TODO: manage chats -->
        <chat :message="log.message" :user-name="log.name" v-for="log in logs"></chat>
        <send-message @send-message="pushLog"></send-message>
    </div>
</template>

<script lang="ts">
    import {Component, Vue} from "vue-property-decorator";
    import {getItem, postAllow, postCancel, postRequest} from "@/fetch";
    import ItemInfo from '@/components/ItemInfo.vue'
    import UserInfo from '@/components/UserInfo.vue'
    import Chat from '@/components/Chat.vue'
    import SendMessage from '@/components/SendMessage.vue'

    @Component({
        components: {
            'item-info': ItemInfo,
            'user-info': UserInfo,
            'chat': Chat,
            'send-message': SendMessage,
        }
    })
    export default class ItemPage extends Vue {
        logs: any = [{name: 'name1', message: 'mes1'}, {name: 'name2', message: 'mes2'}]
        item: any = {}

        async created() {
            this.item = await getItem(this.$route.params.id);
        }

        pushLog(message: string) {
            this.logs.push({
                name: 'my name',
                message: message,
            })
        }

        request() {
            postRequest(this.item.id)
        }

        cancel() {
            postCancel(this.item.id)
        }

        allow() {
            postAllow(this.item.id)
        }

    }
</script>
