<template>
<div class="root">
  <div class="py-2"></div>
  <div class="btn-margin d-flex justify-content-end">
    <button
      type="button" class="btn btn-outline-success"
      @click="$router.push('create')"
      v-if="userinfo.position==='T'"
    >
    새로운 글 쓰기
    </button>
  </div>
  <div v-if="isLoading">
    <div class="spinner-border" role="status"></div>
    <p>LOADING...</p>
  </div>
  <div v-else>
    <div v-if="article.length === 0">
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
        <ul class="pagination justify-content-center">
          <div v-for="page in endpage" :key="page">
            <li class="page-item"><span class="page-link" @click="changePage(page)">{{page}}</span></li>
          </div>
        </ul>
    </div>
  </div>
</div>
</template>


<script lang="ts">
import { ref } from 'vue';
import { useStore } from 'vuex';
import axios from "axios";
import BoardArticles from "../../types/board/BoardArticles"
interface ResponseData {
  [index: string] : any,
  content: Array<BoardArticles>,
  titalPages: number,
}
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
    let pageNow = ref<number>(0)
    let endpage = ref<number>(0)
    let categoryNow = ref<string>('all')
    
    const getAllArticle=(pageNum:number)=>{
      axios.get(process.env.VUE_APP_API_URL+"/board/class", {
        params: {
            school: userinfo.school,
            grade: userinfo.grade,
            classes: userinfo.class_number,
            page: pageNum,
            size: 10,
          }
      })
      .then((res) => {
        const response = res.data as ResponseData
        article.value = response.content
        endpage.value = response.totalPages
      })
      .then(()=>{
        isloading.value = false
      })
      return article.value
    }
    getAllArticle(pageNow.value)
    // 카테고리별 글 가져오기
    const getCategoryArticle = (categoryName:string, pageNum:number)=> {
      axios.get(process.env.VUE_APP_API_URL+"/board/class/category",{
        params:{
          school: userinfo.school,
          grade: userinfo.grade,
          classes: userinfo.class_number,
          category: categoryName,
          page: pageNum,
          size: 10,
        }
      })
      .then((res) => {
        const response = res.data as ResponseData
        article.value = response.content
        endpage.value = response.totalPages
      })
    }
    // 카테고리 선택 시 구분하기
    const onlyCategory = async(categoryName:string) => {
      isloading.value = true;
      if (categoryName === 'all') {
        await getAllArticle(0)
      } else {
        await getCategoryArticle(categoryName, 0)
      }
        categoryNow.value = categoryName;
        pageNow.value = 0
        isloading.value = false;
    }
    // pagination
    const changePage = async(page:number) => {
      isloading.value = true;
      pageNow.value = page
      if (categoryNow.value === 'all') {
        await getAllArticle(page)
      } else {
        await getCategoryArticle(categoryNow.value, page)
      }
      isloading.value = false;
    }

    return { userinfo, isloading, onlyCategory, article, getAllArticle, getCategoryArticle,
            pageNow, endpage, categoryNow, changePage
    };
  } 
}
</script>
<style scoped>
.root {
  font-family: "Gothic A1", sans-serif;
}
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
.btn-margin {
  margin-left: 226px;
  margin-right: 226px;
}
</style>