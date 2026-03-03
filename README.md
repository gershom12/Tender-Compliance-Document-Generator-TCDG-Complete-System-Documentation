# 🎯 TCDG – Tender Compliance Document Generator

[![Version](https://img.shields.io/badge/version-1.0-blue)](https://github.com/your-repo/tcdg)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

**Author:** Gershom Maluleke  
**Target Market:** SMEs bidding for government tenders  
**Deployment:** Multi-tenant SaaS  

---

## 1️⃣ Executive Summary

**TCDG** is a cloud-based SaaS platform designed to help SMEs automatically generate government tender compliance documents. Users upload company information once, and the system generates:

- Company profile  
- Safety file  
- BEE affidavit  
- Method statements  
- Full tender compliance packs  

**Key Features:**

- Multi-tenant SaaS architecture  
- AI-powered tender extraction  
- Event-driven asynchronous document processing  
- Document versioning and audit trails  
- Secure, POPIA-compliant storage  

---

## 2️⃣ System Goals

<details>
<summary>Primary Goals</summary>

- Support multi-tenant SaaS with strict data isolation  
- Ensure high availability, reliability, and scalability  
- Support async AI processing for complex tenders  
- Secure sensitive data in compliance with South African regulations  

</details>

<details>
<summary>Non-Goals (Phase 1)</summary>

- Direct tender portal submission  
- Mobile app interface (planned Phase 2)  
- Enterprise-specific isolated DBs  

</details>

---

## 3️⃣ Technology Stack

| Layer              | Technology                     | Purpose                                          |
|-------------------|--------------------------------|-------------------------------------------------|
| **Frontend**       | React / Next.js                | Responsive web interface                        |
|                   | TailwindCSS                    | Styling & components                            |
|                   | React PDF Viewer               | Document previews                               |
| **API Gateway**    | Spring Cloud Gateway / Nginx   | Routing & rate-limiting                         |
| **Auth**           | Spring Security + JWT          | Role-based access control, tenant isolation     |
| **Core API**       | Spring Boot (Java 17+)        | Business logic & entity management              |
| **Document Service**| Thymeleaf / Freemarker        | Template rendering                              |
|                   | OpenPDF / iText                | PDF generation                                  |
|                   | DOCX4J                         | DOCX document generation                        |
| **AI Service**     | OpenAI API / Local LLM         | Tender extraction & method statements          |
|                   | Kafka / Spring Boot Async      | Async event-driven processing                   |
| **Compliance Engine**| Spring Boot Module           | Checks company vs tender compliance             |
| **Database**       | PostgreSQL                     | Multi-tenant relational storage                 |
|                   | Read replicas                  | Read scalability                                |
| **Cache**          | Redis                          | Caching, rate-limiting, distributed locks      |
| **Object Storage** | AWS S3 / MinIO                 | Generated document storage                       |
| **Monitoring**     | ELK Stack                      | Logs & audit trails                              |
|                   | Prometheus + Grafana           | Metrics & alerting                               |
|                   | OpenTelemetry                  | Distributed tracing                               |
| **Deployment**     | Docker + Kubernetes            | Container orchestration & scaling               |
| **CI/CD**          | GitHub Actions / Jenkins       | Automated builds & deployments                  |
| **Security**       | TLS 1.2+, AES-256, RBAC       | Secure data & access                              |

---

## 4️⃣ Project Structure (Spring Boot)

```text
tcdg-backend/
│
├─ src/main/java/com/tcdg/
│   ├─ TcdgApplication.java
│   ├─ config/          # Security, Kafka, Swagger, Web
│   ├─ common/          # Exceptions, utils, constants, DTOs
│   ├─ auth/            # Auth controllers, services, entities, repos
│   ├─ company/         # Company CRUD, directors, staff, equipment
│   ├─ document/        # Document generation, versioning
│   ├─ ai/              # Tender extraction & method statement async processing
│   ├─ compliance/      # Compliance engine
│   ├─ billing/         # Subscriptions & usage tracking
│   └─ messaging/       # Kafka producers & consumers
│
├─ src/main/resources/
│   ├─ application.yml
│   ├─ templates/       # Thymeleaf / Freemarker
│   └─ db/migration/    # Flyway / Liquibase
└─ pom.xml / build.gradle
5️⃣ Entity Relationship Diagram (ERD)
<details> <summary>Click to expand ERD</summary>
Tenant 1---* User
Tenant 1---* Company 1---* Director
                      1---* Staff
                      1---* Equipment
Company 1---* Document 1---* DocumentVersion
DocumentType 1---* Document
Tenant 1---* Tender 1---* ComplianceResult
Tenant 1---* AIJob

Entity Notes:

Tenant → Multi-tenant isolation

DocumentVersion → Full versioning & audit trails

AIJob → Async tracking of AI tender extraction & method statements

ComplianceResult → Scoring and missing item detection

</details>
6️⃣ REST API Documentation

Base URL: https://api.tcdg.com/v1
Authentication: JWT + tenant_id header

<details> <summary>Auth APIs</summary>
Endpoint	Method	Request	Response
/auth/register	POST	{ username, email, password, tenant_name }	{ user_id, token }
/auth/login	POST	{ username, password }	{ token, refresh_token }
/auth/refresh	POST	{ refresh_token }	{ token, refresh_token }
/auth/logout	POST	{}	{ message }
</details> <details> <summary>Company APIs</summary>

/companies → POST, GET, PUT, DELETE

/companies/{companyId}/directors → CRUD directors

/companies/{companyId}/staff → CRUD staff

/companies/{companyId}/equipment → CRUD equipment

</details> <details> <summary>Document APIs</summary>

/documents/generate → POST (Generate PDF/DOCX)

/documents/{id} → GET (Download document)

/documents/{id}/status → GET

/documents/{id}/versions → GET (List versions)

/documents/{id}/versions/{version_id} → GET (Download version)

</details> <details> <summary>Tender APIs</summary>

/tenders → POST, GET

/tenders/{id} → GET, DELETE

</details> <details> <summary>AI APIs</summary>

/ai/extract → POST (AI extraction & method statement)

/ai/job/{job_id} → GET (Job status)

/ai/job/{job_id}/download → GET (Download AI-generated document)

</details> <details> <summary>Compliance APIs</summary>

/compliance/check → POST (Run compliance check)

/compliance/{id} → GET (Result & score)

/compliance/{id}/download → GET (PDF report)

</details> <details> <summary>Billing APIs</summary>

/billing/subscription → POST, GET

/billing/usage → GET

</details> <details> <summary>Admin APIs</summary>

/admin/tenants → GET

/admin/users → GET

/admin/logs → GET

</details>
7️⃣ Architecture Diagram
Load Balancer
     ↓
Kubernetes Cluster
     ↓
------------------------------
| API Pods (Spring Boot)     |
| Document Worker Pods       |
| AI Worker Pods             |
------------------------------
     ↓
PostgreSQL + Read Replicas
Redis Cache
S3 / MinIO Storage
Kafka Message Broker
8️⃣ Scalability & Reliability

Stateless services → Horizontal scaling via Kubernetes

Kafka → Async event-driven processing for documents & AI jobs

Redis → Caching, distributed locks, rate-limiting

PostgreSQL + read replicas → Read scalability

S3 → Versioned document storage

Retry & circuit breaker patterns → Fault tolerance

9️⃣ Security & Compliance

TLS 1.2+ for all traffic

AES-256 encryption at rest

Field-level encryption for sensitive data

RBAC + tenant-scoped JWT

Audit logging for all sensitive operations

POPIA compliance

🔟 Future Enhancements

Direct integration with eTenders Portal

Mobile application (React Native)

Enterprise isolated DB per tenant

AI-powered compliance scoring & recommendations
