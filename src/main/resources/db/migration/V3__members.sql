CREATE TABLE IF NOT EXISTS members
(
    id UUID PRIMARY KEY,
    group_id VARCHAR(10) NOT NULL,
    member_id VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    date_of_birth DATE,
    relationship VARCHAR(50)
);