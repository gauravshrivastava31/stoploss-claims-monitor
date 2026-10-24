package com.claims.stoploss_monitor.uploads.service;

public interface CsvImportService {

    void importGroups();

    void importPolicies();

    void importMembers();

    void importLasers();
}