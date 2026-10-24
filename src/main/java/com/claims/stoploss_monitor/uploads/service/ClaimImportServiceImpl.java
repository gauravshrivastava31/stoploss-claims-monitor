package com.claims.stoploss_monitor.uploads.service;

import com.claims.stoploss_monitor.claims.entity.Claim;
import com.claims.stoploss_monitor.claims.entity.ClaimVersion;
import com.claims.stoploss_monitor.claims.repository.ClaimRepository;
import com.claims.stoploss_monitor.claims.repository.ClaimVersionRepository;
import com.claims.stoploss_monitor.uploads.dto.ClaimCsvRecord;
import com.claims.stoploss_monitor.uploads.entity.Upload;
import com.claims.stoploss_monitor.uploads.repository.UploadRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClaimImportServiceImpl
        implements ClaimImportService {

    private final ClaimRepository claimRepository;

    private final ClaimVersionRepository claimVersionRepository;

    private final UploadRepository uploadRepository;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Override
    public void importClaimFile(String fileName) {
        if (uploadRepository.existsByFileName(
                fileName
        )) {

            log.info(
                    "File already imported: {}",
                    fileName
            );

            return;
        }
        try {

            Upload upload = Upload.builder()
                    .fileName(fileName)
                    .uploadedAt(LocalDateTime.now())
                    .build();

            upload = uploadRepository.save(upload);

            ClassPathResource resource =
                    new ClassPathResource(
                            "data/claims/" + fileName
                    );

            List<ClaimCsvRecord> records =
                    new CsvToBeanBuilder<ClaimCsvRecord>(
                            new InputStreamReader(resource.getInputStream())
                    )
                            .withType(ClaimCsvRecord.class)
                            .build()
                            .parse();

            for (ClaimCsvRecord record : records) {

                if ("TRAILER".equalsIgnoreCase(
                        record.getClaimId()
                )) {
                    continue;
                }

                processClaim(record, upload);
            }

            log.info(
                    "Successfully processed file {}",
                    fileName
            );

        } catch (Exception ex) {

            log.error(
                    "Failed processing file {}",
                    fileName,
                    ex
            );

            throw new RuntimeException(ex);
        }
    }

    private void processClaim(
            ClaimCsvRecord record,
            Upload upload
    ) {

        log.info(
                "ClaimId={} AdjSeq={} MemberId={}",
                record.getClaimId(),
                record.getAdjustmentSequence(),
                record.getMemberId()
        );

        Claim claim =
                claimRepository
                        .findByClaimNumber(
                                record.getClaimId()
                        )
                        .orElseGet(() ->
                                claimRepository.save(
                                        Claim.builder()
                                                .claimNumber(
                                                        record.getClaimId()
                                                )
                                                .memberId(
                                                        record.getMemberId()
                                                )
                                                .groupId(
                                                        extractGroupId(
                                                                record.getMemberId()
                                                        )
                                                )
                                                .build()
                                )
                        );

        Integer nextVersion =
                claimVersionRepository
                        .findTopByClaimOrderByVersionNumberDesc(
                                claim
                        )
                        .map(v ->
                                v.getVersionNumber() + 1
                        )
                        .orElse(1);

        claimVersionRepository
                .findByClaimAndIsLatestTrue(claim)
                .ifPresent(v -> {

                    v.setIsLatest(false);

                    claimVersionRepository.save(v);
                });

        ClaimVersion claimVersion =
                ClaimVersion.builder()
                        .claim(claim)
                        .upload(upload)
                        .adjustmentSequence(
                                record.getAdjustmentSequence()
                        )
                        .memberId(
                                record.getMemberId()
                        )
                        .serviceDate(
                                parseDate(
                                        record.getServiceDate()
                                )
                        )
                        .paidDate(
                                parseDate(
                                        record.getPaidDate()
                                )
                        )
                        .paidAmount(
                                parseAmount(
                                        record.getPaidAmount()
                                )
                        )
                        .status(
                                record.getStatus()
                        )
                        .diagnosisCode(
                                record.getDiagnosisCode()
                        )
                        .versionNumber(nextVersion)
                        .isLatest(true)
                        .build();

        claimVersionRepository.save(
                claimVersion
        );
    }

    private LocalDate parseDate(
            String value
    ) {

        return LocalDate.parse(
                value,
                DATE_FORMATTER
        );
    }

    private BigDecimal parseAmount(
            String value
    ) {

        return new BigDecimal(
                value.replace(",", "")
        );
    }

    private String extractGroupId(
            String memberId
    ) {

        return memberId.substring(
                0,
                1
        );
    }
}