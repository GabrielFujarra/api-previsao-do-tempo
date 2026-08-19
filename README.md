# API de Previsão do Tempo
 
API REST desenvolvida em **Java + Spring Boot** que consulta dados de previsão do tempo em tempo real através da [Open-Meteo API](https://open-meteo.com/) e disponibiliza autenticação de usuários via **JWT**. Projeto criado com foco em boas práticas de arquitetura backend: camadas bem definidas, segurança stateless, migrations versionadas e containerização com Docker.
 
## ✨ Funcionalidades
 
- Cadastro de usuários com senha criptografada (BCrypt)
- Autenticação via JWT (login gera um token com tempo de expiração configurável)
- Consulta de previsão do tempo (temperatura máxima/mínima e precipitação para os próximos 7 dias) a partir de latitude e longitude, integrando com a Open-Meteo API
- Proteção de rotas com Spring Security + filtro JWT customizado
- Migrations de banco de dados versionadas com Flyway
- Ambiente 100% containerizado com Docker e Docker Compose
## 🛠️ Tecnologias
 
- **Java 21**
- **Spring Boot 4** (Web MVC, Data JPA, Security, RestClient)
- **PostgreSQL**
- **Flyway** (versionamento de schema)
- **JJWT** (geração e validação de tokens JWT)
- **Lombok**
- **Docker / Docker Compose**
- **Maven**
## 📋 Pré-requisitos
 
- [Docker](https://www.docker.com/) e Docker Compose instalados
- Java 21 (apenas se for rodar fora do Docker)
- Maven (apenas se for rodar fora do Docker — o projeto já inclui o wrapper `mvnw`)
## 🚀 Como rodar o projeto
 
### 1. Clone o repositório
 
```bash
git clone https://github.com/GabrielFujarra/api-previsao-do-tempo.git
cd api-previsao-do-tempo
```
 
### 2. Configure as variáveis de ambiente
 
Crie um arquivo `.env` na raiz do projeto com o seguinte conteúdo (ajuste os valores conforme necessário):
 
```env
# Usadas para rodar a aplicação localmente (fora do Docker)
DB_URL=jdbc:postgresql://localhost:5432/previsaotempo
DB_USERNAME=postgres
DB_PASSWORD=sua_senha
 
# Usadas dentro do container (nome do serviço 'db' na rede do compose)
DATASOURCE_URL=jdbc:postgresql://db:5432/previsaotempo
DB_USER_DOCKER=postgres
DB_PASSWORD_DB_USER_DOCKER=sua_senha
 
# JWT
SECRET_KEY=sua_chave_secreta_com_pelo_menos_256_bits
EXPIRATION=7200000
```
 
> ⚠️ **Nunca** faça commit do arquivo `.env`. Confirme que ele está listado no `.gitignore` antes de subir qualquer alteração.
 
### 3. Suba a aplicação com Docker Compose
 
```bash
docker compose up --build
```
 
Isso vai:
1. Subir um container PostgreSQL
2. Buildar a imagem da aplicação Spring Boot (multi-stage build)
3. Rodar as migrations do Flyway automaticamente
4. Expor a API em `http://localhost:8080`
## 📡 Endpoints
 
### Criar usuário
```
POST /usuarios
Content-Type: application/json
 
{
  "email": "usuario@exemplo.com",
  "senha": "senha123"
}
```
**201 Created**
 
### Login (gera token JWT)
```
POST /auth
Content-Type: application/json
 
{
  "email": "usuario@exemplo.com",
  "senha": "senha123"
}
```
**200 OK**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```
 
### Consultar previsão do tempo
```
GET /previsao?latitude=-23.5505&longitude=-46.6333
Authorization: Bearer {token}
```
**200 OK** — retorna temperatura máxima/mínima e precipitação prevista para os próximos 7 dias.
 
> As rotas `POST /usuarios` e `POST /auth` são públicas. Todas as demais rotas exigem um token JWT válido no header `Authorization`.
 
## 🏗️ Arquitetura
 
O fluxo de autenticação segue o padrão stateless com JWT:
 
1. O usuário se cadastra em `POST /usuarios` (senha é armazenada com hash BCrypt)
2. O usuário faz login em `POST /auth`, que valida as credenciais via `AuthenticationManager` e retorna um token JWT
3. Nas requisições seguintes, o token é enviado no header `Authorization: Bearer {token}`
4. Um filtro customizado (`JwtAuthFilter`) intercepta cada requisição, valida o token e popula o `SecurityContext` com os dados do usuário autenticado
5. Rotas protegidas verificam a autenticação antes de processar a requisição
A integração com a previsão do tempo é feita através do `RestClient` do Spring, consumindo a [Open-Meteo API](https://open-meteo.com/) (gratuita, sem necessidade de chave de API).
 
## 🗺️ Possíveis melhorias futuras
 
- Testes unitários (Mockito) e de integração (`@WebMvcTest`, Testcontainers)
- Cache das respostas da Open-Meteo para reduzir chamadas externas repetidas
- Histórico de consultas por usuário
- Deploy em ambiente cloud (AWS)
## 👤 Autor
 
**Gabriel Fujarra**
- LinkedIn: [linkedin.com/in/gabriel-fujarra](https://linkedin.com/in/gabriel-fujarra)
- GitHub: [github.com/GabrielFujarra](https://github.com/GabrielFujarra)
