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
    margin: 50px
}
</style>