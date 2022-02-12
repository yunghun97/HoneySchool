<template>  
    <div class="content">
        <h1>{{ currentarticle.board.title }}</h1>
        <p v-if="currentarticle.board.user!==null">작성자 : {{ currentarticle.board.user.name }}</p>
        <p>작성날짜 : {{currentarticle.board.date.split("T")[0] }}</p>
        <div v-for="content in currentarticle.board.content.split('\r')" :key="content">
            <h2>{{ content }}</h2>
        </div>
        <div v-if="currentarticle.files.length > 0" class="content-files">
            <p>첨부파일</p>
            <div v-for="idx in currentarticle.files.length" :key="idx">
                <a href="`http://localhost:9999/static/uploads/${{currentarticle.files[idx].stored_file_path}}`">첨부파일 {{idx}}</a>
            </div>
        </div>
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
