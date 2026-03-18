package se.lexicon.subscriptionapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.lexicon.subscriptionapi.domain.entity.Subscription;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.email", target = "customerEmail")
    @Mapping(source = "plan.id", target = "planId")
    @Mapping(source = "plan.name", target = "planName")
    @Mapping(source = "plan.price", target = "planPrice")
    @Mapping(source = "plan.serviceType", target = "serviceType")
    @Mapping(source = "plan.operator.id", target = "operatorId")
    @Mapping(source = "plan.operator.name", target = "operatorName")
    SubscriptionResponse toResponse(Subscription subscription);


}
