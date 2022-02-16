<template>  
    <div class="content">
        <div v-if="isLoading">
            <div class="spinner-border" role="status"></div>
            <p>LOADING...</p>
        </div>
        <div v-else>
            <h1>{{ currentarticle.board.title }}</h1>
            <!-- <div v-if="currentarticle.board.user !== null">
                <p>작성자 : {{ currentarticle.board.user.name }}</p>
            </div> -->
            <p class="fs-5">날짜 : <span class="color-orange font-A1-500">{{currentarticle.board.date.split(" ")[0] }}</span></p>
            <hr align="center" style="margin:auto;" noshade>
            <div class="py-4"></div>
            <div v-for="content in currentarticle.board.content.split('\r')" :key="content">
                <h4>{{ content }}</h4>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { reactive } from '@vue/reactivity'
import { computed } from '@vue/runtime-core'
import BoardArticles from "../../types/board/BoardArticles";

export default {
    name: "NoticeContent",
    props: ['currentarticle', 'isLoading'],
    // setup(props:any) {
    //     const state = reactive({
    //         currentarticle : computed(() => props.currentarticle)
    //     })
    // }
}
</script>
<style scoped>
.content {
    padding-top: 20vh;
    margin-bottom: 50px;
    font-family: "Gothic A1", sans-serif;
}
p {
    margin-right: 25vh;
    text-align: right;
}
.color-orange {
    color: #ff7f08;
}
hr {
    border: none;
    border-top: 5px dotted rgb(0, 0, 0);
    color: #fff;
    background-color: #fff;
    height: 1px;
    width: 70%;
}
.font-A1-500{
  font-family: 'Gothic A1', sans-serif;
  font-weight: 500;
}
</style>
