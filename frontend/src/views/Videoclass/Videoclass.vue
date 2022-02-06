<template>
  <div id="main-container" class="container">
    <div id="join" v-if="!sessionCamera">
      <div id="img-div">
        <!-- <img src="resources/images/openvidu_grey_bg_transp_cropped.png" /> -->
      </div>
      <div id="join-dialog" class="jumbotron vertical-center">
        <h1>Join a video session</h1>
        <div class="form-group">
          <p>
            <label>Participant</label>
            <input
              v-model="myUserName"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p>
            <label>세션</label>
            <input
              v-model="mySessionId"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p class="text-center">
            <button class="btn btn-lg btn-success" @click="joinSession()">
              참석!
            </button>
          </p>
        </div>
      </div>
    </div>

    <div id="session" v-if="sessionCamera">
      <div id="session-header">
        <h1 id="session-title">{{ mySessionId }}</h1>
        <input
          class="btn btn-large btn-primary"
          type="button"
          id="buttonScreenShare"
          @click="publishScreenShare"
          value="Screen share"
        />
        <input
          class="btn btn-large btn-warning"
          type="button"
          @click="videofilter"
          value="Video filter"
        />
        <input
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="Leave session"
        />
      </div>
      <div class="row">
        <div class="col-md-9">
          <div class="row">
            <div id="main-video" class="col-md-9">
              <user-screen :stream-manager="mainStreamManager" />
            </div>
            <div class="col-md-3">
              <div id="container-cameras" class="row panel panel-default">
                <p class="panel-heading">User Cameras</p>
                <user-video
                  :stream-manager="publisherCamera"
                  @click="updateMainVideoStreamManager(publisherCamera)"
                />
                <p>subscribersCamera</p>
                <user-video
                  v-for="sub in subscribersCamera"
                  :key="sub.stream.connection.connectionId"
                  :stream-manager="sub"
                  @click="updateMainVideoStreamManager(sub)"
                />
              </div>
              <div id="container-screens" class="row panel panel-default">
                <p class="panel-heading">User Screens</p>
                <user-screen
                  :stream-manager="publisherScreen"
                  @click="updateMainVideoStreamManager(publisherScreen)"
                />
                <user-screen
                  v-for="(sub, i) in subscribersScreen"
                  :key="i"
                  :stream-manager="sub"
                  @click="updateMainVideoStreamManager(sub)"
                />
              </div>
            </div>
          </div>
          <div class="row">
            <p>버튼 목록</p>
          </div>
        </div>
        <div class="col-md-3">
          <p>유저 상태목록</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./UserVideo";
import UserScreen from "./UserScreen";

axios.defaults.headers.post["Content-Type"] = "application/json";

// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
// const OPENVIDU_SERVER_SECRET = "MY_SECRET";

const OPENVIDU_SERVER_URL = "https://i6b201.p.ssafy.io:443";
const OPENVIDU_SERVER_SECRET = "ssafy";

export default {
  name: "App",

  components: {
    UserVideo,
    UserScreen,
  },

  data() {
    return {
      OVCamera: undefined,
      OVScreen: undefined,
      sessionCamera: undefined,
      sessionScreen: undefined,
      mainStreamManager: undefined,
      publisherCamera: undefined,
      publisherScreen: undefined,
      subscribersCamera: [],
      subscribersScreen: [],

      mySessionId: "SessionA",
      myUserName: "Participant" + Math.floor(Math.random() * 100),
      screensharing: false,
    };
  },

  methods: {
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OVCamera = new OpenVidu();
      this.OVScreen = new OpenVidu();

      // --- Init a session ---
      this.sessionCamera = this.OVCamera.initSession();
      this.sessionScreen = this.OVScreen.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.sessionCamera.on("streamCreated", ({ stream }) => {
        if (stream.typeOfVideo == "CAMERA") {
          console.log("카메라 타입");
          console.log("유저 추가");
          console.log(stream.typeOfVideo);
          const subscriberCamera = this.sessionCamera.subscribe(stream);
          this.subscribersCamera.push(subscriberCamera);
        }
      });

      this.sessionScreen.on("streamCreated", ({ stream }) => {
        if (stream.typeOfVideo == "SCREEN") {
          console.log("스크린 타입");
          console.log(stream.typeOfVideo);
          const subscriberScreen = this.sessionScreen.subscribe(stream);
          console.log(subscriberScreen.data);
          this.subscribersScreen.push(subscriberScreen);
        }
      });

      // On every Stream destroyed...
      this.sessionCamera.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribersCamera.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribersCamera.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.sessionCamera.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((token) => {
        this.sessionCamera
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OVCamera.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisherCamera = publisher;

            // --- Publish your stream ---

            this.sessionCamera.publish(this.publisherCamera);
            console.log("확인")
            console.log(this.subscribersCamera)
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      this.getToken(this.mySessionId).then((tokenScreen) => {
        // Create a token for screen share
        this.sessionScreen
          .connect(tokenScreen, { clientData: this.myUserName })
          .then(() => {
            console.log("Session screen connected");
          })
          .catch((error) => {
            console.warn(
              "There was an error connecting to the session for screen share:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    publishScreenShare() {
      // --- 9.1) To create a publisherScreen it is very important that the property 'videoSource' is set to 'screen'
      let publisher = this.OVScreen.initPublisher(undefined, {
        videoSource: "screen",
      });
      this.publisherScreen = publisher;
      // --- 9.2) If the user grants access to the screen share function, publish the screen stream
      this.publisherScreen.once("accessAllowed", (event) => {
        this.screensharing = true;
        // It is very important to define what to do when the stream ends.
        this.publisherScreen.stream
          .getMediaStream()
          .getVideoTracks()[0]
          .addEventListener("ended", () => {
            console.log('User pressed the "Stop sharing" button');
            this.sessionScreen.unpublish(this.publisherScreen);
            this.screensharing = false;
          });
        this.sessionScreen.publish(this.publisherScreen);
        // Screen sharing 시 메인 stream으로 자동 이동
        this.mainStreamManager = this.publisherScreen;
      });

      this.publisherScreen.on("videoElementCreated", ({ stream }) => {
        const subscriberScreen = this.sessionScreen.subscribe(stream);
        subscriberScreen.event.element["muted"] = true;
        this.subscribersScreen.push(subscriberScreen);
      });

      this.publisherScreen.once("accessDenied", (event) => {
        console.error("Screen Share: Access Denied");
      });
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.sessionCamera) {
        this.sessionCamera.disconnect();
        this.sessionScreen.disconnect();
      }

      this.OVCamera = undefined;
      this.OVScreen = undefined;
      this.sessionCamera = undefined;
      this.sessionScreen = undefined;
      this.mainStreamManager = undefined;
      this.publisherCamera = undefined;
      this.publisherScreen = undefined;
      this.subscribersCamera = [];
      this.subscribersScreen = [];
      this.screensharing = false;

      window.removeEventListener("beforeunload", this.leaveSession);
      this.$router.push({ name: "About" });
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      console.log("Update");
      this.mainStreamManager = stream;
      console.log(this.mainStreamManager);
    },

    // videofilter() {
    //   this.mainStreamManager.stream
    //     .applyFilter("GStreamerFilter", { "command": "videobalance saturation=0.0" })
    //     .then(() => {
    //       console.log("Video rotated!");
    //     })
    //     .catch((error) => {
    //       console.error(error);
    //     });
    // },
    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
  },
};
</script>

<style scoped></style>
