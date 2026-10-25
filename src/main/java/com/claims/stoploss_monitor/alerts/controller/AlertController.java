package com.claims.stoploss_monitor.alerts.controller;

import com.claims.stoploss_monitor.alerts.dto.AlertDto;
import com.claims.stoploss_monitor.alerts.repository.AlertAcknowledgementRepository;
import com.claims.stoploss_monitor.alerts.service.AlertGenerationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.claims.stoploss_monitor.alerts.dto.AcknowledgeAlertRequest;
import com.claims.stoploss_monitor.alerts.entity.AlertAcknowledgement;
import com.claims.stoploss_monitor.alerts.repository.AlertAcknowledgementRepository;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
@Tag(name = "Alert APIs")
@SecurityRequirement(name = "basicAuth")
public class AlertController {

    private final AlertGenerationService
            alertGenerationService;

    private final AlertAcknowledgementRepository
            acknowledgementRepository;

    @GetMapping
    public List<AlertDto> getAlerts() {

        return alertGenerationService
                .evaluateAlerts();
    }
    @PostMapping("/{memberId}/acknowledge")
    public Map<String, String> acknowledgeAlert(

            @PathVariable String memberId,

            @Valid
            @RequestBody
            AcknowledgeAlertRequest request,

            Authentication authentication
    ) {

        AlertAcknowledgement acknowledgement =

                AlertAcknowledgement.builder()

                        .id(UUID.randomUUID())

                        .memberId(memberId)

                        .note(request.getNote())

                        .acknowledgedBy(
                                authentication.getName()
                        )

                        .acknowledgedAt(
                                LocalDateTime.now()
                        )

                        .build();

        acknowledgementRepository.save(
                acknowledgement
        );

        return Map.of(
                "status",
                "ACKNOWLEDGED"
        );
    }


}
