<template>
   <div v-if="isLoading">
        <p>...LOADING</p>
    </div>
    
    <!-- Article Detail -->
    <div v-else>
        <div class="card">
            <h4 class="card-header">{{ currentarticle.title }}</h4>
            <div class="card-body">
                <p>작성자 : {{ currentarticle.writer }}</p>
                <p>작성날짜 : {{currentarticle.date.split("T")[0] }}</p>
                <div class="content-container">
                    <p>{{ currentarticle.content }}</p>
                </div>
                <div v-if="currentarticle.category==='photo'" class="img-container">
                    <img :src="currentarticle.file" alt="img" class="img-fluid">
                </div>
                <div >
                    <!--  v-if="currentarticle.files.length > 0" -->
                    <p>{{ currentarticle.files }}</p>
                    <div>
                        <p>{{ currentarticle }}</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row">
                <div class="btns">
                    <button 
                        type="button" class="btn btn-primary"
                        @click="$router.push({ name: 'ArticleUpdate', params: { category: currentarticle.category, article_id: id }})"
                        >
                        수정하기
                    </button>
                    <button 
                        type="button" class="btn btn-danger"
                        @click="deleteArticle"
                        >
                        삭제하기
                    </button>
                </div>
            <hr>
            <!-- comments 작성 -->
            <div class="comment-box">
                <h5>댓글</h5>
                <form role="form">
                    <div class="form-group">
                        <textarea class="form-control" rows="3" v-model="newComment"></textarea>
                    </div>
                    <button type="button" class="btn btn-primary post-btn" @click="postComment"><fa icon="reply" class="fa-icon"></fa> 작성</button>
                </form>
            </div>
            <hr>
            <!-- comments 조회 -->
            <div v-if="isLoadingCom">
                <p>...LOADING</p>
            </div>
            
            <div v-if="!isLoadingCom">
                <div 
                    class="comment-list" 
                    v-for="comment in comments"
                    :key="comment.id"
                >
                    <h3><fa icon="comment" class="fa-icon-b"></fa> {{ comment.writer }}:
                        <!-- <span><small>{{ comment.date.split("T")[0] }}</small></span> -->
                    </h3>
                    <p>{{ comment.content }}</p>
                    <!-- 댓글 수정 -->
                    <!-- <div v-if="!isediting">
                        <button type="button" class="btn btn-secondary comment-btn" @click="requestEditCom(comment.content)"><fa icon="edit" class="fa-icon"></fa></button>
                    </div>
                    <div v-else>
                        <textarea class="form-control" rows="1" v-model="revisedComment"></textarea>
                        <button type="button" class="btn btn-secondary comment-btn" @click="editCom(comment.id)"><fa icon="edit" class="fa-icon"></fa>수정</button>
                    </div> -->
                    <button type="button" class="btn btn-danger comment-btn" @click="deleteCom(comment.id)"><fa icon="times" class="fa-icon"></fa></button>
                    <!-- 대댓글 작성 -->
                    <div v-if="isWrittingReComment">
                        <form role="form">
                            <div class="form-group">
                                <textarea class="form-control" rows="1"></textarea>
                            </div>
                            <button class="btn btn-primary comment-btn"><fa icon="reply" class="fa-icon"></fa> 작성</button>
                            <button type="button" class="btn btn-success comment-btn" @click="requestReCom">취소</button>
                        </form>
                    </div>
                    <div v-else>
                        <button type="button" class="btn btn-success comment-btn" @click="requestReCom"><fa icon="reply" class="fa-icon"></fa></button>
                    </div>
                <hr>
                </div>
                
            </div>
        </div>
  
	</div>
</div>
</template>

<script lang="ts">
import { ref } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router'
import axios from 'axios';
import router from '../../router';

interface BoardArticles {
  id: number,
  category: string,
  title: string,
  content: string,
  writer: string,
  date: number,
}


