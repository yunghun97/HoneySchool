<template>  
  <div>
    <!-- 이전 버튼 -->
    <div v-if="beforearticleidx === -1" class="beforebtn">
      <svg xmlns="http://www.w3.org/2000/svg" width="120" height="120" fill="currentColor" class="bi bi-arrow-left-circle-fill" viewBox="0 0 16 16">
        <path d="M8 0a8 8 0 1 0 0 16A8 8 0 0 0 8 0zm3.5 7.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5z"/>
      </svg>
    </div>
    <div v-else class="beforebtn btns">
      <svg  @click="$router.push({name: 'Photo', params: { article_id: beforearticleid }})" xmlns="http://www.w3.org/2000/svg" width="120" height="120" fill="#F52532" class="bi bi-arrow-left-circle-fill" viewBox="0 0 16 16">
        <path d="M8 0a8 8 0 1 0 0 16A8 8 0 0 0 8 0zm3.5 7.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5z"/>
      </svg>
    </div>

    <!-- 이후 버튼 -->
    <div v-if="nextarticleidx === -1" class="nextbtn">
      <svg xmlns="http://www.w3.org/2000/svg" width="120" height="120" fill="currentColor" class="bi bi-arrow-right-circle-fill" viewBox="0 0 16 16">
        <path d="M8 0a8 8 0 1 1 0 16A8 8 0 0 1 8 0zM4.5 7.5a.5.5 0 0 0 0 1h5.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5H4.5z"/>
      </svg>
    </div>
    <div v-else class="nextbtn btns">
      <svg @click="$router.push({name: 'Photo', params: { article_id: nextarticleid }})" xmlns="http://www.w3.org/2000/svg" width="120" height="120" fill="#F52532" class="bi bi-arrow-right-circle-fill" viewBox="0 0 16 16">
        <path d="M8 0a8 8 0 1 1 0 16A8 8 0 0 1 8 0zM4.5 7.5a.5.5 0 0 0 0 1h5.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5H4.5z"/>
      </svg>
    </div>

    <div style="background-color:#37B6F6">
      <div 
        class="note"
    >
        <div class="content">
          <h1>{{ currentarticle.board.title }}</h1>
          <p v-if="currentarticle.board.user!==null" class="fs-5 d-flex justify-content-between">
            <!-- <span>작성자 : {{ currentarticle.board.user.name }} &nbsp; | &nbsp; 작성날짜 : {{currentarticle.board.date.split(" ")[0] }}</span></p> -->
            <span>작성자 : {{ currentarticle.board.user.name }}</span>
            <span>날짜 : 
              <span class="color-blue">
                {{currentarticle.board.date.split(" ")[0] }}
              </span>
            </span>
          </p>
          <!-- <p>{{ currentarticle.board.content }}</p> -->
          <div v-if="currentarticle.files.length > 0">
            <!-- <div v-for="file in currentarticle.files" :key="file.id">
              <img :src="`http://localhost:9999/static/uploads/${file.stored_file_path}`" class="w-100" alt="사진을 찾을 수 없습니다.">
            </div> -->
            <div id="carouselIndicators" class="carousel slide" data-bs-ride="carousel">
              <div class="carousel-inner">
                <div class="carousel-item" v-for="(image, idx) in currentarticle.files" :key="idx" :class="{ active: idx==0 }">
                  <img :src="`https://i6b201.p.ssafy.io:9999/file/${image.stored_file_path}`" alt="사진을 찾을 수 없습니다.">
                </div>
              </div>
              <fa icon="angle-left" class="carousel-control-prev" type="button" data-bs-target="#carouselIndicators" data-bs-slide="prev" style="color:#87C7F1;">
                <!-- <span class="carousel-control-prev-icon" aria-hidden="false"></span> -->
                <span class="visually-hidden">Previous</span>
              </fa>
              <fa icon="angle-right" class="carousel-control-next" type="button" data-bs-target="#carouselIndicators" data-bs-slide="next" style="color:#87C7F1;">
                <!-- <span class="carousel-control-next-icon" aria-hidden="false"></span> -->
                <span class="visually-hidden">Next</span>
              </fa>
            </div>
          </div>
        </div>
        </div>
    </div>
  </div>
</template>

<script lang="ts">
import axios from 'axios';
import { computed, ref, watch } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import BoardArticles from "../../types/board/BoardArticles";
interface ArticleArray {
    [index: number] : any
}

export default 
  {
  name: "Photo",
  setup() {
    const store = useStore();
    const route = useRoute();

    let isLoading = ref<boolean>(true);
    const localStorageData = localStorage.getItem("vuex");
    let userinfoData;
    if (localStorageData !== null) {
    userinfoData = JSON.parse(localStorageData);
    }
    let userinfo = userinfoData.accountStore.userinfo;
    let currentarticle = ref({});
    const articleDetail = () => {
      return axios.get(process.env.VUE_APP_API_URL+"/board/class/detail",{
        params:{
        school: userinfo.school,
        grade: userinfo.grade,
        classes: userinfo.class_number,
        id : route.params.article_id
        }
      })
      .then((response)=>{
        currentarticle.value = response.data
      })
    }

    const articles = computed(() => store.state.boardStore.photo);
    const articlesdata = articles.value as ArticleArray
    
    let nextarticleidx = ref<number>(articles.value.length);
    let nextarticleid = ref<number>(0);

    let beforearticleidx = ref<number>(-1);
    let beforearticleid = ref<number>(0);

    const getCurrentArticle = () => {
      for (var i = 0; i < articles.value.length; ++i) {
        const article = articlesdata[i] as BoardArticles
          
        if (article.id === +route.params.article_id) {
            if (i == 0) {
              beforearticleidx.value = -1
              }
            if (i >= 1) {
              beforearticleidx.value = i - 1
              beforearticleid.value = articlesdata[i - 1].id
            }
            if (i < articles.value.length - 1) {
              nextarticleidx.value = i + 1
              nextarticleid.value = articlesdata[i + 1].id

            } else if (i === articles.value.length -1) {
              nextarticleidx.value = -1
            }
        break }
      } 
    }
    articleDetail().then(() => {
      getCurrentArticle()
      isLoading.value = false
    })
      
    watch(() => route.params, (newVal, oldVal) => {
      isLoading.value = true
      articleDetail().then(() => {
      getCurrentArticle()
      isLoading.value = false
      })
    })


    return { isLoading, userinfo, articles, getCurrentArticle, currentarticle, beforearticleidx, nextarticleidx, beforearticleid, nextarticleid, articlesdata }
  }
}
</script>
<style scoped>
.note {
  margin: auto;
  background-image: url('../../assets/board/photo-background.png');
  height: 90vh;
  width: 150vh;
  background-position: center;
  background-size: contain;
  background-repeat: no-repeat;
}
.btns {
  cursor: pointer;
}
button {
  margin:5px;
}
img {
  width: 80vh;
  max-height: 600px;
}
.nextbtn {
  float:right;
  margin:80px;
}
.beforebtn {
  float:left;
  margin:80px;
}
.content {
  padding-top: 15vh;
  margin-bottom: 10px;
  font-family: 'Gothic A1', sans-serif;
}
.content > p  {
  margin-right: 25vh;
  margin-left: 30vh;
  /* text-align: right; */
}
.carousel {
  left: 60vh;
  position: absolute;
}
.carousel-control-prev {
  width: 100px;
  height: 100px;
  left: -100px;
  top: 100px;
}
.carousel-control-next {
  width: 100px;
  height: 100px;
  right: -100px;
  top: 100px;
}
.color-blue {
    color: #13a1ff;
}

</style>
