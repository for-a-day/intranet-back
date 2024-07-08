package com.server.intranet.approval.service;

import com.server.intranet.approval.dto.NotificationsResponse;
import com.server.intranet.resource.entity.EmployeeEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    public SseEmitter subscribe(Long memberId, String lastEventId);
    public void send(EmployeeEntity receiver, String url, String content);
    public NotificationsResponse findAllById(Long id);
    public void readNotification(Long id);
}
