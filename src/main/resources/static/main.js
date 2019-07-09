const Index = {
    template: `
<div id="index">
    <input v-model="param">
    <ul>
        <li v-for="item in items">
            <router-link v-bind:to="\`/item/\${item.id}\`">{{ item.id }} - {{ item.name }}</router-link>
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
    data: () => ({item: {}}),
    props: {
        id: Number
    },
    async created() {
        this.item = await getItem(this.id);
    }
};

const UserInfo = {
    template: `
<div id='user-info'>
    <p>User page</p>
    <!--p>userのid,name : {{ user.id }} - {{user.name}} </p -->
</div>
`,
    data: () => ({user: {}}),
    props: {
        id: Number
    },
    async created() {
        //this.user = await getUser(this.id);
    }
};

const Chat = {
    template: `
<div id="chat">
    <p>{{ userName }} : {{ message }}</p>
</div>
`,
    props: {
        'user-name': String,
        'message': String,
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
    <item-info :id="$route.params.id"></item-info>
    <user-info :id="42"></user-info>
    <chat v-for="log in logs" :user-name="log.name" :message="log.message"></chat>
    <send-message @send-message="pushLog"></send-message>
</div>
`,
    data: () => ({
        logs: [
            {name: 'hoge', message: 'huga'},
            {name: 'foo', message: 'bar'}
        ]
    }),
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

