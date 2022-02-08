import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Home from "../views/Home.vue";
import ClassBoard from "../views/board/ClassBoard.vue";
import BoardTable from "../views/board/BoardTable.vue";
import ArticleDetail from "../views/board/ArticleDetail.vue";
import ArticleCreate from "../views/board/ArticleCreate.vue";
import Handout from "../views/board/Handout.vue";
import Photo from "../views/board/Photo.vue";
import Assignment from "../views/board/Assignment.vue";
import Question from "../views/board/Question.vue";
import Signup from "../views/accounts/Signup.vue";
import Login from "../views/accounts/Login.vue";
import Videoclass from "../views/Videoclass/Videoclass.vue";

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
    path: "/classboard/:article_id",
    name: "ArticleDetail",
    component: ArticleDetail,
  },
  {
    path: "/classboard/:category/:article_id/edit",
    name: "ArticleUpdate",
    component: () => import("@/views/board/ArticleUpdate.vue"),
  },
  {
    path: "/classboard/create",
    name: "ArticleCreate",
    component: ArticleCreate,
  },
  {
    path: "/classboard/notice/:article_id",
    name: "Notice",
    component: () => import("@/views/board/Notice.vue"),
    // meta: {
    //   reload: true
    // }
  },
  {
    path: "/classboard/handouts/:article_id",
    name: "Handout",
    component: Handout,
  },
  {
    path: "/classboard/photo/:article_id",
    name: "Photo",
    component: Photo,
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
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/videoclass",
    name: "Videoclass",
    component: Videoclass,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
