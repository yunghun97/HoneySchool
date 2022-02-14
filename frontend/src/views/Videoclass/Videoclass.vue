<template>
  <div id="main-container" class="container">
    <div id="join" v-if="!sessionCamera">
      <div id="img-div">
        <!-- <img src="resources/images/openvidu_grey_bg_transp_cropped.png" /> -->
      </div>
      <div id="join-dialog" class="jumbotron vertical-center">
        <h1>{{ $route.params.UserName }}</h1>
        <h1>{{ $route.params.SessionId }}</h1>
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
            <button class="btn btn-lg btn-info" @click="searchAllSession()">
              검색!
            </button>
          </p>
        </div>
      </div>
    </div>

    <div id="session" v-if="sessionCamera">
      <div id="session-header">
        <h1 id="session-title">{{ ClassName }}</h1>
        <input
          class="btn btn-large btn-primary"
          type="button"
          id="buttonScreenShare"
          @click="publishScreenShare"
          value="Screen share"
        />
        <input
          class="btn btn-large btn-primary"
          type="button"
          id="fullScreen"
          @click="fullScreen"
          value="Full screen"
        />
        <input
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="Leave session"
        />
        <input
          type="button"
          class="btn btn-large btn-success"
          id="buttonQuiz"
          data-bs-toggle="modal"
          data-bs-target="#sendquiz"
          data-bs-whatever="@mdo"
          value="퀴즈내기"
        />
      </div>
      <div class="row">
        <div class="col-md-9">
          <div class="row">
            <div id="main-video" class="col-md-9">
              <user-video :stream-manager="mainStreamManager" id="mainVideoElement" />
            </div>
            <div class="col-md-3">
              <div id="container-cameras" class="row panel panel-default">
                <p class="panel-heading">User Cameras</p>
                <user-video
                  :stream-manager="publisherCamera"
                  @click="updateMainVideoStreamManager(publisherCamera)"
                />
                <p>Subscribers Camera</p>
                <div
                  v-for="sub in subscribersCamera"
                  :key="sub.stream.connection.connectionId"
                  class="subContainer"
                >
                  <user-video
                    :stream-manager="sub"
                    @click="updateMainVideoStreamManager(sub)"
                  />
                  <div class="iconsOnVideo">
                    <!-- 손들기 icon-->
                    <img
                      src="@/assets/videoclass/hand.png"
                      alt="손들기"
                      v-if="sub.raisehand"
                      style="width: 30px; height: 30px"
                      @click="handDownThisStudent(sub.stream.connection)"
                    />
                    <!-- mic icon -->
                    <span>
                      <fa
                        icon="microphone-slash"
                        class="fontawesome"
                        v-if="sub.muted"
                        @click="changeMuteThisStudent(sub.stream.connection)"
                      ></fa>
                      <fa
                        icon="microphone"
                        class="fontawesome-active"
                        v-else
                        @click="changeMuteThisStudent(sub.stream.connection)"
                      ></fa>
                    </span>
                    <!-- 자리비움 icon -->
                    <img
                      src="@/assets/videoclass/clock.png"
                      alt="자리비움"
                      v-if="sub.left"
                      style="width: 30px; height: 30px"
                    />
                  </div>
                </div>
              </div>
              <div id="container-screens" class="row panel panel-default">
                <p class="panel-heading">User Screens</p>
                <user-screen
                  :stream-manager="publisherScreen"
                  @click="updateMainVideoStreamManagerScreen(publisherScreen)"
                />
                <!-- 전체 참여인원의 화면공유 화면 (필요시 활성화) -->
                <!-- <p>Subscribers Screen</p>
                <div
                  v-for="sub in subscribersScreen"
                  :key="sub.stream.connection.connectionId"
                  class="subContainer"
                >
                  <user-screen
                    :stream-manager="sub"
                    @click="updateMainVideoStreamManagerScreen(sub)"
                  />
                </div> -->
              </div>
            </div>
          </div>
          <div class="navbar">
            <!-- 손들기 btn -->
            <div class="nav-cont" v-if="raisehand" @click="handDown()">
              <span class="main-btn">
                <fa icon="hand-paper" class="fontawesome-active"></fa>
              </span>
              <p>손 내리기</p>
            </div>
            <div class="nav-cont" v-else @click="raiseHand()">
              <span class="main-btn">
                <fa icon="hand-paper" class="fontawesome"></fa>
              </span>
              <p>손 들기</p>
            </div>
            <!-- 음소거 btn -->
            <div class="nav-cont" v-if="muted" @click="changeMuteStatus">
              <span class="main-btn">
                <fa icon="microphone-slash" class="fontawesome"></fa>
              </span>
              <p>마이크 켜기</p>
            </div>
            <div class="nav-cont" v-else @click="changeMuteStatus">
              <span class="main-btn">
                <fa icon="microphone" class="fontawesome-active"></fa>
              </span>
              <p>마이크 끄기</p>
            </div>
            <!-- 자리비움 btn -->
            <div class="nav-cont" v-if="left" @click="alarmReturn">
              <span class="main-btn">
                <fa icon="user-clock" class="fontawesome-active"></fa>
              </span>
              <p>자리 돌아오기</p>
            </div>
            <div class="nav-cont" v-else @click="alarmLeft">
              <span class="main-btn">
                <fa icon="user-clock" class="fontawesome"></fa>
              </span>
              <p>자리 비우기</p>
            </div>
            <!-- 퀴즈 링크 btn -->
            <div
              class="nav-cont"
              data-bs-toggle="modal"
              data-bs-target="#recievedquiz"
            >
              <span class="main-btn">
                <fa icon="smile" class="fontawesome"></fa>
              </span>
              <p>퀴즈 풀기</p>
            </div>
            <!-- 방 나가기 btn -->
            <div class="nav-cont" @click="leaveSession">
              <span class="main-btn">
                <fa icon="sign-out-alt" class="fontawesome"></fa>
              </span>
              <p>나가기</p>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <p>유저 상태목록</p>
          <li v-for="(value, index) in participants" v-bind:key="index">
            <div>{{ value }}</div>
          </li>
        </div>
      </div>
    </div>
    <!-- modal quiz 제출 -->
    <div
      class="modal fade"
      id="sendquiz"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">
              우리반 학생들에게 공유 할 퀴즈를 작성해주세요
            </h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <form>
              <div class="mb-3">
                <label for="message-text" class="col-form-label"
                  >퀴즈 내용</label
                >
                <textarea
                  class="form-control"
                  id="message-text"
                  v-model="quizContent"
                ></textarea>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-primary"
              @click="sendQuiz"
              data-bs-dismiss="modal"
            >
              전송
            </button>
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              닫기
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- modal quiz 풀기 -->
    <div
      class="modal fade"
      id="recievedquiz"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">퀴즈!!</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <p>{{ quizReceived }}</p>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
          </div>
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

