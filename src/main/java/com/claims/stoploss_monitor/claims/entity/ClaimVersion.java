package com.claims.stoploss_monitor.claims.entity;

import com.claims.stoploss_monitor.uploads.entity.Upload;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "claim_versions",
        indexes = {
                @Index(name = "idx_claim_version_claim",
                        columnList = "claim_id"),
                @Index(name = "idx_claim_version_latest",
                        columnList = "is_latest")
        }
)
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
    @JoinColumn(
            name = "claim_id",
            nullable = false
    )
    private Claim claim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "upload_id",
            nullable = false
    )
    private Upload upload;

    @Column(
            name = "adjustment_sequence",
            nullable = false
    )
    private Integer adjustmentSequence;

    @Column(
            name = "member_id",
            nullable = false
    )
    private String memberId;

    @Column(name = "service_date")
    private LocalDate serviceDate;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    @Column(
            name = "paid_amount",
            precision = 14,
            scale = 2,
            nullable = false
    )
    private BigDecimal paidAmount;

    @Column(name = "status")
    private String status;

    @Column(name = "diagnosis_code")
    private String diagnosisCode;

    @Column(
            name = "version_number",
            nullable = false
    )
    private Integer versionNumber;

    @Column(
            name = "is_latest",
            nullable = false
    )
    private Boolean isLatest;
}