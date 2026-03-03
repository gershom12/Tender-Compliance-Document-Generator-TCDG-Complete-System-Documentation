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
- Enterprise-specific isolated databases  

</details>

---

## 3️⃣ Technology Stack

| Layer               | Technology                     | Purpose                                         |
|--------------------|--------------------------------|------------------------------------------------|
| **Frontend**        | React / Next.js                | Responsive web interface                       |
|                    | TailwindCSS                    | Styling & components                           |
|                    | React PDF Viewer               | Document previews                              |
| **API Gateway**     | Spring Cloud Gateway / Nginx   | Routing & rate-limiting                        |
| **Auth**            | Spring Security + JWT          | Role-based access control, tenant isolation    |
| **Core API**        | Spring Boot (Java 17+)        | Business logic & entity management             |
| **Document Service**| Thymeleaf / Freemarker        | Template rendering                             |
|                    | OpenPDF / iText                | PDF generation                                 |
|                    | DOCX4J                         | DOCX document generation                       |
| **AI Service**      | OpenAI API / Local LLM         | Tender extraction & method statements         |
|                    | Kafka / Spring Boot Async      | Async event-driven processing                  |
| **Compliance Engine**| Spring Boot Module           | Checks company vs tender compliance            |
| **Database**        | PostgreSQL                     | Multi-tenant relational storage                |
|                    | Read replicas                  | Read scalability                               |
| **Cache**           | Redis                          | Caching, rate-limiting, distributed locks     |
| **Object Storage**  | AWS S3 / MinIO                 | Generated document storage                     |
| **Monitoring**      | ELK Stack                      | Logs & audit trails                             |
|                    | Prometheus + Grafana           | Metrics & alerting                              |
|                    | OpenTelemetry                  | Distributed tracing                             |
| **Deployment**      | Docker + Kubernetes            | Container orchestration & scaling              |
| **CI/CD**           | GitHub Actions / Jenkins       | Automated builds & deployments                 |
| **Security**        | TLS 1.2+, AES-256, RBAC       | Secure data & access                            |

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

