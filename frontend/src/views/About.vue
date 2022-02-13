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
              <div v-if="thisClass === i">
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
    let thisClass = ref(0);
    // let myclassName = ref("");
    let myclassName = ref(
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
            let hour = Number(today.getHours());
            let minutes = Number(today.getMinutes());
            let startTimeH = Number(value.startTime.substring(0, 2));
            let startTimeM = Number(value.startTime.substring(3, 5));
            if (startTimeH == hour && startTimeM <= minutes) {
              // 이번교시 숫자 저장 (1~6교시)
              thisClass.value = index + 1;
              myclassName.value =
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
              console.log("이번 수업시간");
              console.log(myclassName.value);
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
          console.log(myclassName.value);
          console.log(response.data.content);
          response.data.content.forEach((element: any) => {
            if (element.id === myclassName.value) {
              classOn = true;
            }
          });
          if (classOn) {
            console.log("수업 있음");
            if (userinfo.position === "S") {
              router.push({
                name: "Videoclass",
                params: { UserName: userinfo.name, SessionId: myclassName.value },
              });
            } else {
              router.push({
                name: "Videoclass",
                params: { UserName: "선생님", SessionId: myclassName.value },
              });
            }
          } else {
            if (userinfo.position === "S") {
              alert("해당 수업이 없습니다.");
            } else {
              router.push({
                name: "Videoclass",
                params: { UserName: "선생님", SessionId: myclassName.value },
              });
            }
          }
        })
        .catch((error) => {
          console.log(error);
        });
    };

    // 1분마다 활성화된 모든 세션 중 내 수업 탐색
    // const myClass = () => {
    //   axios
    //     .get(process.env.VUE_APP_API_URL + "/lecture")
    //     .then((response) => {
    //       console.log(response.data.content);
    //       response.data.content.forEach((element: any) => {
    //         if (element.id === myclassName) {
    //           console.log("일치");
    //         }
    //       });
    //     })
    //     .catch((error) => {
    //       console.log(error);
    //     });
    // };
    // setInterval(myClass, 10000);

    return {
      userinfo,
      timetableData,
      dateNow,
      timeNow,
      thisClass,
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
