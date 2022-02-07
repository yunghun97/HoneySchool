package com.ssafy.honeySchool.api.service;

import com.ssafy.honeySchool.api.request.LectureReq;
import com.ssafy.honeySchool.api.response.LectureRes;
import com.ssafy.honeySchool.common.util.ThrowingConsumer;
import com.ssafy.honeySchool.db.entity.Lecture;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service("lectureService")
public class LectureServiceImpl implements LectureService{

    WebClient webClient=WebClient.builder()
            .baseUrl("https://i6b201.p.ssafy.io")
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
    public LectureRes createLecture(LectureReq lectureReq,String header) {
        LectureRes result= webClient.post()
                .uri("/openvidu/api/sessions/")
                .header(HttpHeaders.AUTHORIZATION,header)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(lectureReq)
                .retrieve()
                .bodyToMono(LectureRes.class).block();

        return result;
    }


}
