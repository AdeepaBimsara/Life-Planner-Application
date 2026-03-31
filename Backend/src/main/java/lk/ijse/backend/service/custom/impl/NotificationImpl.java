package lk.ijse.backend.service.custom.impl;

import lk.ijse.backend.dto.NotificationDTO;
import lk.ijse.backend.entity.Notification;
import lk.ijse.backend.entity.Task;
import lk.ijse.backend.repo.NotificationRepo;
import lk.ijse.backend.service.custom.NotificationPublisherService;
import lk.ijse.backend.service.custom.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@RequiredArgsConstructor
@Service
public class NotificationImpl implements NotificationService {

    private final NotificationRepo notificationRepo;
    private final NotificationPublisherService notificationPublisherService;
    private final ModelMapper modelMapper;

    @Override
    public void scheduleNotification(Task task, String status, LocalTime time) {
        LocalDateTime now = LocalDateTime.now();

//        LocalDateTime notifTime = LocalDateTime.now()
//                .withHour(time.getHour())
//                .withMinute(time.getMinute())
//                .withSecond(0);
        LocalDateTime notifTime = LocalDateTime.of(
                task.getPlan().getDate(),
                time
        );

        long delay = Duration.between(now, notifTime).toMillis();

        System.out.println("Task: " + task.getName());
        System.out.println("Delay: " + delay);

        if (delay < 0) {
            System.out.println("skip past task");
            return;
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                Notification notification = Notification.builder()
                        .task(task)
                        .notificationTime(LocalDateTime.now())
                        .status(status)
                        .build();

//                NotificationDTO notification = new NotificationDTO(
//                        task.getName(),
//                        status,
//                        LocalDateTime.now()
//                );

                notificationRepo.save(notification);

                //real time send
                notificationPublisherService.sendNotification(notification);

                System.out.println("Notification saved: " + status + " - " + task.getName());
            }
        }, delay);
    }

    @Override
    public List<NotificationDTO> getAllNotifi() {
        List<Notification> notifi = notificationRepo.findAll();

        return notifi.stream().map(n->
                modelMapper.map(n,NotificationDTO.class))
                .toList();
    }

    @Override
    public void deleteNotifi(Long id) {
        System.out.println("notification delete");
        notificationRepo.deleteById(id);
    }
}
