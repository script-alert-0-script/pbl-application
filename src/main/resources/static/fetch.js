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

async function getUser(id) {
    return (await fetch(`api/user/${id}`)).json();
}

async function post(uri, formData) {
    return await fetch(uri, {method: "POST", body: formData});
}

async function postRequest(id) {
    return (await post(`api/user/${id}/request`, new FormData()));
}

async function postCancel(id) {
    return (await post(`api/user/${id}/cancel`, new FormData()));
}

async function postAllow(id) {
    return (await post(`api/user/${id}/allow`, new FormData()));
}