const OPENVIDU_SERVER_URL = "https://i6b201.p.ssafy.io:5443";
const OPENVIDU_SERVER_SECRET = "ssafy";

export default {
  name: "App",

  components: {
    UserVideo,
    UserScreen,
  },

  props: {
    UserName: {
      type: String,
      default: "",
    },
    SessionId: {
      type: String,
      default: "",
    },
    ClassName: {
      type: String,
      default: "",
    },
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

      myConnectionId: "",
      raisehand: false,
      screensharing: false,
      muted: true,
      left: false,
    
      quizContent: "",
      quizReceived: "아직 도착한 퀴즈가 없습니다.",

      participants: [],
    };
  },

  created() {
    console.log(this.UserName);
    console.log(this.SessionId);
    this.joinSession();
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
      // 새로운 카메라 타입을 받았을 때
      this.sessionCamera.on("streamCreated", ({ stream }) => {
        if (stream.typeOfVideo == "CAMERA") {
          console.log("카메라 타입");
          console.log(stream.typeOfVideo);
          const subscriberCamera = this.sessionCamera.subscribe(stream);
          subscriberCamera.raisehand = false;
          subscriberCamera.muted = true;
          subscriberCamera.left = false;
          this.subscribersCamera.push(subscriberCamera);
        } else if (stream.typeOfVideo == "SCREEN") {
          console.log("스크린 타입");
          console.log(stream.typeOfVideo);
          const subscriberScreen = this.sessionScreen.subscribe(stream);
          console.log(subscriberScreen.data);
          this.subscribersScreen.push(subscriberScreen);
        }
        console.log("사람입장");
        console.log(stream.session.remoteConnections);
        this.updateUserList(stream.session.remoteConnections);
      });

      // 스크린 타입의 비디오 추가
      this.sessionScreen.on("streamCreated", ({ stream }) => {
        if (stream.typeOfVideo == "SCREEN") {
          console.log("스크린 타입");
          console.log(stream.typeOfVideo);
          const subscriberScreen = this.sessionScreen.subscribe(stream);
          console.log(subscriberScreen.data);
          this.subscribersScreen.push(subscriberScreen);
        }
          console.log("사람입장");
          this.UserList(); 
      });
      
      // On every Stream destroyed...
      this.sessionCamera.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribersCamera.indexOf(stream.streamManager, 0);

        // 사용자 목록 업데이트
        if (index >= 0) {
          this.subscribersCamera.splice(index, 1);
        }
        console.log("사람 퇴장");
        console.log(stream.session.remoteConnections);
        this.updateUserList(stream.session.remoteConnections);
      });

      // On every asynchronous exception...
      this.sessionCamera.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // 손들기 시그널
      this.sessionCamera.on("signal:handstatus", (event) => {
        this.subscribersCamera.forEach((sub) => {
          if (event.from.connectionId === sub.stream.connection.connectionId) {
            sub.raisehand = event.data === "true" ? true : false;
          }
        });
      });
      // 선생님이 학생 손 내리기 시그널
      this.sessionCamera.on("signal:handStudent", () => {
        this.handDown();
      });

      // mute 시그널
      this.sessionCamera.on("signal:muteStatus", (event) => {
        this.subscribersCamera.forEach((sub) => {
          if (event.from.connectionId === sub.stream.connection.connectionId) {
            console.log("here!", event.data);
            sub.muted = event.data === "true" ? true : false;
          }
        });
      });
      // 선생님이 학생 mute 시그널
      this.sessionCamera.on("signal:muteStudent", () => {
        this.changeMuteStatus();
      });
      // 학생이 선생님께 자리 비움 시그널
      this.sessionCamera.on("signal:leftStudent", (event) => {
        this.subscribersCamera.forEach((sub) => {
          if (event.from.connectionId === sub.stream.connection.connectionId) {
            sub.left = true;
            console.log("sub", sub.left);
          }
        });
        const studentname = JSON.parse(event.from.data).clientData;
        alert(`${studentname} 학생이 자리를 비웠습니다.`);
      });
      // 학생이 선생님께 자리 돌아옴 시그널
      this.sessionCamera.on("signal:returnStudent", (event) => {
        this.subscribersCamera.forEach((sub) => {
          if (event.from.connectionId === sub.stream.connection.connectionId) {
            sub.left = false;
          }
        });
        const studentname = JSON.parse(event.from.data).clientData;
        alert(`${studentname} 학생이 자리에 돌아왔습니다.`);
      });
      // quiz
      this.sessionCamera.on("signal:quiz", (event) => {
        this.quizReceived = event.data;
      });
      // mainstream 화면 전체 바꾸기
      this.sessionCamera.on("signal:updateMainstream", (event) => {
        this.subscribersCamera.forEach((sub) => {
          if (event.data === sub.stream.connection.connectionId) {
            this.changeMainVideo(sub);
          }
        });
        if (
          event.data === this.publisherCamera.stream.connection.connectionId
        ) {
          this.changeMainVideo(this.publisherCamera);
        }
      });
      // mainstream 스크린 화면 전체 바꾸기
      this.sessionScreen.on("signal:updateMainstream", (event) => {
        this.subscribersScreen.forEach((sub) => {
          if (event.data === sub.stream.connection.connectionId) {
            this.changeMainVideo(sub);
          }
        });
        if (
          event.data === this.publisherScreen.stream.connection.connectionId
        ) {
          this.changeMainVideo(this.publisherScreen);
        }
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      // Change: mySessionId => SessionId, myUserName => UserName
      this.getToken(this.SessionId).then((token) => {
        this.sessionCamera
          .connect(token, { clientData: this.UserName })
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

            this.mainStreamManager = publisher;
            this.publisherCamera = publisher;

            // --- Publish your stream ---

            this.sessionCamera.publish(this.publisherCamera);
            console.log("확인");
            console.log(this.subscribersCamera);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });
      // 화면 공유 생성
      this.getToken(this.SessionId).then((tokenScreen) => {
        // Create a token for screen share
        this.sessionScreen
          .connect(tokenScreen, { clientData: this.UserName + "_화면 공유" })
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
        publishAudio: false,
        insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
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
        this.updateMainVideoStreamManagerScreen(this.publisherScreen);
      });

      this.publisherScreen.on("videoElementCreated", ({ stream }) => {
        const subscriberScreen = this.sessionScreen.subscribe(stream);
        subscriberScreen.event.element["muted"] = true;
        this.subscribersScreen.push(subscriberScreen);
        this.updateMainVideoStreamManagerScreen(this.publisherScreen);
      });

      this.publisherScreen.once("accessDenied", (event) => {
        console.error("Screen Share: Access Denied");
      });
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      const headers = {
        Authorization: "OPENVIDUAPP:ssafy",
      };
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      axios
        .delete(
          process.env.VUE_APP_API_URL +
            "/lecture/connect?sessionId=" +
            this.SessionId +
            "&connectionId=" +
            this.connectionId,
          { headers }
        )
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          alert("세션 나가기 오류");
        });
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

      this.connectionId = "";

      window.removeEventListener("beforeunload", this.leaveSession);
      this.$router.push({ name: "About" });
    },

    updateMainVideoStreamManager(camera) {
      this.sessionCamera.signal({
        data: camera.stream.connection.connectionId,
        to: [],
        type: "updateMainstream",
      });
    },

    updateMainVideoStreamManagerScreen(screen) {
      this.sessionScreen.signal({
        data: screen.stream.connection.connectionId,
        to: [],
        type: "updateMainstream",
      });
    },

    changeMainVideo(updatedMain) {
      if (this.mainStreamManager === updatedMain) return;
      this.mainStreamManager = updatedMain;
    },

    fullScreen() {
      var elem = document.getElementById("mainVideoElement");
      if (elem.requestFullscreen()) {
        elem.requestFullscreen();
      }
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

    getToken(SessionId) {
      return this.createSession(SessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
    createSession(sessionId) {
      const headers = {
        Authorization: "OPENVIDUAPP:ssafy",
      };
      return new Promise((resolve, reject) => {
        axios
          .post(
            process.env.VUE_APP_API_URL + "/lecture/",
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              headers,
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error) {
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
    // 해당 세션 정보 가져오기
    searchSession() {
      console.log("검색 실행");
      axios
        .get(
          process.env.VUE_APP_API_URL +
            "/lecture/session?sessionId=" +
            this.SessionId
        )
        .then((response) => {
          console.log(response.data.connections.content);
          return response;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 활성화된 모든 세션 검색하기
    searchAllSession() {
      return new Promise((resolve, reject) => {
        axios
          .get(process.env.VUE_APP_API_URL + "/lecture")
          .then((response) => {
            console.log(response);
          })
          .catch((error) => {
            console.log(error);
          });
      });
    },

    // 연결하기
    joinConnection() {
      const headers = {
        Authorization: "OPENVIDUAPP:ssafy",
      };
      return new Promise((resolve, reject) => {
        axios
          .post(
            process.env.VUE_APP_API_URL + "/lecture/connect",
            {
              customSessionId: this.SessionId,
            },
            { headers }
          )
          .then((response) => {
            this.connectionId = response.data.id;
            resolve(response.data.token);
          })
          .catch((error) => reject(error.response));
      });
    },
    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
    createToken(sessionId) {
      return this.joinConnection(sessionId);
    },

    // 손들기 function
    raiseHand() {
      this.raisehand = !this.raisehand;
      this.sessionCamera.signal({
        data: `${this.raisehand}`,
        to: [],
        type: "handstatus",
      });
      this.publisherCamera.stream.applyFilter("GStreamerFilter", {
        command: "videobox fill=red top=-10 bottom=-10 left=-10 right=-10",
      });
    },
    // 본인 손 내리기
    handDown() {
      this.raisehand = !this.raisehand;
      this.sessionCamera.signal({
        data: `${this.raisehand}`,
        to: [],
        type: "handstatus",
      });
      this.publisherCamera.stream
        .removeFilter()
        .then(() => {
          console.log("Filter removed");
        })
        .catch((error) => {
          console.error(error);
        });
    },
    // 선생님이 학생 손 내리기
    handDownThisStudent(student) {
      this.sessionCamera.signal({
        data: "선생님이 확인했어요. 손 내립니다.",
        to: [student],
        type: "handStudent",
      });
    },
    // 본인 mute 조절
    changeMuteStatus() {
      this.publisherCamera.publishAudio(this.muted); // true to unmute the audio track, false to mute it
      this.muted = !this.muted;
      this.sessionCamera.signal({
        data: `${this.muted}`,
        to: [],
        type: "muteStatus",
      });
    },

    // 선생님이 학생 mute 조절
    changeMuteThisStudent(student) {
      this.sessionCamera.signal({
        data: "mic 조정합니다.",
        to: [student],
        type: "muteStudent",
      });
    },
    // 학생이 선생님께 자리비움 알림
    alarmLeft() {
      this.left = true;
      this.sessionCamera.signal({
        data: "자리비움 알림",
        // TODO: 선생님에게 보내기!!!!
        to: [this.subscribersCamera[0].stream.connection],
        type: "leftStudent",
      });
    },
    alarmReturn() {
      this.left = false;
      this.sessionCamera.signal({
        data: "자리 돌아옴 알림",
        // TODO: 선생님에게 보내기!!!!
        to: [this.subscribersCamera[0].stream.connection],
        type: "returnStudent",
      });
    },
    sendQuiz() {
      if (this.quizContent) {
        this.sessionCamera.signal({
          data: this.quizContent,
          to: [],
          type: "quiz",
        });
        this.quizContent = "";
      }
    },
    updateUserList(UserList) {
      this.participants = [this.UserName];
      for (let i of UserList.keys()) {
        this.participants.push(JSON.parse(UserList.get(i).data).clientData);
      }
      console.log(this.participants);
    },
  },
};
</script>

<style scoped>
#buttonQuiz {
  float: right;
  margin-top: 20px;
  margin-right: 15px;
}
.navbar {
  justify-content: space-evenly;
  background-color: #ffeda9;
  overflow: hidden;
  position: fixed;
  bottom: 0;
  width: 80%;
  height: 110px;
  border-radius: 20px;
  padding: 0;
}
.nav-cont {
  display: block;
  text-align: center;
}
.nav-cont p {
  margin-bottom: 0;
  margin-top: 10px;
}
.main-btn {
  cursor: pointer;
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
  color: #f52532;
}
.subContainer {
  position: relative;
}
.subVideo {
  display: block;
  width: 100%;
}
.iconsOnVideo {
  position: absolute;
  left: 40%;
  bottom: 25%;
  /* bottom: 3px; */
  /* left: 20%; */
}
</style>
