package lk.ijse.backend.controller;

import lk.ijse.backend.dto.APIResponse;
import lk.ijse.backend.dto.PlanDTO;
import lk.ijse.backend.service.custom.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/plan")
@CrossOrigin
public class PlanController {

    private final PlanService planService;

    @PostMapping("/save")
    public ResponseEntity<APIResponse> savePlan(@RequestBody PlanDTO planDTO){

        System.out.println("received plan:"+planDTO);

        planService.savePlan(planDTO);

        return new ResponseEntity<>(new APIResponse<>(
                200,
                "plan save successfully",
                null
        ), HttpStatus.OK);

    }


//    @PutMapping("update")
//    public ResponseEntity<APIResponse>updatePlan(@RequestBody PlanDTO planDTO){
//
//    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<APIResponse> deletePlan(@PathVariable Long id){
        planService.deletePlan(id);

        return new ResponseEntity<>(new APIResponse<>(
                200,
                "plan delete successfully",
                null
        ), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<APIResponse>getAllPlan(){
        List<PlanDTO> allPlans = planService.getAllPlans();

        return new ResponseEntity<>(new APIResponse<>(
                200,
                "plan retrieve successfully",
                allPlans
        ), HttpStatus.OK);
    }


}
