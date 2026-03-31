package lk.ijse.backend.service.custom;

import lk.ijse.backend.entity.Notification;

public interface NotificationPublisherService {
    public void sendNotification(Notification notification);
}
