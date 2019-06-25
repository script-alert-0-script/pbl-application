new Vue({
    el: '#app',
    data: {
        items: [],
        param: ''
    },
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
});

