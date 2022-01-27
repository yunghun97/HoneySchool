import axios from "axios";
import { Module } from "vuex";
import { RootState } from "../index";
// import BoardArticles from "@/types/board/BoardArticles";
import AxiosService from '@/services/axios.service';
import AxiosResponse from '@/services/axios.service';
import { number } from "yup";


interface BoardArticles {
  [index: number] : number | string,
  id: number,
  category: string,
  title: string,
  content: string,
  writer: string,
  date: number,
}

type boardType = Array<BoardArticles>;

export interface boardState {
    classBoardAll: boardType;
    notice: boardType;
    handouts: boardType;
    photo: boardType;
}
export const boardStore: Module<boardState, RootState> = {
  namespaced: true,
  state: () => ({
    notice: [],
    handouts: [],
    photo: [],
    classBoardAll: [],
  }),
  getters: {
    getArticleDetail: (state, id:number) => {
      console.log('here!')
      return state.classBoardAll.find(article => article.id === id)
    },
  }, 
  mutations: {
    GETARTICLES (state, data) {
      state.classBoardAll = data
    },
    CLASSIFYCATEGORY (state) {
      state.notice = state.classBoardAll.filter((article) => article.category === "notice")
      state.handouts = state.classBoardAll.filter((article) => article.category === "handouts")
      state.photo = state.classBoardAll.filter((article) => article.category === "photo")
    }
  },
  actions: {    
    getArticles ({ commit }) {
      axios.get("http://localhost:9999/api/v1/board/class",{
        params:{
          school: "싸피초",
          grade: 1,
          classes: 1,
        }
      })
      .then((response)=>{
        //console.log(response.data);
        commit('GETARTICLES', response.data)
      })
      .catch(()=>
        alert("실패!")
      )    
    },
    classifyCategory ({ commit }) {
      commit('CLASSIFYCATEGORY')
    }
  },

};
