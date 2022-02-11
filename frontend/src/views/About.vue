<template>
  <div class="main_page container">
    <h1>This is an Main page</h1>
    <h2>오늘 날짜 : {{ dateNow }}</h2>
    <h2>현재 시간 : {{ timeNow }}</h2>
    <h2 v-if="userinfo.position === 'S'">
      {{ userinfo.school }} {{ userinfo.name }}학생 반갑습니다.
    </h2>
    <h2 v-else>{{ userinfo.school }} {{ userinfo.name }}선생님 반갑습니다.</h2>
    <div class="row justify-content-center" style="height: 500px">
      <div class="col-5" id="createSession">
        <button class="btn-primary">수업 만들기</button>
      </div>
      <div class="col-5" id="timetable">
        <h1>
          {{ timetableData[0].grade }}학년 {{ timetableData[0].classes }}반 시간표
        </h1>
        <table class="timetable">
          <thead>
            <tr>
              <th scope="col">시간</th>
              <th scope="col">과목</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(sub, i) in timetableData" :key="sub.id">
              <div v-if="timeNow.substring(0,2) === sub.startTime.substring(11,13)">
                <th scope="row" id="current">{{ i+1 }}교시({{ sub.startTime.substring(11,16) }} ~ {{ sub.endTime.substring(11,16) }})</th>
                <td id="current">{{ sub.subject }}</td>
              </div>
              <div v-else>
                <th scope="row">{{ i+1 }}교시({{ sub.startTime.substring(11,16) }} ~ {{ sub.endTime.substring(11,16) }})</th>
                <td>{{ sub.subject }}</td>
              </div>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import dayjs from "dayjs";
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
        
    // 현재 날짜 및 시간 (초단위로 시간 데이터 호출)
    let dateNow = dayjs().format("YYYY-MM-DD");
    let timeNow = ref(dayjs().format("HH:mm:ss"));
    const getTime = () => {
      timeNow.value = dayjs().format("HH:mm:ss");
    };
    setInterval(getTime, 1000);

    // 오늘의 시간표 GET
    let timetableData = ref("등록된 시간표가 없습니다.");
    axios
      .get(process.env.VUE_APP_API_URL + "/timetable/today", {
        params: {
          school: "노형 초등학교",
          grade: 1,
          classes: 1,
          date: dateNow,
        },
      })
      .then((response) => {
        timetableData.value = response.data;
        console.log(timetableData);
      })
      .catch((err) => {
        console.log(err);
      });
    return { userinfo, timetableData, dateNow, timeNow };
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

#current {
  background-color: #882FF6;
}
</style>
