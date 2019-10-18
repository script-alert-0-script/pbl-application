declare module "libermo" {
  export interface Item {
    name: string;
    owner: User;
    state: "AVAILABLE" | "PENDING" | "COMPLETED";
    buyer?: User;
    id: number;
  }

  export interface User {
    id: number;
    name: string;
    displayName: string;
  }
}
