# AI.md

## AI Tools Used

- Microsoft Copilot
- ChatGPT
- IntelliJ AI Assistance

---

## Development Workflow

AI was used to:

- Generate entity classes
- Generate repository interfaces
- Generate Flyway migration scripts
- Build REST controllers
- Generate service implementations
- Create React dashboard
- Generate unit tests
- Produce documentation

---

## Three Cases Where AI Was Wrong

### Case 1

Issue:
AI suggested repository code with incorrect method names.

Detection:
Compilation failure.

Resolution:
Compared repository interface methods against implementation.

---

### Case 2

Issue:
AI generated incorrect Mockito test list typing.

Example:

```java
List.of(spendRow)
```

returned:

```java
List<Object>
```

instead of:

```java
List<Object[]>
```

Detection:
Compilation error.

Resolution:
Explicitly declared List<Object[]>.

---

### Case 3

Issue:
React validation did not handle blank member IDs.

Detection:
Functional testing.

Resolution:
Added client-side validation before API call.

---

## Tasks Not Delegated Fully To AI

### Business Analysis

Reason:
Human review was required to interpret stop-loss insurance concepts and verify behavior.

### Testing

Reason:
All generated code was executed and validated manually.

### Final Design Decisions

Reason:
Required human judgment around architecture and tradeoffs.

---

## Estimated Time

Backend:
~12 hours

Frontend:
~3 hours

Testing:
~2 hours

Documentation:
~2 hours

Total:
~19 hours
