package se.lexicon.subscriptionapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;
import se.lexicon.subscriptionapi.service.PlanService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
@RequiredArgsConstructor//injects PlanService, automatically create a constructor for PlanService
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<PlanResponse> create(@Valid @RequestBody PlanRequest request) {
        PlanResponse response = planService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody PlanRequest request) {
        PlanResponse response = planService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        planService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(planService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PlanResponse>> findAll() {
        return ResponseEntity.ok(planService.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<PlanResponse>> findAllActive() {
        return ResponseEntity.ok(planService.findAllActive());
    }

    @GetMapping("/active/service-type/{serviceType}")
    public ResponseEntity<List<PlanResponse>> findActiveByServiceType(@PathVariable ServiceType serviceType) {
        return ResponseEntity.ok(planService.findActiveByServiceType(serviceType));
    }

    @GetMapping("/operator/{operatorId}")
    public ResponseEntity<List<PlanResponse>> findByOperator(@PathVariable Long operatorId) {
        return ResponseEntity.ok(planService.findByOperator(operatorId));
    }

    @GetMapping("/operator/{operatorId}/active")
    public ResponseEntity<List<PlanResponse>> findActiveByOperator(@PathVariable Long operatorId) {
        return ResponseEntity.ok(planService.findActiveByOperator(operatorId));
    }
}
