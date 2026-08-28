# MathPlatform

**Plataforma educacional full stack** para estudo de Matemática (ENEM): videoaulas, questões comentadas e fórum de dúvidas com autenticação JWT, user roles, pagamento real (PagBank) e deploy automatizado.

[![Live](https://img.shields.io/badge/demo-math.cesarburil.com.br-1f6feb?style=for-the-badge)](https://math.cesarburil.com.br)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-21-DD0031?style=for-the-badge&logo=angular&logoColor=white)](https://angular.dev/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)](.github/workflows)

> Projeto de portfólio de [Cesar Buril](https://github.com/cesarburil), Engenheiro de Computação.  
> **Publicado (utilize agora mesmo):** [math.cesarburil.com.br](https://math.cesarburil.com.br) · conta de exemplo: `demo` / `demo`

![MathPlatform — home, aula, checkout e exercícios](docs/print_sistema.png)

## Arquitetura

Três containers no mesmo Docker Compose. O Nginx do **host** termina HTTPS e encaminha para o frontend; o Nginx **dentro** da imagem Angular serve a SPA e faz proxy de `/api` e `/webhook` para o Spring.

```mermaid
flowchart LR
  U[Usuário] -->|HTTPS| N[Nginx no VPS]
  N -->|localhost:8080| FE[mathv2-frontend<br/>Angular + Nginx]
  FE -->|/api e /webhook| API[mathv2-api<br/>Spring Boot 4 / Java 21]
  API --> DB[(PostgreSQL 16)]
  API -->|orders sandbox| PB[PagBank]
  PB -->|webhook PAID| API
  API -->|SSE /sse| FE
  GH[GitHub Actions] -->|build, test, push| DH[Docker Hub]
  GH -->|ssh + compose up| N
```

### Backend (camadas)

```mermaid
flowchart TB
  subgraph HTTP
    C[Controllers REST]
  end
  subgraph APP
    S[Services]
    CV[Converters]
    DTO[DTOs]
  end
  subgraph DATA
    R[Repositories JPA]
    M[Entities]
  end
  C --> S
  C --> DTO
  S --> CV
  S --> R
  R --> M
  CV --> DTO
  CV --> M
```

---

## O que o aluno (e o admin) fazem

```mermaid
flowchart TD
  A[Landing / home] --> B{Logado?}
  B -->|não| C[Login ou cadastro]
  C --> D[JWT no localStorage]
  B -->|sim| D
  D --> E{Papel}
  E -->|USER| F[Assinar R$ 5 / 1 mês]
  F --> G[Cartão criptografado no browser]
  G --> H[POST /pay]
  H --> I[PagBank sandbox]
  I --> J[Webhook PAID]
  J --> K[Role PREMIUM + evento SSE]
  K --> L[Aulas, exercícios, fórum]
  E -->|PREMIUM ou ADMIN| L
  E -->|ADMIN| M[CRUD de categorias, aulas e questões]
```

---

## Funcionalidades

**Estudo**
- Catálogo por tema (aritmética, álgebra, funções, geometria, trigonometria, combinatória, probabilidade, estatística, matemática financeira)
- Videoaulas com player do YouTube
- Questões de múltipla escolha (5 alternativas, 1 correta), dificuldade e correção no servidor
- Fórum: tópicos e respostas vinculados ao usuário autenticado

**Conta e acesso**
- Cadastro e login com JWT (claim `role`)
- Três roles: `USER`, `PREMIUM`, `ADMIN` (admin herda premium)
- Guards de rota no Angular e `@PreAuthorize` no Spring
- Tema claro / escuro

**Pagamento**
- Checkout de cartão com criptografia PagBank no cliente (o backend nunca vê o PAN)
- Pedido na API sandbox, webhook público e atualização para `PREMIUM`
- Confirmação em tempo real via **Server-Sent Events** (`GET /sse`)

**Operação**
- Painel admin para categorias, aulas e questões
- Swagger UI para explorar a API
- Seed do catálogo na subida da aplicação
- Pipeline separado para backend e frontend (compile → testes → imagem → deploy)

---

## Stack

| Camada | Tecnologia |
| --- | --- |
| Frontend | Angular 21 (standalone, signals), TypeScript, Tailwind CSS 4, RxJS |
| Backend | Java 21, Spring Boot 4.1, Spring Security, Spring Data JPA, Validation |
| Auth | JWT (JJWT 0.12), BCrypt, sessão HTTP stateless |
| Banco | PostgreSQL 16 · H2 em testes |
| Pagamento | PagBank / PagSeguro sandbox, OkHttp, webhook, SSE |
| Docs | springdoc-openapi (Swagger UI) |
| Front de borda | Nginx (SPA + reverse proxy) |
| Entrega | Docker multi-stage, Docker Compose, GitHub Actions, Docker Hub, VPS |

---

## Modelo de dados

```mermaid
erDiagram
  USER ||--o{ COMMENT : escreve
  USER {
    long id
    string username
    string password
    enum role
  }
  PROFILE {
    string full_name
    string cpf
    string phone
    string bio
  }
  USER ||--|| PROFILE : possui
  CATEGORY ||--o{ LESSON : agrupa
  CATEGORY ||--o{ QUESTION : agrupa
  CATEGORY {
    long id
    string title
  }
  LESSON {
    long id
    string title
    string description
    string video
  }
  QUESTION ||--|{ ALTERNATIVE : tem
  QUESTION {
    long id
    string title
    enum difficulty
    string video
  }
  ALTERNATIVE {
    long id
    string text
    boolean correct
  }
  COMMENT ||--o{ ANSWER : recebe
  COMMENT {
    long id
    string title
  }
  ANSWER {
    long id
    string text
  }
```

---

## Pagamento (sequência)

O cartão é criptografado no browser com a chave pública do PagBank. O Spring envia só o payload criptografado, registra o `username` como `reference_id` e promove o usuário quando o webhook chega com `PAID`.

```mermaid
sequenceDiagram
  actor Aluno
  participant Angular
  participant Spring
  participant PagBank

  Aluno->>Angular: preenche o cartão
  Angular->>Angular: PagSeguro.encryptCard
  Angular->>Spring: GET /sse
  Angular->>Spring: POST /pay (encryptedCard + JWT)
  Spring->>PagBank: POST /orders (sandbox)
  PagBank-->>Spring: 202 / order criada
  PagBank->>Spring: POST /webhook status PAID
  Spring->>Spring: USER → PREMIUM
  Spring-->>Angular: evento SSE
  Angular->>Aluno: acesso liberado /categories
```

---

## Autenticação e autorização

| Papel | Quem é | O que libera |
| --- | --- | --- |
| `USER` | Cadastro padrão | Conta + checkout |
| `PREMIUM` | Pagamento confirmado | Aulas, exercícios, fórum |
| `ADMIN` | Operação do conteúdo | Tudo do premium + CRUD |

- Filtro JWT antes de `UsernamePasswordAuthenticationFilter`
- Rotas públicas: `/login`, `/register`, `/webhook`, `/sse`, Swagger
- Rotas de escrita de catálogo: `hasRole('ADMIN')`
- Angular: `authguardGuard` (precisa de token) e `payGuard` (bloqueia quem já é premium)
- Interceptor HTTP anexa `Authorization: Bearer …` e limpa a sessão em `401`

Documentação interativa da API: `http://localhost:8080/swagger-ui/index.html` (no container) ou via `/api` no ambiente publicado.

---

## Estrutura do repositório

```
MathPlatform/
├── mathBackend/                 Spring Boot (Java 21)
│   └── src/main/java/.../mathBackend/
│       ├── auth/                login, JWT, UserDetails, SecurityFilterChain
│       ├── admin/               ping de admin
│       ├── category/            CRUD + seeder do catálogo
│       ├── lesson/              videoaulas
│       ├── question/            questões e correção
│       ├── comment/             fórum (Comment + Answer)
│       ├── payment/             PagBank, webhook, SSE
│       ├── profile/             dados do usuário autenticado
│       └── infra/exception/     handler global
├── mathFrontend/                Angular 21
│   └── src/app/
│       ├── components/          home, aulas, questões, fórum, admin, pagamento
│       ├── services/            HTTP + SSE
│       ├── models/              contratos da API
│       └── http/                interceptors (auth + toast de erro)
├── docker/
│   ├── backend/Dockerfile
│   ├── frontend/Dockerfile
│   ├── host-nginx/              HTTPS no VPS
│   └── seed-math-content.sql
├── docker-compose.yml
└── .github/workflows/           pipeline-backend.yml + pipeline-frontend.yml
```

---

## Como rodar localmente

### Requisitos

- JDK 21, Maven Wrapper (`./mvnw`)
- Node 22 + npm
- Docker (PostgreSQL, ou o stack inteiro)

### 1. Banco

Na raiz do repositório, com um `.env` contendo `POSTGRES_USERNAME` e `POSTGRES_PASSWORD`:

```bash
docker compose up -d postgres
```

O Postgres sobe em **`localhost:5433`**, database `mathv2`.

### 2. Backend

```bash
cd mathBackend
cp src/main/resources/application.properties.example \
   src/main/resources/application.properties
```

Exporte as variáveis (o arquivo `application.properties` não é versionado):

```bash
export POSTGRES_URL=jdbc:postgresql://localhost:5433/mathv2
export POSTGRES_USERNAME=...
export POSTGRES_PASSWORD=...
export PAGBANK_TOKEN=...          # sandbox
export SITE_URL=http://localhost:8081
```

```bash
./mvnw spring-boot:run
```

Em Docker Compose a API é publicada em **`8081→8080`**, que é o endereço usado pelo Angular em desenvolvimento.

```bash
cd mathBackend
./mvnw test -Dspring.profiles.active=test
```

Os testes usam H2 em memória (`application-test.properties`) e o seeder **não** roda no profile `test`.

### 3. Frontend

```bash
cd mathFrontend
npm install
npm start
```

Abre `http://localhost:4200`. Em `environment.development.ts`, `apiUrl` aponta para `http://localhost:8081`.

```bash
npm test
```

### 4. Stack completa (imagens)

As imagens vêm do Docker Hub (`${DOCKERHUB_USERNAME}/math-backend` e `math-frontend`). Com o `.env` preenchido:

```bash
docker compose pull
docker compose up -d
```

| Serviço | Porta no host |
| --- | --- |
| Frontend (Nginx) | `127.0.0.1:8080` |
| API Spring | `8081` |
| PostgreSQL | `5433` |

Produção: [https://math.cesarburil.com.br](https://math.cesarburil.com.br), o Nginx do host faz proxy para o container do frontend; `/api` e `/webhook` seguem para o Spring.

---

## API (visão geral)

| Método | Rota | Quem |
| --- | --- | --- |
| `POST` | `/login` | público |
| `POST` | `/register` | público |
| `GET` | `/profile/` | autenticado |
| `GET` | `/categories/` | autenticado |
| `POST/PUT/DELETE` | `/categories/…` | `ADMIN` |
| `GET` | `/lessons/`, `/lessons/c/{categoryId}`, `/lessons/{id}` | autenticado |
| `POST/PUT/DELETE` | `/lessons/…` | `ADMIN` |
| `GET` | `/questions/`, `/questions/{id}` | autenticado |
| `POST` | `/questions/verify` | autenticado |
| `POST/PUT/DELETE` | `/questions/…` | `ADMIN` |
| `CRUD` | `/comments/…`, `/answers/…` | autenticado |
| `POST` | `/pay` | autenticado |
| `POST` | `/webhook` | PagBank (público) |
| `GET` | `/sse` | stream de pagamento |
| `GET` | `/admin/` | `ADMIN` |

---

## CI/CD

Dois workflows em [`.github/workflows`](.github/workflows), disparados por path na branch de pipeline:

```mermaid
flowchart LR
  P[git push] --> B[Compile Java 21]
  P --> T[Testes Maven / H2]
  B --> PKG[package]
  T --> PKG
  PKG --> IMG[Docker buildx<br/>tag versão + latest]
  IMG --> HUB[Docker Hub]
  HUB --> VPS[SCP .env + compose]
  VPS --> UP[docker compose pull && up -d]
```

**Backend:** checkout → Temurin 21 → `./mvnw compile` → `./mvnw test` → `package` → build da imagem (`docker/backend/Dockerfile`) → push → deploy SSH no VPS.

**Frontend:** extrai a versão do `package.json` → build da imagem (`docker/frontend/Dockerfile`, `CONFIGURATION=production`) → push → o mesmo Compose no VPS.

Secrets usados: banco, `PAGBANK_TOKEN`, `SITE_URL`, Docker Hub e credenciais SSH da VPS. Nada disso vai para o Git.

---

## Variáveis de ambiente

| Variável | Uso |
| --- | --- |
| `POSTGRES_URL` | JDBC do Spring |
| `POSTGRES_USERNAME` / `POSTGRES_PASSWORD` | Postgres + Spring |
| `PAGBANK_TOKEN` | Bearer da API sandbox |
| `SITE_URL` | URL pública no `notification_urls` do pedido |
| `DOCKERHUB_USERNAME` | nome das imagens no Compose |

Modelo: [`mathBackend/src/main/resources/application.properties.example`](mathBackend/src/main/resources/application.properties.example).

---

## Autor

**Cesar Buril**, Engenheiro de Computação  
GitHub: [cesarburil](https://github.com/cesarburil) · Demo: [math.cesarburil.com.br](https://math.cesarburil.com.br)
