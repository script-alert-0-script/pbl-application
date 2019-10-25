import firebase from "firebase/app";
import "firebase/auth";

const settings: firebase.auth.ActionCodeSettings = {
  url:
    process.env.NODE_ENV === "production"
      ? "https://alert0.itsp.club/"
      : "http://localhost:3000",
  handleCodeInApp: false
};

export async function signin(email: string, password: string) {
  await firebase.auth().signInWithEmailAndPassword(email, password);
}

export async function signup(email: string, password: string) {
  const providers = await firebase.auth().fetchSignInMethodsForEmail(email);
  if (
    providers.find(
      p => p === firebase.auth.EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD
    )
  ) {
    throw new Error("このメールアドレスは既に使われています");
  }

  const { user } = await firebase
    .auth()
    .createUserWithEmailAndPassword(email, password);
  if (user) user.sendEmailVerification(settings);
}

export async function signout() {
  await firebase.auth().signOut();
}
