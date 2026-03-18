package se.lexicon.subscriptionapi.dto.response;

import se.lexicon.subscriptionapi.domain.constant.ServiceType;
import se.lexicon.subscriptionapi.domain.constant.SubscriptionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SubscriptionResponse(
        Long id,
        Long customerId,
        String customerEmail,
        Long planId,
        String planName,
        BigDecimal planPrice,
        ServiceType serviceType,
        Long operatorId,
        String operatorName,
        SubscriptionStatus status,
        LocalDateTime startDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
