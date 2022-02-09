<template>

    <div class="box">
    <div>
      <div class="card ask" @click="$router.push({name: 'AskQuestion'})">
        <h2>질문 할래요</h2>
      </div>
      <div class="card ans">
        <h2>선생님 답변 볼래요</h2>
      </div>
    </div>

    </div>

</template>
<script lang="ts">
import { defineComponent, ref } from 'vue';
import axios from "axios";
import Record from '../../components/Board/Record.vue'
import router from '../../router';
export default defineComponent({
    name:"Question",
})
</script>
<style scoped>
template {
	height: 100%;
 	width: 100%;
}
.box {
 	height: 100%;
 	width: 100%;
 	background-color:#F99D07;
  margin: 0;
  padding: 60px 0px;
}
.card:hover {
  transform: scale (1.15);
  box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.9);
}
.card {
  display: inline-block;
  width: 40%;
	height: 600px;
  border-radius: 18px;
  box-shadow: 5px 5px 15px rgba(0,0,0,0.4);
  text-align: center;
  transition: 0.5s ease;
  cursor: pointer;
  margin: 50px;
  padding: 80px;
}
.ask {
	background-color: #31B4F2;
}
.ans {
	background-color: #35D461;
}
</style>