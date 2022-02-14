<template>
  <div class="main_page container">
    <h1>This is an Main page</h1>
    <h2>오늘 날짜 : {{ dateNow }}</h2>
    <h2>현재 시간 : {{ timeNow }}</h2>
    <h2>{{ thisClass }} 교시</h2>
    <!-- 선생님, 학생 분기 인사말 -->
    <h2 v-if="userinfo.position === 'S'">
      {{ userinfo.school }} {{ userinfo.name }}학생 반갑습니다.
    </h2>
    <h2 v-else>{{ userinfo.school }} {{ userinfo.name }}선생님 반갑습니다.</h2>
    <div class="row justify-content-center" style="height: 500px">
      <!-- 선생님, 학생 분기 화상수업 -->
      <div class="col-5" id="createSession">
        <h3>지금은 {{ thisClassName }} 수업시간 입니다.</h3>
        <div v-if="userinfo.position === 'T'">
          <button class="btn-primary" @click="joinSession">수업 만들기, 참석하기</button>
        </div>
        <div v-else>
          <button class="btn-primary" @click="joinSession">
            수업 참석하기
          </button>
        </div>
      </div>
      <!-- 선생님, 학생 분기 시간표 -->
      <div class="col-5" id="timetable">
        <h1>{{ userinfo.grade }}학년 {{ userinfo.class_number }}반 시간표</h1>
        <table class="timetable">
          <thead>
            <tr>
              <th scope="col">시간</th>
              <th scope="col">과목</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(sub, i) in timetableData" :key="sub.id">
              <div v-if="thisClass === i + 1">
                <th scope="row" id="current">
                  {{ i + 1 }}교시({{ sub.startTime }} ~ {{ sub.endTime }})
                </th>
                <td id="current">{{ sub.subject }}</td>
              </div>
              <div v-else>
                <th scope="row">
                  {{ i + 1 }}교시({{ sub.startTime }} ~ {{ sub.endTime }})
                </th>
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
import { useRouter } from "vue-router";

export default {
  setup() {
    const router = useRouter();
    // LocalStorage의 유저정보 도출
    const localStorageData = localStorage.getItem("vuex");
    let userinfoData;
    if (localStorageData !== null) {
      userinfoData = JSON.parse(localStorageData);
    }
    let userinfo = userinfoData.accountStore.userinfo;

    // 오늘의 시간표 GET & 현재시각과 비교 => 현재 수업 확인
    let timetableData = ref([]);
    let thisClass = ref(10);
    let thisClassName = ref("");
    // let mySessionName = ref("");
    let mySessionName = ref(
      dayjs().format("YYMMDD") +
        "_" +
        "1" +
        "_" +
        userinfo.grade +
        "_" +
        userinfo.class_number +
        "_" +
        "1"
    );
    let dateNow = dayjs().format("YYYY-MM-DD");
    let timeNow = ref(dayjs().format("HH:mm:ss"));
    axios
      .get(process.env.VUE_APP_API_URL + "/timetable/today", {
        params: {
          school: "노형 초등학교",
          grade: 1,
          classes: 1,
          date: "2022-02-11",
          // school: userinfo.school,
          // grade: userinfo.grade,
          // classes: userinfo.class_number,
          // date: dayjs().format("YYYY-MM-DD"),
        },
      })
      .then((response) => {
        timetableData.value = response.data;
        console.log(timetableData);
        console.log(response.data);

        // 현재 시각과 현재 수업교시 구하기, 1초마다 갱신
        const getThistime = () => {
          response.data.forEach(function (value: any, index: any, array: any) {
            timeNow.value = dayjs().format("HH:mm:ss");
            let today = new Date();
            // let hour = Number(today.getHours());
            // let minutes = Number(today.getMinutes());
            let hour = 13;
            let minutes = 30;
            let startTimeH = Number(value.startTime.substring(0, 2));
            let startTimeM = Number(value.startTime.substring(3, 5));
            let endTimeH = Number(value.endTime.substring(0, 2));
            let endTimeM = Number(value.endTime.substring(3, 5));
            // 수업시간 기준으로 교시 지정
            if ((startTimeH == hour && startTimeM <= minutes) || (endTimeH == hour && endTimeM > minutes)) {
              // 이번교시 번호와 이름 저장 (1~6교시)
              thisClass.value = index + 1;
              thisClassName.value = value.subject;
              mySessionName.value =
                dayjs().format("YYMMDD") +
                "_" +
                // userinfo.school +
                "1" +
                "_" +
                userinfo.grade +
                "_" +
                userinfo.class_number +
                "_" +
                String(index + 1);
            }
          });
        };
        setInterval(getThistime, 1000);
      })
      .catch((err) => {
        console.log(err);
      });

    // Join Session
    const joinSession = () => {
      axios
        .get(process.env.VUE_APP_API_URL + "/lecture")
        .then((response) => {
          let classOn = false;
          console.log(mySessionName.value);
          console.log(response.data.content);
          response.data.content.forEach((element: any) => {
            if (element.id === mySessionName.value) {
              classOn = true;
            }
          });
          if (classOn) {
            console.log("수업 있음");
            if (userinfo.position === "S") {
              router.push({
                name: "Videoclass",
                params: { UserName: userinfo.name, SessionId: mySessionName.value, ClassName: thisClassName.value },
              });
            } else {
              router.push({
                name: "Videoclass",
                params: { UserName: userinfo.name + "선생님", SessionId: mySessionName.value, ClassName: thisClassName.value },
              });
            }
          } else {
            if (userinfo.position === "S") {
              alert("해당 수업이 없습니다.");
            } else {
              router.push({
                name: "Videoclass",
                params: { UserName: userinfo.name + "선생님", SessionId: mySessionName.value, ClassName: thisClassName.value },
              });
            }
          }
        })
        .catch((error) => {
          console.log(error);
        });
    };

    return {
      userinfo,
      timetableData,
      dateNow,
      timeNow,
      thisClass,
      thisClassName,
      joinSession,
    };
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
  background-color: #882ff6;
}
</style>
