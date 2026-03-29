package lk.ijse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse <T>{
    private int code;
    private String message;
    private Object data;
}
