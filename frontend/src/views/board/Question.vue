<template>
    <div>
        <h2>선생님께 질문을 남겨보아요</h2>
    </div>
    <div class="recordbox">
        <Record />
    </div>
</template>
<script lang="ts">
import { defineComponent } from 'vue'
import Record from '@/components/Board/Record.vue'
export default defineComponent({
    name:"Question",
    components: { Record },
    // setup() {
        
    // },
})
</script>
<style scoped>
.recordbox {
    display: flex;
    justify-content: center;
    align-items: center;
    /* min-height: 90vh; */

}
</style>