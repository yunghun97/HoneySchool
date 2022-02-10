<template>
  <div class="main_page container">
    <h1>This is an Main page</h1>
    <h2 v-if="userinfo.position === 'S'">
      {{ userinfo.school }} {{ userinfo.name }}학생 반갑습니다.
    </h2>
    <h2 v-else>{{ userinfo.school }} {{ userinfo.name }}선생님 반갑습니다.</h2>
    <div class="row justify-content-center" style="height: 500px">
      <div class="col-5" id="createSession">
        <button class="btn-primary">수업 만들기</button>
      </div>
      <div class="col-5" id="timetable">
        <h1>시간표</h1>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
export default {
  setup() {
    const localStorageData = localStorage.getItem("vuex");
    let userinfoData;
    if (localStorageData !== null) {
      userinfoData = JSON.parse(localStorageData);
    }
    let userinfo = userinfoData.accountStore.userinfo;
    return { userinfo };
  },
};
</script>
<style scoped>
#createSession {
  background-color: #37B6F6;
  border-radius: 50px;
  margin: 30px;
}

#timetable {
  background-color: #F99D07;
  border-radius: 50px;
  margin: 30px;
}
</style>
