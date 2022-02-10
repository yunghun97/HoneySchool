import { createStore } from "vuex";
import { accountStore, accountState } from "@/store/modules/accountStore";
import { boardStore, boardState } from "@/store/modules/boardStore";
import createPersistedState from "vuex-persistedstate";
// import videoStore from "@/store/modules/videoStore";

export interface RootState {
  accountStore: accountState;
  boardStore: boardState;
  // videoStore: videoState;
}

export default createStore({
  // modules: { accountStore, boardStore, videoStore }
  modules: { accountStore, boardStore },
  plugins: [
    createPersistedState({
      paths: ["accountStore"],
    }),
  ],
});
