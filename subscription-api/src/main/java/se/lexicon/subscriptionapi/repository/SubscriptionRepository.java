package se.lexicon.subscriptionapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.entity.Subscription;
import se.lexicon.subscriptionapi.domain.constant.SubscriptionStatus;

import java.util.List;

@Repository
@Validated
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByCustomerId(Long customerId);
    List<Subscription> findByCustomerIdAndStatus(Long customerId, SubscriptionStatus status);
    List<Subscription> findByCustomerIdAndPlanServiceTypeAndStatus(Long customerId, ServiceType serviceType, SubscriptionStatus status);
    boolean existsByCustomerIdAndPlanServiceTypeAndStatus(Long customerId, ServiceType serviceType, SubscriptionStatus status);


}
