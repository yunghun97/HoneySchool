<template>  
  <div class="content">
    <div v-if="isLoading">
      <div class="spinner-border" role="status"></div>
      <p>LOADING...</p>
    </div>
    <div v-else>
      <h1>{{ currentarticle.board.title }}</h1>
      <p v-if="currentarticle.board.user!==null">작성자 : {{ currentarticle.board.user.name }}</p>
      <p>작성날짜 : {{currentarticle.board.date.split(" ")[0] }}</p>
      <div v-for="content in currentarticle.board.content.split('\r')" :key="content" class="content-files">
        <h2>{{ content }}</h2>
      </div>
      <div v-if="currentarticle.files.length > 0" class="content-files">
        <hr>
        <p class="filelist">첨부 파일 목록</p>
        <div v-for="idx in currentarticle.files.length" :key="idx">
          <img src="@/assets/board/file.png" alt="" class="fileimg"><a :href="`http://localhost:9999/static/uploads/${currentarticle.files[idx-1].stored_file_path}`"> 첨부파일 {{idx}}</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">

export default {
  name: "HandoutContent",
  props: ['currentarticle', 'isLoading'],
}
</script>
<style scoped>
.content {
  padding-top: 22vh;
  margin-bottom: 50px;
  font-family: "Gothic A1", sans-serif;
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
  font-weight: bold;
}
hr {
  width: 92%
}
</style>
