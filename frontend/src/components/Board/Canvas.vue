<template>
  <div class="flex-row">
    <div class="source">
      <vue-drawing-canvas
        ref="VueCanvasDrawing"
        v-model:image="image"
        :stroke-type="strokeType"
        :fill-shape="fillShape"
        :eraser="eraser"
        :lineWidth="line"
        :color="color"
        :background-color="backgroundColor"
        :background-image="backgroundImage"
        :watermark="watermark"
        saveAs="jpeg"
        :lock="disabled"
        @mousemove="getCoordinate($event)"
        class="canvasdrawing"
      />
      <div class="button-container">
        <button type="button" class="btn btn-dark btn-lg" @click.prevent="eraser = !eraser">
          <span v-if="eraser">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-eraser"
              viewBox="0 0 16 16"
            >
              <path
                d="M8.086 2.207a2 2 0 0 1 2.828 0l3.879 3.879a2 2 0 0 1 0 2.828l-5.5 5.5A2 2 0 0 1 7.879 15H5.12a2 2 0 0 1-1.414-.586l-2.5-2.5a2 2 0 0 1 0-2.828l6.879-6.879zm2.121.707a1 1 0 0 0-1.414 0L4.16 7.547l5.293 5.293 4.633-4.633a1 1 0 0 0 0-1.414l-3.879-3.879zM8.746 13.547 3.453 8.254 1.914 9.793a1 1 0 0 0 0 1.414l2.5 2.5a1 1 0 0 0 .707.293H7.88a1 1 0 0 0 .707-.293l.16-.16z"
              />
            </svg>
            지우개
          </span>
          <span v-else>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-pencil"
              viewBox="0 0 16 16"
            >
              <path
                d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"
              />
            </svg>
            연필
          </span>
        </button>
        <!-- <button type="button" @click.prevent="disabled = !disabled">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock" viewBox="0 0 16 16">
              <path v-if="!disabled" d="M11 1a2 2 0 0 0-2 2v4a2 2 0 0 1 2 2v5a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V9a2 2 0 0 1 2-2h5V3a3 3 0 0 1 6 0v4a.5.5 0 0 1-1 0V3a2 2 0 0 0-2-2zM3 8a1 1 0 0 0-1 1v5a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V9a1 1 0 0 0-1-1H3z"/>
              <path v-else d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2zM5 8h6a1 1 0 0 1 1 1v5a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V9a1 1 0 0 1 1-1z"/>
            </svg>
            <span v-if="!disabled">Unlock</span>
            <span v-else>Lock</span>
          </button> -->
        <button type="button" class="btn btn-danger btn-lg" @click.prevent="$refs.VueCanvasDrawing.undo()">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            fill="currentColor"
            class="bi bi-arrow-counterclockwise"
            viewBox="0 0 16 16"
          >
            <path
              fill-rule="evenodd"
              d="M8 3a5 5 0 1 1-4.546 2.914.5.5 0 0 0-.908-.417A6 6 0 1 0 8 2v1z"
            />
            <path
              d="M8 4.466V.534a.25.25 0 0 0-.41-.192L5.23 2.308a.25.25 0 0 0 0 .384l2.36 1.966A.25.25 0 0 0 8 4.466z"
            />
          </svg>
          되돌리기
        </button>
        <button type="button" class="btn btn-success btn-lg" @click.prevent="$refs.VueCanvasDrawing.redo()">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            fill="currentColor"
            class="bi bi-arrow-clockwise"
            viewBox="0 0 16 16"
          >
            <path
              fill-rule="evenodd"
              d="M8 3a5 5 0 1 0 4.546 2.914.5.5 0 0 1 .908-.417A6 6 0 1 1 8 2v1z"
            />
            <path
              d="M8 4.466V.534a.25.25 0 0 1 .41-.192l2.36 1.966c.12.1.12.284 0 .384L8.41 4.658A.25.25 0 0 1 8 4.466z"
            />
          </svg>
          되돌리기 취소
        </button>
        <button type="button" class="btn btn-primary btn-lg" @click.prevent="$refs.VueCanvasDrawing.reset()">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            class="bi bi-file-earmark"
            viewBox="0 0 16 16"
          >
            <path
              d="M14 4.5V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h5.5L14 4.5zm-3 0A1.5 1.5 0 0 1 9.5 3V1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h-2z"
            />
          </svg>
          새로하기
        </button>
        <button type="button" class="btn btn-warning btn-lg" @click="saveImage">
        제출하기
        </button>
      </div>
    </div> 
    </div>
  <div>

  </div>

</template>

<script>
import VueDrawingCanvas from "vue-drawing-canvas";
import { mapState, route } from "vuex";
import axios from 'axios';
import router from '../../router';
export default {
  name: "App",
  components: {
    VueDrawingCanvas,
  },
  props: ['article'],
  data() {
    return {
      x: 0,
      y: 0,
      image: "",
      eraser: false,
      disabled: false,
      fillShape: false,
      line: 5,
      color: "#000000",
      strokeType: "dash",
      backgroundColor: "#FFFFFF",
      backgroundImage: this.article.files.length === 0 ? null : `//home/ubuntu/honeyschool/file/${this.article.files[0].stored_file_path}`,
      // TODO: background image cross origin 문제 해결
      watermark: null,      
      id : this.article.board.id
    };
  },
  computed: {
    ...mapState('accountStore', ['userinfo'])
  },
  methods: {
    async setImage(event) {
      let URL = window.URL;
      this.backgroundImage = URL.createObjectURL(event.target.files[0]);
      await this.$refs.VueCanvasDrawing.redraw();
    },
    getCoordinate(event) {
      let coordinates = this.$refs.VueCanvasDrawing.getCoordinates(event);
      this.x = coordinates.x;
      this.y = coordinates.y;
    },
    // base64 to imagefile
    dataURItoBlob(dataURI) {
      var byteString;
      if (dataURI.split(',')[0].indexOf('base64') >= 0)
        byteString = atob(dataURI.split(',')[1]);
      else
        byteString = unescape(dataURI.split(',')[1]);
      var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
      var ia = new Uint8Array(byteString.length);
      for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
      }
      return new File([ia], this.userinfo.name +'의 숙제',{type:mimeString});
    },
    // imagefile로 변환한 파일을 첨부하여 숙제 제출
    saveImage() {
      const formData = new FormData()
        formData.append('userId', this.userinfo.userId);
        formData.append('content', '');
        formData.append('files', this.dataURItoBlob(this.image));
        axios.post(process.env.VUE_APP_API_URL+`/board/class/${this.id}/comment/`, formData, 
            { headers: {'Content-Type' : 'multipart/form-data;charset=utf-8' }})
        .then(()=> {
          this.$emit('submitted')
        })
    }
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Roboto:ital,wght@1,700&display=swap");
body {
  font-family: "Roboto", sans-serif;

}
.flex-row {
  display: flex;
  flex-direction: row;
}
.button-container {
  display: flex;
  flex-direction: row;
  position: fixed;
  bottom: 10px;
}
.button-container > * {
  margin-top: 15px;
  margin-right: 10px;
}
.canvasdrawing {
  border: 3px solid black;
  border-radius: 20px;
}
</style>
