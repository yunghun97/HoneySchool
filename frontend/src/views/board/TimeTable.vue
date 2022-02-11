<template>
   <div v-if="isLoading">
        <p>...LOADING</p>
    </div>
    <div v-else>
        <p>{{timedetail}}</p>
    <!-- <table>
        <thead>
            <tr>
                <th scope="col">수업 시작</th>
                <th scope="col">수업 종료</th>
                <th scope="col">과목명</th>
                <th scope="col">과목명</th>
                <th scope="col">과목명</th>
                <th scope="col">과목명</th>
                <th scope="col">과목명</th>
                <th scope="col">과목명</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table> -->
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
                    <button 
                        type="button" class="btn btn-danger"
                        @click="deleteArticle"
                        >
                        삭제하기
                    </button>
                </div> -->
</template>

<script lang="ts">
import { ref } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router'
import axios from 'axios';

interface timedetail {
    [index: string] : any;
}


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
        console.log(userinfo)
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
            .catch(()=>
                alert("실패!")
            )  
        }
        articleDetail().then(()=>isLoading.value = false)
        const date=['월','화','수','목','금'];
        let mon = ref<Array<any>>([]);
        let tue = ref<Array<any>>([]);
        let wed = ref<Array<any>>([]);
        let thu = ref<Array<any>>([]);
        let fri = ref<Array<any>>([]);
        
        
        


        return { id, isLoading, userinfo, timedetail, articleDetail }
    }
}
</script>
<style scoped>
.card {
    margin: 30px;
    width: 1320px;
    display: inline-block;
}
.card-body > p{
    text-align: right;
}
.btns {
    margin-bottom:20px;
    text-align: right;
}
.content-container {
    text-align: left;
}
.comment-box {
    padding-top: 20px;
    background-color: #f8f9fa;
}
.comment-box > h5 {
    text-align: left;
}
.comment-btn {
    display:inline-block; 
    margin-top: -42px;
    /* float: right; */
}
.comment-list {
    text-align: left;
    margin-bottom: 30px;
}

.fa-icon{
    width: 15px;
}
.fa-icon-b{
    width: 30px;
}
.post-btn {
    float: right;
}
button {
    margin:5px;
}
img {
    max-width: 600px;
    height: auto;
    
}
</style>