CREATE TABLE groups
(
    group_id VARCHAR(10) PRIMARY KEY,
    group_name VARCHAR(255) NOT NULL
);

CREATE TABLE policy_years
(
    id UUID PRIMARY KEY,

    group_id VARCHAR(10) NOT NULL,

    policy_start DATE NOT NULL,

    policy_end DATE NOT NULL,

    specific_deductible NUMERIC(12,2) NOT NULL,

    CONSTRAINT fk_policy_group
        FOREIGN KEY (group_id)
        REFERENCES groups(group_id)
);

CREATE TABLE members
(
    id UUID PRIMARY KEY,

    group_id VARCHAR(10) NOT NULL,

    member_id VARCHAR(50) NOT NULL,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100) NOT NULL,

    date_of_birth DATE,

    relationship VARCHAR(50),

    CONSTRAINT fk_member_group
        FOREIGN KEY (group_id)
        REFERENCES groups(group_id),

    CONSTRAINT uk_group_member
        UNIQUE(group_id, member_id)
);

CREATE TABLE lasers
(
    id UUID PRIMARY KEY,

    group_id VARCHAR(10) NOT NULL,

    member_id VARCHAR(50) NOT NULL,

    policy_start DATE NOT NULL,

    specific_deductible NUMERIC(12,2) NOT NULL
);