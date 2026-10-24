package com.claims.stoploss_monitor.claims.service;

import com.claims.stoploss_monitor.claims.entity.Claim;
import com.claims.stoploss_monitor.claims.entity.ClaimVersion;
import com.claims.stoploss_monitor.claims.repository.ClaimRepository;
import com.claims.stoploss_monitor.claims.repository.ClaimVersionRepository;
import com.claims.stoploss_monitor.uploads.entity.Upload;
import com.claims.stoploss_monitor.uploads.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClaimImportServiceImpl
        implements ClaimImportService {

    private final ClaimRepository claimRepository;

    private final ClaimVersionRepository claimVersionRepository;

    private final UploadRepository uploadRepository;

    @Override
    public void importClaimFile(String fileName) {

        log.info("Processing file : {}", fileName);

        // Next step:
        // 1. Create Upload record
        // 2. Read CSV
        // 3. Create / update claim versions
        // 4. Generate alerts
    }
}