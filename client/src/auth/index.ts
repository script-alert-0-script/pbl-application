import firebase from "firebase/app";
import "firebase/auth";
import "firebase/analytics";
import { user } from "@/store/modules/user";
import { getMe } from "@/api";

const options = {
  apiKey: "AIzaSyDrFnDTumQBdhER4pAe_Q0sfsAZ0nQiUAY",
  authDomain: "libermo.firebaseapp.com",
  databaseURL: "https://libermo.firebaseio.com",
  projectId: "libermo",
  storageBucket: "libermo.appspot.com",
  messagingSenderId: "375740935092",
  appId: "1:375740935092:web:cb993e3c262ec8bd44e0e1",
  measurementId: "G-JTSBWZ15SD"
};

const settings: firebase.auth.ActionCodeSettings = {
  url:
    process.env.NODE_ENV === "production"
      ? "https://alert0.itsp.club/"
      : "http://localhost:3000",
  handleCodeInApp: false
};

class Auth {
  constructor() {
    firebase.initializeApp(options);
    firebase.analytics();
    firebase.auth().onAuthStateChanged(async firebaseUser => {
      if (firebaseUser) {
        user.setUser(await getMe());
      } else {
        user.setUser(null);
      }
    });
  }

  async signIn(email: string, password: string) {
    await firebase.auth().signInWithEmailAndPassword(email, password);
  }

  async signUp(email: string, password: string) {
    const providers = await firebase.auth().fetchSignInMethodsForEmail(email);
    if (
      providers.find(
        p => p === firebase.auth.EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD
      )
    ) {
      throw new Error("このメールアドレスは既に使われています");
    }

    const firebaseUser = (await firebase
      .auth()
      .createUserWithEmailAndPassword(email, password)).user;
    if (firebaseUser) firebaseUser.sendEmailVerification(settings);
  }

  async signOut() {
    await firebase.auth().signOut();
    user.setUser(null);
  }
}

export default new Auth();
