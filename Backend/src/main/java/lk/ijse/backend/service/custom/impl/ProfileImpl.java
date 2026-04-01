package lk.ijse.backend.service.custom.impl;

import lk.ijse.backend.dto.ProfileDTO;
import lk.ijse.backend.entity.User;
import lk.ijse.backend.repo.UserRepo;
import lk.ijse.backend.service.custom.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileImpl implements ProfileService {

    private final UserRepo userRepo;

    @Override
    public ProfileDTO getProfile(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email));

        return new ProfileDTO(user.getName(),user.getEmail());
    }
}
