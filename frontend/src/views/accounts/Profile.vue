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
            </div>
        </div>
    </div>
  </div>
</template>

<script>
export default {
    name: "Profile",
    created() {
    const localStorageData = localStorage.getItem("vuex");
    let userinfoData;
    if (localStorageData !== null) {
      userinfoData = JSON.parse(localStorageData);
    }
    this.userinfo = userinfoData.accountStore.userinfo;
    console.log(this.userinfo);
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