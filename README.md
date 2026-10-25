## Key Features

- Multi-TPA claim ingestion
- Claim versioning and auditability
- Member spend calculations
- Deductible utilization alerts
- Alert acknowledgement workflow
- Role-based access control
- Broker-specific data isolation
- React dashboard
- Docker support
- Infrastructure-as-Code templates


Files:

```text
Dockerfile
docker-compose.yml
```

---

# Infrastructure as Code

Terraform templates included:

```text
terraform/
 ├── main.tf
 ├── variables.tf
 └── outputs.tf
```

These demonstrate cloud deployment structure and can be expanded for production deployment.

---

# Project Highlights

Implemented:

✅ Authentication and Authorization

✅ Broker Data Isolation

✅ Claim Upload API

✅ Duplicate Upload Prevention

✅ Claim Versioning

✅ Spend Calculation

✅ Alert Generation

✅ Alert Acknowledgement

✅ Access-Control Testing

✅ React Dashboard

✅ PostgreSQL + Flyway

✅ Docker

✅ GitHub Actions

✅ Terraform

✅ Multi-TPA Claim Upload Support

✅ File Validation

✅ Upload History Tracking

---

# Future Improvements

- OAuth2 / OpenID Connect
- Hash-based upload deduplication
- Alert lifecycle management
- Notification channels
- Upload schema validation
- Claim audit reporting
- Advanced reporting dashboards
- Production-grade observability

---

# Submission Notes

This solution focuses on correctness of business rules, security, claim versioning, maintainability and testability while keeping the overall architecture simple and easy to operate.