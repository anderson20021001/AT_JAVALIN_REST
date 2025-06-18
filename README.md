# AT_JAVALIN_REST

# Projeto API REST com Javalin - Etapa 1

Este projeto implementa uma API REST simples em Java usando o framework Javalin. Também inclui testes unitários com JUnit e um cliente HTTP em Java que consome a API usando `HttpURLConnection`.

---

## Funcionalidades

- Endpoint GET `/hello` - retorna mensagem simples "Hello, Javalin!"
- Endpoint GET `/status` - retorna JSON com status e timestamp atual ISO-8601
- Endpoint POST `/echo` - retorna o mesmo JSON recebido
- Endpoint GET `/saudacao/{nome}` - retorna uma mensagem personalizada em JSON
- CRUD básico para usuários:
  - POST `/usuarios` - cria um usuário em memória
  - GET `/usuarios` - lista todos usuários
  - GET `/usuarios/{email}` - busca usuário por email

---

## Requisitos

- Java 17 ou superior
- Gradle (wrapper incluído)
- IDE ou terminal para compilar e rodar

---

## Como rodar a aplicação

1. Clone o repositório:

```bash
git clone <url-do-repositorio>
cd <nome-do-projeto>
