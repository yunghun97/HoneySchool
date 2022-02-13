<template>
<div>
  <button 
    type="button" class="btn btn-outline-success"
    @click="$router.push('create')"
  >
    새로운 글 쓰기
  </button>
  <div v-if="isLoading">
    <p>...LOADING</p>
    
  </div>
  <div v-else>

    <div v-if="articles === null">
      <h1>아직 작성된 글이 없습니다.</h1>
    </div>
    <div v-else class="box">
      <table class="table table-hover">
        <thead>
          <tr>
            <th scope="col">글 번호</th>
            <th scope="col">제목</th>
            <th scope="col">
              <div class="dropdown">
                <button  class="btn dropdown-toggle" type="button" id="dropdownCategory" data-bs-toggle="dropdown" aria-expanded="false">
                  분류
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownCategory">
                  <li><p class="dropdown-item" @click="onlyCategory('notice')">알림장</p></li>
                  <li><p class="dropdown-item" @click="onlyCategory('handouts')">유인물</p></li>
                  <li><p class="dropdown-item" @click="onlyCategory('photo')">사진첩</p></li>
                  <li><p class="dropdown-item" @click="onlyCategory('assignment')">숙제</p></li>
                  <li><p class="dropdown-item" @click="onlyCategory('question')">질문</p></li>
                  <li><p class="dropdown-item" @click="onlyCategory('all')">전체보기</p></li>
                </ul>
              </div>
            </th>
            <th scope="col">작성자</th>
            <th scope="col">작성 날짜</th>
          </tr>
        </thead>
        <tbody>
          <tr @click="$router.push({ name: 'TimeTable' })">
            <td class="table-warning"><fa icon="calendar-check" style="width:25px;"></fa></td>
            <td class="table-warning">이번주 시간표</td>
            <td class="table-warning">시간표</td>
            <td class="table-warning"></td>
            <td class="table-warning"></td>

          </tr>
          <tr 
          v-for="a in article" :key="a.id"
          @click="$router.push({name: 'ArticleDetail', params: { category:a.category ,article_id: a.id }})"
          >
            <td scope="row">{{a.id}}</td>
            <td>{{ a.title }}</td>
            <td v-if="a.category === 'notice'">알림장</td>
            <td v-if="a.category === 'handouts'">유인물</td>
            <td v-if="a.category === 'photo'">사진첩</td>
            <td v-if="a.category === 'assignment'">숙제</td>
            <td v-if="a.category === 'question'">질문</td>
            <td v-if="a.user !== null">{{ a.user.name }}</td>
            <td v-else>비로그인 사용자</td>
            <td>{{ a.date.split(" ")[0] }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
</template>


<script lang="ts">
import { computed, ref } from 'vue';
import { useStore } from 'vuex';
export default {
  name: "BoardTable",
  setup() {
    const store = useStore();
    // user 정보
    const localStorageData = localStorage.getItem("vuex");
    let userinfoData;
    if (localStorageData !== null) {
      userinfoData = JSON.parse(localStorageData);
    }
    let userinfo = userinfoData.accountStore.userinfo;
    // 전체 글 가져오기
    
    let isloading = ref<boolean>(true);
    let article = ref<any>()

    store.dispatch('boardStore/getArticles', userinfo)
    const articles = computed(() => store.state.boardStore.classBoardAll);
    article.value = articles.value
    console.log('now', articles)
    // const load = async() => {
    //   await store.dispatch('boardStore/getArticles', userinfo)
    //   article.value = await computed(() => store.state.boardStore.classBoardAll).value;
    //   return article.value
    // }
    // load().then(() => {
    //   isloading.value = false 
    //   console.log('now',article.value)
    // })

    // 카테고리 선택 시 구분하기
    const onlyCategory = async(categoryName:string) => {
      isloading.value = true;
      if (categoryName === 'all') {
        article.value = computed(() => store.state.boardStore.classBoardAll).value;
      } else {
        isloading.value = true;
        await store.dispatch("boardStore/classifyCategory", [categoryName, userinfo])
        if (categoryName === 'notice') {
          article.value = computed(() => store.state.boardStore.notice).value
        } else if (categoryName === 'handouts') {
          article.value = computed(() => store.state.boardStore.handouts).value
        } else if (categoryName === 'photo') {
          article.value = computed(() => store.state.boardStore.photo).value
        } else if (categoryName === 'assignment') {
          article.value = computed(() => store.state.boardStore.assignment).value
        } else if (categoryName === 'question') {
          article.value = computed(() => store.state.boardStore.question).value
        }
        isloading.value = false;
      }
    }
    return { userinfo, isloading, onlyCategory, article, articles};
  } 
}
</script>
<style scoped>
.box {
    margin: 30px 30vh;
}

tbody>tr:hover {
    cursor: pointer;
    margin: 20px;
}
.dropdown-item {
  cursor: pointer;
}
#dropdownCategory {
  font-weight: bold;
  padding:0px;
}
</style>