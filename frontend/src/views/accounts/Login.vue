<template>
  <div id="outer-div">
    <img
      class="profile mb-3"
      src="@/assets/board/all.png"
      alt="login image"
    />
    <h3 class="my-4">로그인</h3>
    <div class="d-flex justify-content-center">
      <div id="login-form" class="card m-2 p-4">
        <div class="card-body">
          <Form
            @submit="onSubmit"
            :validation-schema="schema"
            v-slot="{ errors }"
          >
            <div class="form-group py-2 d-flex flex-column align-items-start">
              <label class="py-2 fs-5">아이디</label>
              <Field
                name="user_id"
                type="text"
                class="form-control"
                :class="{ 'is-invalid': errors.user_id }"
              />
              <div class="invalid-feedback">{{ errors.user_id }}</div>
            </div>
            <div class="form-group py-2 d-flex flex-column align-items-start">
              <label class="py-2 fs-5">비밀번호</label>
              <Field
                name="password"
                type="password"
                class="form-control"
                :class="{ 'is-invalid': errors.password }"
              />
              <div class="invalid-feedback">{{ errors.password }}</div>
            </div>
            <div class="form-group d-grid gap-2 pt-4">
              <button type="submit" class="btn bg-primary d-block">로그인</button>
            </div>
          </Form>
        </div>
      </div>
    </div>
    <div class="d-flex justify-content-center">
      <div id="signup-button" class="card m-2 p-4 d-flex flex-column justify-content-center">
        <p class="m-0">
          아직 회원이 아니신가요?
          <router-link to="/signup" class="text-decoration-none">회원가입 하세요.</router-link>
        </p>
      </div>
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
  height: 450px;
  background-color: rgb(255, 237, 169);
  border-radius: 15px;
}
#signup-button {
  width: 600px;
  height: 100px;
  border-radius: 15px;
}
#outer-div {
  margin : 50px;
}
input {
    height: 50px;
}
.btn {
  height: 50px;
}

/* .pinkColor {
  color: rgb(254, 183, 211);
} */
</style>
