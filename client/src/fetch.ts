async function fetchJSON(uri) {
    return (await fetch(uri)).json();
}

export async function getAllItems() {
    return await fetchJSON('/api/item');
}

export async function getItem(id) {
    return await fetchJSON(`/api/item/${id}`);
}

export async function getItemSearch(q) {
    let param = new URLSearchParams();
    param.set('name', q);
    return await fetchJSON('/api/item/search?' + param.toString());
}

export async function getUser(id) {
    return (await fetch(`/api/user/${id}`)).json();
}

async function post(uri, formData) {
    return await fetch(uri, {method: "POST", body: formData});
}

export async function postRequest(id) {
    return (await post(`/api/item/${id}/request`, new FormData()));
}

export async function postCancel(id) {
    return (await post(`/api/item/${id}/cancel`, new FormData()));
}

export async function postAllow(id) {
    return (await post(`/api/item/${id}/allow`, new FormData()));
}
