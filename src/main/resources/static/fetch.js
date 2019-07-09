async function getAllItems() {
    return (await fetch('api/item')).json();
}

async function getItem(id) {
    return (await fetch(`api/item/${id}`)).json();
}

async function getItemSearch(q) {
    let param = new URLSearchParams();
    param.set('name', q);
    return (await fetch('api/item/search?' + param.toString())).json();
}

async function getUser(id) {
    return (await fetch(`api/user/${id}`)).json();
}
