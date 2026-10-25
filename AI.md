# AI.md

# AI Usage Report

## AI Tools Used

The following AI tools were used throughout development:

- Microsoft Copilot
- ChatGPT
- IntelliJ AI Assistant

---

## Development Workflow

AI was used to accelerate implementation, troubleshooting, infrastructure setup and documentation generation.

AI-assisted areas included:

- Entity generation
- Repository generation
- REST API implementation
- Flyway migration generation
- React dashboard components
- Spring Security configuration
- Broker authorization implementation
- Claim upload workflow
- Claim versioning logic
- Alert generation workflow
- Alert acknowledgement workflow
- Unit test generation
- Dockerfile creation
- Docker Compose configuration
- GitHub Actions workflow
- Terraform templates
- Documentation authoring

All generated code was reviewed, modified, tested and validated before being committed.

---

## Three Cases Where AI Was Wrong

### Case 1 – Repository Method Generation

Issue:

AI suggested repository methods that did not match actual entity relationships.

Detection:

Compilation failures during Maven build.

Resolution:

Reviewed entity mappings and manually corrected repository methods and relationship traversal.

---

### Case 2 – Mockito Collection Typing

Issue:

AI suggested:

```java
List.of(spendRow)
```

which produced:

```java
List<Object>
```

instead of:

```java
List<Object[]>
```

Detection:

Compilation failure in unit tests.

Resolution:

Explicitly created typed collections using:

```java
List<Object[]> spendData =
        new ArrayList<>();
```

---

### Case 3 – Security Configuration Conflict

Issue:

AI-generated security configuration conflicted with an existing SecurityConfig class, causing duplicate bean definitions.

Detection:

Application startup failure with bean registration conflicts.

Resolution:

Reviewed startup logs, removed duplicate configuration and consolidated security settings into a single implementation.

---

## Human Validation Performed

The following activities were performed manually:

- Maven compilation verification
- Integration testing
- Security validation
- Authorization testing
- Database verification
- API validation via Postman
- React dashboard testing
- Upload workflow testing
- Claim versioning verification
- Swagger verification

Generated code was never accepted without review and validation.

---

## Tasks Not Delegated Fully To AI

### Business Rule Validation

AI generated implementation suggestions, but business rules were validated manually.

Examples:

- Stop-loss deductible calculations
- Alert threshold definitions
- Broker access restrictions
- Claim versioning behavior

---

### Testing and Verification

AI generated test scaffolding, but all workflows were manually executed and verified against PostgreSQL and application behavior.

---

### Architectural Decisions

Final architectural decisions were made manually, including:

- Claim versioning approach
- Broker data isolation
- Security strategy
- Upload deduplication strategy
- Alert acknowledgement workflow

---

## Significant AI-Assisted Deliverables

The following features were implemented with AI assistance and then reviewed manually:

- Authentication and authorization
- Broker access control
- Access-control testing
- Claim versioning
- Spend calculations
- Alert generation
- Alert acknowledgement
- Claim upload API
- Duplicate upload prevention
- React dashboard
- Docker configuration
- GitHub Actions CI pipeline
- Terraform templates
- Flyway migrations
- Project documentation

---

## Estimated Effort

### Backend

Approximately 12 hours

### Frontend

Approximately 3 hours

### Testing and Validation

Approximately 3 hours

### Documentation and Infrastructure

Approximately 2 hours

### Total

Approximately 20 hours

---

## Reflection

AI significantly accelerated routine development tasks, infrastructure setup and documentation generation.

The greatest value came from reducing boilerplate implementation effort, allowing more time to focus on business logic, validation, security, testing and architectural decisions.

The final application combines AI-assisted development with manual review, testing and validation to ensure correctness and maintainability.