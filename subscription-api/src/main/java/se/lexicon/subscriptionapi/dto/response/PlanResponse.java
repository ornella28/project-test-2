package se.lexicon.subscriptionapi.dto.response;

import se.lexicon.subscriptionapi.domain.entity.ServiceType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PlanResponse(
        Long id,
        String name,
        BigDecimal price,
        ServiceType serviceType,
        Boolean active,
        Long operatorId,
        String operatorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
