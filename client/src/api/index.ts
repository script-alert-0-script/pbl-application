import axios from "axios";
import firebase from "firebase/app";
import "firebase/auth";
import { Item, User } from "libermo";

const http = axios.create({
  withCredentials: true,
  xsrfCookieName: "XSRF-TOKEN",
  xsrfHeaderName: "X-XSRF-TOKEN"
});
http.interceptors.request.use(async config => {
  const user = firebase.auth().currentUser;
  if (user) {
    const token = await user.getIdToken();
    config.headers["Authorization"] = `Bearer ${token}`;
  }
  return config;
});

export async function getAllItems() {
  return (await http.get<Item[]>("/api/item")).data;
}

export async function getItem(id: number) {
  return (await http.get<Item>(`/api/item/${id}`)).data;
}

export async function getItemSearch(q: string) {
  return (await http.get<Item[]>("/api/item/search", {
    params: {
      name: q
    }
  })).data;
}

export async function getUser(id: number) {
  return (await http.get<User>(`/api/user/${id}`)).data;
}

export async function getMe() {
  return (await http.get<User>("/api/user/me")).data;
}

export async function postRequest(id: number) {
  return (await http.post<Item>(`/api/item/${id}/request`)).data;
}

export async function postCancel(id: number) {
  return (await http.post<Item>(`/api/item/${id}/cancel`)).data;
}

export async function postAllow(id: number) {
  return (await http.post<Item>(`/api/item/${id}/allow`)).data;
}
