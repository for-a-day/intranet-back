package com.server.intranet.approval.dto;

import com.server.intranet.approval.entity.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationResponse {
    /**
     * 알림 id
     */
    private Long id;

    /**
     * 알림 내용
     */
    private String content;

    /**
     * 알림 클릭 시 이동할 url
     */
    private String url;

    /**
     * 알림이 생성된 날짜(몇일 전 계산 위함)
     */
    private Integer[] createdAt;

    /**
     * 알림 읽음 여부
     */
    private boolean read;

    /**
     * 보여 준 여부
     */
    private boolean view;

    @Builder
    public NotificationResponse(Long id, String content, String url, boolean read, boolean view) {
        this.id = id;
        this.content = content;
        this.url = url;
        this.read = read;
        this.view = view;
    }

    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .content(notification.getContent())
                .url(notification.getUrl())
                .read(notification.isRead())
                .view(notification.isView())
                .build();
    }
}
