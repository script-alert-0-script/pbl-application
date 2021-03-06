import {
  VuexModule,
  Module,
  Mutation,
  getModule
} from "vuex-module-decorators";
import store from "@/store";
import { User } from "libermo";

export interface UserState {
  user: User | null;
}

@Module({ dynamic: true, store, name: "user" })
export class UserModule extends VuexModule implements UserState {
  user: User | null = null;

  @Mutation
  setUser(user: User | null) {
    this.user = user;
  }

  get isAuthenticated() {
    return this.user != null;
  }
}

export const user = getModule(UserModule);
