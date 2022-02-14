<template>
  <div class="d-flex justify-content-center">
    <div id="signup-form" class="card">
      <h3 class="card-header">회원 가입</h3>
      <div class="card-body">
        <Form
          @submit="onSubmit"
          :validation-schema="schema"
          v-slot="{ errors }"
        >
          <div class="form-row mb-3">
            <Field
              name="position"
              id="student"
              type="radio"
              class="form-control input-hidden"
              :class="{ 'is-invalid': errors.position }"
              value="S"
              v-model="position"
            />
            <label for="student" class="form-check-label">
              <img
                class="profile"
                src="@/assets/accounts/Signup_student.png"
                alt="Signup_student"
              />
            </label>
            <Field
              name="position"
              id="teacher"
              type="radio"
              class="form-control input-hidden"
              :class="{ 'is-invalid': errors.position }"
              value="T"
              v-model="position"
            />
            <label for="teacher" class="form-check-label">
              <img
                class="profile"
                src="@/assets/accounts/Signup_teacher.png"
                alt="Signup_teacher"
              />
            </label>
            <p class="text-danger">{{ errors.position }}</p>
          </div>
          <div class="row mb-3">
            <div class="form-group col-4">
              <label>이름</label>
              <Field
                name="name"
                type="text"
                class="form-control"
                :class="{ 'is-invalid': errors.name }"
              />
              <div class="invalid-feedback">{{ errors.name }}</div>
            </div>
            <div class="form-group col-4">
              <label>생년월일</label>
              <Field
                name="birth"
                type="date"
                class="form-control"
                :class="{ 'is-invalid': errors.birth }"
              />
              <div class="invalid-feedback">{{ errors.birth }}</div>
            </div>
            <div class="form-group col-4">
              <label>학교</label>
              <Field
                name="school"
                as="select"
                class="form-control"
                :class="{ 'is-invalid': errors.school }"
              >
                <option disabled value="" selected>학교를 선택하세요.</option>
                <option value="샘머리초등학교">샘머리초등학교</option>
                <option value="노형초등학교">노형초등학교</option>
                <option value="갑천초등학교">갑천초등학교</option>
                <option value="지장초등학교">지장초등학교</option>
                <option value="불당초등학교">불당초등학교</option>
              </Field>
              <div class="invalid-feedback">{{ errors.school }}</div>
            </div>
          </div>
          <div class="row mb-3">
            <div class="form-group col-4">
              <label>학년</label>
              <Field
                name="grade"
                as="select"
                class="form-control"
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
            <div class="form-group col-4">
              <label>반</label>
              <Field
                name="class_number"
                as="select"
                class="form-control"
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
              <div class="invalid-feedback">{{ errors.class_number }}</div>
            </div>
            <div class="form-group col-4">
              <label>번호</label>
              <Field
                name="student_number"
                type="number"
                class="form-control"
                :class="{ 'is-invalid': errors.student_number }"
              />
              <div class="invalid-feedback">{{ errors.student_number }}</div>
            </div>
          </div>
          <div class="row mb-3">
            <div class="form-group col-6">
              <label>아이디</label>
              <Field
                name="user_id"
                type="text"
                class="form-control"
                :class="{ 'is-invalid': errors.user_id }"
              />
              <div class="invalid-feedback">{{ errors.user_id }}</div>
            </div>
            <div class="form-group col-6">
              <label>Email</label>
              <Field
                name="email"
                type="text"
                class="form-control"
                :class="{ 'is-invalid': errors.email }"
              />
              <div class="invalid-feedback">{{ errors.email }}</div>
            </div>
          </div>
          <div class="row mb-3">
            <div class="form-group col-6">
              <label>비밀번호</label>
              <Field
                name="password"
                type="password"
                class="form-control"
                :class="{ 'is-invalid': errors.password }"
              />
              <div class="invalid-feedback">{{ errors.password }}</div>
            </div>
            <div class="form-group col-6">
              <label>비밀번호 확인</label>
              <Field
                name="confirmPassword"
                type="password"
                class="form-control"
                :class="{ 'is-invalid': errors.confirmPassword }"
              />
              <div class="invalid-feedback">{{ errors.confirmPassword }}</div>
            </div>
          </div>
          <div class="form-group mb-3">
            <button type="submit" class="btn btn-primary mr-1">
              회원 가입
            </button>
          </div>
        </Form>
        <router-link to="/login">이미 회원이신가요?</router-link>
      </div>
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
      position: Yup.string().required("학생인지 선생님인지 선택해주세요."),
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

    const onSubmit = (values: any) => {
      console.log(schema);
      axios
        .post(process.env.VUE_APP_API_URL+"/users/register/", values)
        .then((res) => {
          console.log(res);
          // alert("SUCCESS!! :-)\n\n" + JSON.stringify(values, null, 4));
          const payload = {
            user_id: values.user_id,
            password: values.password,
          };
          store.dispatch("accountStore/getToken", payload);
        })
        .catch((err) => {
          console.log(err);
        });
    };

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
      onSubmit,
    };
  },
};
</script>

<style>
#signup-form {
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
