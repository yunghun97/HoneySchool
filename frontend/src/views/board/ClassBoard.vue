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
      class="cardbtn"
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
import { defineComponent, ref, computed } from 'vue'
import { useStore } from 'vuex';
import Category from '../../types/board/Category'
import BoardArticles from "../../types/board/BoardArticles";
type boardType = Array<BoardArticles>;

export default defineComponent({
    name: 'ClassBoard',
    setup() {
        
        const store = useStore();
        const localStorageData = localStorage.getItem("vuex");
        let userinfoData;
        if (localStorageData !== null) {
          userinfoData = JSON.parse(localStorageData);
        }
        let userinfo = userinfoData.accountStore.userinfo;
        
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

        const pushRouter = async(category:string) => {
          if (category == "all") {
            return router.push({name: 'BoardTable'})
          } else {
            await store.dispatch("boardStore/classifyCategory", [category, userinfo, 0])
              if (category == "notice") {
                const article = await computed(() => store.state.boardStore.notice[0]).value as BoardArticles;
                if (article === undefined) {
                  alert('아직 게시글이 없습니다.')
                } else {
                  return router.push({name: 'Notice', params: { article_id: article.id }})
                }
              } else if (category =="handouts") {
                const article = await computed(() => store.state.boardStore.handouts[0]).value as BoardArticles;
                if (article === undefined) {
                  alert('아직 게시글이 없습니다.')
                } else {
                  return router.push({name: 'Handout', params: { article_id: article.id }})
                }
              } else if (category == "photo") {
                const article = await computed(() => store.state.boardStore.photo[0]).value as BoardArticles;
                if (article === undefined) {
                  alert('아직 게시글이 없습니다.')
                } else {
                  return router.push({name: 'Photo', params: { article_id: article.id }})
                }
              } else if (category == "assignment") {
                const article = computed(() => store.state.boardStore.assignment[0]).value as BoardArticles;
                if (article === undefined) {
                  alert('아직 게시글이 없습니다.')
                } else {
                  return router.push({name: 'Assignment', params: { article_id: article.id }})
                }
              } else if (category == "questions") {
                const article = computed(() => store.state.boardStore.question[0]);
                return router.push({name: 'Question'})
              } 
          }
        }
        return { categories, pushRouter, userinfo } 
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
.cardbtn:hover {
  transform: scale (1.15);
  box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.9);
}
.cardbtn {
  display: flex;
  flex-direction: column;
  min-width: 50px;
  border-radius: 18px;
  box-shadow: 5px 5px 15px rgba(0,0,0,0.4);
  text-align: center;
  transition: 0.5s ease;
  cursor: pointer;
  margin: 10px;
  padding: 30px;
}
.card-name {
  margin-top: 20px;
}
.card-icon {
  flex: auto;
}
</style>