export default {
    name: "ArticleDetail",
    setup() {
        const store = useStore();
        const route = useRoute();

        let isLoading = ref<boolean>(true);
        let id = +route.params.article_id;

        // article detail 요청            
        let currentarticle = ref({});
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
        // comment get 요청
        let isLoadingCom = ref<boolean>(true);
        let comments = ref({});
        const commentList = () => {
            return axios.get("http://localhost:9999/api/v1/board/class/comment",{
                params:{
                school: "싸피초",
                grade: 1,
                classes: 1,
                board_id : route.params.article_id
                }
            })
            .then((res) => {
                isLoadingCom.value = false
                comments.value = res.data
            })
        }
        commentList().then(() =>{
            isLoadingCom.value = false
        })

        // 삭제 버튼 클릭
        const deleteArticle = () => {
            // delete 요청 보내기
            axios.delete("http://localhost:9999/api/v1/board/class/",{
                params:{
                    school: "싸피초",
                    grade: 1,
                    classes: 1,
                    id: id
                }
            })
            .then(() => {
                router.push({name: 'BoardTable'})
            })
        }
        // 댓글 작성
        let newComment = ref<string>('');
        const postComment = () => {
            console.log(newComment.value)
            if (newComment.value.length === 0) {
                alert("댓글 내용을 작성해주세요")
            } else {
                axios.post(`http://localhost:9999/api/v1/board/class/${id}/comment/`, {
                    'content': newComment.value,
                    'writer': '박싸피',
                    })
                .then(() => {
                    newComment.value = ''
                    isLoadingCom.value = false
                    commentList()
                })
                .then(() => {
                    isLoadingCom.value = false
                })
            }
        }
        // 댓글 삭제
        const deleteCom = (comId:number) => {
            axios.delete(`http://localhost:9999/api/v1/board/class/${id}/comment/${comId}`)
            .then(() => {
                isLoadingCom.value = false
                commentList()
            })
            .then(() =>{
                isLoadingCom.value = false
            })
        }
        // 댓글 수정
        // let isediting = ref<boolean>(false);
        // let revisedComment = ref<string>('')
        // const requestEditCom = (oldCom:string) => {
        //     isediting.value = !isediting.value
        //     revisedComment.value = oldCom
        // }
        // const editCom = (comId:number) => {
        //     if (revisedComment.value.length === 0) {
        //         alert("댓글 내용을 작성해주세요")
        //     } else {
        //         axios.put(`http://localhost:9999/api/v1/board/class/${id}/comment/${comId}`, {
        //             'content': revisedComment.value,
        //             'writer': '박싸피',
        //             })
        //         .then(() => {
        //             revisedComment.value = ''
        //             isediting.value = false
        //             isLoadingCom.value = false
        //             commentList()
        //         })
        //         .then(() => {
        //             isLoadingCom.value = false
        //         })
        //     }
        // }
    
        let isWrittingReComment = ref<boolean>(false);
        const requestReCom = () => {
            isWrittingReComment.value = !isWrittingReComment.value;
        }
        return { id, isLoading, 
        isLoadingCom, commentList, comments, deleteCom,
        currentarticle, deleteArticle, 
        // isediting, revisedComment, requestEditCom, editCom,
        newComment, postComment, isWrittingReComment, requestReCom, 
        }
    }
}
</script>
<style scoped>
.card {
    margin: 30px;
    width: 1320px;
    display: inline-block;
}
.card-body > p{
    text-align: right;
}
.btns {
    margin-bottom:20px;
    text-align: right;
}
.content-container {
    text-align: left;
}
.comment-box {
    padding-top: 20px;
    background-color: #f8f9fa;
}
.comment-box > h5 {
    text-align: left;
}
.comment-btn {
    display:inline-block; 
    margin-top: -42px;
    float: right;
}
.comment-list {
    text-align: left;
    margin-bottom: 30px;
}
.fa-icon{
    width: 15px;
}
.fa-icon-b{
    width: 30px;
}
.post-btn {
    float: right;
}
button {
    margin:5px;
}
img {
    max-width: 600px;
    height: auto;
    
}
</style>
