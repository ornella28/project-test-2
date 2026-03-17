package se.lexicon.subscriptionapi.service;

import se.lexicon.subscriptionapi.dto.request.SubscriptionChangeRequest;
import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {

    SubscriptionResponse subscribe(SubscriptionRequest request);

    List<SubscriptionResponse> findByCustomerId(Long customerId);

    SubscriptionResponse changePlan(SubscriptionChangeRequest request);

    SubscriptionResponse cancel(Long customerId, Long subscriptionId);
}
