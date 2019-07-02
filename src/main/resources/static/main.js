const Index = {
    template: `
<div id="index">
    <input v-model="param">
    <ul>
        <li v-for="item in items">
            <router-link v-bind:to="\`/item/\${item.id}\`">{{ item.id }} - {{ item.name }} - {{ item.price }}</router-link>
        </li>
    </ul>
</div>
`,
    data: /*{
        items: [],
        param: ''
    }*/ () => ({items: [], param: ''}),
    async created() {
        // インスタンス作成時に自動的にローカルストレージをfetchする
        this.items = await getAllItems();

        this.debouncedGetItemSearch = _.debounce(this.getItemSearch, 500);
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

const Item = {
    template: '<div>item page</div>'
}

const routes = [
    {path: '/', component: Index},
    {path: '/item/:id', component: Item}
]

const router = new VueRouter({routes});

const app = new Vue({router}).$mount("#app");

