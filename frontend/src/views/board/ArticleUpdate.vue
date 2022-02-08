<template>
        <!-- Article Update -->
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
                    <input @change="fileSelect()" type="file" multiple ref="file" class="form-control" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04" aria-label="Upload">
                    <!-- <button @click="clearFiles" class="btn btn-outline-danger">파일 전체 삭제</button> -->
                </div>
            </div>
            <button class="btn btn-success">수정하기</button>
        </form>
    </div>
</template>

<script lang="ts">
import router from "../../router";
import { computed, ref } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router'
import axios from "axios";
import Reference from 'yup/lib/Reference';

interface BoardArticles {
  id: number,
  category: string,
  title: string,
  content: string,
  file_link? :any,
}

export default {
    name: "ArticleUpdate",
    setup() {
        const store = useStore();
        const route = useRoute();
        let id = +route.params.article_id;
 
        let isLoading = ref<boolean>(true);
        // article detail 요청            
        let currentarticle = ref({
            title: '',
            category : route.params.category as string,
            content: '',
            file_link: '',
        });
        const articleDetail = () => {
            return axios.get("http://localhost:9999/api/v1/board/class/detail",{
                params:{
                school: "싸피초",
                grade: 1,
                classes: 1,
                id : route.params.article_id
                }
            })
            .then((response)=>{
                currentarticle.value = response.data
                
            })
            .catch(()=>
                alert("실패!")
            )  
        }

        articleDetail().then(() => {
            isLoading.value = false
        })
        let updatingarticle = {
            id : +route.params.article_id,
            category : route.params.category as string,
            title : currentarticle.value.title,
            content : currentarticle.value.content,
            file_link : currentarticle.value.file_link,
        } as BoardArticles

        const fileSelect = () => {
            const file = ref() as any
            console.log(file.files)
            currentarticle.value.file_link= file.files;
        };
        const clearFiles = (event:any) => {
            event.preventDefault()
            currentarticle.value.file_link=''
        };
        const updateArticle = () => {
            const formData = new FormData() 
            formData.append('category', currentarticle.value.category);
            formData.append('title', currentarticle.value.title);
            formData.append('content', currentarticle.value.content);
            formData.append('files', currentarticle.value.file_link);

            // 글작성하느라 임의로 추가한내용
            formData.append('writer', "김싸피"); // user가 기본키여서 김싸피만 user로 등록되어있어서 작성자 바꿀려면 사람 User에서 추가해야합니다.
            formData.append('grade', '1');
            formData.append('classes', '1');
            formData.append('school', "싸피초");

            console.log(formData)
            //TODO : PUT 요청 보내기
            axios.put("http://localhost:9999/api/v1/board/class",formData,
            {headers: {'Content-Type' : 'multipart/form-data;charset=utf-8'} }
            )
            .then((response) => {
                console.log(response.data)
                alert('성공')
                router.push({name: 'ArticleDetail', params: { category:updatingarticle.category ,article_id: updatingarticle.id }})
            })
            .catch(() => {
                alert('수정 실패')
            })
        }
        return { id, isLoading, currentarticle, fileSelect, clearFiles, updateArticle}
    }
}



</script>

<style>
.submit-form {
  margin: auto;
  width: 100vh;
}
</style>