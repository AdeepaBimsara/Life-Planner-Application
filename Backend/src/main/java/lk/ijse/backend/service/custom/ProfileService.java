package lk.ijse.backend.service.custom;

import lk.ijse.backend.dto.ProfileDTO;

public interface ProfileService {
    public ProfileDTO getProfile(String email);
}
