package lk.ijse.backend.service.custom;

import lk.ijse.backend.dto.NotificationDTO;
import lk.ijse.backend.entity.Task;

import java.time.LocalTime;
import java.util.List;

public interface NotificationService {
    public void scheduleNotification(Task task, String status, LocalTime time);

    List<NotificationDTO> getAllNotifi();

    public void deleteNotifi(Long id);
}
