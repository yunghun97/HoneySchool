<template>  
    <div>
        <div>
            {{articles}}
            <!-- 이전 버튼 -->
            <div v-if="beforearticleidx === false" class="beforebtn">
                <svg xmlns="http://www.w3.org/2000/svg" width="120" height="120" fill="currentColor" class="bi bi-arrow-left-circle-fill" viewBox="0 0 16 16">
                    <path d="M8 0a8 8 0 1 0 0 16A8 8 0 0 0 8 0zm3.5 7.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5z"/>
                </svg>
            </div>
            <div v-else class="beforebtn btns">
                <svg  @click="$router.push({name: 'Notice', params: { article_id: beforearticleid }})" xmlns="http://www.w3.org/2000/svg" width="120" height="120" fill="#F52532" class="bi bi-arrow-left-circle-fill" viewBox="0 0 16 16">
                    <path d="M8 0a8 8 0 1 0 0 16A8 8 0 0 0 8 0zm3.5 7.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5z"/>
                </svg>
            
            </div>

            <!-- 이후 버튼 -->
            <div v-if="nextarticleidx" class="nextbtn btns">
                <svg @click="$router.push({name: 'Notice', params: { article_id: nextarticleid }})" xmlns="http://www.w3.org/2000/svg" width="120" height="120" fill="#F52532" class="bi bi-arrow-right-circle-fill" viewBox="0 0 16 16">
                    <path d="M8 0a8 8 0 1 1 0 16A8 8 0 0 1 8 0zM4.5 7.5a.5.5 0 0 0 0 1h5.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5H4.5z"/>
                </svg>
            </div>
            <div v-else class="nextbtn">
                <svg xmlns="http://www.w3.org/2000/svg" width="120" height="120" fill="currentColor" class="bi bi-arrow-right-circle-fill" viewBox="0 0 16 16">
                    <path d="M8 0a8 8 0 1 1 0 16A8 8 0 0 1 8 0zM4.5 7.5a.5.5 0 0 0 0 1h5.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5H4.5z"/>
                </svg>
            </div>
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
        let id = +route.params.article_id;

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
                    console.log('articleid', article.id)
                    currentarticle = article
                    console.log('currentarticle', currentarticle)
                    if (i >= 1) {
                        beforearticleidx = i - 1
                        beforearticleid = articlesdata[i - 1].id
                        console.log('before', beforearticleid)
                    }
                    if (i < articles.value.length) {
                        nextarticleidx = i + 1
                        nextarticleid = articlesdata[i + 1].id
                        console.log('next', nextarticleid)
                    } 
                    if (i === articles.value.length) {
                        nextarticleidx = false
                        beforearticleidx = i -1
                        beforearticleid = articlesdata[i - 1].id
                        console.log('next', nextarticleid)
                        console.log('before', beforearticleid)
                    } 
                    break
                } 
            } 
        }
        if (currentarticle === null) {
                getCurrentArticle()

            }

  
        watch(() => route.params, (newVal, oldVal) => {
            if (newVal !== oldVal) {
                getCurrentArticle()
                // router.go(0)
            }

            //TODO : article detail axios 요청 보내기

        })

        // TODO: article detail API 가져오기

        return { id, isLoading, articles, getCurrentArticle, currentarticle, beforearticleidx, nextarticleidx, beforearticleid, nextarticleid }
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
/* h2 {
    margin-left:30vh; 
    text-align: left;
} */
.btns {
    cursor: pointer;
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

.nextbtn {
    float:right;
    margin:80px;
    
}
.beforebtn {
    /* position: fixed;
    right: 20px;
    top: 50px; */
    float:left;
    margin:80px;
}
</style>
