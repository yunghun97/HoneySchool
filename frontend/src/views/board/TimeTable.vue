<template>
  <div v-if="isLoading">
    <div class="spinner-border" role="status"></div>
    <p>LOADING...</p>
  </div>
  <div v-else class="box">
    <div class="buttons">
      <button 
      type="button" class="btn btn-outline-success buttonall"
      @click="$router.go(-1)"
      >
        전체 게시판
      </button>
      <div v-if="userinfo.position==='T'">
        <button v-if="!isEditing" type="button" class="btn btn-outline-primary" @click="requestEdit">수정하기</button>
        <button v-else type="button" class="btn btn-primary" @click="editTimetable">수정완료</button>
      </div>
    </div>
    <table class="table">
      <thead>
      <tr>
        <th scope="col">교시</th>
        <th scope="col">시작 시간</th>
        <th scope="col">종료 시간</th>
        <th scope="col">월요일</th>
        <th scope="col">화요일</th>
        <th scope="col">수요일</th>
        <th scope="col">목요일</th>
        <th scope="col">금요일</th>
      </tr>
      </thead>
      <tbody v-if="!isEditing">
        <tr
          v-for="idx in 4" :key="idx"
        >   
          <td>{{idx}}교시</td>
          <td v-if="standardTime.length >= idx-1">{{ standardTime[idx-1][0] }}</td>
          <td v-else class="table-warning"></td>
          <td v-if="standardTime.length >= idx">{{ standardTime[idx-1][1] }}</td>
          <td v-else class="table-warning"></td>
          <td v-if="table[0].length >= idx-1">{{ table[0][idx-1].subject }}</td>
          <td v-else class="table-warning"></td>
          <td v-if="table[1].length >= idx-1">{{ table[1][idx-1].subject }}</td>
          <td v-else class="table-warning"></td>
          <td v-if="table[2].length >= idx-1">{{ table[2][idx-1].subject }}</td>
          <td v-else class="table-warning"></td>
          <td v-if="table[3].length >= idx-1">{{ table[3][idx-1].subject }}</td>
          <td v-else class="table-warning"></td>
          <td v-if="table[4].length >= idx-1">{{ table[4][idx-1].subject }}</td>
          <td v-else class="table-warning"></td>
        </tr>
        <tr>
          <th class="table-warning"></th>
          <td class="table-warning">12:10</td>
          <td class="table-warning">13:00</td>
          <td class="table-warning">점</td>
          <td class="table-warning">심</td>
          <td class="table-warning">시</td>
          <td class="table-warning">간</td>
          <td class="table-warning"></td>
        </tr>
        <tr 
          v-for="idx in 2" :key="idx"
        >   
          <td>{{idx+4}}교시</td>
          <td v-if="standardTime.length < idx+3" class="table-warning"></td>
          <td v-else >{{ standardTime[idx+3][0] }}</td>
          <td v-if="standardTime.length < idx+3" class="table-warning"></td>
          <td v-else>{{ standardTime[idx+3][1] }}</td>
          <td v-if="table[0].length < idx+3" class="table-warning"></td>
          <td v-else>{{ table[0][idx+3].subject }}</td>
          <td v-if="table[1].length < idx+3" class="table-warning"></td>
          <td v-else>{{ table[1][idx+3].subject }}</td>
          <td v-if="table[2].length < idx+3" class="table-warning"></td>
          <td v-else>{{ table[2][idx+3].subject }}</td>
          <td v-if="table[3].length < idx+3" class="table-warning"></td>
          <td v-else>{{ table[3][idx+3].subject }}</td>
          <td v-if="table[4].length < idx+3" class="table-warning"></td>
          <td v-else>{{ table[4][idx+3].subject }}</td>
        </tr>
      </tbody>
      <tbody v-else>
        <tr
          v-for="idx in 4" :key="idx"
        >   
          <td>{{idx}}교시</td>
          <td>{{ standardTime[idx-1][0] }}</td>
          <td>{{ standardTime[idx-1][1] }}</td>
          <td v-if="table[0].length >= idx-1"><input type="text" class="form-control" :id="`mon${idx}`" :value="table[0][idx-1].subject"></td>
          <td v-else class="table-warning"></td>
          <td v-if="table[1].length >= idx-1"><input type="text" class="form-control" :id="`tue${idx}`" :value="table[1][idx-1].subject"></td>
          <td v-else class="table-warning"></td>
          <td v-if="table[2].length >= idx-1"><input type="text" class="form-control" :id="`wed${idx}`" :value="table[2][idx-1].subject"></td>
          <td v-else class="table-warning"></td>
          <td v-if="table[3].length >= idx-1"><input type="text" class="form-control" :id="`thu${idx}`" :value="table[3][idx-1].subject"></td>
          <td v-else class="table-warning"></td>
          <td v-if="table[4].length >= idx-1"><input type="text" class="form-control" :id="`fri${idx}`" :value="table[4][idx-1].subject"></td>
          <td v-else class="table-warning"></td>
        </tr>
        <tr>
          <th class="table-warning"></th>
          <td class="table-warning">12:10</td>
          <td class="table-warning">13:00</td>
          <td class="table-warning">점</td>
          <td class="table-warning">심</td>
          <td class="table-warning">시</td>
          <td class="table-warning">간</td>
          <td class="table-warning"></td>
        </tr>
        <tr 
          v-for="idx in 2" :key="idx"
        >   
          <td>{{idx+4}}교시</td>
          <td>{{ standardTime[idx+3][0] }}</td>
          <td>{{ standardTime[idx+3][1] }}</td>
          <td v-if="table[0].length >= idx+3"><input type="text" class="form-control" :id="`mon${idx+4}`" :value="table[0][idx+3].subject" ></td>
          <td v-else class="table-warning"></td>
          <td v-if="table[1].length >= idx+3"><input type="text" class="form-control" :id="`tue${idx+4}`" :value="table[1][idx+3].subject"></td>
          <td v-else class="table-warning"></td>
          <td v-if="table[2].length >= idx+3"><input type="text" class="form-control" :id="`wed${idx+4}`" :value="table[2][idx+3].subject"></td>
          <td v-else class="table-warning"></td>
          <td v-if="table[3].length >= idx+3"><input type="text" class="form-control" :id="`thu${idx+4}`" :value="table[3][idx+3].subject"></td>
          <td v-else class="table-warning"></td>
          <td v-if="table[4].length >= idx+3"><input type="text" class="form-control" :id="`fri${idx+4}`" :value="table[4][idx+3].subject"></td>
          <td v-else class="table-warning"></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts">
