package com.claims.stoploss_monitor.claims.entity;

import com.claims.stoploss_monitor.uploads.entity.Upload;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "claim_versions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id")
    private Claim claim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upload_id")
    private Upload upload;

    @Column(name = "paid_amount", nullable = false)
    private BigDecimal paidAmount;

    @Column(name = "incurred_date")
    private LocalDate incurredDate;

    @Column(name = "diagnosis_code")
    private String diagnosisCode;

    @Column(name = "version_number", nullable = false)
    private Integer versionNumber;

    @Column(name = "is_latest", nullable = false)
    private Boolean isLatest;
}