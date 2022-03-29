<template>
  <div id="outer-div">
    <h3 class="my-4 font-A1-500">회원 가입</h3>
    <div class="d-flex justify-content-center">
      <div id="signup-form" class="card m-2 px-4 pb-4 pt-2">
        <div class="card-body">
          <Form
            @submit="onSubmit"
            :validation-schema="schema"
            v-slot="{ errors }"
          >
            <div class="row pb-2">
              <div class="col-4"></div>
              <label class="col-1 font-A1-500 fs-5 px-0 height label-height"
                >구분</label
              >
              <div
                class="form-row col-3 d-flex justify-content-center align-content-center"
              >
                <Field
                  name="position"
                  id="student"
                  type="radio"
                  class="form-control input-hidden"
                  :class="{ 'is-invalid': errors.position }"
                  value="S"
                  v-model="position"
                  checked="checked"
                />
                <label for="student" class="form-check-label label-height">
                  <p class="font-A1-500 fs-4 px-2 p-border my-0">학생</p>
                </label>
                <p class="d-inline my-0">&nbsp;&nbsp;&nbsp;</p>
                <Field
                  name="position"
                  id="teacher"
                  type="radio"
                  class="form-control input-hidden"
                  :class="{ 'is-invalid': errors.position }"
                  value="T"
                  v-model="position"
                />
                <label for="teacher" class="form-check-label label-height">
                  <p class="font-A1-500 fs-4 px-2 p-border my-0">교직원</p>
                </label>
              </div>
              <p class="text-danger">{{ errors.position }}</p>
              <div class="col-4"></div>
            </div>
            <div class="row mb-3">
              <div class="form-group col-6">
                <div class="row">
                  <label class="col-4 font-A1-500 fs-5 px-0 height">이름</label>
                  <div class="col-8">
                    <Field
                      name="name"
                      type="text"
                      class="form-control underline d-inline"
                      :class="{ 'is-invalid': errors.name }"
                    />
                    <div class="invalid-feedback">{{ errors.name }}</div>
                  </div>
                </div>
              </div>
              <div class="form-group col-6">
                <div class="row">
                  <label class="col-4 font-A1-500 fs-5 px-0 height"
                    >생년월일</label
                  >
                  <div class="col-8">
                    <Field
                      name="birth"
                      type="date"
                      class="form-control underline"
                      :class="{ 'is-invalid': errors.birth }"
                    />
                    <div class="invalid-feedback">{{ errors.birth }}</div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row mb-3">
              <div class="form-group col-6">
                <div class="row">
                  <label class="col-4 font-A1-500 fs-5 px-0 height"
                    >아이디</label
                  >
                  <div class="col-8">
                    <Field
                      name="user_id"
                      type="text"
                      class="form-control underline"
                      :class="{ 'is-invalid': errors.user_id }"
                      v-model="idCk"
                      @keyup="memberCheck"
                    />
                    <div class="invalid-feedback">{{ errors.user_id }}</div>
                    <p v-if="bool" id="check-id">중복된 아이디입니다.</p>
                  </div>
                </div>
              </div>
              <div class="form-group col-6">
                <div class="row">
                  <label class="col-4 font-A1-500 fs-5 px-0 height">학교</label>
                  <div class="col-8">
                    <Field
                      name="school"
                      as="select"
                      class="form-control underline"
                      :class="{ 'is-invalid': errors.school }"
                    >
                      <option disabled value="" selected>
                        학교를 선택하세요.
                      </option>
                      <option value="샘머리초등학교">샘머리초등학교</option>
                      <option value="노형초등학교">노형초등학교</option>
                      <option value="갑천초등학교">갑천초등학교</option>
                      <option value="지장초등학교">지장초등학교</option>
                      <option value="불당초등학교">불당초등학교</option>
                      <option value="싸피초등학교">싸피초등학교</option>
                    </Field>
                    <div class="invalid-feedback">{{ errors.school }}</div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row mb-3">
              <div class="form-group col-6">
                <div class="row">
                  <label class="col-4 font-A1-500 fs-5 px-0 height"
                    >비밀번호</label
                  >
                  <div class="col-8">
                    <Field
                      name="password"
                      type="password"
                      class="form-control underline"
                      :class="{ 'is-invalid': errors.password }"
                    />
                    <div class="invalid-feedback">{{ errors.password }}</div>
                  </div>
                </div>
              </div>
              <div class="form-group col-6">
                <div class="row">
                  <label class="col-4 font-A1-500 fs-5 px-0 height">학년</label>
                  <div class="col-8">
                    <Field
                      name="grade"
                      as="select"
                      class="form-control underline"
                      :class="{ 'is-invalid': errors.grade }"
                    >
                      <option disabled value="" selected>
                        몇 학년인지 선택하세요.
                      </option>
                      <option value="1">1학년</option>
                      <option value="2">2학년</option>
                      <option value="3">3학년</option>
                      <option value="4">4학년</option>
                      <option value="5">5학년</option>
                      <option value="6">6학년</option>
                    </Field>
                    <div class="invalid-feedback">{{ errors.grade }}</div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row mb-3">
              <div class="form-group col-6">
                <div class="row">
                  <label class="col-4 font-A1-500 fs-5 px-0 height"
                    >비밀번호 확인</label
                  >
                  <div class="col-8">
                    <Field
                      name="confirmPassword"
                      type="password"
                      class="form-control underline"
                      :class="{ 'is-invalid': errors.confirmPassword }"
                    />
                    <div class="invalid-feedback">
                      {{ errors.confirmPassword }}
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group col-6">
                <div class="row">
                  <label class="col-4 font-A1-500 fs-5 px-0 height">반</label>
                  <div class="col-8">
                    <Field
                      name="class_number"
                      as="select"
                      class="form-control underline"
                      :class="{ 'is-invalid': errors.class_number }"
                    >
                      <option disabled value="" selected>
                        몇 반인지 선택하세요.
                      </option>
                      <option value="1">1반</option>
                      <option value="2">2반</option>
                      <option value="3">3반</option>
                      <option value="4">4반</option>
                      <option value="5">5반</option>
                      <option value="6">6반</option>
                    </Field>
                    <div class="invalid-feedback">
                      {{ errors.class_number }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row mb-3">
              <div class="form-group col-6">
                <div class="row">
                  <label class="col-4 font-A1-500 fs-5 px-0 height"
                    >이메일</label
                  >
                  <div class="col-8">
                    <Field
                      name="email"
                      type="text"
                      class="form-control underline"
                      :class="{ 'is-invalid': errors.email }"
                    />
                    <div class="invalid-feedback">{{ errors.email }}</div>
                  </div>
                </div>
              </div>
              <div class="form-group col-6">
                <div class="row">
                  <label class="col-4 font-A1-500 fs-5 px-0 height">번호</label>
                  <div class="col-8">
                    <Field
                      name="student_number"
                      type="number"
                      class="form-control underline"
                      :class="{ 'is-invalid': errors.student_number }"
                    />
                    <div class="invalid-feedback">
                      {{ errors.student_number }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-group pt-4 d-flex justify-content-center">
              <button
                type="submit"
                class="login-btn d-block font-A1-500 btn-width"
              >
                확인
              </button>
            </div>
            <!-- <div class="form-group mb-3">
              <button type="submit" class="btn btn-primary mr-1">
                회원 가입
              </button>
            </div> -->
          </Form>
        </div>
      </div>
    </div>
    <div class="d-flex justify-content-center">
      <p class="p-3">
        <router-link
          to="/login"
          class="text-decoration-none link-color font-A1-500"
          >로그인</router-link
        >
      </p>
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { Form, Field } from "vee-validate";
import * as Yup from "yup";
import { ref } from "vue";

export default {
  name: "Signup",
  components: {
    Form,
    Field,
  },
  setup() {
    const schema = Yup.object().shape({
      position: Yup.string().required("학생인지 교직원인지 선택해주세요."),
      name: Yup.string().required("학생 이름은 필수 기입사항 입니다."),
      birth: Yup.string()
        .required("생년월일은 필수 기입사항 입니다.")
        .matches(
          /^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/,
          "생년월일은 YYYY-MM-DD 형식을 따라야 합니다."
        ),
      school: Yup.string().required("학교는 필수 선택사항 입니다."),
      grade: Yup.string().required("학년은 필수 선택사항 입니다."),
      class_number: Yup.string().required("반은 필수 선택사항 입니다."),
      student_number: Yup.string().required("번호는 필수 기입사항 입니다."),
      user_id: Yup.string().required("아이디는 필수 기입사항 입니다."),
      email: Yup.string()
        .required("Email 은 필수 기입사항 입니다.")
        .email("Email 이 유효하지 않습니다."),
      password: Yup.string()
        .min(6, "비밀번호는 적어도 6자리 이상이어야 합니다.")
        .required("비밀번호는 필수 기입사항 입니다."),
      confirmPassword: Yup.string()
        .oneOf([Yup.ref("password"), null], "비밀번호와 같지 않습니다.")
        .required("비밀번호 확인은 필수 기입사항 입니다."),
    });

    const store = useStore();
    const router = useRouter();

    let bool = ref(false);
    const onSubmit = (values: any) => {

      if (bool.value) {
        alert("중복된 아이디입니다.");
      } else {
        axios
          .post(process.env.VUE_APP_API_URL + "/users/register/", values)
          .then((res) => {

            // alert("SUCCESS!! :-)\n\n" + JSON.stringify(values, null, 4));
            const payload = {
              user_id: values.user_id,
              password: values.password,
            };
            store.dispatch("accountStore/getToken", payload);
          })
          // .catch((err) => {
          //   console.log(err);
          // });
      }
    };
    const memberCheck = () => {
      axios
        .get(process.env.VUE_APP_API_URL + "/users/IdCheck/" + idCk.value)
        .then((response) => {

          let cnt = response.data;
          if (cnt) bool.value = true;
          else bool.value = false;
        })
        // .catch((error) => {
        //   console.log(error);
        // });
    };

    const idCk = ref("");
    const position = ref("");
    // const grade = ref(0);
    // const class_number = ref(0);
    // const student_number = ref(0);

    return {
      position,
      // grade,
      // class_number,
      // student_number,
      schema,
      idCk,
      bool,
      onSubmit,
      memberCheck,
    };
  },
};
</script>

<style>
#signup-form {
  width: 900px;
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

input[type="radio"]:checked + label > p {
  border: 1px solid #fff;
  box-shadow: 0 0 3px 3px #fad749;
}

/* Stuff after this is only to make things more pretty */
input[type="radio"] + label > p {
  /* border: 1px dashed #444; */
  /* width: 150px;
  height: 150px; */
  transition: 500ms all;
}

input[type="radio"]:checked + label > p {
  /* transform: rotateZ(-10deg) rotateX(10deg); */
  color: #37d3be;
  text-shadow: 2px 2px 2px rgba(136, 152, 170, 0.15);
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
  margin: 50px;
  /* background-color: rgb(255, 237, 169); */
}
.height {
  line-height: 50px;
}
.btn-width {
  width: 200px;
}
.p-border {
  border: 1px solid gray;
  border-radius: 10px;
}
.label-height {
  height: 37px;
  margin-top: 6px;
  margin-bottom: 6px;
}
input,
select {
  height: 50px;
}
input.underline {
  border-left-width: 0;
  border-right-width: 0;
  border-top-width: 0;
  border-bottom-width: 1;
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
  background-image: linear-gradient(310deg, #fad749 0%, #fad749 100%);
  /* 밑에 두개가 내가 한 것 */
  /* background-color: #aae9e5; */
  /* background-image: linear-gradient(310deg, rgb(55, 211, 190) 0%, #aae9e5 100%); */
  /* 원본 색 */
  /* background-image: linear-gradient(310deg, #2dce89 0%, #2dcecc 100%); */
}
.card {
  box-shadow: 5px 5px 5px rgba(136, 152, 170, 0.15) !important;
}
.link-color {
  color: rgb(49, 180, 242);
}
.font-A1-200 {
  font-family: "Gothic A1", sans-serif;
  font-weight: 200;
}
.font-A1-300 {
  font-family: "Gothic A1", sans-serif;
  font-weight: 300;
}
.font-A1-400 {
  font-family: "Gothic A1", sans-serif;
  font-weight: 400;
}
.font-A1-500 {
  font-family: "Gothic A1", sans-serif;
  font-weight: 500;
}
#check-id {
  margin: 0;
  color: red;
  text-decoration-color: red;
}
/* backup */
/* input[type="radio"]:checked + label > img {
  border: 1px solid #fff;
  box-shadow: 0 0 3px 3px #fad749;
}

input[type="radio"] + label > img {
  width: 150px;
  height: 150px;
  transition: 500ms all;
}

input[type="radio"]:checked + label > img {
  transform: rotateZ(-10deg) rotateX(10deg);
} */
</style>
