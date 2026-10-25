package com.claims.stoploss_monitor.claims.controller;

import com.claims.stoploss_monitor.claims.dto.ClaimUploadResponse;
import com.claims.stoploss_monitor.claims.service.ClaimUploadService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class ClaimUploadController {

    private final ClaimUploadService
            claimUploadService;

    @PostMapping("/upload")
    public ClaimUploadResponse upload(

            @RequestParam("file")
            MultipartFile file
    ) {

        return claimUploadService
                .upload(file);
    }
}