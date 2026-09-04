# OnboardAssist 🎓

**AI-Powered Onboarding Query Resolution System**

OnboardAssist is a full-stack conversational onboarding assistant that uses **Angular**, **Spring Boot**, **PostgreSQL + pgvector**, and **Google Gemini** within a **RAG (Retrieval-Augmented Generation)** architecture.

---

## 📁 Project Structure

```
OnboardAssist/
├── onboardassist-backend/      # Spring Boot Java backend
├── onboardassist-frontend/     # Angular 18 frontend
├── database/
│   ├── schema.sql              # PostgreSQL schema (run first)
│   └── sample-knowledge.sql   # Sample knowledge base content
└── README.md
```

---

## 🔧 Prerequisites

| Tool | Version | Download |
|------|---------|----------|
| Java | 17+ | https://www.oracle.com/java/technologies/downloads/ |
| Maven | 3.9+ | https://maven.apache.org/download.cgi |
| Node.js | 18+ | https://nodejs.org/ |
| Angular CLI | 18 | `npm install -g @angular/cli@18` |
| PostgreSQL | 15+ | https://www.postgresql.org/download/windows/ |
| pgvector | latest | Installed via `CREATE EXTENSION vector;` |

---

## 🐘 PostgreSQL + pgvector Setup

### Step 1: Install PostgreSQL
Download and install from: https://www.postgresql.org/download/windows/

During installation:
- Set password for `postgres` user (default: `postgres`)
- Keep default port: `5432`
- ✅ Check "Stack Builder" to install pgvector extension

### Step 2: Install pgvector
After PostgreSQL is installed, you can install pgvector via:

**Option A: pgAdmin** (easiest)
1. Open pgAdmin → connect to your server
2. Create a database named `onboardassist`
3. Open Query Tool and run: `CREATE EXTENSION vector;`

**Option B: Using psql command line**
```bash
psql -U postgres
CREATE DATABASE onboardassist;
\c onboardassist
CREATE EXTENSION vector;
```

**Option C: Download pgvector binaries**
- Visit: https://github.com/pgvector/pgvector/releases
- Download the Windows version matching your PostgreSQL version
- Copy files to PostgreSQL installation directory

### Step 3: Run the Schema
In pgAdmin Query Tool (connected to `onboardassist` database):
```sql
-- Copy and run the contents of database/schema.sql
```

---

## ⚙️ Backend Setup (Spring Boot)

### Step 1: Configure your Gemini API Key
Edit `onboardassist-backend/src/main/resources/application.properties`:
```properties
gemini.api.key=YOUR_ACTUAL_GEMINI_API_KEY_HERE
```
Get your key from: https://aistudio.google.com/

### Step 2: Configure database password (if different from default)
```properties
spring.datasource.password=your_postgres_password
```

### Step 3: Build and run
```bash
cd onboardassist-backend
mvn clean install
mvn spring-boot:run
```

Backend starts at: **http://localhost:8080**

---

## 🌐 Frontend Setup (Angular)

```bash
cd onboardassist-frontend
npm install
ng serve
```

Frontend starts at: **http://localhost:4200**

---

## 📚 Seed the Knowledge Base

After both servers are running, seed the knowledge base using Postman or curl:

### Step 1: Register a user
```http
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "name": "Admin User",
  "email": "admin@company.com",
  "password": "password123"
}
```

### Step 2: Login to get token
```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "admin@company.com",
  "password": "password123"
}
```

Copy the `token` from the response.

### Step 3: Add knowledge documents (repeat for each topic)
```http
POST http://localhost:8080/api/knowledge/documents
Authorization: Bearer YOUR_TOKEN_HERE
Content-Type: application/json

{
  "title": "Mandatory Training Courses",
  "content": "All new joiners must complete mandatory training courses within the first 30 days of joining. Mandatory training is available on the Learning Portal. To access mandatory training: 1. Log in to the Learning Portal. 2. Click on My Learning. 3. Navigate to the Mandatory Training section. 4. Select your assigned course. 5. Complete the training before the due date.",
  "source": "HR Onboarding Guide"
}
```

Repeat for other topics (assessment policy, software installation, etc.)

---

## 🔌 API Reference

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/login` | Login, returns JWT | No |
| POST | `/api/chat/ask` | Ask a question (RAG) | Bearer Token |
| POST | `/api/knowledge/documents` | Add knowledge doc | Bearer Token |
| GET | `/api/knowledge/documents` | List all docs | Bearer Token |
| GET | `/api/users/me` | Get current user | Bearer Token |

---

## 🤖 How RAG Works

```
User Question
      ↓
Generate Embedding (Gemini Embedding API)
      ↓
Vector Similarity Search (pgvector cosine distance)
      ↓
Retrieve Top 5 Relevant Knowledge Chunks
      ↓
Build Prompt (Context + Question + Instructions)
      ↓
Google Gemini (gemini-1.5-flash)
      ↓
Context-Aware Answer
```

---

## 🎤 Voice Input

The app uses the **Web Speech API** (built into Chrome/Edge).
1. Click the 🎤 microphone button
2. Speak your question
3. Text auto-fills the input
4. Press Ask / Send

---

## 📊 Technology Stack

| Layer | Technology |
|-------|-----------|
| Frontend | Angular 18 |
| Backend | Spring Boot 3.2.5 + Java 17 |
| Database | PostgreSQL 15+ |
| Vector Search | pgvector |
| Embeddings | Gemini Embedding 001 |
| LLM | Gemini 1.5 Flash |
| Auth | Spring Security + JWT |
| Voice | Web Speech API |
