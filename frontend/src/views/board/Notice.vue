<template>  
    <div>
        <!-- 이전 버튼 -->
        <div v-if="beforearticleidx === false">
            <button>이전 없음</button>
        </div>
        <div v-else>
            <button @click="$router.push({name: 'Notice', params: { article_id: beforearticleid }})">
                이전
            </button>
        </div>

        <!-- 이후 버튼 -->
        <div v-if="nextarticleidx">
            <button @click="$router.push({name: 'Notice', params: { article_id: nextarticleid }})">
                이후
            </button>
        </div>
        <div v-if="nextarticleidx === false">
            <button>이후 없음</button>
        </div>

        <div style="background-color:#FAD749">
            <div 
                class="note"
                >
                <notice-content :currentarticle = currentarticle />
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import router from "../../router";
import { computed, ref, watch } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import Category from '../../types/board/Category';
import Article from '../../types/board/Article';
import BoardArticles from "../../types/board/BoardArticles";
import NoticeContent from "@/components/Board/NoticeContent.vue";
interface ArticleArray {
    [index: number] : any
}

export default 
    {
    components: { NoticeContent },
    name: "Notice",
    setup() {
        const store = useStore();
        const route = useRoute();

        let isLoading = true;
        // let id = +route.params.article_id;

        // TODO: server에 category get 요청
        store.dispatch("boardStore/classifyCategory")
        const articles = computed(() => store.state.boardStore.notice);
        const articlesdata = articles.value as ArticleArray

        let nextarticleidx = articles.value.length as boolean | number;
        let nextarticleid = 0 as number;
        let beforearticleidx = false as boolean | number;
        let beforearticleid = 0 as number;
        let currentarticle = null as BoardArticles | any;
 
        const getCurrentArticle = () => {
            for (var i = 0; i < articles.value.length; ++i) {
                const article = articlesdata[i] as BoardArticles
                if (article.id === +route.params.article_id) {
                    currentarticle = article
                    if (i >= 1) {
                        beforearticleidx = i - 1
                        beforearticleid = articlesdata[i - 1].id
                    }
                    if (i < articles.value.length) {
                        nextarticleidx = i + 1
                        nextarticleid = articlesdata[i + 1].id
                    } else if (i === articles.value.length) {
                        nextarticleidx = false
                    }
                } break
            } 
        }
        if (currentarticle === null) {
                getCurrentArticle()
            }
  
        watch(() => route.params, (newVal, oldVal) => {
            getCurrentArticle()

            //TODO : article detail axios 요청 보내기

        })

        // TODO: article detail API 가져오기

        return { isLoading, articles, getCurrentArticle, currentarticle, beforearticleidx, nextarticleidx, beforearticleid, nextarticleid }
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
