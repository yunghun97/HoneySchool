<template>
  <div class="about">
    <h1>This is an Main page</h1>
    <h2 v-if="userinfo.position === 'S'">{{ userinfo.school }} {{ userinfo.name }}학생 반갑습니다.</h2>
    <h2 v-else>{{ userinfo.school }} {{ userinfo.name }}선생님 반갑습니다.</h2>
    <h2>{{ count }}</h2>
    <h2>{{ userinfo }}</h2>
    <button @click="increase">Increase</button>
    <button class="btn-warning" @click="logout">Logout</button>
  </div>
</template>

<script lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";

export default {
  setup() {
    const store = useStore();
    const count = computed(() => store.state.accountStore.count);
    let userinfo = computed(() => store.state.accountStore.userinfo);
    const increase = () => store.dispatch("accountStore/incrementNumber");
    const logout = () => store.commit("accountStore/logout");
    return { count, userinfo, increase, logout };
  },
};
</script>
