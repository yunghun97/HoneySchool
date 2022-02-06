import axios from "axios";
import { Module } from "vuex";
import { RootState } from "../index";
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
type category = 'notice' | 'handouts' | 'photo' | 'assignment' | 'question'
type boardType = Array<BoardArticles>;

export interface boardState {
    [index: string] : boardType,
    classBoardAll: boardType;
    notice: boardType;
    handouts: boardType;
    photo: boardType;
    assignment: boardType;
    question: boardType;
}
export const boardStore: Module<boardState, RootState> = {
  namespaced: true,
  state: () => ({
    classBoardAll: [],
    notice: [],
    handouts: [],
    photo: [],
    assignment: [],
    question: [],
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
    CLASSIFYCATEGORY (state, payload) {
      state[payload[0]] = payload[1]
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
    classifyCategory ({ commit }, category) {
      return axios.get("http://localhost:9999/api/v1/board/class/category",{
          params:{
            school: "싸피초",
            grade: 1,
            category: category,
            classes: 1,
          }
        })
        .then((response)=>{
          const payload = [category as category, response.data]
          commit('CLASSIFYCATEGORY', payload)
        })
        .catch(()=>
          alert("카테고리 받아오기 실패!")
        )  
    },
  },

};
