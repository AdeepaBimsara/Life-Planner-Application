package lk.ijse.backend.service.custom.impl;

import lk.ijse.backend.entity.Notification;
import lk.ijse.backend.service.custom.NotificationPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationPublisherImpl implements NotificationPublisherService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendNotification(Notification notification){
        simpMessagingTemplate.convertAndSend("/topic/notifications", notification);
    }
}
