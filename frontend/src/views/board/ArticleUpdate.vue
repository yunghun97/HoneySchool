<template>
<!-- Article Update -->
  <div v-if="isLoading">
    <div class="spinner-border" role="status"></div>
    <p>LOADING...</p>
  </div>
  <div v-else>
    <div class="submit-form">
        <form @submit.prevent="updateArticle">
            <div class="row mb-3">
                <label for="title" class="col-sm-2 col-form-label">제목</label>
                <div class="col-sm-10">
                <input class="form-control" id="title" required v-model="currentarticle.title" type="text">
                </div>
            </div>
            <fieldset class="row mb-3">
                <legend class="col-form-label col-sm-2 pt-0">분류</legend>
                <div class="col-sm-10">
                <div class="form-check form-check-inline me-5">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="notice" v-model="currentarticle.category">
                    <label class="form-check-label" for="inlineRadio1">알림장</label>
                </div>
                <div class="form-check form-check-inline me-5">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="handouts" v-model="currentarticle.category">
                    <label class="form-check-label" for="inlineRadio2">유인물</label>
                </div>
                <div class="form-check form-check-inline me-5">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio3" value="assignment" v-model="currentarticle.category">
                    <label class="form-check-label" for="inlineRadio3">숙제</label>
                </div>
                <div class="form-check form-check-inline me-5">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio4" value="photo" v-model="currentarticle.category">
                    <label class="form-check-label" for="inlineRadio4">사진첩</label>
                </div>
                </div>
            </fieldset>
            <div class="row mb-3">
                <label for="content" class="col-sm-2 col-form-label">내용</label>
                <div class="col-sm-10">
                <textarea class="form-control" id="content" rows="10" required v-model="currentarticle.content" type="text"></textarea>
                </div>
            </div>
            <div class="row mb-3">
                <label for="title" class="col-sm-2 col-form-label">첨부</label>
                <div class="col-sm-10">
                    <input @change="fileSelect()" multiple type="file" ref="selectedFile" class="form-control" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04" aria-label="Upload">
                    <!-- <button @click="clearFiles" class="btn btn-outline-danger">파일 전체 삭제</button> -->
                </div>
            </div>
            <button class="btn btn-success" type="button" @click="updateArticle">수정하기</button>
        </form>
    </div>

  </div>
</template>

<script lang="ts">
import router from "../../router";
import { computed, ref, watch } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router'
import axios from "axios";

interface BoardArticles {
  id: number,
  category: string,
  title: string,
  content: string,
  files? :any,
}

export default {
    name: "ArticleUpdate",
    setup() {
        const store = useStore();
        const route = useRoute();
        let id = +route.params.article_id;
 
        let isLoading = ref<boolean>(true);
        //user정보
        const localStorageData = localStorage.getItem("vuex");
        let userinfoData;
        if (localStorageData !== null) {
        userinfoData = JSON.parse(localStorageData);
        }
        let userinfo = userinfoData.accountStore.userinfo;

        // article detail 요청            
        let currentarticle = ref({
            id: route.params.article_id as string,
            title: '',
            category : route.params.category as string,
            content: '',
            files: '' as any,
        });
        const articleDetail = () => {
            return axios.get(process.env.VUE_APP_API_URL+"/board/class/detail",{
                params:{
                school: userinfo.school,
                grade: userinfo.grade,
                classes: userinfo.class_number,
                id : route.params.article_id
                }
            })
            .then((response)=>{
                currentarticle.value = response.data.board
            })
        }

        articleDetail().then(() => {
            isLoading.value = false
        })
        let updatingarticle = {
            id : +route.params.article_id,
            category : route.params.category as string,
            title : currentarticle.value.title,
            content : currentarticle.value.content,
            file_link : currentarticle.value.files,
        } as BoardArticles

        let isUploading = ref<boolean>(false);
        const selectedFile = ref({files:''})
        const fileSelect = () => {
            if (selectedFile.value !== null) {
            isUploading.value = true
            currentarticle.value.files = selectedFile.value.files
            //console.log(currentarticle.value.files)
            }
        };
        const clearFiles = (event:any) => {
            event.preventDefault()
        };
        const updateArticle = () => {
            const formData = new FormData();
            formData.append('category', currentarticle.value.category);
            formData.append('title', currentarticle.value.title);
            formData.append('content', currentarticle.value.content);
            formData.append('id', route.params.article_id as string); // user가 기본키여서 김싸피만 user로 등록되어있어서 작성자 바꿀려면 사람 User에서 추가해야합니다.
            if (isUploading.value) {
                for (var index=0; index < currentarticle.value.files.length; index++) {
                    formData.append('files', currentarticle.value.files[index])
                    formData.append('fileIsChanged', 'Y');
                }
            } else {
                formData.append('fileIsChanged', 'N');
            }
            formData.append('school', userinfo.school);
            formData.append('grade', userinfo.grade);
            formData.append('classes', userinfo.class_number);

            // console.log(...formData.entries())
            axios.put(process.env.VUE_APP_API_URL+"/board/class/",formData,
            {headers: {'Content-Type' : 'multipart/form-data;charset=utf-8'} }
            )
            .then((response) => {
                router.push({name: 'ArticleDetail', params: { category:updatingarticle.category ,article_id: updatingarticle.id }})
            })

        }
        return { id, isLoading, currentarticle, isUploading, userinfo, selectedFile, fileSelect, clearFiles, updateArticle}
    }
}



</script>

<style>
.submit-form {
  margin: auto;
  width: 100vh;
}
</style>