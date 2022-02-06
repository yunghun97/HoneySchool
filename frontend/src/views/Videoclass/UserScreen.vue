<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" />
  </div>
</template>

<script>
import OvVideo from "./OvVideo.vue";

export default {
  name: "UserScreen",

  components: {
    OvVideo,
  },

  data() {
    return {
      muteStatus: true,
    };
  },
  props: {
    streamManager: Object,
  },
};
</script>
