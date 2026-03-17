package se.lexicon.subscriptionapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import se.lexicon.subscriptionapi.domain.entity.Operator;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.domain.entity.ServiceType;

import java.util.List;

@Repository
@Validated
public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByActiveTrue();
    List<Plan> findByActiveTrueAndServiceType(ServiceType serviceType);
    List<Plan> findByOperatorId(Long operatorId);
    List<Plan> findByOperatorIdAndActiveTrue(Long operatorId);
    List<Plan> findByOperatorIdAndActiveTrueAndServiceType(Long operatorId, ServiceType serviceType);


}
