import axios from "axios";
import { Module } from "vuex";
import { RootState } from "../index";
import BoardArticles from "../../types/board/BoardArticles"

// interface BoardArticles {
//   [index: number] : number | string,
//   id: number,
//   category: string,
//   title: string,
//   content: string,
//   writer: string,
//   date: number,
// }
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
    getArticles ({ commit }, data) {        
      return axios.get(process.env.VUE_APP_API_URL+"/board/class",{
        params:{
          school: data[0].school,
          grade: data[0].grade,
          classes: data[0].class_number,
          page: data[1],
          size: 20,
        }
      })
      .then((response :any)=>{
        commit('GETARTICLES', response.data.content)
      })
       
    },
    classifyCategory ({ commit }, data) {
      return axios.get(process.env.VUE_APP_API_URL+"/board/class/category",{
          params:{
            school: data[1].school,
            grade: data[1].grade,
            classes: data[1].class_number,
            category: data[0],
            page: data[2],
            size: 20,
          }
        })
        .then((response)=>{
          const payload = [data[0] as category, response.data.content]
          commit('CLASSIFYCATEGORY', payload)
        })
    },
    classifyCategorybyUser ({ commit }, data) {
      return axios.get(process.env.VUE_APP_API_URL+"/board/class/category/user",{
        params:{
          school: data[1].school,
          grade: data[1].grade,
          classes: data[1].class_number,
          category: data[0],
          userId: data[1].userId,
          page: data[2],
          size: 20,
        }
      })
      .then((response)=>{
        const payload = [ data[0] as category, response.data.content]
        commit('CLASSIFYCATEGORY', payload)
      })
    },
  },

};
