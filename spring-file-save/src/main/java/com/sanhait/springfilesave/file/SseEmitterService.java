package com.sanhait.springfilesave.file;

import com.sanhait.springfilesave.file.dto.ReservationDto;
import com.sanhait.springfilesave.file.dto.RoomDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SseEmitterService {
    // thread-safe 한 컬렉션 객체로 sse emitter 객체를 관리해야 한다.
    private static final Map<String, SseEmitter> emitterReservationMap = new ConcurrentHashMap<>();
    private static final Map<String, SseEmitter> emitterRoomMap = new ConcurrentHashMap<>();
    private static final long TIMEOUT = 1000 * 60 * 10;
    private static final long RECONNECTION_TIMEOUT = 1000 * 3;

    public SseEmitter reservationSubscribe(String id) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);

        //연결 세션 timeout 이벤트 핸들러 등록
        emitter.onTimeout(() -> {
//            log.error("ERR id : {} {}", id, Thread.currentThread().getName());
            log.info("RSVN server sent event timed out : id={}", id);
            //onCompletion 핸들러 호출
            emitter.complete();
        });

        //에러 핸들러 등록
        emitter.onError(e -> {
//            log.error("ERR id : {} {}", id, Thread.currentThread().getName());
            log.info("RSVN server sent event error occurred : id={}, message={}", id, e.getMessage());
            //onCompletion 핸들러 호출
            emitter.complete();
        });

        //SSE complete 핸들러 등록
        emitter.onCompletion(() -> {
            log.info("RSVN count : {}", emitterReservationMap.size());
            if (emitterReservationMap.remove(id) != null) {
                log.info("RSVN server sent event removed in emitter cache: id={}", id);
            }

            log.error("RSVN disconnected by completed server sent event: id={}", id);
        });

        emitterReservationMap.put(id, emitter);

        //초기 연결시에 응답 데이터를 전송할 수도 있다.
        try {
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    //event 명 (event: event example)
                    .name("event example")
                    //event id (id: id-1) - 재연결시 클라이언트에서 `Last-Event-ID` 헤더에 마지막 event id 를 설정
                    .id(String.valueOf("id-1"))
                    //event data payload (data: SSE connected)
                    .data("SSE connected")
                    //SSE 연결이 끊어진 경우 재접속 하기까지 대기 시간 (retry: <RECONNECTION_TIMEOUT>)
                    .reconnectTime(RECONNECTION_TIMEOUT);
            emitter.send(event);
        } catch (IOException e) {
            log.error("RSVN failure send media position data, id={}, {}", id, e.getMessage());
        }
        return emitter;
    }

    public SseEmitter roomSubscribe(String id) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);

        //연결 세션 timeout 이벤트 핸들러 등록
        emitter.onTimeout(() -> {
            log.info("ROOM server sent event timed out : id={}", id);
            //onCompletion 핸들러 호출
            emitter.complete();
        });

        //에러 핸들러 등록
        emitter.onError(e -> {
            log.error("ROOM server sent event error occurred : id={}, message={}", id, e.getMessage());
            //onCompletion 핸들러 호출
            emitter.complete();
        });

        //SSE complete 핸들러 등록
        emitter.onCompletion(() -> {
            log.info("ROOM count : {}", emitterRoomMap.size());
            if (emitterRoomMap.remove(id) != null) {
                log.info("ROOM server sent event removed in emitter cache: id={}", id);
            }

            log.error("ROOM disconnected by completed server sent event: id={}", id);
        });

        emitterRoomMap.put(id, emitter);

        //초기 연결시에 응답 데이터를 전송할 수도 있다.
        try {
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    //event 명 (event: event example)
                    .name("event example")
                    //event id (id: id-1) - 재연결시 클라이언트에서 `Last-Event-ID` 헤더에 마지막 event id 를 설정
                    .id(String.valueOf("id-1"))
                    //event data payload (data: SSE connected)
                    .data("SSE connected")
                    //SSE 연결이 끊어진 경우 재접속 하기까지 대기 시간 (retry: <RECONNECTION_TIMEOUT>)
                    .reconnectTime(RECONNECTION_TIMEOUT);
            emitter.send(event);
        } catch (IOException e) {
            log.error("ROOM failure send media position data, id={}, {}", id, e.getMessage());
        }
        return emitter;
    }


    public static void broadcastReservation(ReservationDto eventPayload) {
        for (String id : emitterReservationMap.keySet()) {
            SseEmitter emitter = emitterReservationMap.get(id);
            try {
                emitter.send(SseEmitter.event()
                        .name("broadcast event")
                        .id("broadcast event 1")
                        .reconnectTime(RECONNECTION_TIMEOUT)
                        .data(eventPayload, MediaType.APPLICATION_JSON)
                );
                log.info("RSVN sended notification, id={}, payload={}", id, eventPayload);
            } catch (IOException e) {
                log.error("fail to send emitter id={}, {}", id, e.getMessage());
            }
        }
    }

    public static void broadcastRoom(RoomDto eventPayload) {
        for (String id : emitterRoomMap.keySet()) {
            SseEmitter emitter = emitterRoomMap.get(id);
            try {
                emitter.send(SseEmitter.event()
                        .name("broadcast event")
                        .id("broadcast event 1")
                        .reconnectTime(RECONNECTION_TIMEOUT)
                        .data(eventPayload, MediaType.APPLICATION_JSON)
                );
                log.info("ROOM sended notification, id={}, payload={}", id, eventPayload);
            } catch (IOException e) {
                log.error("ROOM fail to send emitter id={}, {}", id, e.getMessage());
            }
        }
    }
}