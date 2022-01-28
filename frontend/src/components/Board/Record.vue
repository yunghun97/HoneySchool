<template>
  <div>
    <AudioRecorder
    :timer="true"
    timer-color="#333"
    :timer-font-size="3"
    timer-background="#eee"
    :audio-width="300"
    @audioFile="myAudioFile"
    />
  </div>
</template>

<script>
  import AudioRecorder from 'jamit-audio-recorder';

  export default {
  name: 'Record',
  components: {
    AudioRecorder,
    },
    methods: {
        myAudioFile(audioFile) {
        console.log('myAudioFile: ', audioFile);
        },
    },
};
</script>

