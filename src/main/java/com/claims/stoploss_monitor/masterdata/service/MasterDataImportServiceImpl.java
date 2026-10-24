package com.claims.stoploss_monitor.masterdata.service;

import com.claims.stoploss_monitor.members.dto.MemberCsvRecord;
import com.claims.stoploss_monitor.members.entity.Member;
import com.claims.stoploss_monitor.members.repository.MemberRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MasterDataImportServiceImpl
        implements MasterDataImportService {

    private final MemberRepository memberRepository;

    @Override
    public void importMembers() {
        if (memberRepository.count() > 0) {

            log.info(
                    "Members already imported. Skipping."
            );

            return;
        }
        try {

            ClassPathResource resource =
                    new ClassPathResource(
                            "data/members.csv"
                    );

            List<MemberCsvRecord> records =
                    new CsvToBeanBuilder<MemberCsvRecord>(
                            new InputStreamReader(
                                    resource.getInputStream()
                            )
                    )
                            .withType(MemberCsvRecord.class)
                            .build()
                            .parse();

            log.info(
                    "Member records found: {}",
                    records.size()
            );

            for (MemberCsvRecord record : records) {

                if (memberRepository
                        .findByMemberId(
                                record.getMemberId()
                        )
                        .isPresent()) {

                    continue;
                }

                Member member =
                        Member.builder()
                                .groupId(
                                        record.getGroupId()
                                )
                                .memberId(
                                        record.getMemberId()
                                )
                                .firstName(
                                        record.getFirstName()
                                )
                                .lastName(
                                        record.getLastName()
                                )
                                .dateOfBirth(
                                        LocalDate.parse(
                                                record.getDateOfBirth()
                                        )
                                )
                                .relationship(
                                        record.getRelationship()
                                )
                                .build();

                memberRepository.save(
                        member
                );
            }

            log.info(
                    "Members imported successfully"
            );

        } catch (Exception ex) {

            log.error(
                    "Failed importing members",
                    ex
            );

            throw new RuntimeException(
                    ex
            );
        }
    }
}