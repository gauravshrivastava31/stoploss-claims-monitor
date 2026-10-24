package com.claims.stoploss_monitor.policies.service;

import com.claims.stoploss_monitor.groups.entity.Group;
import com.claims.stoploss_monitor.groups.repository.GroupRepository;
import com.claims.stoploss_monitor.policies.dto.PolicyCsvRecord;
import com.claims.stoploss_monitor.policies.entity.PolicyYear;
import com.claims.stoploss_monitor.policies.repository.PolicyYearRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PolicyImportServiceImpl
        implements PolicyImportService {

    private final GroupRepository groupRepository;

    private final PolicyYearRepository policyYearRepository;

    @Override
    public void importPolicies() {
        if (policyYearRepository.count() > 0) {

            log.info(
                    "Policies already imported. Skipping."
            );

            return;
        }
        try {

            ClassPathResource resource =
                    new ClassPathResource(
                            "data/policies.csv"
                    );

            List<PolicyCsvRecord> records =
                    new CsvToBeanBuilder<PolicyCsvRecord>(
                            new InputStreamReader(
                                    resource.getInputStream()
                            )
                    )
                            .withType(PolicyCsvRecord.class)
                            .build()
                            .parse();

            for (PolicyCsvRecord record : records) {

                Group group =
                        groupRepository
                                .findById(
                                        record.getGroupId()
                                )
                                .orElseGet(() ->
                                        groupRepository.save(
                                                Group.builder()
                                                        .groupId(
                                                                record.getGroupId()
                                                        )
                                                        .groupName(
                                                                record.getGroupName()
                                                        )
                                                        .build()
                                        )
                                );

                PolicyYear policyYear =
                        PolicyYear.builder()
                                .id(UUID.randomUUID())
                                .group(group)
                                .policyStart(
                                        LocalDate.parse(
                                                record.getPolicyStart()
                                        )
                                )
                                .policyEnd(
                                        LocalDate.parse(
                                                record.getPolicyEnd()
                                        )
                                )
                                .specificDeductible(
                                        new BigDecimal(
                                                record.getSpecificDeductible()
                                        )
                                )
                                .build();

                policyYearRepository.save(
                        policyYear
                );
            }

            log.info(
                    "Policies imported successfully"
            );

        } catch (Exception ex) {

            throw new RuntimeException(
                    ex
            );
        }
    }
}