🏥 API de Consultas Médicas





API para gestão de consultas médicas, construída em Spring Boot, com persistência em MySQL e cache em Redis.
O projeto já inclui Docker Compose para orquestração dos serviços.

👉 Repositório: api_consultas


🚀 Funcionalidades actuais

✅ Cadastro de Pacientes

✅ Cadastro de Médicos

✅ Agendamento de Consultas

Verificação automática de conflitos de horário

✅ Integração com MySQL (via Spring Data JPA)

✅ Cache Redis no ConsultaService (para listagens rápidas)

✅ Docker Compose com serviços (MySQL, Redis, Aplicação)


🔄 Funcionalidades pendentes

⏳ Implementar cache em Redis para PacienteService

⏳ Implementar cache em Redis para MedicoService

⏳ Criar testes automatizados (unitários e de integração)

⏳ Documentar API com Swagger/OpenAPI

⏳ Pipeline de CI/CD para build e deploy automático

📂 Estrutura do Projeto

api-consultas/
├── src/
│   ├── main/java/com/capassola/consultas/
│   │   ├── model/         # Entidades (Paciente, Medico, Consulta)
│   │   ├── repository/    # Repositories JPA
│   │   ├── service/       # Regras de negócio + cache Redis (parcial)
│   │   ├── controller/    # Endpoints REST
│   │   └── config/        # RedisConfig
│   └── resources/
│       └── application.yml
├── pom.xml
├── Dockerfile
├── docker-compose.yml
└── README.md


⚙️ Configuração
Banco de Dados (MySQL)

spring:
datasource:
url: jdbc:mysql://mysql:3306/consultasdb
username: root
password: root
jpa:
hibernate:
ddl-auto: update
show-sql: true

Cache (Redis)

Atualmente habilitado apenas em Consultas.
Falta aplicar em Pacientes e Médicos.

🐳 Docker
1. Build do projeto
    mvn clean package -DskipTests
2. Subir containers
   docker-compose up --build

Serviços disponíveis:

App → http://localhost:8080

MySQL → localhost:3306

Redis → localhost:6379

📌 Endpoints REST
Pacientes

POST /pacientes → criar paciente

GET /pacientes → listar pacientes

Médicos

POST /medicos → criar médico

GET /medicos → listar médicos

Consultas

POST /consultas → agendar consulta (valida conflito de horário)

GET /consultas → listar consultas

🔮 Próximos Passos

Finalizar cache Redis em Paciente e Médico

Documentar endpoints com Swagger

Implementar testes unitários (JUnit + Mockito)

Configurar CI/CD para build automático no GitHub Actions
