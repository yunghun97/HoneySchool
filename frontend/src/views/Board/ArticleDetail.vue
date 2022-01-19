<template>
    <!-- <div>
        <p>{{ id }}</p>
        <p>{{ category }}</p>
        <p>{{ categories[articles[id -1].category] }}</p>
    </div> -->

    <div :style="`background-color:${categories[articles[id -1].category]}`">
        <div 
            class="note"
            :style="{'background-image':'url('+require(`@/assets/board/${articles[id-1].category}-background.png`)+')'}"
            >
            
            <div class="content">
                <h1>{{ articles[id-1].title }}</h1>
                <p>작성자 : {{ articles[id-1].writer }}</p>
                <p>작성날짜 : {{articles[id-1].date }}</p>
                <h2>{{ articles[id-1].content }}</h2>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { computed, ref, onBeforeMount } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router'
import Category from '@/types/board/Category'


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
        let id = route.params.article_id;
        let category = route.params.category;

        const categories= ref<any>({
            notice:"#FAD749",
            handouts: "#C9D9F0",
            photo: "#37B6F6",
            assignment: "#882FF6",
            questions: "#F99D07",
            all: "#F52532",
        });
        const articles = computed(() => store.state.boardStore.classBoardAll);

        // TODO: article detail API 가져오기
        // onBeforeMount(() => {
        //     // let id = +route.params.article_id;
        //     // let category = route.params.category;
        //     const articles = computed(() => store.state.boardStore.classBoardAll);
        //     //const article = computed(() => store.getters['boardStore'].getArticleDetail(id));
        //     isLoading = false;
        //     console.log(isLoading)
        // })

        return { categories, id, category, isLoading, articles }
    }
}
</script>
<style scoped>
/* .back {
    background-color: #FAD749;
} */
.note {
    margin: auto;
    padding-top: 20vh;
    background-image: url('../../assets/board/notice-background.png');
    height: 90vh;
    width: 150vh;
    background-position: center;
    background-size: contain;
    background-repeat: no-repeat;
}
p {
    margin-right: 25vh;
    text-align: right;
}
h2 {
    /* margin-left:30vh; */
    /* text-align: left; */
}

</style>
//         