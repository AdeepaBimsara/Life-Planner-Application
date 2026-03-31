package lk.ijse.backend.controller;

import lk.ijse.backend.dto.APIResponse;
import lk.ijse.backend.dto.TaskDTO;
import lk.ijse.backend.entity.Notification;
import lk.ijse.backend.entity.Task;
import lk.ijse.backend.service.custom.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/task")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;


    @GetMapping("getAll")
    public ResponseEntity<APIResponse>getAllTask(){
        List<TaskDTO> allTask = taskService.getAllTask();

        return new ResponseEntity<>(new APIResponse<>(
                200,
                "task retrieve success",
                allTask
        ), HttpStatus.OK);
    }


}
