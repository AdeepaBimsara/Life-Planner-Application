package lk.ijse.backend.service.custom.impl;

import jakarta.transaction.Transactional;
import lk.ijse.backend.dto.PlanDTO;
import lk.ijse.backend.entity.Plan;
import lk.ijse.backend.entity.Task;
import lk.ijse.backend.repo.NotificationRepo;
import lk.ijse.backend.repo.PlanRepo;
import lk.ijse.backend.repo.TaskRepo;
import lk.ijse.backend.service.custom.NotificationService;
import lk.ijse.backend.service.custom.PlanService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlanImpl implements PlanService {

    private final PlanRepo planRepo;
    private final NotificationRepo notificationRepo;
    private final TaskRepo taskRepo;

    private final NotificationService notificationService;
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

        //save plan
        planRepo.save(plan);

        //notification after save
        plan.getTasks().forEach(task -> {
            notificationService.scheduleNotification(task,"Your task start",task.getStartTime());
            notificationService.scheduleNotification(task,"Your task end",task.getEndTime());
        });


    }

    @Override
    public List<PlanDTO> getAllPlans() {
        List<Plan> plans = planRepo.findAll();

        return plans.stream().map(plan ->
                modelMapper.map(plan, PlanDTO.class))
                .toList();
    }

    @Override
    public void delete(Long id) {
        System.out.println("plan delete");
        planRepo.deleteById(id);


    }

    @Transactional
    public void deletePlan(Long id){

        Plan plan = planRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        // delete notifications first
        plan.getTasks().forEach(task -> {
            notificationRepo.deleteAll(task.getNotifications());
        });

        // delete tasks
        taskRepo.deleteAll(plan.getTasks());

        // delete plan
        planRepo.delete(plan);
    }
}
