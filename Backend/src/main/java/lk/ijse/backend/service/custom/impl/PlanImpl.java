package lk.ijse.backend.service.custom.impl;

import lk.ijse.backend.dto.PlanDTO;
import lk.ijse.backend.entity.Plan;
import lk.ijse.backend.entity.Task;
import lk.ijse.backend.repo.PlanRepo;
import lk.ijse.backend.service.custom.PlanService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanImpl implements PlanService {

    private final PlanRepo planRepo;
    private final ModelMapper modelMapper;

    @Override
    public void savePlan(PlanDTO planDTO) {
        System.out.println("plan save");

        //model mapper not set time
//        planRepo.save(modelMapper.map(planDTO,Plan.class));


        Plan plan = new Plan();
        plan.setPlanType(planDTO.getPlanType());
        plan.setCategory(planDTO.getCategory());

        if (planDTO.getDate() != null){
            plan.setDate(planDTO.getDate());
        }

        if (planDTO.getWeekStart() != null){
            plan.setWeekStart(planDTO.getWeekStart());
        }

        plan.setMonth(planDTO.getMonth());
        plan.setYear(planDTO.getYear());

        List<Task> taskList = planDTO.getTasks().stream().map(
                t->{
                    Task task = new Task();
                    task.setName(t.getName());
                    task.setStartTime(t.getStartTime());
                    task.setEndTime(t.getEndTime());

                    task.setPlan(plan);

                    return task;
                }
        ).toList();

        plan.setTasks(taskList);

        planRepo.save(plan);

    }

    @Override
    public List<PlanDTO> getAllPlans() {
        List<Plan> plans = planRepo.findAll();
        return plans.stream().map(plan -> modelMapper.map(plan, PlanDTO.class)).toList();
    }

    @Override
    public void deletePlan(Long id) {
        System.out.println("plan delete");
        planRepo.deleteById(id);
    }
}
