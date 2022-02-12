<template>
   <div v-if="isLoading">
        <p>...LOADING</p>
    </div>
    <div v-else class="box">
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
            <tbody>
            <tr 
                v-for="idx in 4" :key="idx"
            >   
                <td>{{idx}}교시</td>
                <td v-if="standardTime.length >= idx-1">{{ standardTime[idx-1][0] }}</td>
                <td v-else class="table-warning">{{idx}}</td>
                <td v-if="standardTime.length >= idx">{{ standardTime[idx-1][1] }}</td>
                <td v-else class="table-warning">{{idx-1}}</td>
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
                <tr>
                  <td>5교시</td>
                  <td>13:00</td>
                  <td>13:40</td>
                  <td>{{ table[0][4].subject }}</td>
                  <td>{{ table[1][4].subject }}</td>
                  <td>{{ table[2][4].subject }}</td>
                  <td>{{ table[3][4].subject }}</td>
                  <td>{{ table[4][4].subject }}</td>
                </tr>
                <tr>
                  <td>6교시</td>
                  <td>13:50</td>
                  <td>14:30</td>
                  <td>{{ table[0][4].subject }}</td>
                  <td>{{ table[1][4].subject }}</td>
                  <td>{{ table[2][4].subject }}</td>
                  <td>{{ table[3][4].subject }}</td>
                  <td>{{ table[4][4].subject }}</td>
                </tr>
            </tbody>
        </table>

    </div>
        <!-- <div class="container">
            <div class="row">
                <div class="btns">
                    <button 
                        type="button" class="btn btn-primary"
                        @click="$router.push({ name: 'ArticleUpdate', params: { category: currentarticle.category, article_id: id }})"
                        >
                        수정하기
                    </button>
                </div> -->
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
        
        return { id, isLoading, userinfo, timedetail, articleDetail,
                table, standardTime
        }
    }
}
</script>
<style scoped>
.box {
    margin: 30px 30vh;
}
</style>