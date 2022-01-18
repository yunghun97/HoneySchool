<template>
  <div class="about">
    <h1>This is an about page</h1>
    <h2>{{ count }}</h2>
    <button @click="increase">Increase</button>
  </div>
</template>

<script lang="ts">
import { computed } from "vue";
import { useStore } from "vuex";

export default {
  setup() {
    const store = useStore();
    const count = computed(() => store.state.accountStore.count);
    const increase = () => store.dispatch("accountStore/incrementNumber");

    return { count, increase };
  },
};
</script>
