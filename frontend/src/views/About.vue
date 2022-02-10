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
        <p>{{ timetableData }}</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import { ref } from "vue";

export default {
  setup() {
    // LocalStorage의 유저정보 도출
    const localStorageData = localStorage.getItem("vuex");
    let userinfoData;
    if (localStorageData !== null) {
      userinfoData = JSON.parse(localStorageData);
    }
    let userinfo = userinfoData.accountStore.userinfo;

    // 오늘의 시간표 GET
    let timetableData = ref("등록된 시간표가 없습니다.");
    axios
      .get(process.env.VUE_APP_API_URL + "/timetable/today", {
        params: {
          school: "싸피초",
          grade: 1,
          classes: 1,
          date: "2022-02-21",
        },
      })
      .then((response) => {
        timetableData.value = response.data[0];
      })
      .catch((err) => {
        console.log(err);
      });
    return { userinfo, timetableData };
  },
};
</script>
<style scoped>
#createSession {
  background-color: #37b6f6;
  border-radius: 50px;
  margin: 30px;
}

#timetable {
  background-color: #f99d07;
  border-radius: 50px;
  margin: 30px;
}
</style>
