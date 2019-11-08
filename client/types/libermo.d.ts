declare module "libermo" {
  interface Item {
    name: string;
    owner: User;
    author: string;
    description: string;
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
