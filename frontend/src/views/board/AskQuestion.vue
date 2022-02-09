<template>
    <div style="background-color:#F99D07">
        <div class="note">
            <div>
                <h1>선생님께 질문을 남겨보아요</h1>
                <div class="recordbox">
                    <Record @finished="finished" />
                </div>
                <div v-if="isfinished" class="mt-5">
                    <button @click="postQuestion" class="btn btn-warning btn-lg">질문 다 했어요</button>
                </div>
            </div>
        </div>
    </div>
    
</template>
<script lang="ts">
import { defineComponent, ref } from 'vue'
import axios from "axios";
import Record from '@/components/Board/Record.vue'
import router from '@/router';
export default defineComponent({
    name:"AskQuestion",
    components: { Record },
    data() {
        return {
            isfinished :ref<boolean>(false),
            recordfile :ref<any>(''),
            submitted: ref<boolean>(false)
        }
    },
    methods: {
        finished(value:any) {
            this.isfinished = true
            this.recordfile = value
        },
        postQuestion() {
            const formData = new FormData()
            formData.append('category', 'question');
            formData.append('title', '김싸피 질문글');
            formData.append('content', '김싸피 질문 녹음입니다.');
            formData.append('file_link', '녹음파일'); // TODO : this.recordfile로 넘겨주기
                
            // 글작성하느라 임의로 추가한내용
            formData.append('writer', "김싸피"); // user가 기본키여서 김싸피만 user로 등록되어있어서 작성자 바꿀려면 사람 User에서 추가해야합니다.
            formData.append('grade', '1');
            formData.append('classes', '1');
            formData.append('school', "싸피초");

            axios.post("http://localhost:9999/api/v1/board/class",formData, 
            {headers: {'Content-Type' : 'multipart/form-data;charset=utf-8'} }
            )
            .then((response)=>{
                alert('질문 성공! 답변을 기다려주세요!')
                this.submitted = true
                //console.log(formData)
                router.push({name: 'ClassBoard'})
            })
            .catch(()=>{
                alert("글 작성 실패")
            })

        }
    }
})
</script>
<style scoped>
.recordbox {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top:5%;
    /* min-height: 90vh; */

}
.note {
    margin: auto;
    padding-top: 20%;
    background-image: url('../../assets/board/question-background.png');
    height: 90vh;
    width: 150vh;
    background-position: center;
    background-size: contain;
    background-repeat: no-repeat;
}
</style>