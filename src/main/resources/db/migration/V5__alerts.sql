CREATE TABLE alerts
(
    id UUID PRIMARY KEY,
    member_id VARCHAR(50) NOT NULL,
    group_id VARCHAR(10) NOT NULL,
    spend NUMERIC(14,2) NOT NULL,
    deductible NUMERIC(14,2) NOT NULL,
    utilization_percent NUMERIC(8,2) NOT NULL,
    alert_level VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL
);