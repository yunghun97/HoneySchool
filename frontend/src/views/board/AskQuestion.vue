<template>
    <div style="background-color:#F99D07">
        <div class="note">
            <div>
                <h1>선생님께 질문을 남겨보아요</h1>
                <div class="recordbox">
                    <Record @finished="finished"/>
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
import { mapState } from "vuex";
import axios from "axios";
import Record from '../../components/Board/Record.vue'
import router from '../../router';
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
    computed: {
        ...mapState('accountStore', ['userinfo'])
    },
    methods: {
        finished(value:any) {
            this.isfinished = true
            // BLOB to file
            this.recordfile = new File([value], "recordfile.wav", {type: "audio/wav", lastModified: Date.now()});
        },
        postQuestion() {
            const formData = new FormData()
            formData.append('category', 'question');
            formData.append('title', `${this.userinfo.name}의 질문`);
            formData.append('content', '');
            formData.append('files', this.recordfile);
            formData.append('school', this.userinfo.school);
            formData.append('grade', this.userinfo.grade);
            formData.append('classes', this.userinfo.class_number);
            formData.append('userId', this.userinfo.userId);
            //console.log(...formData.entries())
            axios.post(process.env.VUE_APP_API_URL+"/board/class",formData, 
            {headers: {'Content-Type' : 'multipart/form-data;charset=utf-8'} }
            )
            .then(()=>{
                alert('질문 성공! 답변을 기다려주세요!')
                this.submitted = true
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