<template>

  <div id="signup">
    <h2>ユーザー登録</h2>
    <!-- TODO: メールアドレスがmアドレスだけだと分かりやすいように -->
    <input type="email" placeholder="メールアドレス" v-model="email">

    <input type="password" placeholder="パスワード" v-model="password">

    <input type="text" placeholder="ユーザー名" v-model="name">

    <button>登録</button>

    <p>アカウントをお持ちの方は 
      <router-link to="/signin">ログイン</router-link>
    </p>
  </div>

</template>
