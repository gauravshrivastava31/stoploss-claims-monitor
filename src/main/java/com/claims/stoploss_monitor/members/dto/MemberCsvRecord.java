package com.claims.stoploss_monitor.members.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCsvRecord {

    @CsvBindByName(column = "group_id")
    private String groupId;

    @CsvBindByName(column = "member_id")
    private String memberId;

    @CsvBindByName(column = "first_name")
    private String firstName;

    @CsvBindByName(column = "last_name")
    private String lastName;

    @CsvBindByName(column = "date_of_birth")
    private String dateOfBirth;

    @CsvBindByName(column = "relationship")
    private String relationship;
}
