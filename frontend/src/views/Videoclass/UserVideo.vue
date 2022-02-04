<template>
  <div v-if="streamManager" class="video">
    <div class="video-container">
      <ov-video :stream-manager="streamManager"/>
      <!-- raise hand icon-->
      <img src="@/assets/videoclass/hand.png" alt="손들기" id="overlay" v-if="streamManager.raisehand">
    </div>
    <div>
      <p>{{ clientData }}</p>
      <!-- mute btn -->    
      <div>
        <button v-if="muteStatus" @click="changeMuteStatus()">mic-on</button>
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
      muteStatus: true,
    };
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
      this.streamManager.subscribeToAudio(this.muteStatus);
      this.muteStatus = !this.muteStatus;
    },
  },
};
</script>
<style scoped>
.video {
  display: flex;
}
.video-container {
  position: relative;
  width: 100%;
  height: 100%;
}
#overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 20%;
	height: 20%;
}

/* .video-container::before {
  content: "";
  position: absolute;
  bottom: 8px;
  right: 8px;
  width: 30px;
  height: 30px;
  background: url('../../assets/videoclass/hand.png');
  background-size: cover;
} */


</style>