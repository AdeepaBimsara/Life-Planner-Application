package lk.ijse.backend.dto;

import lk.ijse.backend.entity.Task;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class NotificationDTO {
    private Long id;
    private LocalDateTime notificationTime;
    private String status;
    private String taskName;
}
