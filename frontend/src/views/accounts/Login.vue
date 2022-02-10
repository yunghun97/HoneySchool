<template>
  <div class="d-flex justify-content-center">
    <div id="login-form" class="card">
      <h3 class="card-header">로그인</h3>
      <div class="card-body">
        <Form
          @submit="onSubmit"
          :validation-schema="schema"
          v-slot="{ errors }"
        >
          <div class="form-group">
            <label>아이디</label>
            <Field
              name="user_id"
              type="text"
              class="form-control"
              :class="{ 'is-invalid': errors.user_id }"
            />
            <div class="invalid-feedback">{{ errors.user_id }}</div>
          </div>
          <div class="form-group">
            <label>비밀번호</label>
            <Field
              name="password"
              type="password"
              class="form-control"
              :class="{ 'is-invalid': errors.password }"
            />
            <div class="invalid-feedback">{{ errors.password }}</div>
          </div>
          <div class="form-group">
            <button type="submit" class="btn btn-primary mr-1">로그인</button>
          </div>
        </Form>
        <router-link to="/signup">아직 회원이 아니신가요?</router-link>
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
#login-form {
  width: 800px;
}

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
</style>
