package com.ssafy.honeySchool.api.service;

import com.ssafy.honeySchool.api.request.LectureReq;
import com.ssafy.honeySchool.api.response.LectureRes;
import com.ssafy.honeySchool.common.util.ThrowingConsumer;
import com.ssafy.honeySchool.db.entity.Lecture;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.net.http.HttpResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service("lectureService")
public class LectureServiceImpl implements LectureService{
    WebClient webClient=WebClient.builder()
            .baseUrl("https://i6b201.p.ssafy.io:443")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(
                    new ReactorClientHttpConnector(
                            HttpClient
                                    .create()
                                    .secure(
                                            ThrowingConsumer.unchecked(
                                                    sslContextSpec -> sslContextSpec.sslContext(
                                                            SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build()
                                                    )
                                            )
                                    ))).build();
    @Override
    public Mono<String> createLecture(LectureReq lectureReq,String header) {
        String input = "{\r\n"
                + "    \"mediaMode\": \"ROUTED\",\r\n"
                + "    \"recordingMode\": \"MANUAL\",\r\n"
                + "    \"customSessionId\": \""+lectureReq.getCustomSessionId()+"\",\r\n"
                + "    \"forcedVideoCodec\": \"VP8\",\r\n"
                + "    \"allowTranscoding\": false,\r\n"
                + "    \"defaultRecordingProperties\": {\r\n"
                + "        \"name\": \"MyRecording\",\r\n"
                + "        \"hasAudio\": true,\r\n"
                + "        \"hasVideo\": true,\r\n"
                + "        \"outputMode\": \"COMPOSED\",\r\n"
                + "        \"recordingLayout\": \"BEST_FIT\",\r\n"
                + "        \"resolution\": \"1280x720\",\r\n"
                + "        \"frameRate\": 25,\r\n"
                + "        \"shmSize\": 536870912,\r\n"
                + "        \"mediaNode\": {\r\n"
                + "            \"id\": \"media_i-0c58bcdd26l11d0sd\"\r\n"
                + "        }\r\n"
                + "    },\r\n"
                + "    \"mediaNode\": {\r\n"
                + "        \"id\": \"media_i-0c58bcdd26l11d0sd\"\r\n"
                + "    }\r\n"
                + "}";

        JSONParser parser = new JSONParser();
        JSONObject obj = null;

        try {
            obj = (JSONObject)parser.parse(input);
            System.out.println(obj.toJSONString());
            return webClient.post()
                    .uri("/openvidu/api/sessions/")
                    .header(HttpHeaders.AUTHORIZATION,header)
                    .bodyValue(obj)
                    .retrieve()
                    .bodyToMono(String.class);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Mono<String> searchAllLecture(String header) {
        return webClient.get()
                .uri("/openvidu/api/sessions/")
                .header(HttpHeaders.AUTHORIZATION, header)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> deleteLecture(String sessionId, String header) {
        return webClient.delete()
                .uri("/openvidu/api/sessions/"+sessionId)
                .header(HttpHeaders.AUTHORIZATION, header)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> connectLecture(String sessionId, String header){

        String input = "{\r\n"
                + "    \"type\": \"WEBRTC\",\r\n"
                + "    \"record\": true,\r\n"
                + "    \"role\": \"PUBLISHER\",\r\n"
                + "    \"kurentoOptions\": {\r\n"
                + "        \"videoMaxRecvBandwidth\": 1000,\r\n"
                + "        \"videoMinRecvBandwidth\": 300,\r\n"
                + "        \"videoMaxSendBandwidth\": 1000,\r\n"
                + "        \"videoMinSendBandwidth\": 300,\r\n"
                + "        \"allowedFilters\": [ \"GStreamerFilter\", \"ZBarFilter\" ]\r\n"
                + "    }\r\n"
                + "}";

        JSONParser parser = new JSONParser();
        JSONObject obj = null;

        try {
            System.out.println("진입");
            obj = (JSONObject)parser.parse(input);
            System.out.println(obj.toString());

            return webClient.post()
                    .uri("openvidu/api/sessions/"+sessionId+"/connection")
                    .header(HttpHeaders.AUTHORIZATION, header)
                    .bodyValue(obj)
                    .retrieve()
                    .bodyToMono(String.class);
        } catch (Exception e) {			//
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public HttpStatus disconnectLecture(String sessionId, String connectionId, String header) {
        webClient.delete()
                .uri("openvidu/api/sessions/"+sessionId+"/connection/"+connectionId)
                .header(HttpHeaders.AUTHORIZATION, header)
                .retrieve();
        return HttpStatus.OK;
    }


}
