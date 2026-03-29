package lk.ijse.backend.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class PlanDTO {

    private Long id;

    private String planType;
    private String category;

    private LocalDate date;
    private LocalDate weekStart;
    private String month;
    private Integer year;

    private List<TaskDTO> tasks;

}
