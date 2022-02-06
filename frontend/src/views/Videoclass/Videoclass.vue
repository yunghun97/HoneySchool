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
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="Leave session"
        />
      </div>
      <div class="row">
        <div id="main-video" class="col-md-6">
          <user-video :stream-manager="mainStreamManager" />
        </div>
        <div class="col-md-6">
          <div id="container-cameras" class="row panel panel-default">
            <p class="panel-heading">User Cameras</p>
            <user-video
              :stream-manager="publisherCamera"
              @click="updateMainVideoStreamManager(publisherCamera)"
            />

            <div
              v-for="sub in subscribersCamera"
              :key="sub.stream.connection.connectionId"
              style="width:100%"
            >
              <user-video
                :stream-manager="sub"
                @click="updateMainVideoStreamManager(sub)"
              />
              <!-- raise hand icon-->
              <img src="@/assets/videoclass/hand.png" alt="손들기" v-if="sub.raisehand" style="width:30px; height:30px" @click="handDownThisStudent(sub.stream.connection)">
              <!-- mic icon -->
              
              <p v-if="sub.muted" @click="changeMuteThisStudent(sub.stream.connection)">마이크 off</p>
              <p v-else @click="changeMuteThisStudent(sub.stream.connection)">마이크 on</p>
            </div>

          </div>
          <div class="row panel panel-default">
            <p class="panel-heading">User Screens</p>
            <user-video
              :stream-manager="publisherScreen"
              @click="updateMainVideoStreamManager(publisherScreen)"
            />
            <user-video
              v-for="sub in subscribersScreen"
              :key="sub.stream.connection.connectionId"
              :stream-manager="sub"
              @click="updateMainVideoStreamManager(sub)"
            />
          </div>
        </div>
      </div>

    <!-- 하단 nav -->
    </div>
      <div class="navbar">
         <!-- 손들기 btn -->
        <div class="nav-cont" v-if="raisehand" @click="handDown()">
          <span class="main-btn">   
            <fa icon="hand-paper" class="fontawesome-active"></fa>
          </span>
          <p style="margin-bottom:0; margin-top:10px;">손 내리기</p>
        </div>
        <div class="nav-cont" v-else @click="raiseHand()">
          <span class="main-btn">   
            <fa icon="hand-paper" class="fontawesome"></fa>
          </span>
          <p style="margin-bottom:0; margin-top:10px;">손 들기</p>
        </div>
        <!-- 음소거 btn -->
        <div class="nav-cont" v-if="muted" @click="changeMuteStatus">
        <span class="main-btn">   
          <fa icon="microphone-slash" class="fontawesome" ></fa>
        </span>
        <p style="margin-bottom:0; margin-top:10px;">마이크 켜기</p>
      </div>
      <div class="nav-cont" v-else @click="changeMuteStatus">
        <span class="main-btn">   
          <fa icon="microphone" class="fontawesome-active"></fa>
        </span>
        <p style="margin-bottom:0; margin-top:10px;">마이크 끄기</p>
      </div>
      <!-- 자리비움 btn -->
      <div class="nav-cont" v-if="left">
        <span class="main-btn">   
          <fa icon="user-clock" class="fontawesome-active"></fa>
        </span>
        <p style="margin-bottom:0; margin-top:10px;">자리 비우기 취소</p>
      </div>
      <div class="nav-cont" v-else>
        <span class="main-btn">   
          <fa icon="user-clock" class="fontawesome"></fa>
        </span>
        <p style="margin-bottom:0; margin-top:10px;">자리 비우기</p>
      </div>
      <!-- 퀴즈 링크 btn -->
      <div class="nav-cont">
        <span class="main-btn">   
          <fa icon="smile" class="fontawesome-active" ></fa>
        </span>
        <p style="margin-bottom:0; margin-top:10px;">퀴즈 풀기</p>
      </div>
      <!-- 방 나가기 btn -->
      <div class="nav-cont" @click="leaveSession">
        <span class="main-btn">   
          <fa icon="sign-out-alt" class="fontawesome"></fa>
        </span>
        <p style="margin-bottom:0; margin-top:10px;">나가기</p>
      </div>

  </div>


