<template>
  <div id="outer-div">
    <div class="d-flex justify-content-center">
      <div class="card m-3 p-4 profile-card">
        <h3 class="font-A1-500 my-2 pb-3">내 정보 수정</h3>
        <div class="card-body">
          <Form
            @submit="editProfile"
            :validation-schema="schema"
            v-slot="{ errors }"
          >
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-1 px-2 py-1 fs-5 font-A1-500">이름</p>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300">{{ userinfo.name }}</p>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-500">구분</p>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300" v-if="userinfo.position ==='S'">
                학생
              </p>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300" v-else>
                선생님
              </p>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-1 px-2 py-1 fs-5 font-A1-500">아이디</p>
              <p class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300">{{ userinfo.userId }}</p>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-1 px-2 py-1 fs-5 font-A1-500">비밀번호</p>
              <div class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300">
                <Field
                  name="password"
                  type="password"
                  class="form-control underline"
                  :class="{ 'is-invalid': errors.password }"
                  v-model="password"
                />
                <div class="invalid-feedback">{{ errors.password }}</div>
              </div>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-1 px-2 py-1 fs-5 font-A1-500">비밀번호 확인</p>
              <div class="col-5 text-left my-0 px-2 py-1 fs-5 font-A1-300">
                <Field
                  name="confirmPassword"
                  type="password"
                  class="form-control underline"
                  :class="{ 'is-invalid': errors.confirmPassword }"
                />
                <div class="invalid-feedback">{{ errors.confirmPassword }}</div>
              </div>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-1 px-2 py-1 fs-5 font-A1-500">학교</p>
              <div class="col-5 text-left my-1 px-2 py-1 fs-5 font-A1-300" >
                <Field
                  name="school"
                  as="select"
                  class="form-control underline"
                  :class="{ 'is-invalid': errors.school }"
                  v-model="userinfo.school"
                >
                  <option value="샘머리초등학교">샘머리초등학교</option>
                  <option value="노형초등학교">노형초등학교</option>
                  <option value="갑천초등학교">갑천초등학교</option>
                  <option value="지장초등학교">지장초등학교</option>
                  <option value="불당초등학교">불당초등학교</option>
                </Field>
                <div class="invalid-feedback">{{ errors.school }}</div>
              </div>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-1 px-2 py-1 fs-5 font-A1-500">학년</p>
              <div class="col-5 text-left my-1 px-2 py-1 fs-5 font-A1-300">
               <Field
                name="grade"
                as="select"
                class="form-control underline"
                :class="{ 'is-invalid': errors.grade }"
                v-model="userinfo.grade"
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
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-1 px-2 py-1 fs-5 font-A1-500">반</p>
            <div class="col-5 text-left my-1 px-2 py-1 fs-5 font-A1-300">
              <Field
                name="class_number"
                as="select"
                class="form-control underline"
                :class="{ 'is-invalid': errors.class_number }"
                v-model="userinfo.class_number"
              >
                <option value="1">1반</option>
                <option value="2">2반</option>
                <option value="3">3반</option>
                <option value="4">4반</option>
                <option value="5">5반</option>
                <option value="6">6반</option>
              </Field>
              <div class="invalid-feedback">{{ errors.class_number }}</div>
            </div>
            </div>
            <div class="row">
              <div class="col-2"></div>
              <p class="col-5 text-left my-1 px-2 py-1 fs-5 font-A1-500">번호</p>
              <div class="col-5 text-left my-1 px-2 py-1 fs-5 font-A1-300">
                <Field
                  name="student_number"
                  type="number"
                  class="form-control underline"
                  :value="`${userinfo.student_number}`"
                  v-model="userinfo.student_number"
                  :class="{ 'is-invalid': errors.student_number }"
                />
                <div class="invalid-feedback">{{ errors.student_number }}</div>
              </div>
            </div>
            <div class="mt-4 d-flex justify-content-center">
              <button type="submit" class="login-btn d-block font-A1-500 mx-3" @click="editProfile">수정 완료</button>
            </div>
          </Form>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import axios from "axios";
import * as Yup from "yup";
import { useStore } from "vuex";
import { ref } from 'vue';
import { useRouter } from "vue-router";
import { Form, Field } from "vee-validate";
interface UserInfo {
  [index: string] : any
  name: string,
  position: string,
  userId : string,
  school : string,
  grade: number,
  class_number : number,
  student_number : number,
  password? : any,
}
export default {
  name: "EditProfile",
  components: {
    Form,
    Field,
  },
  setup() {
    const localStorageData = localStorage.getItem("vuex");
    let userinfoData;
    if (localStorageData !== null) {
      userinfoData = JSON.parse(localStorageData);
    }
    let userinfo = ref<UserInfo>(userinfoData.accountStore.userinfo);
    console.log(userinfo)
    const schema = Yup.object().shape({
      school: Yup.string().required("학교는 필수 선택사항 입니다."),
      grade: Yup.string().required("학년은 필수 선택사항 입니다."),
      class_number: Yup.string().required("반은 필수 선택사항 입니다."),
      student_number: Yup.string().required("번호는 필수 기입사항 입니다."),
      password: Yup.string()
        .min(6, "비밀번호는 적어도 6자리 이상이어야 합니다.")
        .required("비밀번호는 필수 기입사항 입니다."),
      confirmPassword: Yup.string()
        .oneOf([Yup.ref("password"), null], "비밀번호와 같지 않습니다.")
        .required("비밀번호 확인은 필수 기입사항 입니다."),
    });
    const store = useStore();
    const router = useRouter();

    const editProfile = (value:any) => {
      const newinfo = {
        'name': userinfo.value.name,
        'school': value.school,
        'grade': value.grade,
        'classes': value.class_number,
        'number': value.student_number,
        'password': value.password,
        // 오류때문에 하나씩 추가해봤습니다.
        // 'user_id' : userinfo.value.userId,
        //'email': 'email@email.com',
        //'position': userinfo.value.position,
      }

      axios.put(process.env.VUE_APP_API_URL+`/users/${userinfo.value.userId}`, newinfo)
      .then(() => {
        const payload = {
          user_id: userinfo.value.user_id,
          password: newinfo.password,
        };
        store.dispatch("accountStore/getToken", payload);
      })
      .then(() => {
        router.push({name: 'Profile'})
      })
    }
    return { userinfo, editProfile, schema }
  },
}
</script>

<style>

</style>