package lk.ijse.backend.service.custom;

import lk.ijse.backend.dto.PlanDTO;

import java.util.List;

public interface PlanService {
    public void savePlan(PlanDTO planDTO);
    public List<PlanDTO>getAllPlans();
    public void deletePlan(Long id);
    public void delete(Long id);
}