import router from "../../router";
import { ref } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router'
import axios from 'axios';

interface timedetail {
  [index: string ] : any;
}
interface timeTable {
  [index: number] : Array<any>;
};

export default {
  name: "TimeTable",
  setup() {
    const store = useStore();
    const route = useRoute();

    let isLoading = ref<boolean>(true);
    let id = +route.params.article_id;
    const localStorageData = localStorage.getItem("vuex");
    let userinfoData;
    if (localStorageData !== null) {
    userinfoData = JSON.parse(localStorageData);
    }
    let userinfo = userinfoData.accountStore.userinfo;
    // 시간표 get 요청
    // 이번주 월요일 날짜 구하기
    var today = new Date();
    var day = today.getDay() || 7;
    if (day !== 1 )
        today.setHours(-24 * (day - 1));
    // 이번주의 week number 구하기
    var gettoday = new Date();
    var dayNum = gettoday.setDate(gettoday.getDate() + 4 - (gettoday.getDay() || 7));
    var yearStart = new Date(today.getFullYear(), 0, 1);
    var firstNum = yearStart.setDate(yearStart.getDate() + 4 - (yearStart.getDay() ||7))
    var weeknum = Math.ceil((((dayNum-firstNum) /86400000) + 1)/7);
    let timedetail = ref<timedetail>({});
    const articleDetail = () => {
        return axios.get(process.env.VUE_APP_API_URL+"/timetable/week",{
            params:{
              school: userinfo.school,
              grade: userinfo.grade,
              classes: userinfo.class_number,
              date: `${today.getFullYear()}-${today.getMonth()+1}-${today.getDate()}`,

              },
        })
        .then((response)=>{
            timedetail.value = response.data
      
        })
        .catch(()=> {
            alert("아직 이번주 시간표가 없습니다!")
            router.push({name: 'BoardTable'})
        })  
    }
    const standardTime = [['09:00','09:40'],['09:50','10:30'],['10:40','11:20'],['11:30','12:10'],['13:00','13:40'],['13:50','14:30']]
    let table = [[], [], [], [], []] as timeTable;
    articleDetail().then(() => {            
      timedetail.value.forEach((detail:any) => {
      if (detail.day === "월") {
          table[0].push(detail)
      } else if (detail.day === "화") {
          table[1].push(detail)
      } else if (detail.day === "수") {
          table[2].push(detail)
      } else if (detail.day === "목") {
          table[3].push(detail)
      } else if (detail.day === "금") {
          table[4].push(detail)
      }
    })
    isLoading.value = false
    })
    //시간표 수정
    let isEditing = ref<boolean>(false)
    const requestEdit = () => {
      isEditing.value = true
    }
    const editTimetable = () => {
      const day=['mon','tue','wed','thu','fri'];
      let timetable = [[], [], [], [], []] as timeTable;
      for (var i=1; i<7; i++) {
        day.forEach((d) => {
          const subject = {
            'start_time': standardTime[i-1][0],
            'end_time': standardTime[i-1][1],
            'subject': (document.getElementById(`${d}${i}`) as HTMLInputElement).value,
          }
          timetable[day.indexOf(d)].push(subject)
        })
      }
      let data:any = {
        'year': today.getFullYear(),
        'week': weeknum - 1,
        'school': userinfo.school,
        'grade': userinfo.grade,
        'classes': userinfo.class_number,
        '1': timetable[0],
        '2': timetable[1],
        '3': timetable[2],
        '4': timetable[3],
        '5': timetable[4]
      }
      axios.put(process.env.VUE_APP_API_URL+"/timetable/week", data)
      .then(() => {
        router.push({name: 'BoardTable'})
      })
    }
    
    return { id, isLoading, userinfo, timedetail, articleDetail,
            table, standardTime,
            isEditing, requestEdit, editTimetable
    }
  }
}
</script>
<style scoped>
.box {
  margin: 50px 30vh;
}
.btn {
  margin-bottom: 20px;
  margin-left: 20px;
}
.buttons {
  display: flex;
  justify-content: right;
}
</style>