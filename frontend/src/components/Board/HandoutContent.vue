<template>  
    <div class="content">
        <h1>{{ currentarticle.board.title }}</h1>
        <p v-if="currentarticle.board.user.name!==null">작성자 : {{ currentarticle.board.user.name }}</p>
        <p>작성날짜 : {{currentarticle.board.date.split("T")[0] }}</p>
        <h2>{{ currentarticle.board.content }}</h2>
    </div>
</template>

<script lang="ts">

export default {
    name: "HandoutContent",
    props: ['currentarticle'],
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
