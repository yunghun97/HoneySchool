<template>  
    <div class="content">
        <h1>{{ currentarticle.board.title }}</h1>
        <div v-if="currentarticle.board.user !== null">
            <p>작성자 : {{ currentarticle.board.user.name }}</p>
        </div>
        <p>작성날짜 : {{currentarticle.board.date.split("T")[0] }}</p>
        <h2>{{ currentarticle.board.content }}</h2>
    </div>
</template>

<script lang="ts">
import { reactive } from '@vue/reactivity'
import { computed } from '@vue/runtime-core'
import BoardArticles from "../../types/board/BoardArticles";

export default {
    name: "NoticeContent",
    props: ['currentarticle'],
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
}
p {
    margin-right: 25vh;
    text-align: right;
}
</style>
