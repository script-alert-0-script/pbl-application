declare namespace NodeJS {
  interface Process {
    env: {
      readonly BASE_URL: string;
      readonly NODE_ENV: "development" | "production";
    };
  }
}
