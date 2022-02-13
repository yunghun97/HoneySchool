<template>
<!-- 이전 버튼 -->
  <div v-if="beforearticleidx === -1" class="beforebtn">
    <svg xmlns="http://www.w3.org/2000/svg" width="120" height="120" fill="currentColor" class="bi bi-arrow-left-circle-fill" viewBox="0 0 16 16">
        <path d="M8 0a8 8 0 1 0 0 16A8 8 0 0 0 8 0zm3.5 7.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5z"/>
    </svg>
  </div>
  <div v-else class="beforebtn btns">
    <svg  @click="$router.push({name: 'Assignment', params: { article_id: beforearticleid }})" xmlns="http://www.w3.org/2000/svg" width="120" height="120" fill="#F52532" class="bi bi-arrow-left-circle-fill" viewBox="0 0 16 16">
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
    <svg @click="$router.push({name: 'Assignment', params: { article_id: nextarticleid }})" xmlns="http://www.w3.org/2000/svg" width="120" height="120" fill="#F52532" class="bi bi-arrow-right-circle-fill" viewBox="0 0 16 16">
        <path d="M8 0a8 8 0 1 1 0 16A8 8 0 0 1 8 0zM4.5 7.5a.5.5 0 0 0 0 1h5.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5H4.5z"/>
    </svg>
  </div>
  <div>
    <h4>{{currentarticle.board.title}}</h4>
    <span><p>{{ currentarticle.board.user.name }} &nbsp; {{ currentarticle.board.date.split(' ')[0] }}</p></span>
    <h5>{{currentarticle.board.content}}</h5>
  </div>
  <div v-if="done">
    <h1><span class="badge bg-warning">완료!</span></h1><p></p>
    <div v-if="teacherCom === ''">
        <h4>아직 선생님께서 확인중이에요</h4>
    </div>
    <div v-else>
        <h4>선생님의 한마디: {{teacherCom.content}}</h4>
    </div>
    <img :src="`http://localhost:9999/static/uploads/${myimg}`" alt="X" class="assignmentImg">
  </div>
  <div class="canvas" v-else>
    <Canvas :article="currentarticle" />
  </div>
</template>
<script lang="ts">
import { computed, ref, watch } from 'vue'
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import axios from 'axios';
import Canvas from '../../components/Board/Canvas.vue'
import BoardArticles from "../../types/board/BoardArticles";
interface ArticleArray {
  [index: number] : any
}
interface User {
  [index: string] : any
  userId: string
}
interface Comment {
  [index: number] : any
  id: number
  user: User
  parent_id:number
}
interface File {
  [index: string] : any
  commentId: number
  stored_file_path: string
}
type Comments = Array<Comment>;
type Files = Array<File>;
interface Article {
  [index: string] : any
  comments: Comments
  files: Files

}
export default ({
    name: 'Assignment',
    components: { Canvas},
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

      let currentarticle = ref<Article>({comments:[], files:[]});
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
      let done = ref<boolean>(false)
      let teacherCom = ref<any>('')
      let myimg = ref<any>('')
      let comId = ref<any>('')
      const checkDone = () => {
          for (var i = 0; i < currentarticle.value.comments.length; ++i) {
              const userId = currentarticle.value.comments[i].user.userId
              if (userId === userinfo.userId) {
                  done.value = true
                  comId.value = currentarticle.value.comments[i].id
              } else if (comId.value === currentarticle.value.comments[i].parent_id) {
                  teacherCom.value = currentarticle.value.comments[i]
              }
          }
          for (var j = 0; j < currentarticle.value.files.length; ++j) {
              const fileId = currentarticle.value.files[j].commentId
              if (fileId === comId.value) {
                  myimg.value = currentarticle.value.files[j].stored_file_path
              }
          }
      }

      const articles = computed(() => store.state.boardStore.assignment);
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
      // 대댓글 조회 error
      // const getReComment = () => {
      //     axios.get(process.env.VUE_APP_API_URL+`/board/class/${route.params.article_id}/comment/${comId.value}`)
      //     .then((res)=>{
      //         console.log(res)
      //     })
      // }
      articleDetail().then(() => {
          getCurrentArticle()
          checkDone()
      }).then(() => {
          isLoading.value = false
      })

      watch(() => route.params, (newVal, oldVal) => {
          isLoading.value = true
          articleDetail().then(() => {
          done.value=false
          myimg.value=''
          comId.value=''
          getCurrentArticle()
          checkDone() 
          }).then(() => {
            isLoading.value = false
          })
      })


      return { isLoading, userinfo, articles, getCurrentArticle, currentarticle, 
      beforearticleidx, nextarticleidx, beforearticleid, nextarticleid, articlesdata,
      done, comId, myimg, teacherCom }
    }
})
</script>
<style scoped>
.canvas {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 90vh;
}
.nextbtn {
  float:right;
  margin-right:80px;  
}
.beforebtn {
  float:left;
  margin-left:80px;
}
.btns {
  cursor: pointer;
}
.assignmentImg {
  border: 1px solid black;
  height: 800px;
  width: 620px;

}
</style>