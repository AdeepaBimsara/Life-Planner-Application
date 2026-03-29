package lk.ijse.backend.dto;

import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class TaskDTO {

    private Long id;

    private String name;
    private LocalTime startTime;
    private LocalTime endTime;

}
