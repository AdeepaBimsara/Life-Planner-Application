package lk.ijse.backend.service.custom;

import lk.ijse.backend.dto.AuthDTO;
import lk.ijse.backend.dto.AuthResponseDTO;
import lk.ijse.backend.dto.RegisterDTO;


public interface AuthService {
    public AuthResponseDTO authenticate(AuthDTO authDTO);
    public String register(RegisterDTO registerDTO);
}
