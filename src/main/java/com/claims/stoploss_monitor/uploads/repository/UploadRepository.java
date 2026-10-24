package com.claims.stoploss_monitor.uploads.repository;

import com.claims.stoploss_monitor.uploads.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UploadRepository
        extends JpaRepository<Upload, UUID> {
}