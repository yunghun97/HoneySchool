<template>
  <div id="outer-div">
    <div class="d-flex justify-content-center">
      <div class="card m-3 p-4 profile-card">
        <h3 class="font-A1-500 my-2 pb-3">내 정보</h3>
        <div class="card-body">
          <div class="row">
            <div class="col-2"></div>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">이름</p>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300">{{ userinfo.name }}</p>
          </div>
          <div class="row">
            <div class="col-2"></div>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">구분</p>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300" v-if="userinfo.position ==='S'">
              학생
            </p>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300" v-else>
              선생님
            </p>
          </div>
          <div class="row">
            <div class="col-2"></div>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">아이디</p>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300">{{ userinfo.userId }}</p>
          </div>
          <div class="row">
            <div class="col-2"></div>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">학교</p>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300">{{ userinfo.school }}</p>
          </div>
          <div class="row">
            <div class="col-2"></div>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">학년</p>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300">{{ userinfo.grade }}</p>
          </div>
          <div class="row">
            <div class="col-2"></div>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">반</p>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300">{{ userinfo.class_number }}</p>
          </div>
          <div class="row">
            <div class="col-2"></div>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">번호</p>
            <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300">{{ userinfo.student_number }}</p>
          </div>
          <!-- <div class="row mt-4">
            <div class="col-1"></div>
            <button type="submit" class="login-btn d-block font-A1-500 col-4">내 정보 수정</button>
            <div class="col-2"></div>
            <button type="submit" class="withdrawal-btn d-block font-A1-500 col-4">회원 탈퇴</button>
            <div class="col-1"></div>
          </div> -->
          <div class="mt-4 d-flex justify-content-center">
            <button type="button" class="login-btn d-block font-A1-500 mx-3">
              <router-link to="/editprofile" class="text-decoration-none font-A1-500 btn-color">정보 수정</router-link>
            </button>
            <button type="button" class="withdrawal-btn d-block font-A1-500 mx-3" data-bs-toggle="modal" data-bs-target="#exampleModal">회원 탈퇴</button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">정말로 회원 탈퇴하시겠습니까?</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
        <button type="button" class="btn btn-danger" @click="leave">회원 탈퇴</button>
      </div>
    </div>
  </div>
</div>
</template>

<script lang="ts">
import axios from "axios";
import { useStore } from 'vuex';
export default {
    name: "Profile",
    setup() {

    const localStorageData = localStorage.getItem("vuex");
    let userinfoData;
    if (localStorageData !== null) {
      userinfoData = JSON.parse(localStorageData);
    }
    let userinfo = userinfoData.accountStore.userinfo;

    const store = useStore();
    const leave = () => {
      axios.delete(process.env.VUE_APP_API_URL+`/users/${userinfo.userId}`)
      .then(()=> {
        store.commit("accountStore/logout")
      })
    }
    return {userinfo, leave}
    },
};
</script>

<style>
.profile-card {
  width: 500px;
  padding-left: 100px;
}
.text-left {
  text-align: left;
}
.btn-color {
  color: #67748E;
}
.login-btn {
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
  background-image: linear-gradient(310deg, #fad749 0%, #fad749 100%);
  /* 밑에 두개가 내가 한 것 */
  /* background-color: #aae9e5; */
  /* background-image: linear-gradient(310deg, rgb(55, 211, 190) 0%, #aae9e5 100%); */
  /* 원본 색 */
  /* background-image: linear-gradient(310deg, #2dce89 0%, #2dcecc 100%); */
}
.withdrawal-btn {
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
  background-image: linear-gradient(310deg, #c7dee7 0%, #c7dee7 100%);
  /* 밑에 두개가 내가 한 것 */
  /* background-color: #aae9e5; */
  /* background-image: linear-gradient(310deg, rgb(55, 211, 190) 0%, #aae9e5 100%); */
  /* 원본 색 */
  /* background-image: linear-gradient(310deg, #2dce89 0%, #2dcecc 100%); */
}
.card {
  box-shadow: 5px 5px 5px rgba(136, 152, 170, 0.15) !important;
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
</style>