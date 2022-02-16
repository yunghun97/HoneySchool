<template>
  <div id="outer-div">
    <div class="d-flex justify-content-center">
      <div class="card m-3 p-4 profile-card">
        <h3 class="font-A1-500 my-2 pb-3">내 정보 수정</h3>
        <div class="card-body">
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">이름</p>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300">
              </p>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">구분</p>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300">
                {{ userinfo.position }}
              </p>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">아이디</p>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300">{{ userinfo.userId }}</p>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">비밀번호</p>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300"></p>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">비밀번호 확인</p>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300"></p>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">학교</p>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300"></p>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">학년</p>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300"></p>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">반</p>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300"></p>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">번호</p>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300"></p>
            </div>
            <div class="mt-4 d-flex justify-content-center">
              <button type="submit" class="login-btn d-block font-A1-500 mx-3">수정 완료</button>
            </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
    name: "EditProfile",
    created() {
    const localStorageData = localStorage.getItem("vuex");
    let userinfoData;
    if (localStorageData !== null) {
      userinfoData = JSON.parse(localStorageData);
    }
    this.userinfo = userinfoData.accountStore.userinfo;
    console.log(this.userinfo);
    },
}
</script>

<style>

</style>