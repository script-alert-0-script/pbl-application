
var STORAGE_KEY = 'items-vuejs-demo'
var itemStorage = {
    fetch: function(){
        var items = JSON.parse(localStorage.getItem(STORAGE_KEY) || '[]')
        items.forEach(function(item, index){
            item.id = index
        })
        itemStorage.uid = items.length
        return items
    },
    save: function(items){
        localStorage.setItem(STORAGE_KEY, JSON.stringify(items))
    }
}

new Vue({
    el: '#app',
    data: {
        items: []
    },
    watch: {
        // 変更に対して監視を行う
        items: {
            handler: function(itemAfterChange){
                // 引数はウォッチしているプロパティの変更後の値
                itemStorage.save(itemAfterChange)
            },
            deep: true
            // deepオプションでネスとしているデータも監視できる
        }
    },
    created: function() {
        // インスタンス作成時に自動的にローカルストレージをfetchする
        this.items = itemStorage.fetch()
    },
    methods: {
        doAdd: function(event, value){
            var shouhinmei = this.$refs.shouhinmei
            var nedan = this.$refs.nedan

            if(!shouhinmei.value.length){
                return alert("商品名が入力されていません")
            }
            if(!nedan.value.length){
                return alert("値段が入力されていません")
            }

            this.items.push({
                id: itemStorage.uid++,
                name: shouhinmei.value,
                price: nedan.value
            })
            // フォームの要素を空にする
            name.value = ''
            price.value = ''
        }
    }
})