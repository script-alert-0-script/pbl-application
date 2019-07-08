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

const Comment = {
    template: `
<div id="comment">
    <p>{{ userName }} : {{ content }}</p>
</div>
`,
    props: {
        userName: String,
        content: String,
    }
};

const Chat = {
    template: `
<div id="chat">
    <input type='text' v-model='message'> 
    <button v-on:click='postMessage'>送信</button> 
</div>
`,
    props: {},
    data: () => ({message: ''}),
    methods: {
        postMessage: function () {
            this.$emit('post-message', this.message)
        }
    }
};

const ItemPage = {
    components: {
        comment: Comment,
        chat: Chat,
    },
    template: `
<div id="itemPage">
    <comment v-for="log in logs" :user-name="log.name" :content="log.message"></comment>
    <chat @post-message="inText"></chat>
</div>
`,
    data: () => ({
        logs: [
            {name: 'hoge', message: 'huga'},
            {name: 'foo', message: 'bar'}
        ]
    }),
    methods: {
        inText: function (message) {
            this.logs.push({
                name: 'string literal',
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

