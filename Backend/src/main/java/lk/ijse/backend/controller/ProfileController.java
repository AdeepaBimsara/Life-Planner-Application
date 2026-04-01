package lk.ijse.backend.controller;

import lk.ijse.backend.dto.APIResponse;
import lk.ijse.backend.dto.ProfileDTO;
import lk.ijse.backend.service.custom.ProfileService;
import lk.ijse.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/profile")
@CrossOrigin(origins = "http://localhost:63342")
public class ProfileController {

    private final ProfileService profileService;
    private final JwtUtil jwtUtil;

    @GetMapping("/me")
    public ResponseEntity<APIResponse<ProfileDTO>> getProfile(@RequestHeader("Authorization") String token){
        String jwt = token.substring(7);
        String email = jwtUtil.extractUsername(jwt);

        System.out.println("TOKEN: " + token);
        System.out.println("EMAIL: " + email);

        ProfileDTO profile = profileService.getProfile(email);

        System.out.println(profile);

        return new ResponseEntity<>(new APIResponse<>(
                200,
                "profile retrieve successfully",
                profile
        ), HttpStatus.OK);


    }

}

