<template>  
  <div id="nav">
     <nav class="navbar navbar-light bg-light fixed-top">
  <div class="container-fluid">
    <a class="navbar-brand"><router-link to="/about">꿀잼스쿨</router-link></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
      <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="offcanvasNavbarLabel">꿀잼스쿨</h5>
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
      </div>
      <span v-if="userinfo.position === 'S'"
      >{{ userinfo.name }}학생 안녕하세요.
    </span>
    <span v-else-if="userinfo.position === 'T'">
      {{ userinfo.name }}선생님 안녕하세요.
    </span>
    <span v-else>로그인 해주세요.</span>
      <div class="offcanvas-body">
        <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
          <li class="nav-item">
            <a class="nav-link"><router-link to="/">Home</router-link></a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" aria-current="page"><router-link to="/signup">Signup</router-link></a>
          </li>
          <li class="nav-item">
            <a class="nav-link"><router-link to="/login">Login</router-link></a>
          </li>
          <li class="nav-item">
            <a class="nav-link"><router-link to="/classboard">학급게시판</router-link></a>
          </li>
          <li class="nav-item">
            <a class="nav-link"><router-link to="/videoclass">화상수업</router-link></a>
          </li>
        </ul>
        <button v-if="userinfo.position === 'S' || userinfo.position === 'T'" class="btn-warning" @click="logout">Logout</button>
      </div>
    </div>
  </div>
</nav>
    
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

.nav-link a,.navbar-brand a{
  text-decoration: none;
}
</style>
