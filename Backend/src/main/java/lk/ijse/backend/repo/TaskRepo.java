package lk.ijse.backend.repo;

import lk.ijse.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task,Long> {
}
