<template>
    <div class="box">
    <div>
      <div class="cardbtn" id="ask" @click="$router.push({name: 'AskQuestion'})">
        <img src="@/assets/board/recordquestion.png" alt="질문하기" class="card-icon">
        <h2>질문 할래요</h2>
      </div>
      <div class="cardbtn" id="ans" @click="checkAns">
        <img src="@/assets/board/answer.png" alt="답변보기" class="card-icon">
        <h2>선생님 답변 볼래요</h2>
      </div>
    </div>

    </div>

</template>
<script lang="ts">
import { defineComponent, computed } from 'vue';
import { useStore } from 'vuex';
import router from "../../router";
import BoardArticles from "../../types/board/BoardArticles";
export default defineComponent({
    name:"Question",
    setup() {
      const store = useStore();
      const localStorageData = localStorage.getItem("vuex");
      let userinfoData;
      if (localStorageData !== null) {
        userinfoData = JSON.parse(localStorageData);
      }
      let userinfo = userinfoData.accountStore.userinfo;
      const checkAns = async() => {
        await store.dispatch("boardStore/classifyCategorybyUser", ['question', userinfo, 0])
        const article = await computed(() => store.state.boardStore.question[0]).value as BoardArticles;
        if (article === undefined) {
                alert('작성한 질문이 없습니다.')
              } else {
                return router.push({name: 'AnswerQuestion', params: { article_id: article.id }})
              }
      }
      return { checkAns, userinfo } 
    }
})
</script>
<style scoped>
template {
	height: 100%;
 	width: 100%;
}
.box {
 	height: 100%;
 	width: 100%;
 	background-color:#F99D07;
  margin: 0;
  padding: 60px 0px;
}
.cardbtn:hover {
  transform: scale (1.15);
  box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.9);
}
.cardbtn {
  display: inline-block;
  width: 40%;
	height: 600px;
  border-radius: 18px;
  box-shadow: 5px 5px 15px rgba(0,0,0,0.4);
  text-align: center;
  transition: 0.5s ease;
  cursor: pointer;
  margin: 50px;
  padding: 80px;
}
#ask {
	background-color: #31B4F2;
}
#ans {
	background-color: #35D461;
}
.card-icon {
  flex:auto;
  margin-bottom:15px;
}
</style>