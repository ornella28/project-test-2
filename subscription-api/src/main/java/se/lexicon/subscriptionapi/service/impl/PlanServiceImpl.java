package se.lexicon.subscriptionapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.dto.request.PlanRequest;
import se.lexicon.subscriptionapi.dto.response.PlanResponse;
import se.lexicon.subscriptionapi.mapper.PlanMapper;
import se.lexicon.subscriptionapi.repository.OperatorRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;
import se.lexicon.subscriptionapi.service.PlanService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {


    private final PlanRepository planRepository;
    private final OperatorRepository operatorRepository;
    private final PlanMapper planMapper;

    @Override
    @Transactional
    public PlanResponse create(PlanRequest request) {
        Operator operator = operatorRepository.findById(request.operatorId())
                .orElseThrow(() -> new RuntimeException("Operator not found with id: " + request.operatorId()));

        Plan plan = planMapper.toEntity(request);
        plan.setOperator(operator);

        Plan savedPlan = planRepository.save(plan);
        return planMapper.toResponse(savedPlan);
    }

    @Override
    @Transactional
    public PlanResponse update(Long id, PlanRequest request) {
        Plan existingPlan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found with id: " + id));

        Operator operator = operatorRepository.findById(request.operatorId())
                .orElseThrow(() -> new RuntimeException("Operator not found with id: " + request.operatorId()));

        existingPlan.setName(request.name());
        existingPlan.setPrice(request.price());
        existingPlan.setServiceType(request.serviceType());
        //existingPlan.setDataLimit(request.dataLimit());
        existingPlan.setActive(request.active());
        existingPlan.setOperator(operator);

        Plan updatedPlan = planRepository.save(existingPlan);
        return planMapper.toResponse(updatedPlan);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found with id: " + id));

        planRepository.delete(plan);
    }

    @Override
    public PlanResponse findById(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found with id: " + id));

        return planMapper.toResponse(plan);
    }

    @Override
    public List<PlanResponse> findAll() {
        return planRepository.findAll()
                .stream()
                .map(planMapper::toResponse)
                .toList();
    }

    @Override
    public List<PlanResponse> findAllActive() {
        return planRepository.findByActiveTrue()
                .stream()
                .map(planMapper::toResponse)
                .toList();
    }

    @Override
    public List<PlanResponse> findActiveByServiceType(ServiceType serviceType) {
        return planRepository.findByActiveTrueAndServiceType(serviceType)
                .stream()
                .map(planMapper::toResponse)
                .toList();
    }

    @Override
    public List<PlanResponse> findByOperator(Long operatorId) {
        return planRepository.findByOperatorId(operatorId)
                .stream()
                .map(planMapper::toResponse)
                .toList();
    }

    @Override
    public List<PlanResponse> findActiveByOperator(Long operatorId) {
        return planRepository.findByOperatorIdAndActiveTrue(operatorId)
                .stream()
                .map(planMapper::toResponse)
                .toList();
    }
}
