<template>  
  <div class="content">
    <h1>{{ currentarticle.board.title }}</h1>
    <!-- <p v-if="currentarticle.board.user!==null">작성자 : {{ currentarticle.board.user.name }}</p> -->
    <p class="fs-5">날짜 : <span class="color-orange font-A1-500">{{currentarticle.board.date.split(" ")[0] }}</span></p>
    <hr align="center" style="margin:auto;" noshade>
    <div v-for="content in currentarticle.board.content.split('\r')" :key="content" class="content-files">
      <h4>{{ content }}</h4>
    </div>
    <div v-if="currentarticle.files.length > 0" class="content-files">
      <p class="filelist mt-5">
        <span class="img-bg fs-5">
          <img src="@/assets/board/handout.png" alt="" class="fileimg"> 첨부 파일
        </span>
      </p>
      <div v-for="idx in currentarticle.files.length" :key="idx">
        <!-- <img src="@/assets/board/file.png" alt="" class="fileimg"> -->
        <a :href="`https://i6b201.p.ssafy.io:9999/file/${currentarticle.files[idx-1].stored_file_path}`" target='_black'> 첨부파일 {{idx}}</a>
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
  padding-top: 24vh;
  margin-bottom: 50px;
  font-family: 'Gothic A1', sans-serif;
}
.content > p {
  margin-right: 25vh;
  text-align: right;
}
.content-files {
  text-align: center;
  /* padding-left: 80px; */
}
.fileimg {
  width: 30px;
  height: 30px;
}
.filelist {
  margin-top: 20px;
}
.color-orange {
    color: #13a1ff;
}
hr {
    border: none;
    border-top: 5px dotted rgb(0, 0, 0);
    color: #fff;
    background-color: #fff;
    height: 1px;
    width: 70%;
    padding-bottom: 20px;
}
.font-A1-500{
  font-family: 'Gothic A1', sans-serif;
  font-weight: 500;
}
.img-bg {
  background-color: rgb(221, 254, 255);
}
</style>
