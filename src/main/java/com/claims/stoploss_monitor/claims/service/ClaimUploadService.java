package com.claims.stoploss_monitor.claims.service;

import com.claims.stoploss_monitor.claims.dto.ClaimUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ClaimUploadService {

    ClaimUploadResponse upload(
            MultipartFile file
    );
}