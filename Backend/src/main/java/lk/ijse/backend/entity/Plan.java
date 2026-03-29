package lk.ijse.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String planType;
    private String category;

    private LocalDate date;
    private LocalDate weekStart;
    private String month;
    private Integer year;

    @OneToMany(mappedBy = "plan",cascade = CascadeType.ALL)
    private List<Task> tasks;


}
