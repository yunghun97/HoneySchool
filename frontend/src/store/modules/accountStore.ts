import { Module, ActionContext } from "vuex";
import { RootState } from "../index"
import axios from "axios"
import router from "../../router"

export interface accountState {
  accessToken: string;
  accounts: Array<any>;
  user: any;
}

export const accountStore: Module<accountState, RootState> = {
  namespaced: true,
  state: () => ({
    accessToken: "",
    accounts: [],
    user: {},
  }),
  getters: {
    isLogin: (state) => {
      return !!state.accessToken;
    },
    getUser: (state) => {
      // return JSON.parse(atob(state.accessToken.split('.')[1])).username
      return JSON.parse(atob(state.accessToken.split(".")[1]));
    },
  },
  mutations: {
    setToken(state, newAccessToken) {
      state.accessToken = newAccessToken;
    },
    deleteToken(state) {
      state.accessToken = "";
    },
    setAccounts(state, newAccounts) {
      state.accounts = newAccounts;
      // console.log(state.accounts)
    },
    setUser(state, user) {
      state.user = user;
      // console.log(state.accounts)
    },
  },
  actions: {
    getToken({ commit }, { username, password }) {
      axios
        .post("http://localhost:8000/api/token/", { username, password })
        .then((response) => {
          localStorage.setItem("accessToken", response.data.access);
          commit("setToken", response.data.access);
          router.push({ name: "Index" });
        })
        .catch((err) => {
          console.log("에러", err.response);
        });
    },
    getAccounts({ commit }) {
      axios.get("http://127.0.0.1:8000/accounts/").then((response) => {
        commit("setAccounts", response.data);
      });
    },
    getUserdetail({ commit }, user_id) {
      axios
        .get(`http://127.0.0.1:8000/accounts/${user_id}/detail/`)
        .then((response) => {
          commit("setUser", response.data);
        });
    },
  },
};
