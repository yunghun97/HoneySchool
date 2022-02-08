<template>  
    <div class="content">
        <h1>{{ currentarticle.title }}</h1>
        <p>작성자 : {{ currentarticle.writer }}</p>
        <p>작성날짜 : {{currentarticle.date.split("T")[0] }}</p>
        <h2>{{ currentarticle.content }}</h2>
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
