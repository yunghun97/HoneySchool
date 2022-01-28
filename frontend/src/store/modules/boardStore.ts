import axios from "axios";
import { Module } from "vuex";
import { RootState } from "../index";
// import BoardArticles from "@/types/board/BoardArticles";
import AxiosService from '@/services/axios.service';
import AxiosResponse from '@/services/axios.service';


interface BoardArticles {
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
    classBoardAll: [
        {   id:1,
            category: 'notice',
            title: '2022.01.17 알림장',
            content: '지각하지 않기!',
            writer: '1반 담임 선생님',
            date: 20220117
        },
        {
            id: 2,
            category: 'notice',
            title: '2022.01.18 알림장',
            content: '숙제하기!',
            writer: '1반 담임 선생님',
            date: 20220118
        },
        {   id:3,
          category: 'handouts',
          title: '2022.01.17 수학',
          content: '1단원 유인물',
          writer: '1반 담임 선생님',
          file: 'url',
          date: 20220117
      },
      {   id:4,
        category: 'handouts',
        title: '2022.01.17 국어',
        content: '1단원 유인물',
        writer: '1반 담임 선생님',
        file: 'url',
        date: 20220118
    },
    {   id:5,
        category: 'photo',
        title: '2022.01.20 체육시간',
        content: '체육시간',
        writer: '1반 담임 선생님',
        file: 'https://cdn.pixabay.com/photo/2015/04/20/06/29/childrens-730667_960_720.jpg',
        date: 20220120
  },
    
    ]
  }),
  getters: {
    getArticleDetail: (state, id:number) => {
      console.log('here!')
      return state.classBoardAll.find(article => article.id === id)
    },
  }, 
  mutations: {
    CLASSIFYCATEGORY (state) {
      for (let i = 0; i < state.classBoardAll.length; ++i) {
        const article = state.classBoardAll[i] as BoardArticles
        if (article.category === 'notice') {
          state.notice.push(article)
        } else if (article.category === 'handouts') {
          state.handouts.push(article)
        } else {
          state.photo.push(article)
        }
      }
    }
  },
  actions: {
    classifyCategory ({ commit }) {
      commit('CLASSIFYCATEGORY')
    }
  },

};
