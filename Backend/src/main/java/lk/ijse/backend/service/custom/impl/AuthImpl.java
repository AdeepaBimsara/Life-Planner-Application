package lk.ijse.backend.service.custom.impl;

import lk.ijse.backend.dto.AuthDTO;
import lk.ijse.backend.dto.AuthResponseDTO;
import lk.ijse.backend.dto.RegisterDTO;
import lk.ijse.backend.entity.Role;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repo.UserRepo;
import lk.ijse.backend.service.custom.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import lk.ijse.backend.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthImpl implements AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO authenticate(AuthDTO  authDTO) {
        User user = userRepo.findByEmail(authDTO.getEmail()).orElseThrow(
                ()->new UsernameNotFoundException(authDTO.getEmail())
        );

        if (!passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(authDTO.getEmail());
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDTO(token);

    }

    public String register(RegisterDTO registerDTO) {

        if (userRepo.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email Already Use");
        }
        User user = User.builder()
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.valueOf(registerDTO.getRole()))
                .build();
        userRepo.save(user);
        return "user registered successfully";
    }

}
