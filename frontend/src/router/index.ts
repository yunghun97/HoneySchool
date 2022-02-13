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
import AskQuestion from "../views/board/AskQuestion.vue";
import TimeTable from "../views/board/TimeTable.vue";
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
    path: "/classboard/:article_id/edit",
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
    // 세션 명 props
    props: true,
  },
  {
    path: "/classboard/askquestion",
    name: "AskQuestion",
    component: AskQuestion,
  },
  {
    path: "/classboard/timetable",
    name: "TimeTable",
    component: TimeTable,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

// 전역 가드
// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html#%E1%84%8C%E1%85%A5%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%A8-%E1%84%80%E1%85%A1%E1%84%83%E1%85%B3
router.beforeEach((to, from, next) => {
  const accessToken = localStorage.getItem("accessToken");
  if (to.name === "Login" || to.name === "Signup") {
    if (accessToken) {
      next({ name: "About" });
    }
  } else {
    if (!accessToken) {
      next({ name: "Login" });
    }
  }
  next();
});

export default router;
