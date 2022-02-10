<template>
  <div v-if="streamManager">
    <div>
      <ov-video :stream-manager="streamManager"/>
    </div>
    <div>
      <p>{{ clientData }}</p>
    </div>
  </div>
</template>

<script>
import OvVideo from "./OvVideo.vue";

export default {
  name: "UserVideo",

  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    },
  },

  methods: {
    getConnectionData() {
      return JSON.parse(this.streamManager.stream.connection.data);
    },
  },
};
</script>