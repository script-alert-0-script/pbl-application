async function fetchJSON(uri: string) {
    return (await fetch(uri)).json();
}

export async function getAllItems() {
    return await fetchJSON('/api/item');
}

export async function getItem(id: number) {
    return await fetchJSON(`/api/item/${id}`);
}

export async function getItemSearch(q: string) {
    let param = new URLSearchParams();
    param.set('name', q);
    return await fetchJSON('/api/item/search?' + param.toString());
}

export async function getUser(id: number) {
    return await fetchJSON(`/api/user/${id}`);
}

async function post(uri: string, formData: FormData) {
    return await fetch(uri, {method: 'POST', body: formData});
}

export async function postRequest(id: number) {
    return (await post(`/api/item/${id}/request`, new FormData()));
}

export async function postCancel(id: number) {
    return (await post(`/api/item/${id}/cancel`, new FormData()));
}

export async function postAllow(id: number) {
    return (await post(`/api/item/${id}/allow`, new FormData()));
}
