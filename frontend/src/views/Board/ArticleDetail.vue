<template>
    <!-- <div>
        <p>{{ id }}</p>
        <p>{{ category }}</p>
        <p>{{ categories[articles[id -1].category] }}</p>
    </div> -->

    <!-- Article Update -->
    <!-- <div v-if="isUpdating">
        <form>
            <div class="row mb-3">
                <label for="title" class="col-sm-2 col-form-label">제목</label>
                <div class="col-sm-10">
                <input class="form-control" id="title" required v-model.trim="updatingArticle.title" type="text" value="articles[id-1].title">
                </div>
            </div>
            <fieldset class="row mb-3">
                <legend class="col-form-label col-sm-2 pt-0">분류</legend>
                <div class="col-sm-10">
                <div class="form-check form-check-inline me-5">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="notice" v-model="updatingArticle.category">
                    <label class="form-check-label" for="inlineRadio1">알림장</label>
                </div>
                <div class="form-check form-check-inline me-5">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="handouts" v-model="updatingArticle.category">
                    <label class="form-check-label" for="inlineRadio2">유인물</label>
                </div>
                <div class="form-check form-check-inline me-5">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio3" value="assignment" v-model="updatingArticle.category">
                    <label class="form-check-label" for="inlineRadio3">숙제</label>
                </div>
                <div class="form-check form-check-inline me-5">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio4" value="photo" v-model="updatingArticle.category">
                    <label class="form-check-label" for="inlineRadio4">사진첩</label>
                </div>
                </div>
            </fieldset>
            <div class="row mb-3">
                <label for="content" class="col-sm-2 col-form-label">내용</label>
                <div class="col-sm-10">
                <textarea class="form-control" id="content" rows="10" required v-model="updatingArticle.content" type="text"></textarea>
                </div>
            </div>
            <div class="row mb-3">
                <label for="title" class="col-sm-2 col-form-label">첨부</label>
                <div class="col-sm-10">
                <input @change="fileSelect()" type="file" multiple ref="file" class="form-control" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04" aria-label="Upload">
                <button @click="clearFiles" class="btn btn-outline-danger">파일 전체 삭제</button> -->
                <!-- </div>
            </div>
            <button @click="saveArticle" class="btn btn-success">작성하기</button>
        </form>
    </div> -->
    <!-- Article Detail -->
    <div>
        <div :style="`background-color:${categories[articles.category]}`">
            <div 
                class="note"
                :style="{'background-image':'url('+require(`@/assets/board/${articles.category}-background.png`)+')'}"
                >
                <div class="btns">
                    <button 
                        type="button" class="btn btn-primary"
                        @click="requestUpdate"
                        >
                        수정하기
                    </button>
                    <button 
                        type="button" class="btn btn-danger"
                        >
                        삭제하기
                    </button>
                </div>
                <div class="content">
                    <h1>{{ articles.title }}</h1>
                    <p>작성자 : {{ articles.writer }}</p>
                    <p>작성날짜 : {{articles.date }}</p>
                    <div v-if="isphoto" class="imgcontainer">
                        <h5>{{ articles.content }}</h5>
                        <img :src="articles.file" alt="img" class="img-fluid">
                    </div>
                    <div v-else>
                        <h2>{{ articles.content }}</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { computed, ref, onBeforeMount } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router'
import Category from '@/types/board/Category'
import Article from '@/types/board/Article';


interface BoardArticles {
  id: number,
  category: string,
  title: string,
  content: string,
  writer: string,
  date: number,
}
type boardType = Array<BoardArticles>;

export default {
    name: "ArticleDetail",
    setup() {
        const store = useStore();
        const route = useRoute();

        let isLoading = true;
        let isUpdating = false;
        let id = +route.params.article_id;
        let category = route.params.category;
        let isphoto = false;
        if (category === "photo") {
            isphoto = true
        }

        
        const categories= ref<any>({
            notice:"#FAD749",
            handouts: "#C9D9F0",
            photo: "#37B6F6",
            assignment: "#882FF6",
            questions: "#F99D07",
            all: "#F52532",
        });


        const articles = computed(() => store.state.boardStore.classBoardAll[id - 1]);

        const currentarticle = {}

        // const getArticle = () => {
            
        // }

        const requestUpdate = () => {
            isUpdating = true
        }



        // TODO: article detail API 가져오기
        // onBeforeMount(() => {
        //     // let id = +route.params.article_id;
        //     // let category = route.params.category;
        //     const articles = computed(() => store.state.boardStore.classBoardAll);
        //     //const article = computed(() => store.getters['boardStore'].getArticleDetail(id));
        //     isLoading = false;
        //     console.log(isLoading)
        // })

        return { categories, id, category, isLoading, articles, isphoto, }
    }
}
</script>
<style scoped>
/* .back {
    background-color: #FAD749;
} */
.note {
    margin: auto;

    background-image: url('../../assets/board/notice-background.png');
    height: 90vh;
    width: 150vh;
    background-position: center;
    background-size: contain;
    background-repeat: no-repeat;
}
.content {
        padding-top: 20vh;
        margin-bottom: 50px;
}
p {
    margin-right: 25vh;
    text-align: right;
}
h2 {
    /* margin-left:30vh; */
    /* text-align: left; */
}
.btns {
    float: right;
    margin: 2vh;
}
button {
    margin:5px;
}
img {
    max-width: 600px;
    height: auto;
    
}
/* .imgcontainer {
    max-width: 70%;
    max-height: 60%;
    overflow: hidden;
} */
</style>
//         