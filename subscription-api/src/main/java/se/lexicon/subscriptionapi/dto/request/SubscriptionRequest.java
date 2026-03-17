package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.NotNull;

public record SubscriptionRequest(
        @NotNull(message = "Customer id is required")
        Long customerId,

        @NotNull(message = "Plan id is required")
        Long planId
) {
}
