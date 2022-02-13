import axios from "axios";
import { Module } from "vuex";
import { RootState } from "../index";


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
      return state.classBoardAll.find(article => article.id === id)
    },
  }, 
  mutations: {
    GETARTICLES (state, data) {
      state.classBoardAll = data
      console.log('Did!')
      console.log('Here!', state.classBoardAll)
    },
    CLASSIFYCATEGORY (state, payload) {
      state[payload[0]] = payload[1]
    }
  },
  actions: {
    getArticles ({ commit }, userinfo) {        
      axios.get(process.env.VUE_APP_API_URL+"/board/class",{
        params:{
          school: userinfo.school,
          grade: userinfo.grade,
          classes: userinfo.class_number,
        }
      })
      .then((response)=>{
        commit('GETARTICLES', response.data)
      })   
    },
    classifyCategory ({ commit }, [category, userinfo]) {
      return axios.get(process.env.VUE_APP_API_URL+"/board/class/category",{
          params:{
            school: userinfo.school,
            grade: userinfo.grade,
            classes: userinfo.class_number,
            category: category,
          }
        })
        .then((response)=>{
          const payload = [category as category, response.data]
          commit('CLASSIFYCATEGORY', payload)
        })
    },
    classifyCategorybyUser ({ commit }, [category, userinfo]) {
      return axios.get(process.env.VUE_APP_API_URL+"/board/class/category/user",{
        params:{
          school: userinfo.school,
          grade: userinfo.grade,
          classes: userinfo.class_number,
          category: category,
          userId: userinfo.userId,
        }
      })
      .then((response)=>{
        const payload = [category as category, response.data]
        commit('CLASSIFYCATEGORY', payload)
      })
    },
  },

};
