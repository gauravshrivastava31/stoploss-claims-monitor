# DECISIONS.md

## Overview

This document captures key design decisions, tradeoffs, assumptions, and shortcuts taken during implementation.

---

## Technology Stack

### Spring Boot

Decision:
Use Spring Boot for backend development.

Reason:
- Rapid development
- Built-in REST support
- Dependency Injection
- JPA integration
- Strong ecosystem

Tradeoff:
Slightly heavier than lightweight frameworks.

---

### PostgreSQL

Decision:
Use PostgreSQL as the relational database.

Reason:
- Strong ACID guarantees
- Excellent support for relational data
- Suitable for insurance and healthcare domains
- Supports foreign keys and constraints

Tradeoff:
Requires more administration than embedded databases.

---

### Flyway

Decision:
Use Flyway migrations.

Reason:
- Version-controlled database schema
- Reproducible deployments
- Easy onboarding

Tradeoff:
Additional migration management.

---

### React Dashboard

Decision:
Create a lightweight React frontend.

Reason:
- Modern UI framework
- Separation of frontend and backend
- Demonstrates full-stack capability

Tradeoff:
Additional build process.

---

## Claim Versioning

Decision:
Store multiple versions of claims rather than overwriting existing records.

Reason:
Insurance claims may be adjusted over time.

Benefits:
- Auditable history
- Data lineage
- Historical analysis

Tradeoff:
More storage required.

---

## Alert Thresholds

Decision:
Implemented utilization-based alerts.

Initial implementation:
- WARNING = 80%
- HIGH = 90%
- CRITICAL = 100%

Assignment Requirement:
- WARNING = 50%
- HIGH = 75%
- CRITICAL = 100%

Action:
Thresholds should be aligned with assignment rules before final submission.

---

# Shortcuts

## Authentication and Authorization

Shortcut:
Roles not fully implemented initially.

Risk:
Cannot enforce broker data isolation.

Fix Cost:
Medium (~4-6 hours).

---

## File Upload API

Shortcut:
CSV files imported through startup services.

Risk:
Analysts cannot upload files through UI/API.

Fix Cost:
Medium (~6-8 hours).

---

## Alert Acknowledgement

Shortcut:
Read-only alert viewing.

Risk:
No workflow management.

Fix Cost:
Low (~2 hours).

---

## Docker

Shortcut:
Application runs locally without containerization.

Risk:
Deployment setup not standardized.

Fix Cost:
Low (~1 hour).

---

## Terraform

Shortcut:
Infrastructure code not yet implemented.

Risk:
Deployment not automated.

Fix Cost:
Low (~2 hours using AI-generated templates).

## Claim Upload API
Shortcut:
Current State

Claims are imported through startup data-loading services rather than a dedicated upload API.

Why This Is Acceptable For Now

The primary focus was implementing claim versioning, spend calculations,
alerts, authorization, API design, testing, and the React dashboard.

What Could Go Wrong

Analysts cannot upload new claim files without restarting the application.

This limits operational usability and prevents self-service file ingestion.

What Would Tell Us To Fix It

Need for recurring claim ingestion.

Need for analyst self-service uploads.

Need to support multiple monthly claim files without application restarts.

Proposed Fix

Implement:

POST /api/claims/upload

using MultipartFile and OpenCSV.

Return:

- processed rows
- failed rows
- validation errors

and support both administrator claim layouts.

Estimated Cost

Approximately 6-10 hours including validation, duplicate-upload detection,
error reporting and automated tests.

## Alert Acknowledgement
Shortcut:
Current State

Alerts are generated and displayed but are not currently acknowledged by analysts.

Why This Is Acceptable For Now

The business rules for generating alerts are implemented and working.

What Could Go Wrong

Repeated review of the same alert.

No audit trail of alert review activity.

What Would Tell Us To Fix It

Operational users begin managing alerts daily and require tracking.

Proposed Fix

Add:

POST /api/alerts/{id}/acknowledge

with analyst notes and audit timestamps.

Estimated Cost

2-4 hours.
