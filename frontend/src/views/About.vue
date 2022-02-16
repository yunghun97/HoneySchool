<template>
  <div class="main_page container">
    <!-- <div class="top-container"> -->
      <!-- 선생님, 학생 분기 인사말 -->
      <!-- <h2 v-if="userinfo.position === 'S'">
        {{ userinfo.school }} {{ userinfo.name }}학생 반갑습니다.
      </h2>
      <h2 v-else>{{ userinfo.school }} {{ userinfo.name }}선생님 반갑습니다.</h2>
      <h2>오늘 날짜 : {{ dateNow }}</h2>
      <h2>현재 시간 : {{ timeNow }} &emsp;</h2>
      <h2>현재 교시 : {{ thisClass }} 교시 &emsp; &nbsp; &nbsp;</h2>
    </div> -->
    <div class="row justify-content-center" style="height: 600px">
      <!-- 선생님, 학생 분기 화상수업 -->
      <div class="col-5" id="createSession">
        <img src="@/assets/videoclass/videoclass.png">
        <h3>지금은 <b>{{ thisClassName }}</b> 수업시간 입니다.</h3>
        <div v-if="userinfo.position === 'T'">
          <button class="btn-class" @click="joinSession">수업 개설 및 참석하기</button>
        </div>
        <div v-else>
          <button class="btn-class" @click="joinSession">
            수업 참석하기
          </button>
        </div>
      </div>
      <!-- 선생님, 학생 분기 시간표 -->
      <div class="col-5" id="timetable">
        <h1>{{ userinfo.grade }}학년 {{ userinfo.class_number }}반 시간표</h1>
        <div class="time">
          <p>교시</p>
          <p>시간</p>
          <p>과목</p>
        </div>
        <div v-for="(sub, i) in timetableData" :key="sub.id" >
          <div v-if="thisClass === i + 1" class="time" id="current">
            <p>{{ i + 1 }}교시</p>
            <p>{{ sub.startTime }} ~ {{ sub.endTime }}</p>
            <p>{{ sub.subject }}</p>
          </div>
          <div v-else class="time">
            <p>{{ i + 1 }}교시</p>
            <p>{{ sub.startTime }} ~ {{ sub.endTime }}</p>
            <p>{{ sub.subject }}</p>
          </div>
        </div>
        <!-- <table class="timetable">
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
        </table> -->
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
    let download = "undefined";
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
        userinfo.school_number +
        "_" +
        userinfo.grade +
        "_" +
        userinfo.class_number +
        "_" +
        thisClass.value
    );
    let dateNow = dayjs().format("YYYY-MM-DD");
    let timeNow = ref(dayjs().format("HH:mm:ss"));
    axios
      .get(process.env.VUE_APP_API_URL + "/timetable/today", {
        params: {
          // school: "노형 초등학교",
          // grade: 1,
          // classes: 1,
          // date: "2022-02-11",
          school: userinfo.school,
          grade: userinfo.grade,
          classes: userinfo.class_number,
          date: dayjs().format("YYYY-MM-DD"),
        },
      })
      .then((response) => {
        timetableData.value = response.data;
        // console.log(timetableData);
        // console.log(response.data);

        // 현재 시각과 현재 수업교시 구하기, 1초마다 갱신
        const getThistime = () => {
          response.data.forEach(function (value: any, index: any, array: any) {
            timeNow.value = dayjs().format("HH:mm:ss");
            let today = new Date();
            // let hour = Number(today.getHours());
            // let minutes = Number(today.getMinutes());
            let hour = 9;
            let minutes = 50;
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
                userinfo.school_number +
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
    const getFile = () =>{
      return axios.get(process.env.VUE_APP_API_URL+"/board/file")
      .then((response)=> {
        console.log(response)
        download = response.data;        
        // let blob = new Blob([response.data], {type: "image/jpeg"});
        console.log(response.data);
        let array = [];
        for (let i = 0; i < response.data.length; i++) {
          array.push(response.data.charCodeAt(i));
        }
        const blob = new Blob([new Uint8Array(array)], {type: 'image/jpeg'});
        download = window.URL.createObjectURL(blob);
        console.log(blob.size, blob.type);
        console.log(download);        
      })
      .catch((error)=> console.log(error))
    }
    return {
      userinfo,      
      timetableData,
      dateNow,
      timeNow,
      thisClass,
      thisClassName,
      joinSession,      
      getFile,
    };
  },
};
</script>
<style scoped>
#createSession {
  background-color: #f99d07;
  border-radius: 40px;
  margin: 30px;
  padding: 60px 20px;
}

#createSession > img {
  max-width: 300px;
  margin-bottom: 25px;
}

#timetable {
  background-color:#37b6f6;
  border-radius: 40px;
  margin: 30px 10px;
  padding: 60px 20px;
}

#current {
  background-color: #F9E104;
}

.container {
  font-family: "Gothic A1", sans-serif;
}
 
.time {
  display: flex;
  justify-content: space-around;
  background-color: #ffff;
  border-radius: 12px;
  margin: 10px auto 0;
  padding: 7px 0;
}

.time > p {
  margin:0;
  font-size: 18px;
  font-weight: bold;
}

.btn-class {
  background-color: #882FF6;
  border: 1px solid #882FF6;
  color: white;
  font-weight: bold;
  font-size: 18px;
  padding: 10px 70px;
  border-radius: 12px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.4);
  margin-top: 10px;
}

.top-container {
  background-color: #F7F7F7;
  border-radius: 40px;
  padding: 10px;
  max-width: 1155px;
  margin: auto;
}

#timetable > h1 {
  margin-bottom: 30px;
}
</style>
