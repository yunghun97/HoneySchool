<template>
<div>
  <button 
    type="button" class="btn btn-outline-success"
    @click="$router.push('create')"
  >
    새로운 글 쓰기
  </button>
  <div class="box">
  <table class="table table-hover">
    <thead>
      <tr>
        <th scope="col">글 번호</th>
        <th scope="col">제목</th>
        <th scope="col">분류</th>
        <th scope="col">작성 날짜</th>
      </tr>
    </thead>
    <tbody>
      <tr 
      v-for="article in articles" :key="article.id"
      @click="$router.push({name: 'ArticleDetail', params: { category:article.category ,article_id: article.id }})"
      >
        <td scope="row">{{article.id}}</td>
        <td>{{ article.title }}</td>
        <td>{{ article.category }}</td>
        <td>{{ article.date }}</td>
      </tr>
    </tbody>
  </table>
  </div>
</div>
</template>


<script lang="ts">
import { computed } from 'vue';
import { useStore } from 'vuex';

export default {
  name: "BoardTable",
  setup() {
    const store = useStore();
    const articles = computed(() => store.state.boardStore.classBoardAll);
    return { articles };
  } 
}
</script>
<style scoped>
.box {
    margin: 30px 30vh;
}
/* table { 
    padding: 20px
}
tr {
    border-top: 1px solid black;
    border-bottom: 1px solid black;
} */
tbody>tr:hover {
    /* background-color: #FFEDA9; */
    cursor: pointer;
    margin: 20px;
}
</style>