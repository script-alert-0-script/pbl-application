declare module "libermo" {
  interface Item {
    name: string;
    owner: User;
    state: "AVAILABLE" | "PENDING" | "COMPLETED";
    buyer?: User;
    id: number;
  }

  interface User {
    id: number;
    email: string;
    name: string;
  }
}
