package lk.ijse.backend.service.custom.impl;

import lk.ijse.backend.dto.TaskDTO;
import lk.ijse.backend.entity.Notification;
import lk.ijse.backend.entity.Task;
import lk.ijse.backend.repo.NotificationRepo;
import lk.ijse.backend.repo.TaskRepo;
import lk.ijse.backend.service.custom.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final NotificationRepo notificationRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<TaskDTO> getAllTask() {
         List<Task> tasks = taskRepo.findAll();

         return tasks.stream().map(task ->
                 modelMapper.map(task,TaskDTO.class))
                 .toList();
    }

}
