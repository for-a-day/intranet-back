package com.server.intranet.approval.service.impl;

import com.server.intranet.approval.dto.NotificationResponse;
import com.server.intranet.approval.dto.NotificationsResponse;
import com.server.intranet.approval.entity.Notification;
import com.server.intranet.approval.repository.EmitterRepository;
import com.server.intranet.approval.repository.NotificationRepository;
import com.server.intranet.approval.service.NotificationService;
import com.server.intranet.resource.entity.EmployeeEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    public SseEmitter subscribe(Long memberId, String lastEventId) {
        String id = memberId + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        sendToClient(emitter, id, "EventStream Created. [userId=" + memberId + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithId(String.valueOf(memberId));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }
        System.out.println("11111111111 => " + emitter);
        return emitter;
    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            log.error("SSE 연결 오류!", exception);
        }
    }

    @Transactional
    public void send(EmployeeEntity receiver, String url, String content) {
        Notification notification = createNotification(receiver, url, content);
        String id = String.valueOf(receiver.getEmployeeId());
        notificationRepository.save(notification);
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithById(id);
        sseEmitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendToClient(emitter, key, NotificationResponse.from(notification));
                }
        );
    }

    private Notification createNotification(EmployeeEntity receiver, String url, String content) {
        return Notification.builder()
                .employeeId(receiver)
                .content(content)
                .url(url)
                .isRead(false)
                .isView(false)
                .build();
    }

    @Transactional
    public NotificationsResponse findAllById(Long id) {
        List<NotificationResponse> responses = notificationRepository.findAllByEmployeeId_EmployeeId(id).stream()
                .map(NotificationResponse::from)
                .collect(Collectors.toList());
        long unreadCount = responses.stream()
                .filter(notification -> !notification.isRead())
                .count();

        return NotificationsResponse.of(responses, unreadCount);
    }

    @Transactional
    public void readNotification(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        notification.read();
    }

    @Transactional
    public void viewNotification(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        notification.view();
    }

    @Transactional
    public void readNotificationAll(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        List<Notification> notificationList = notificationRepository.findByEmployeeId(notification.getEmployeeId());
        for(Notification notice : notificationList){
            notice.read();
        }
    }
}