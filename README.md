# 🧪 Projeto de Testes de API - DummyJSON

## 📋 Descrição
Este projeto realiza testes automatizados na API pública [DummyJSON](https://dummyjson.com), simulando operações de login, consulta e gerenciamento de produtos eletrônicos.

## 🛠️ Tecnologias
- Java 17+
- JUnit 5
- RestAssured
- JSON Schema Validator

## 🚀 Como executar
1. Clone o repositório:
   ```bash
   git clone https://https://github.com/fabriciosantoss/SicrediApi.git
   cd nome-do-projeto

2. Execute os testes com o Maven:
   ```bash
    mvn clean test

## 🧪 Plano de Testes

## 🔐 Autenticação
| Cenário        | Descrição                               | Resultado Esperado               |
| -------------- | --------------------------------------- |----------------------------------|
| Login válido   | Realizar login com credenciais corretas | Retorna `200` e token válido     |
| Login inválido | Realizar login com senha incorreta      | Retorna `401` e mensagem de erro |
| Token inválido | Requisição com token inválido           | Retorna `500`                    |
| Sem token      | Requisição sem token                    | Retorna `401`                    |

## 📦 Produtos
| Cenário                      | Descrição                           | Resultado Esperado                |
| ---------------------------- | ----------------------------------- |-----------------------------------|
| Listar produtos              | Obter todos os produtos cadastrados | Retorna `200` e lista de produtos |
| Buscar produto por ID válido | Consultar produto existente         | Retorna `200` e dados do produto  |
| Buscar produto inexistente   | Consultar produto inexistente       | Retorna `404`                     |
| Criar produto                | Enviar produto válido com token     | Retorna `201` e produto criado    |
| Criar produto sem token      | Tentativa sem autenticação          | Retorna `401`                     |

## 👥 Usuários
| Cenário         | Descrição               | Resultado Esperado                |
| --------------- | ----------------------- | --------------------------------- |
| Listar usuários | Obter lista de usuários | Retorna `200` e lista de usuários |

## 🧭 Estratégia de Testes

1. Abordagem:

-    Os testes foram desenvolvidos utilizando o framework JUnit 5, com o RestAssured para chamadas HTTP e validação de resposta.

2. Organização:

- @BeforeAll: Realiza o login e armazena o token para os demais testes.
    
-  Testes agrupados por funcionalidades: autenticação, produtos e usuários.
    
- Validações de status code, corpo da resposta e contrato JSON.

3. Cobertura:
- Fluxos de sucesso (200 OK);
- Fluxos de exceção (401, 403, 404);
- Testes de contrato com JsonSchemaValidator.

4. Critérios de sucesso:
    
- Quase todos os endpoints retornaram o status esperado e o corpo de resposta conforme o schema documentado.
## 🧾 Bug Identificado
Ao executar a a rota de execeção, de um produto com o token inválido, o sistema deveria retornar o status code 403 Forbidden status. Mas é exibido o status code 500 Internal server error.

## 📊 Estrutura do Projeto
    
    📦 src
    ┣ 📂 test
    ┃ ┗ 📂 java
    ┃   ┗ 📜 ApiTest.java
    ┣ 📜 pom.xml
    ┣ 📜 README.md

## 🧑‍💻 Autor

    Fabrício Santos
    QA Engineer | Java | Playwright | API Testing | Automation
    📧 contato: fabriciosantos12@hotmail.com
    