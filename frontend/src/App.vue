<template>
  <div id="nav">
    <router-link to="/">Home</router-link> |
    <span v-if="userinfo.position === 'S'"
      >{{ userinfo.name }}학생 안녕하세요.
    </span>
    <span v-else-if="userinfo.position === 'T'">
      {{ userinfo.name }}선생님 안녕하세요.
    </span>
    <span v-else>로그인 해주세요.</span>
     |
    <router-link to="/about">About</router-link> |
    <router-link to="/signup">Signup</router-link> |
    <router-link to="/login">Login</router-link> |
    <router-link to="/classboard">학급게시판</router-link> |
    <router-link to="/videoclass">화상수업</router-link> |
    <button v-if="userinfo.position === 'S' || userinfo.position === 'T'" class="btn-warning" @click="logout">Logout</button>
  </div>
  <router-view />
</template>

<script lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";

export default {
  setup() {
    const store = useStore();
    let userinfo = computed(() => store.state.accountStore.userinfo);
    const logout = () => store.commit("accountStore/logout");
    return { userinfo, logout };
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
