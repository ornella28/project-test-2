package se.lexicon.subscriptionapi.dto.request;

import jakarta.validation.constraints.NotNull;

public record SubscriptionChangeRequest(
        @NotNull(message = "Customer id is required")
        Long customerId,

        @NotNull(message = "Subscription id is required")
        Long subscriptionId,

        @NotNull(message = "New plan id is required")
        Long newPlanId
) {
}
