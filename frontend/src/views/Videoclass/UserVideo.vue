<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" />
    <div>
      <p>{{ clientData }}</p>
      
      <!-- mute btn -->
      <div>
        <button v-if="muteStatus"  @click="changeMuteStatus()">mic-on</button>
        <button v-else @click="changeMuteStatus()">mic-off</button>
      </div>
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

  data() {
    return {
      muteStatus : true,
    }
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
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
    
    // mute function
    changeMuteStatus() {
      //this.publisher.publishAudio(this.muteStatus); // true to unmute the audio track, false to mute it
      this.streamManager.subscribeToAudio(this.muteStatus)
      this.muteStatus = !this.muteStatus;
    },

  },
};
</script>
