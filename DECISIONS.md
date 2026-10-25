# DECISIONS.md

# Architecture and Trade-Off Decisions

This document explains the major design decisions made during implementation, why they were chosen, what risks they introduce, and possible future improvements.

---

# Decision 1: Claim Versioning Instead of Updates

## Decision

Claims are never updated in place.

Every modification creates a new `ClaimVersion` record while the previous version is retained.

Only a single version is marked:

```text
is_latest = true
```

at any given time.

---

## Why

Stop-loss claim data is frequently adjusted by TPAs.

An immutable version history provides:

- Auditability
- Historical traceability
- Recovery from incorrect updates
- Regulatory transparency

---

## Risks

Storage usage grows over time.

Queries become slightly more complex because only latest versions should be used for calculations.

---

## Future Improvement

Archive very old versions into lower-cost storage when retention requirements permit.

Estimated effort:

~1 day

---

# Decision 2: Aggregations Use Only Latest Claim Versions

## Decision

All spend calculations operate only on:

```text
is_latest = true
```

claim versions.

---

## Why

Historical claim versions should not contribute to current spend totals.

Using only the latest version ensures calculations reflect the most recent TPA adjustments.

---

## Risks

A bug that incorrectly marks versions as latest can affect calculations.

---

## Future Improvement

Add database constraints and automated validation jobs to verify only one latest version exists per claim.

Estimated effort:

~4 hours

---

# Decision 3: Basic Authentication

## Decision

Spring Security HTTP Basic Authentication was used.

Roles:

```text
ROLE_ANALYST
ROLE_BROKER
```

---

## Why

Provides a secure and simple solution appropriate for the assignment scope.

Minimizes implementation complexity while enabling role-based access control.

---

## Risks

Not suitable for internet-facing production systems.

Credentials are static and in-memory.

---

## Future Improvement

Replace with:

- OAuth2
- OpenID Connect
- Azure AD / Entra ID

Estimated effort:

~1-2 days

---

# Decision 4: Broker Data Isolation

## Decision

Broker users are restricted to their assigned groups.

Example:

```text
brokerA -> GA

brokerB -> GB
```

Analysts can access all groups.

---

## Why

The assignment explicitly requires broker-level data isolation.

This prevents data leakage between employer groups.

---

## Risks

Current mapping is static and hardcoded.

New brokers require code changes.

---

## Future Improvement

Persist broker-to-group mappings in the database and manage them administratively.

Estimated effort:

~1 day

---

# Decision 5: Alert Acknowledgement Persistence

## Decision

Alert acknowledgements are stored separately with:

- memberId
- note
- acknowledgedBy
- acknowledgedAt

---

## Why

Creates an audit trail of analyst review activity.

Allows future workflow expansion.

---

## Risks

Acknowledged alerts are not currently hidden or resolved automatically.

---

## Future Improvement

Implement:

- alert status lifecycle
- resolved state
- comments
- audit history

Estimated effort:

~1 day

---

# Decision 6: Duplicate Upload Detection

## Decision

Duplicate claim file uploads are detected using:

```text
file_name
```

stored in the uploads table.

---

## Why

Simple implementation that satisfies idempotency requirements.

Prevents accidental re-processing of identical uploads.

---

## Risks

Renaming a file allows duplicate content to bypass detection.

Example:

```text
claims_june.csv
claims_june_copy.csv
```

---

## Future Improvement

Store SHA-256 checksums of uploaded files and compare hashes instead of filenames.

Estimated effort:

~4 hours

---

# Decision 7: Support For Multiple TPA Layouts

## Decision

The upload service detects the claim file layout using CSV headers and parses accordingly.

Supported formats:

### TPA One

```csv
Claim ID,Adj Seq,Member ID,...
```

### TPA Two

```csv
claim_number,version,member,...
```

---

## Why

The assignment requires support for multiple administrator file formats.

---

## Risks

Future file format changes may break parsing.

---

## Future Improvement

Introduce:

- schema version detection
- configurable mappings
- validation framework

Estimated effort:

~1-2 days

---

# Decision 8: Monolithic Architecture

## Decision

The solution uses a single Spring Boot application.

---

## Why

Provides faster development, simpler deployment and lower operational overhead.

Appropriate for assignment scope.

---

## Risks

Future scaling may require service decomposition.

---

## Future Improvement

Separate workloads into:

- Claims Service
- Upload Service
- Alert Service

Estimated effort:

~3-5 days

---

# Decision 9: Infrastructure Skeletons

## Decision

Docker, GitHub Actions and Terraform were implemented as starter templates rather than production-ready infrastructure.

---

## Why

The assignment focuses on application functionality and allows AI-assisted infrastructure generation.

---

## Risks

Additional work would be required before production deployment.

---

## Future Improvement

Enhance:

- Terraform modules
- remote state management
- CI/CD release pipelines
- environment-specific deployments

Estimated effort:

~2-3 days

---

# Known Limitations

1. Basic Authentication uses in-memory users.
2. Duplicate upload detection relies on file names rather than content hashes.
3. Alert lifecycle is limited to acknowledgements.
4. Upload validation is intentionally lightweight.
5. Infrastructure templates are starter implementations.

---

# Summary

The implementation prioritizes:

- correctness of business rules
- claim versioning
- spend calculation accuracy
- broker data isolation
- testability
- maintainability

while keeping operational complexity appropriate for the scope of the assignment.