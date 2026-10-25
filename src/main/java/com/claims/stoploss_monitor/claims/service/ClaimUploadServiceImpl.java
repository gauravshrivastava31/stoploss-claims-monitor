package com.claims.stoploss_monitor.claims.service;

import com.claims.stoploss_monitor.claims.dto.ClaimUploadResponse;
import com.claims.stoploss_monitor.claims.entity.Claim;
import com.claims.stoploss_monitor.claims.entity.ClaimVersion;
import com.claims.stoploss_monitor.claims.repository.ClaimRepository;
import com.claims.stoploss_monitor.claims.repository.ClaimVersionRepository;
import com.claims.stoploss_monitor.uploads.entity.Upload;
import com.claims.stoploss_monitor.uploads.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimUploadServiceImpl
        implements ClaimUploadService {

    private final ClaimRepository claimRepository;

    private final ClaimVersionRepository
            claimVersionRepository;

    private final UploadRepository
            uploadRepository;

    @Override
    public ClaimUploadResponse upload(
            MultipartFile file
    ) {
        if (file == null || file.isEmpty()) {

            throw new IllegalArgumentException(
                    "File is empty"
            );
        }
        List<String> errors =
                new ArrayList<>();

        int processedRows = 0;

        try {

            if (uploadRepository.existsByFileName(
                    file.getOriginalFilename()
            )) {

                return ClaimUploadResponse
                        .builder()
                        .fileName(
                                file.getOriginalFilename()
                        )
                        .processedRows(0)
                        .failedRows(1)
                        .errors(
                                List.of(
                                        "File already uploaded"
                                )
                        )
                        .build();
            }

            Upload upload =
                    Upload.builder()
                            .fileName(
                                    file.getOriginalFilename()
                            )
                            .uploadedAt(
                                    LocalDateTime.now()
                            )
                            .build();

            upload =
                    uploadRepository.save(
                            upload
                    );

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    file.getInputStream()
                            )
                    );

            String header =
                    reader.readLine();

            boolean tpaOne =
                    header.contains(
                            "Claim ID"
                    );

            boolean tpaTwo =
                    header.contains(
                            "claim_number"
                    );

            String line;

            while (
                    (line = reader.readLine())
                            != null
            ) {

                try {

                    String[] cols =
                            line.split(",");

                    String claimNumber;
                    Integer adjustmentSequence;
                    String memberId;
                    LocalDate serviceDate;
                    LocalDate paidDate;
                    BigDecimal paidAmount;
                    String status;
                    String diagnosisCode;

                    if (tpaOne) {

                        claimNumber =
                                cols[0].trim();

                        adjustmentSequence =
                                Integer.parseInt(
                                        cols[1].trim()
                                );

                        memberId =
                                cols[2].trim();

                        serviceDate =
                                LocalDate.parse(
                                        cols[3].trim(),
                                        DateTimeFormatter.ofPattern(
                                                "MM/dd/yyyy"
                                        )
                                );

                        paidDate =
                                LocalDate.parse(
                                        cols[4].trim(),
                                        DateTimeFormatter.ofPattern(
                                                "MM/dd/yyyy"
                                        )
                                );

                        paidAmount =
                                new BigDecimal(
                                        cols[5].trim()
                                );

                        status =
                                cols[6].trim();

                        diagnosisCode =
                                cols[7].trim();

                    } else if (tpaTwo) {

                        claimNumber =
                                cols[0].trim();

                        adjustmentSequence =
                                Integer.parseInt(
                                        cols[1].trim()
                                );

                        memberId =
                                cols[2].trim();

                        serviceDate =
                                LocalDate.parse(
                                        cols[3].trim()
                                );

                        paidDate =
                                LocalDate.parse(
                                        cols[4].trim()
                                );

                        paidAmount =
                                new BigDecimal(
                                        cols[5]
                                                .replace("$", "")
                                                .trim()
                                );

                        status =
                                cols[6].trim();

                        diagnosisCode =
                                null;

                    } else {

                        throw new RuntimeException(
                                "Unsupported file format"
                        );
                    }

                    Claim claim =
                            claimRepository
                                    .findByClaimNumber(
                                            claimNumber
                                    )
                                    .orElseGet(() ->
                                            claimRepository.save(
                                                    Claim.builder()
                                                            .claimNumber(
                                                                    claimNumber
                                                            )
                                                            .memberId(
                                                                    memberId
                                                            )
                                                            .groupId(
                                                                    memberId.startsWith("A")
                                                                            ? "GA"
                                                                            : "GB"
                                                            )
                                                            .build()
                                            )
                                    );

                    int nextVersion = 1;

                    var latestVersion =
                            claimVersionRepository
                                    .findTopByClaimOrderByVersionNumberDesc(
                                            claim
                                    );

                    if (
                            latestVersion.isPresent()
                    ) {

                        ClaimVersion existing =
                                latestVersion.get();

                        existing.setIsLatest(
                                false
                        );

                        claimVersionRepository.save(
                                existing
                        );

                        nextVersion =
                                existing.getVersionNumber()
                                        + 1;
                    }

                    ClaimVersion version =
                            ClaimVersion.builder()
                                    .claim(claim)
                                    .upload(upload)
                                    .adjustmentSequence(
                                            adjustmentSequence
                                    )
                                    .memberId(memberId)
                                    .serviceDate(serviceDate)
                                    .paidDate(paidDate)
                                    .paidAmount(paidAmount)
                                    .status(status)
                                    .diagnosisCode(
                                            diagnosisCode
                                    )
                                    .versionNumber(
                                            nextVersion
                                    )
                                    .isLatest(true)
                                    .build();

                    claimVersionRepository.save(
                            version
                    );

                    processedRows++;

                } catch (Exception ex) {

                    errors.add(
                            ex.getMessage()
                    );
                }
            }

        } catch (Exception ex) {

            errors.add(
                    ex.getMessage()
            );
        }

        return ClaimUploadResponse
                .builder()
                .fileName(
                        file.getOriginalFilename()
                )
                .processedRows(
                        processedRows
                )
                .failedRows(
                        errors.size()
                )
                .errors(
                        errors
                )
                .build();
    }
}