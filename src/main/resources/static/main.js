const Index = {
    template: `
<div id="index">
    <input v-model="param">
    <ul>
        <li v-for="item in items">
            <router-link v-bind:to="\`/item/\${item.id}\`">id : {{ item.id }} - name : {{ item.name }} by {{item.owner.id}}</router-link>
        </li>
    </ul>
</div>
`,
    data: () => ({items: [], param: ''}),
    async created() {
        this.debouncedGetItemSearch = _.debounce(this.getItemSearch, 500);
        this.items = await getAllItems();
    },
    watch: {
        param: function () {
            this.debouncedGetItemSearch();
        }
    },

    methods: {
        getItemSearch: async function () {
            if (this.param == '') return;
            this.items = await getItemSearch(this.param);
        }
    }
};

const ItemInfo = {
    template: `
<div id='item-info'>
    <p>出品物のid,name : {{ item.id }} - {{item.name}} </p>
</div>
`,
    props: {
        item: {
            type: Object,
            default: () => ({id: null, name: null}),
            required: true
        }
    }
};

const UserInfo = {
    template: `
<div id='user-info'>
    <p>ownerのid : {{ owner.id }}</p>
</div>
`,
    props: {
        owner: {
            type: Object,
            default: () => ({id: null}),
            required: true
        }
    }
};

const Chat = {
    template: `
<div id="chat">
    <p>{{ userName }} : {{ message }}</p>
</div>
`,
    props: {
        'user-name': {type: String, required: true},
        'message': {type: String, required: true},
    }
};

const SendMessage = {
    template: `
<div id="send-message">
    <input type='text' v-model='message'> 
    <button v-on:click='sendMessage'>送信</button> 
</div>
`,
    props: {},
    data: () => ({message: ''}),
    methods: {
        sendMessage: function () {
            this.$emit('send-message', this.message);
        }
    }
};

const ItemPage = {
    components: {
        'item-info': ItemInfo,
        'user-info': UserInfo,
        'chat': Chat,
        'send-message': SendMessage,
    },
    template: `
<div id="item-page">
    <item-info :item="item"></item-info>
    <user-info :owner="item.owner"></user-info>
    <!-- TODO: manage chats -->
    <chat v-for="log in logs" :user-name="log.name" :message="log.message"></chat>
    <send-message @send-message="pushLog"></send-message>
</div>
`,
    data: () => ({
        logs: [
            {name: 'hoge', message: 'huga'},
            {name: 'foo', message: 'bar'}
        ],
        item: {}
    }),
    async created() {
        this.item = await getItem(this.$route.params.id);
    },
    methods: {
        pushLog: function (message) {
            this.logs.push({
                name: 'my name',
                message: message
            })
        }
    }
};

const routes = [
    {path: '/', component: Index},
    {path: '/item/:id', component: ItemPage}
];

const router = new VueRouter({routes});

const app = new Vue({router}).$mount("#app");

