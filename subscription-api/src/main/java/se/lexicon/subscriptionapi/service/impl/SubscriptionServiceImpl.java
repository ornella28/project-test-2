package se.lexicon.subscriptionapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.lexicon.subscriptionapi.domain.constant.SubscriptionStatus;
import se.lexicon.subscriptionapi.domain.entity.Customer;
import se.lexicon.subscriptionapi.domain.entity.Plan;
import se.lexicon.subscriptionapi.domain.entity.Subscription;
import se.lexicon.subscriptionapi.dto.request.SubscriptionChangeRequest;
import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;
import se.lexicon.subscriptionapi.mapper.SubscriptionMapper;
import se.lexicon.subscriptionapi.repository.CustomerRepository;
import se.lexicon.subscriptionapi.repository.PlanRepository;
import se.lexicon.subscriptionapi.repository.SubscriptionRepository;
import se.lexicon.subscriptionapi.service.SubscriptionService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final PlanRepository planRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    public SubscriptionResponse subscribe(SubscriptionRequest request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.customerId()));

        Plan plan = planRepository.findById(request.planId())
                .orElseThrow(() -> new RuntimeException("Plan not found with id: " + request.planId()));

        if (!plan.isActive()) {
            throw new RuntimeException("Cannot subscribe to an inactive plan");
        }

        boolean alreadyHasActiveSubscription =
                subscriptionRepository.existsByCustomerIdAndPlanServiceTypeAndStatus(
                        customer.getId(),
                        plan.getServiceType(),
                        SubscriptionStatus.ACTIVE
                );

        if (alreadyHasActiveSubscription) {
            throw new RuntimeException(
                    "Customer already has an active subscription for service type: " + plan.getServiceType()
            );
        }

        Subscription subscription = Subscription.builder()
                .customer(customer)
                .plan(plan)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .build();

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toResponse(savedSubscription);
    }

    @Override
    public List<SubscriptionResponse> findByCustomerId(Long customerId) {
        return subscriptionRepository.findByCustomerId(customerId)
                .stream()
                .map(subscriptionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public SubscriptionResponse changePlan(SubscriptionChangeRequest request) {
        Subscription subscription = subscriptionRepository.findById(request.subscriptionId())
                .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + request.subscriptionId()));

        if (!subscription.getCustomer().getId().equals(request.customerId())) {
            throw new RuntimeException("Subscription does not belong to customer");
        }

        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            throw new RuntimeException("Only active subscriptions can be changed");
        }

        Plan newPlan = planRepository.findById(request.newPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found with id: " + request.newPlanId()));

        if (!newPlan.isActive()) {
            throw new RuntimeException("Cannot change to an inactive plan");
        }

        Plan currentPlan = subscription.getPlan();

        if (!currentPlan.getServiceType().equals(newPlan.getServiceType())) {
            throw new RuntimeException("Plan change is only allowed within the same service type");
        }

        if (!currentPlan.getOperator().getId().equals(newPlan.getOperator().getId())) {
            throw new RuntimeException("Plan change is only allowed within the same operator");
        }

        subscription.setPlan(newPlan);

        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toResponse(updatedSubscription);
    }

    @Override
    @Transactional
    public SubscriptionResponse cancel(Long customerId, Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + subscriptionId));

        if (!subscription.getCustomer().getId().equals(customerId)) {
            throw new RuntimeException("Subscription does not belong to customer");
        }

        if (subscription.getStatus() == SubscriptionStatus.CANCELLED) {
            throw new RuntimeException("Subscription is already cancelled");
        }

        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscription.setCancellationDate(LocalDateTime.now());

        Subscription cancelledSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toResponse(cancelledSubscription);
    }
}
