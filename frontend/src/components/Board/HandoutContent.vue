<template>  
  <div class="content">
    <h1>{{ currentarticle.board.title }}</h1>
    <p v-if="currentarticle.board.user!==null">작성자 : {{ currentarticle.board.user.name }}</p>
    <p>작성날짜 : {{currentarticle.board.date.split(" ")[0] }}</p>
    <div v-for="content in currentarticle.board.content.split('\r')" :key="content" class="content-files">
      <h2>{{ content }}</h2>
    </div>
    <div v-if="currentarticle.files.length > 0" class="content-files">
      <p class="filelist"><img src="@/assets/board/handout.png" alt="" class="fileimg"> 첨부 파일 목록</p>
      <div v-for="idx in currentarticle.files.length" :key="idx">
        <img src="@/assets/board/file.png" alt="" class="fileimg"><a :href="`https://i6b201.p.ssafy.io:9999/file/${currentarticle.files[idx-1].stored_file_path}`"> 첨부파일 {{idx}}</a>
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
.content > p {
  margin-right: 25vh;
  text-align: right;
}
.content-files {
  text-align: left;
  padding-left: 80px;
}
.fileimg {
  width: 30px;
  height: 30px;
}
.filelist {
  margin-top: 20px;
}
</style>
