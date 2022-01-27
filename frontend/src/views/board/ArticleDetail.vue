<template>
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
                        @click="$router.push({name: 'ArticleUpdate', params: { category:category ,article_id: id }})"
                        >
                        수정하기
                    </button>
                    <button 
                        type="button" class="btn btn-danger"
                        @click="deleteArticle"
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
                    <div>
                        {{articles }}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { computed, ref } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router'
import axios from 'axios';
import router from '@/router';

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

        let isLoading = false;
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

        // TODO: article detail 요청 보내기
        const articles = computed(() => store.state.boardStore.classBoardAll[id - 1]);
        
        // let currentarticle = {};
        // const articleDetail = () => {
        //     axios.get("http://localhost:9999/api/v1/board/class",{
        //         params:{
        //         school: "싸피초",
        //         grade: 1,
        //         classes: 1,
        //         id : id
        //         }
        //     })
        //     .then((response)=>{
        //         console.log(response.data)
        //         currentarticle = response.data
        //     })
        //     .catch(()=>
        //         alert("실패!")
        //     )  
        // }
        // articleDetail()

        const deleteArticle = () => {
            console.log('삭제')
            // TODO : delete 요청 보내기
        //     axios.delete("http://localhost:9999/api/v1/board/class",{
        //         params:{
        //             school: "싸피초",
        //             grade: 1,
        //             classes: 1,
        //             id: id
        //         }
        //     })
        //     .then(() => {
        //         router.push({name: 'BoardTable'})
        //     })
        }

        return { categories, id, category, isLoading, articles, isphoto, deleteArticle}
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
/* h2 {
    margin-left:30vh;
    text-align: left;
} */
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
