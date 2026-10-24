CREATE TABLE uploads
(
    id UUID PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    uploaded_at TIMESTAMP NOT NULL
);

CREATE TABLE claims
(
    id UUID PRIMARY KEY,

    claim_number VARCHAR(100) NOT NULL UNIQUE,

    member_id VARCHAR(50) NOT NULL,

    group_id VARCHAR(10) NOT NULL
);

CREATE TABLE claim_versions
(
    id UUID PRIMARY KEY,

    claim_id UUID NOT NULL,

    upload_id UUID NOT NULL,

    adjustment_sequence INTEGER NOT NULL,

    member_id VARCHAR(50) NOT NULL,

    service_date DATE,

    paid_date DATE,

    paid_amount NUMERIC(14,2) NOT NULL,

    status VARCHAR(50),

    diagnosis_code VARCHAR(50),

    version_number INTEGER NOT NULL,

    is_latest BOOLEAN NOT NULL,

    CONSTRAINT fk_claim_version_claim
        FOREIGN KEY (claim_id)
        REFERENCES claims(id),

    CONSTRAINT fk_claim_version_upload
        FOREIGN KEY (upload_id)
        REFERENCES uploads(id)
);
CREATE TABLE alerts
(
    id UUID PRIMARY KEY,

    group_id VARCHAR(10),

    member_id VARCHAR(50),

    alert_type VARCHAR(100),

    message VARCHAR(1000),

    resolved BOOLEAN NOT NULL,

    created_at TIMESTAMP
);
