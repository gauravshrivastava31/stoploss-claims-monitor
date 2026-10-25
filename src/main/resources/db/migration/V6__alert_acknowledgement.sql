CREATE TABLE alert_acknowledgements
(
    id UUID PRIMARY KEY,

    member_id VARCHAR(50) NOT NULL,

    note TEXT NOT NULL,

    acknowledged_by VARCHAR(100) NOT NULL,

    acknowledged_at TIMESTAMP NOT NULL
);