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

    paid_amount NUMERIC(14,2) NOT NULL,

    diagnosis_code VARCHAR(50),

    incurred_date DATE,

    version_number INTEGER NOT NULL,

    is_latest BOOLEAN NOT NULL,

    CONSTRAINT fk_claim_version_claim
        FOREIGN KEY (claim_id)
        REFERENCES claims(id),

    CONSTRAINT fk_claim_version_upload
        FOREIGN KEY (upload_id)
        REFERENCES uploads(id)

        ALTER TABLE claim_versions
        ADD COLUMN adjustment_sequence INTEGER NOT NULL,
        ADD COLUMN member_id VARCHAR(50) NOT NULL,
        ADD COLUMN service_date DATE,
        ADD COLUMN paid_date DATE,
        ADD COLUMN status VARCHAR(50);
);