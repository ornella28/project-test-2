package se.lexicon.subscriptionapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.subscriptionapi.dto.request.SubscriptionChangeRequest;
import se.lexicon.subscriptionapi.dto.request.SubscriptionRequest;
import se.lexicon.subscriptionapi.dto.response.SubscriptionResponse;
import se.lexicon.subscriptionapi.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionResponse> subscribe(@Valid @RequestBody SubscriptionRequest request) {
        SubscriptionResponse response = subscriptionService.subscribe(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<SubscriptionResponse>> findByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(subscriptionService.findByCustomerId(customerId));
    }

    @PutMapping("/change-plan")
    public ResponseEntity<SubscriptionResponse> changePlan(@Valid @RequestBody SubscriptionChangeRequest request) {
        return ResponseEntity.ok(subscriptionService.changePlan(request));
    }

    @PutMapping("/{subscriptionId}/cancel/customer/{customerId}")
    public ResponseEntity<SubscriptionResponse> cancel(@PathVariable Long customerId,
                                                       @PathVariable Long subscriptionId) {
        return ResponseEntity.ok(subscriptionService.cancel(customerId, subscriptionId));
    }
}