</div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./UserVideo";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "App",

  components: {
    UserVideo,
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

      raisehand : false,
      screensharing: false,
      muted: true,
      left: false,
      quize: false,
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
          console.log(stream.typeOfVideo);
          const subscriberCamera = this.sessionCamera.subscribe(stream);
          subscriberCamera.raisehand = false;
          subscriberCamera.muted = true;
          this.subscribersCamera.push(subscriberCamera);
        }
      });

      this.sessionScreen.on("streamCreated", ({ stream }) => {
        if (stream.typeOfVideo == "SCREEN") {
          console.log("스크린 타입");
          console.log(stream.typeOfVideo);
          const subscriberScreen = this.sessionScreen.subscribe(stream);
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
      
      // 손들기 시그널
      this.sessionCamera.on('signal:handstatus', (event)=>{				
				this.subscribersCamera.forEach((sub)=>{
					if(event.from.connectionId === sub.stream.connection.connectionId) {
						sub.raisehand = event.data==='true'?true:false;
					}
				})
			});
      // 선생님이 학생 손 내리기 시그널 
      this.sessionCamera.on('signal:handStudent', () => {
        this.handDown();
      });

      // mute 시그널
      this.sessionCamera.on('signal:muteStatus', (event) => {
        this.subscribersCamera.forEach((sub) => {
          if(event.from.connectionId === sub.stream.connection.connectionId) {
            console.log('here!', event.data)
            sub.muted = event.data==='true'?true:false;
          }
        })
      });
      // muteStudent
      this.sessionCamera.on('signal:muteStudent', () => {
        this.changeMuteStatus();
      });


      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((token) => {
        this.sessionCamera
          .connect(token, { clientData: this.myUserName,})
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OVCamera.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: false, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            // publisher.on("videoElementCreated", (event) => {
            //   this.initMainVideo(event.element, this.myUserName);
            //   this.appendUserData(event.element, this.myUserName);
            //   event.element["muted"] = true;
            // });

            this.mainStreamManager = publisher;
            this.publisherCamera = publisher;

            // --- Publish your stream ---

            this.sessionCamera.publish(this.publisherCamera);
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
      let publisher = this.OVScreen.initPublisher("container-screens", {
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
      });

      this.publisherScreen.on("videoElementCreated", ({ stream }) => {
        const subscriberScreen = this.sessionScreen.subscribe(stream);
        this.subscribersScreen.event.element["muted"] = true;
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
      console.log(stream);
      this.mainStreamManager = stream;
    },
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
      const jsondata = {
        "type": "WEBRTC",
        //"data": "user_data",
        "role": "PUBLISHER",
        "kurentoOptions": {
            "allowedFilters": ["GStreamerFilter", "FaceOverlayFilter"]
    }
      }
      return new Promise((resolve, reject) => {
        axios({
          method: 'post',
          url:  `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, 
          data: JSON.stringify(jsondata),
          auth: {
            username: "OPENVIDUAPP",
            password: OPENVIDU_SERVER_SECRET,
          },
        })
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
    
    // 손들기 function
    raiseHand() {
      this.raisehand = !this.raisehand
      this.sessionCamera.signal({
        data: `${this.raisehand}`,
        to : [],
        type: 'handstatus'
      })
      this.publisherCamera.stream.applyFilter("GStreamerFilter", { command: "videobox fill=red top=-10 bottom=-10 left=-10 right=-10" })
    },
    // 본인 손 내리기 
    handDown() {
      this.raisehand = !this.raisehand
      this.sessionCamera.signal({
        data: `${this.raisehand}`,
        to : [],
        type: 'handstatus'
      })
      this.publisherCamera.stream.removeFilter()
        .then(() => {
            console.log("Filter removed");

        })
        .catch(error => {
            console.error(error);
        });

    },
    // 선생님이 학생 손 내리기
    handDownThisStudent(student) {
      this.sessionCamera.signal({
        data: "선생님이 확인했어요. 손 내립니다.",
        to:[student],
        type: 'handStudent'
      })
    },
    // 본인 mute 조절
    changeMuteStatus() {
      this.publisherCamera.publishAudio(this.muted); // true to unmute the audio track, false to mute it
      this.muted = !this.muted;
      this.sessionCamera.signal({
        data: `${this.muted}`,
        to:[],
        type: 'muteStatus'
      })
    },

    // 선생님이 학생 mute 조절
    changeMuteThisStudent(student) {
      this.sessionCamera.signal({
        data: "mic 조정합니다.",
        to:[student],
        type: 'muteStudent'
      })
    }

  },
};
</script>

<style scoped>
.navbar {
  justify-content: space-evenly;
  background-color: #FFEDA9;
  overflow: hidden;
  position: fixed;
  bottom: 0;
  width: 80%;
  height: 110px;
  border-radius: 20px;
  padding: 0;
}
.nav-cont{
  float: left;
  display: block;
  text-align: center;
}
.main-btn {
  cursor:pointer;
  border: 5px solid #ffff;
  background: #ffff;
  border-radius: 100%;
  padding: 15px 10px;
  margin-right: 15px;
}
.fontawesome {
  width: 30px;
  height: 30px;
}
.fontawesome-active {
  width: 30px;
  height: 30px;
  color: #F52532;
}

</style>
