<template>
<div class="root">
  <div
  class="box">
  <div
    cols="4"
    v-for="category in categories"
    :key="category"
  >
    <div
      class="card"
      @click="pushRouter(category.url)"

      :style="{backgroundColor : category.color}"
    >
      <div class="card-body">
        <div class="card-icon">
          <img
            :src="require(`@/assets/board/${category.url}.png`)"
            alt="img"
          />
        </div>
        <div class="card-name">
          <h1>
            {{ category.name }}
          </h1>
        </div>

      </div>
    </div>
  </div>

  </div>
</div>
</template>

<script lang="ts">
import router from "../../router";
import { defineComponent, ref, computed, onMounted } from 'vue'
import { useStore } from 'vuex';
import Category from '../../types/board/Category'
import BoardArticles from "../../types/board/BoardArticles";
import axios from "axios";
type boardType = Array<BoardArticles>;
      // @click="$router.push({name: 'BoardTable', params: { category: category.url }, replace:true})"


export default defineComponent({
    name: 'ClassBoard',
    //beforeCreate() {
      //console.log('before')
      // axios.get("http://localhost:9999/api/v1/test")
        // axios.get("http://localhost:9999/api/v1/board/class",{
        //   params:{
        //     school: "싸피초",
        //     grade: 1,
        //     classes: 1,
        //   }
        // })
        // .then((data)=>{
        //   console.log(data);
        // })
        // .catch(()=>
        //   alert("실패!")
        // )        

    //},
    setup() {
        
        const store = useStore();
        store.dispatch('boardStore/getArticles')
        
        
        const categories= ref<Category[]>([
        {
          url: "notice",
          name: "알림장",
          color: "#FAD749",
        },
        {
          url: "handouts",
          name: "유인물",
          color: "#35D461",
        },
        {
          url: "photo",
          name: "사진첩",
          color: "#37B6F6",
        },
        {
          url: "assignment",
          name: "숙제하기",
          color: "#882FF6",
        },
        {
          url: "questions",
          name: "질문하기",
          color: "#F99D07",
        },
        {
          url: "all",
          name: "전체게시판",
          color: "#F52532",
        }
        ])
        // TODO : category 선택 시, 게시글 카테고리 필터 서버에 요청
        const pushRouter = (category:string) => {
          store.dispatch("boardStore/classifyCategory")
          if (category == "notice") {
            const article = computed(() => store.state.boardStore.notice[0]).value as BoardArticles;
            return router.push({name: 'Notice', params: { article_id: article.id, }})
          } else if (category =="handouts") {
            const articles = computed(() => store.state.boardStore.handouts[0]);
            return router.push({name: 'Handout', params: { article_id: articles.value.id }})
          } else if (category == "photo") {
            const articles = computed(() => store.state.boardStore.photo[0]);
            return router.push({name: 'Photo', params: { article_id: articles.value.id }})
          } else if (category == "assignment") {
            // const articles = computed(() => store.state.boardStore.photo[0]);
            return router.push({name: 'Assignment'})
          } else if (category == "questions") {
            // const articles = computed(() => store.state.boardStore.photo[0]);
            return router.push({name: 'Question'})
          } else if (category == "all") {
            return router.push({name: 'BoardTable'})
          }

        //   if (category === "notice" || category === "handouts" || category === "photo") {
        //      return router.push({name: 'ArticleDetail', params: { category:category ,article_id: recentArticle.id }})
        //   }
      
        }

          

        return { categories, pushRouter } 
    }

})
</script>
<style scoped>
.root {
    height: 100%;
    width: 100%;
}
.box {
  background-color: #fcf1c9;
  border-radius: 18px;
  height: auto;
  width: auto;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: repeat(2, 3fr);
  margin: 0;
  padding: 10px;
}
.card:hover {
  transform: scale (1.15);
  box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.9);
}
.card {
  display: flex;
  flex-direction: column;
  min-width: 50px;
  border-radius: 18px;
  box-shadow: 5px 5px 15px rgba(0,0,0,0.4);
  font-family: roboto;
  text-align: center;
  transition: 0.5s ease;
  cursor: pointer;
  margin: 10px;
  padding: 30px
}
.card-name {
  margin-top: 20px;
}
.card-icon {
  flex:auto;
}
</style>