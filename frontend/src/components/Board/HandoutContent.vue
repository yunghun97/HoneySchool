<template>  
    <div class="content">
        <h1>{{ currentarticle.title }}</h1>
        <p>작성자 : {{ currentarticle.writer }}</p>
        <p>작성날짜 : {{currentarticle.date }}</p>
        <h2>{{ currentarticle.content }}</h2>
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
