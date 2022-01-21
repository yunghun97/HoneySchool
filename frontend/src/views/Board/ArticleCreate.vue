<template>
  <div class="submit-form">
    <div v-if="!submitted">

    <form>
      <div class="row mb-3">
        <label for="title" class="col-sm-2 col-form-label">제목</label>
        <div class="col-sm-10">
          <input class="form-control" id="title" required v-model="article.title" type="text">
        </div>
      </div>
      <fieldset class="row mb-3">
        <legend class="col-form-label col-sm-2 pt-0">분류</legend>
        <div class="col-sm-10">
          <div class="form-check form-check-inline me-5">
            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="notice" v-model="article.category">
            <label class="form-check-label" for="inlineRadio1">알림장</label>
          </div>
          <div class="form-check form-check-inline me-5">
            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="handouts" v-model="article.category">
            <label class="form-check-label" for="inlineRadio2">유인물</label>
          </div>
          <div class="form-check form-check-inline me-5">
            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio3" value="assignment" v-model="article.category">
            <label class="form-check-label" for="inlineRadio3">숙제</label>
          </div>
          <div class="form-check form-check-inline me-5">
            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio4" value="photo" v-model="article.category">
            <label class="form-check-label" for="inlineRadio4">사진첩</label>
          </div>
        </div>
      </fieldset>
      <div class="row mb-3">
        <label for="content" class="col-sm-2 col-form-label">내용</label>
        <div class="col-sm-10">
          <textarea class="form-control" id="content" rows="10" required v-model="article.content" type="text"></textarea>
        </div>
      </div>
      <div class="row mb-3">
        <label for="title" class="col-sm-2 col-form-label">첨부</label>
        <div class="col-sm-10">
          <input @change="fileSelect()" type="file" multiple ref="file" class="form-control" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04" aria-label="Upload">
          <!-- <button @click="clearFiles" class="btn btn-outline-danger">파일 전체 삭제</button> -->
        </div>

      </div>

      <button @click="saveArticle" class="btn btn-success">작성하기</button>
    </form>

      
    </div>

    <div v-else>
      <!-- TODO -->
      <h4>TODO : Axios 요청 보내고, 작성한 글 detail로 넘어가기!!</h4>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import Article from "@/types/board/Article";
import axios from "axios";
// import ResponseData from "@/types/board/ResponseData";

export default defineComponent({
  name: "CreateArticle",
  data() {
    return {
      article: {
        id: null,
        category: "",
        title: "",
        content: "",
        file: "",
        date: new Date(),
      } as Article,
      submitted: false,
    };
  },
  methods: {
    fileSelect() {
      const uploadedfile = this.$refs.file as any
      this.article.file= uploadedfile.files;

    },
    clearFiles(event:any) {
      event.preventDefault()
      this.article.file=''

    },
    saveArticle() {
      if (this.article.category.length <=0 || this.article.title.length <=0 || this. article.content.length <=0) {
        window.alert('제목, 분류 및 내용을 모두 작성해주세요')
        return false;
      }

      const formData = new FormData()
      formData.append('category', this.article.category);
      formData.append('title', this.article.title);
      formData.append('content', this.article.content);
      formData.append('file', this.article.file);
      formData.append('date', this.article.date);

      this.submitted = true
    // TODO
      axios.post('http://127.0.0.1:8000/board/class',
        formData,
        {
          headers: {
            'Content-Type':'multipart/form-data'
          }
        })
      .then(response =>
        console.log('loaded')
      );
    },
  },
});
</script>

<style>
.submit-form {
  margin: auto;
  width: 100vh;
}
</style>