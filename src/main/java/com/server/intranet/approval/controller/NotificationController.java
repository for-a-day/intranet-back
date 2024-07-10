package com.server.intranet.approval.controller;

import com.server.intranet.approval.dto.NotificationsResponse;
import com.server.intranet.approval.service.impl.NotificationServiceImpl;
import com.server.intranet.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationServiceImpl notificationServiceImpl;

    //로그인 한 유저 sse 연결
    @GetMapping(value = "/app/auth/notice", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestParam(value = "lastEventId", required = false, defaultValue = "") String lastEventId) {
        Long employeeId = Long.valueOf(Objects.requireNonNull(SecurityUtil.getCurrentUserId()));
        return notificationServiceImpl.subscribe(employeeId, lastEventId);
    }


    //로그인 한 유저의 모든 알림 조회
    @PostMapping("/app/auth/notice")
    public ResponseEntity<Map<String,Object>> notifications() {
        Long employeeId = Long.valueOf(Objects.requireNonNull(SecurityUtil.getCurrentUserId()));
        NotificationsResponse data = notificationServiceImpl.findAllById(employeeId);

        Map<String, Object> map = new HashMap<>();
        map.put("data",data);
        map.put("code", "SUCCESS");
        map.put("msg", "조회가 완료되었습니다.");

        return ResponseEntity.ok().body(map);
    }

    //알림 읽음 상태 변경
    @PutMapping("/app/auth/notice/{id}")
    public ResponseEntity<Map<String,Object>> readNotification(@PathVariable Long id) {
        notificationServiceImpl.readNotification(id);

        Map<String, Object> map = new HashMap<>();
        map.put("code", "SUCCESS");
        map.put("msg", "읽음처리 되었습니다");

        return ResponseEntity.ok().body(map);
    }

    //알림 읽음 상태 변경
    @PatchMapping("/app/auth/notice/{id}")
    public ResponseEntity<Map<String,Object>> viewNotification(@PathVariable Long id) {
        notificationServiceImpl.viewNotification(id);

        Map<String, Object> map = new HashMap<>();
        map.put("code", "SUCCESS");
        map.put("msg", "읽음처리 되었습니다");

        return ResponseEntity.ok().body(map);
    }

    //알림 모두 읽음
    @PostMapping("/app/auth/notice/{id}")
    public ResponseEntity<Map<String,Object>> readNotificationAll(@PathVariable Long id) {
        notificationServiceImpl.readNotificationAll(id);

        Map<String, Object> map = new HashMap<>();
        map.put("code", "SUCCESS");
        map.put("msg", "모두 읽음처리 되었습니다");

        return ResponseEntity.ok().body(map);
    }
}
