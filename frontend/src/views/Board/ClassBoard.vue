<template>
<div class="root">
  <div
  class="box">
  <div
    cols="4"
    v-for="category in categories"
    :key="category"
  >
    <div
      class="card"
      @click="$router.push({name: 'BoardTable', params: { category: category.url }, replace:true})"
      :style="{backgroundColor : category.color}"
    >
      <div class="card-body">
        <div class="card-icon">
          <img
            :src="require(`@/assets/board/${category.url}.png`)"
            alt="img"
          />
        </div>
        <div class="card-name">
          <h1>
            {{ category.name }}
          </h1>
        </div>

      </div>
    </div>
  </div>

  </div>
</div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'

interface Category {
    url: string,
    name: string,
    color: string,
}

export default defineComponent({
    name: 'ClassBoard',
    setup() {
        const categories= ref<Category[]>([
        {
          url: "notice",
          name: "알림장",
          color: "#FAD749",
        },
        {
          url: "handouts",
          name: "유인물",
          color: "#35D461",
        },
        {
          url: "photo",
          name: "학급사진첩",
          color: "#37B6F6",
        },
        {
          url: "assignment",
          name: "숙제하기",
          color: "#882FF6",
        },
        {
          url: "questions",
          name: "질문하기",
          color: "#F99D07",
        },
        {
          url: "all",
          name: "전체게시판",
          color: "#F52532",
        }
        ])
        return { categories }
    }

})
</script>
<style scoped>
.root {
    height: 100%;
    width: 100%;
}
.box {
  background-color: #fcf1c9;
  border-radius: 18px;
  height: auto;
  width: auto;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: repeat(2, 3fr);
  margin: 0;
  padding: 10px;
}
.card:hover {
  transform: scale (1.15);
  box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.9);
}
.card {
  display: flex;
  flex-direction: column;
  min-width: 50px;
  border-radius: 18px;
  box-shadow: 5px 5px 15px rgba(0,0,0,0.4);
  font-family: roboto;
  text-align: center;
  transition: 0.5s ease;
  cursor: pointer;
  margin: 10px;
  padding: 30px
}
.card-name {
  margin-top: 20px;
}
.card-icon {
  flex:auto;
}
</style>