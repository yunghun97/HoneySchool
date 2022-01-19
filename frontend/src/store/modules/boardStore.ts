import axios from "axios";
import { Module } from "vuex";
import { RootState } from "../index";
// import boardType from "@/types/board/BoardArticles";

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
}
export const boardStore: Module<boardState, RootState> = {
  namespaced: true,
  state: () => ({
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
    ]
  }),
  getters: {
    getArticleDetail: (state, id:number) => {
      console.log('here!')
      return state.classBoardAll.find(article => article.id === id)
    }
  },
  mutations: {
  
  },
  actions: {
  },
};
