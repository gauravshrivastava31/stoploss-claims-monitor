package com.claims.stoploss_monitor.lasers.service;

import com.claims.stoploss_monitor.lasers.dto.LaserCsvRecord;
import com.claims.stoploss_monitor.lasers.entity.Laser;
import com.claims.stoploss_monitor.lasers.repository.LaserRepository;
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
public class LaserImportServiceImpl
        implements LaserImportService {

    private final LaserRepository laserRepository;

    @Override
    public void importLasers() {
        if (laserRepository.count() > 0) {

            log.info(
                    "Lasers already imported. Skipping."
            );

            return;
        }
        try {

            ClassPathResource resource =
                    new ClassPathResource(
                            "data/lasers.csv"
                    );

            List<LaserCsvRecord> records =
                    new CsvToBeanBuilder<LaserCsvRecord>(
                            new InputStreamReader(
                                    resource.getInputStream()
                            )
                    )
                            .withType(LaserCsvRecord.class)
                            .build()
                            .parse();
            log.info(
                    "Laser records found: {}",
                    records.size()
            );

            for (LaserCsvRecord record : records) {

                Laser laser =
                        Laser.builder()
                                .id(UUID.randomUUID())
                                .groupId(
                                        record.getGroupId()
                                )
                                .memberId(
                                        record.getMemberId()
                                )
                                .policyStart(
                                        LocalDate.parse(
                                                record.getPolicyStart()
                                        )
                                )
                                .specificDeductible(
                                        new BigDecimal(
                                                record.getSpecificDeductible()
                                        )
                                )
                                .build();

                laserRepository.save(
                        laser
                );
            }

            log.info(
                    "Laser import completed"
            );

        } catch (Exception ex) {

            throw new RuntimeException(
                    ex
            );
        }
    }
}