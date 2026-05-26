# avaliacao2javaspring

# 📚 Aluno Online API

API REST para gerenciamento acadêmico desenvolvida com **Spring Boot** e **Java 21**. Permite o cadastro de alunos, professores e disciplinas, além do controle de matrículas com lançamento de notas e cálculo automático de aprovação/reprovação.

---

## 🛠️ Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.3 |
| Spring Data JPA | — |
| Spring Web MVC | — |
| PostgreSQL | — |
| Lombok | — |
| Maven | — |

---

## 🗂️ Diagrama de Entidades

```
Aluno              Professor
├── id             ├── id
├── nome           ├── nome
├── email          ├── email
└── cpf            └── cpf
                         │
              Disciplina ◄┘ (ManyToOne)
              ├── id
              ├── nome
              ├── cargaHoraria
              └── professor_id (FK)
                    │
       MatriculaAluno ◄┘ (ManyToOne)
       ├── id
       ├── aluno_id (FK)
       ├── disciplina_id (FK)
       ├── nota1
       ├── nota2
       └── status  →  MATRICULADO | APROVADO | REPROVADO | TRANCADO | DESLIGADO
```

---

## ⚙️ Configuração

### 1. Pré-requisitos

- Java 21+
- Maven 3.9+
- PostgreSQL

### 2. Clone o repositório

```bash
git clone https://github.com/joaovictordants/avaliacao2javaspring.git
cd avaliacao2javaspring/api/api
```

### 3. Crie o banco de dados

```sql
CREATE DATABASE aluno_online;
```

### 4. Configure o `application.properties`

O arquivo já vem pré-configurado em `src/main/resources/application.properties`:

```properties
spring.application.name=Aluno Online

spring.datasource.url=jdbc:postgresql://localhost:5432/aluno_online
spring.datasource.username=postgres
spring.datasource.password=1234
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080
```

> Ajuste `username` e `password` conforme seu ambiente local.

### 5. Execute a aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 🔗 Endpoints

### 👤 Alunos — `/alunos`

| Método | Rota | Descrição | Status |
|---|---|---|---|
| `POST` | `/alunos` | Cadastra um novo aluno | `201 Created` |
| `GET` | `/alunos` | Lista todos os alunos | `200 OK` |
| `GET` | `/alunos/{id}` | Busca aluno por ID | `200 OK` |
| `PUT` | `/alunos/{id}` | Atualiza dados do aluno | `204 No Content` |
| `DELETE` | `/alunos/{id}` | Remove aluno por ID | `204 No Content` |

**Exemplo de body (POST/PUT):**
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "cpf": "123.456.789-00"
}
```

---

### 🧑‍🏫 Professores — `/professores`

| Método | Rota | Descrição | Status |
|---|---|---|---|
| `POST` | `/professores` | Cadastra um novo professor | `201 Created` |
| `GET` | `/professores` | Lista todos os professores | `200 OK` |
| `GET` | `/professores/{id}` | Busca professor por ID | `200 OK` |
| `PUT` | `/professores/{id}` | Atualiza dados do professor | `204 No Content` |
| `DELETE` | `/professores/{id}` | Remove professor por ID | `204 No Content` |

**Exemplo de body (POST/PUT):**
```json
{
  "nome": "Maria Souza",
  "email": "maria@escola.com",
  "cpf": "987.654.321-00"
}
```

---

### 📖 Disciplinas — `/disciplinas`

| Método | Rota | Descrição | Status |
|---|---|---|---|
| `POST` | `/disciplinas` | Cadastra uma nova disciplina | `201 Created` |
| `GET` | `/disciplinas` | Lista todas as disciplinas | `200 OK` |
| `GET` | `/disciplinas/{id}` | Busca disciplina por ID | `200 OK` |
| `PUT` | `/disciplinas/{id}` | Atualiza dados da disciplina | `204 No Content` |
| `DELETE` | `/disciplinas/{id}` | Remove disciplina por ID | `204 No Content` |

**Exemplo de body (POST/PUT):**
```json
{
  "nome": "Programação Orientada a Objetos",
  "cargaHoraria": 80,
  "professor": { "id": 1 }
}
```

---

### 📋 Matrículas — `/matriculas`

| Método | Rota | Descrição | Status |
|---|---|---|---|
| `POST` | `/matriculas` | Cria matrícula (status inicial: `MATRICULADO`) | `201 Created` |
| `PATCH` | `/matriculas/trancar/{id}` | Tranca a matrícula | `204 No Content` |
| `PATCH` | `/matriculas/atualizar-notas/{id}` | Lança nota1 e/ou nota2 | `204 No Content` |

**Exemplo de body — criar matrícula:**
```json
{
  "aluno": { "id": 1 },
  "disciplina": { "id": 2 }
}
```

**Exemplo de body — atualizar notas:**
```json
{
  "nota1": 8.5,
  "nota2": 6.0
}
```

> 💡 A média é calculada automaticamente. Média ≥ **7.0** → `APROVADO`; abaixo → `REPROVADO`.

---

### 🎓 Status de Matrícula

| Status | Descrição |
|---|---|
| `MATRICULADO` | Status inicial ao criar a matrícula |
| `APROVADO` | Média final ≥ 7.0 |
| `REPROVADO` | Média final < 7.0 |
| `TRANCADO` | Matrícula trancada manualmente |
| `DESLIGADO` | Aluno desligado da disciplina |

---

## 📁 Estrutura do Projeto

```
src/main/java/br/com/alunoonline/api/
├── controller/
│   ├── AlunoController.java
│   ├── ProfessorController.java
│   ├── DisciplinaController.java
│   └── MatriculaAlunoController.java
├── service/
│   ├── AlunoService.java
│   ├── ProfessorService.java
│   ├── DisciplinaService.java
│   └── MatriculaAlunoService.java
├── repository/
│   ├── AlunoRepository.java
│   ├── ProfessorRepository.java
│   ├── DisciplinaRepository.java
│   └── MatriculaAlunoRepository.java
├── model/
│   ├── Aluno.java
│   ├── Professor.java
│   ├── Disciplina.java
│   └── MatriculaAluno.java
├── dtos/
│   └── AtualizarNotasRequestDTO.java
├── MatriculaAlunoStatusEnum.java
└── ApiApplication.java
```

---

## 🧪 Testes

```bash
./mvnw test
```

---

## 📄 Licença

Projeto desenvolvido para fins acadêmicos.
