<template>
<div class="canvas">
    <Canvas />

</div>
</template>
<script lang="ts">
import { defineComponent } from 'vue'
import Canvas from '@/components/Board/Canvas.vue'
export default defineComponent({
    name: 'Assignment',
    components: { Canvas }
})
</script>
<style scoped>
.canvas {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 90vh;
}
</style>