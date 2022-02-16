<template>
  
  <div id="nav">
     <nav class="navbar navbar-light bg-white fixed-top">
  <div class="container-fluid">
    <a class="navbar-brand text-truncate font-A1-500"><router-link to="/about"><img
                        src="@/assets/logo.png"
                        style="width: 27px; height: 27px; margin-left: 10px"
                      /></router-link></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="offcanvas offcanvas-start w-25" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel " data-bs-keyboard="false" data-bs-backdrop="false">
      <div class="offcanvas-header">
        <h5 class="offcanvas-title d-none d-sm-block font-A1-500" id="offcanvasNavbarLabel">꿀잼스쿨</h5>
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
      </div>
      <span class="ms-1 d-none d-sm-inline font-A1-500" v-if="userinfo.position === 'S'"
      >{{ userinfo.name }}학생 안녕하세요.
    </span>
    <span class="ms-1 d-none d-sm-inline font-A1-500" v-else-if="userinfo.position === 'T'">
      {{ userinfo.name }}선생님 안녕하세요.
    </span>
    <span class="ms-1 d-none d-sm-inline font-A1-500" v-else>로그인 해주세요.</span>
      <div class="offcanvas-body px-0">
        <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
          <li class="p-2 nav-item">
            <router-link to="/"><i class="fs-5 bi-house text-dark"></i><a class="nav-link ms-1 d-none d-sm-inline font-A1-500 text-dark" >메인 화면</a></router-link>
          </li>
          <li class="p-2 nav-item">
            <router-link to="/profile"><i class="fs-5 bi-person text-dark"></i><a class="nav-link active ms-1 d-none d-sm-inline font-A1-500 text-dark" aria-current="page">내 정보</a></router-link>
          </li>
          <li class="p-2 nav-item">
            <router-link to="/classboard"><i class="fs-5 bi-window text-dark"></i><a class="nav-link ms-1 d-none d-sm-inline font-A1-500 text-dark">학급게시판</a></router-link>
          </li>
          <li class="p-2 nav-item">
            <button v-if="userinfo.position === 'S' || userinfo.position === 'T'" class="logout-btn font-A1-500" @click="logout">나가기</button>
          </li>
        </ul>
        
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
.nav-color{
  color:#aae9e5;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.font-A1-200{
  font-family: 'Gothic A1', sans-serif;
  font-weight: 200;
}
.font-A1-300{
  font-family: 'Gothic A1', sans-serif;
  font-weight: 300;
}
.font-A1-400{
  font-family: 'Gothic A1', sans-serif;
  font-weight: 400;
}
.font-A1-500{
  font-family: 'Gothic A1', sans-serif;
  font-weight: 500;
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

.nav-item a,.navbar-brand a{
  text-decoration: none;
}
.logout-btn {
  display: inline-block;
  line-height: 1.5;
  color: #67748e;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  user-select: none;
  border: 1px solid transparent;
  padding: 0.625rem 1.25rem;
  transition: all 0.15s ease-in;
  /* font-weight: 500; */
  /* font-size: 0.875rem; */
  /* background-color: transparent; */
  border-radius: 0.3rem;
  background-color: #aae9e5;
  /* background-image: linear-gradient(310deg, #2dce89 0%, #2dcecc 100%); */
  background-image: linear-gradient(310deg, rgb(55, 211, 190) 0%, #aae9e5 100%);
}
</style>
