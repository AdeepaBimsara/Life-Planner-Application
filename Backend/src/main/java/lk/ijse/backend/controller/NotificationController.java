package lk.ijse.backend.controller;

import lk.ijse.backend.dto.APIResponse;
import lk.ijse.backend.dto.NotificationDTO;
import lk.ijse.backend.dto.TaskDTO;
import lk.ijse.backend.service.custom.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notification")
@CrossOrigin
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("getAll")
    public ResponseEntity<APIResponse>getAllNotification(){
        List<NotificationDTO> allNotifi = notificationService.getAllNotifi();

        return new ResponseEntity<>(new APIResponse<>(
                200,
                "notification success",
                allNotifi
        ), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<APIResponse>deleteNotifi(@PathVariable Long id){
        notificationService.deleteNotifi(id);

        return new ResponseEntity<>(new APIResponse(
                200,
                "notification delete success",
                null
        ),HttpStatus.OK);
    }
}
