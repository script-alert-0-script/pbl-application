new Vue({
    el: '#app',
    data: {
        items: []
    },
    async created() {
        // インスタンス作成時に自動的にローカルストレージをfetchする
        console.log(this.item);
        this.items = await getAllItems();
        console.log(this.item);
    },
})