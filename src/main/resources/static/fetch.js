async function fetchJSON(uri) {
    return (await fetch(uri)).json();
}

async function getAllItems() {
    return await fetchJSON('api/item');
}

async function getItem(id) {
    return await fetchJSON(`api/item/${id}`);
}

async function getItemSearch(q) {
    let param = new URLSearchParams();
    param.set('name', q);
    return await fetch('api/item/search?' + param.toString());
}

async function getUserInfo(id) {
    return (await fetch(`api/user/${id}`)).json();
}
