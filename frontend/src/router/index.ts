import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Home from "../views/Home.vue";
import ClassBoard from "../views/board/ClassBoard.vue";
import BoardTable from "../views/board/BoardTable.vue";
import ArticleDetail from "../views/board/ArticleDetail.vue";
import ArticleCreate from "../views/board/ArticleCreate.vue";
import Assignment from "../views/board/Assignment.vue";
import Question from "../views/board/Question.vue"
import Signup from "../views/accounts/Signup.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
  {
    path: "/classboard",
    name: "ClassBoard",
    component: ClassBoard,
  },
  {
    path: "/classboard/all",
    name: "BoardTable",
    component: BoardTable,
  },
  {
    path: "/classboard/:category/:article_id",
    name: "ArticleDetail",
    component: ArticleDetail,
  },
  {
    path: "/classboard/create",
    name: "ArticleCreate",
    component: ArticleCreate,
  },
  {
    path: "/classboard/assignment",
    name: "Assignment",
    component: Assignment,
  },
  {
    path: "/classboard/question",
    name: "Question",
    component: Question,
  },
  {
    path: "/signup",
    name: "Signup",
    component: Signup,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
