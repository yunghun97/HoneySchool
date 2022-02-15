<template>
  <div id="outer-div">
    <h3 class="my-4 font-A1-500">로그인</h3>
    <div class="d-flex justify-content-center">
      <div id="login-form" class="card m-2 p-4">
        <div class="card-body">
          <Form
            @submit="onSubmit"
            :validation-schema="schema"
            v-slot="{ errors }"
          >
            <div class="form-group py-2 d-flex flex-column align-items-start">
              <!-- <label class="py-2 fs-5">아이디</label> -->
              <Field
                name="user_id"
                type="text"
                placeholder="아이디"
                class="form-control underline font-A1-200"
                :class="{ 'is-invalid': errors.user_id }"
              />
              <div class="invalid-feedback">{{ errors.user_id }}</div>
            </div>
            <div class="form-group py-2 d-flex flex-column align-items-start">
              <!-- <label class="py-2 fs-5">비밀번호</label> -->
              <Field
                name="password"
                type="password"
                placeholder="비밀번호"
                class="form-control underline font-A1-200"
                :class="{ 'is-invalid': errors.password }"
              />
              <div class="invalid-feedback">{{ errors.password }}</div>
            </div>
            <div class="form-group d-grid gap-2 pt-4">
              <button type="submit" class="login-btn d-block font-A1-500">로그인</button>
            </div>
          </Form>
        </div>
      </div>
    </div>
    <div class="d-flex justify-content-center">
      <p class="p-3">
        <router-link to="/signup" class="text-decoration-none text-dark font-A1-500">회원가입</router-link>
      </p>
    </div>
  </div>
</template>

<script lang="ts">
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { Form, Field } from "vee-validate";
import * as Yup from "yup";

export default {
  name: "Login",
  components: {
    Form,
    Field,
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const schema = Yup.object().shape({
      user_id: Yup.string().required("아이디는 필수 기입사항 입니다."),
      password: Yup.string()
        .min(6, "비밀번호는 적어도 6자리 이상이어야 합니다.")
        .required("비밀번호는 필수 기입사항 입니다."),
    });

    const onSubmit = (values: any) => {
      console.log(schema);
      // alert("SUCCESS!! :-)\n\n" + JSON.stringify(values, null, 4));
      const payload = {
        user_id: values.user_id,
        password: values.password,
      };
      store.dispatch("accountStore/getToken", payload);
    };

    return {
      schema,
      onSubmit,
    };
  },
};
</script>

<style>
.profile {
  width: 150px;
  height: 150px;
  border-radius: 70%;
  overflow: hidden;
}

.input-hidden {
  position: absolute;
  left: -9999px;
}

input[type="radio"]:checked + label > img {
  border: 1px solid #fff;
  box-shadow: 0 0 3px 3px #fad749;
}

/* Stuff after this is only to make things more pretty */
input[type="radio"] + label > img {
  /* border: 1px dashed #444; */
  width: 150px;
  height: 150px;
  transition: 500ms all;
}

input[type="radio"]:checked + label > img {
  transform: rotateZ(-10deg) rotateX(10deg);
}

/* pyun */
#login-form {
  width: 600px;
}
#signup-button {
  width: 600px;
  height: 100px;
  border-radius: 15px;
}
#outer-div {
  margin : 50px;
  /* background-color: rgb(255, 237, 169); */
}
input {
    height: 50px;
}
input.underline {
  border-left-width:0;
  border-right-width:0;
  border-top-width:0;
  border-bottom-width:1;
}
.login-btn {
  display: inline-block;
  line-height: 1.5;
  color: #67748e;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  user-select: none;
  border: 1px solid transparent;
  padding: 0.625rem 1.25rem;
  transition: all 0.15s ease-in;
  /* font-weight: 500; */
  /* font-size: 0.875rem; */
  /* background-color: transparent; */
  border-radius: 0.3rem;
  background-color: #aae9e5;
  /* background-image: linear-gradient(310deg, #2dce89 0%, #2dcecc 100%); */
  background-image: linear-gradient(310deg, rgb(55, 211, 190) 0%, #aae9e5 100%);
}
.card {
  box-shadow: 5px 5px 5px rgba(136, 152, 170, 0.15) !important;
}
.font-A1-200{
  font-family: 'Gothic A1', sans-serif;
  font-weight: 200;
}
.font-A1-300{
  font-family: 'Gothic A1', sans-serif;
  font-weight: 300;
}
.font-A1-400{
  font-family: 'Gothic A1', sans-serif;
  font-weight: 400;
}
.font-A1-500{
  font-family: 'Gothic A1', sans-serif;
  font-weight: 500;
}
/* .pinkColor {
  color: rgb(254, 183, 211);
} */
</style>
