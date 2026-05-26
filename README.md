# Desafio Técnico: Simulador de Financiamentos - Caixaverso 2026

Este repositório contém o backend de uma API RESTful para simulação de financiamentos e investimentos, desenvolvida com precisão matemática nas simulações, arquitetura Spec-Driven e testes automatizados. O projeto foi construído em conformidade com as diretrizes e critérios de avaliação do edital.

Link do Projeto no GitHub: [Desafio Caixaverso 2026](https://github.com/martincLL/Desafio-Caixaverso-2026/tree/main)

---

## Stacks Tecnológicas Utilizadas

* **Linguagem:** Java 25
* **Framework:** Quarkus
* **Base de Dados:** H2 Database — Executado de forma 100% nativa, sem necessidade de Docker ou scripts SQL manuais.
* **Precisão Matemática:** Uso sistemático de BigDecimal para evitar erros de arredondamento em cálculos de juros compostos.
* **Testes & Cobertura:** JUnit 5, Mockito, REST Assured e Jacoco para relatórios de cobertura.
* **Documentação & Spec-Driven:** OpenAPI / SmallRye OpenAPI (Swagger UI).

---

## Seed Inicial de Dados Automático (Protegido contra Testes)

Para garantir que a API forneça dados imediatamente após a inicialização, o projeto implementa uma classe de carga automatizada (`SeedDadosIniciais`) acionada pelo evento `StartupEvent` do Quarkus.

Assim que o servidor de desenvolvimento é iniciado, o banco de dados H2 é populado automaticamente com 5 simulações completas e variadas, cobrindo diferentes cenários de negócios (valores, taxas e prazos distintos).

Isolamento de Testes (Sem interferência no clean verify): O seed utiliza a anotação `@UnlessBuildProfile("test")`. Isso significa que, quando o comando de testes e verificação (`mvnw clean verify`) é acionado, o Quarkus ativa o perfil de teste (`test`) e ignora completamente a execução do Seed. O banco de dados inicia perfeitamente limpo e controlado, impedindo que dados pré-existentes quebrem as asserções de contagem ou integridade dos testes de integração.

---

## Como Executar a Aplicação (Modo de Desenvolvimento)

A aplicação roda de maneira nativa localmente, dispensando o uso de contêineres Docker. Abra o terminal na raiz do projeto (onde está o arquivo `pom.xml`) e execute o comando correspondente ao seu sistema operacional:

### No Windows (PowerShell / CMD):
```powershell
.\mvnw.cmd quarkus:dev
```

Após o boot completo do framework, a mensagem confirmando a execução do seed de dados será exibida no terminal e as 5 simulações prontas estarão disponíveis para consulta.

---

## Documentação da API & Contratos (OpenAPI/Swagger UI)

Alinhado ao pilar de Spec-Driven Development, todos os contratos e payloads (sucessos 201/200 e erros 400/404) foram  documentados na classe de controller utilizando anotações nativas do MicroProfile OpenAPI.

Com a aplicação rodando em modo de desenvolvimento, você pode testar todas as rotas interativamente acessando a interface do Swagger pelo navegador:
[http://localhost:8080/q/swagger-ui](http://localhost:8080/q/swagger-ui)

### Endpoints Disponíveis:
* `POST /simulacoes` - Cria uma nova simulação (calcula juros compostos mês a mês e gera memória de cálculo).
* `GET /simulacoes` - Retorna a lista de todas as simulações cadastradas (incluindo as 5 geradas pelo Seed).
* `GET /simulacoes/{id}` - Retorna os detalhes e a evolução completa de uma simulação específica via parâmetro de rota.

---

## Como Executar a Suíte de Testes e Validar a Cobertura

O projeto conta com uma suíte de testes dividida em duas categorias:
1. **Testes Unitários:** Isolam a lógica matemática e os fluxos de serviço utilizando dublês de teste (`@InjectMock`).
2. **Testes de Integração:** Validam o fluxo completo de ponta a ponta (`@QuarkusTest` + `REST Assured`), interagindo diretamente com as rotas HTTP e persistindo no banco H2 real de testes (sem qualquer interferência do Seed).

Para executar todos os testes, gerar os relatórios de cobertura do Jacoco e certificar que a barreira eliminatória de 80% de cobertura foi superada, execute a verificação:

### No Windows (PowerShell / CMD):
`.\mvnw.cmd clean verify`


### Visualizando o Relatório do Jacoco:
### Estratégia de Cobertura (Jacoco)
O pacotes de configuração (`org.caixa.config`) foi intencionalmente excluído do relatório de cobertura via `application.properties`.

**Motivação:** A classe `SeedDadosIniciais` possui a anotação `@UnlessBuildProfile("test")`, impedindo-a de ser executada durante os testes de integração para não poluir o banco.
Após o término com sucesso do build (BUILD SUCCESS), o relatório visual detalhado pode ser visualizado abrindo o arquivo abaixo em qualquer navegador:
`target/jacoco-report/index.html`

---

## Organização de Código e Clean Code

O projeto segue o padrão arquitetural em camadas recomendado (Resource -> Service -> Repository) para evitar o acoplamento de lógica de negócios nos controladores:
* **Resource (Controller):** Expõe os endpoints REST e centraliza as anotações do Swagger.
* **Service:** Centraliza o motor de cálculo financeiro e as regras de negócio.
* **Repository:** Gerencia o acesso e persistência dos dados no H2.
* **ExceptionMapper:** Intercepta exceções globais e de validação, impedindo que erros internos de validação estourem HTTP 500 e garantindo o retorno limpo de payloads de erro estruturados com os status HTTP 400 ou HTTP 404.# Desafio Técnico: Simulador de Financiamentos - Caixaverso 2026

Este repositório contém o backend de uma API RESTful para simulação de financiamentos e investimentos, desenvolvida com precisão matemática nas simulações, arquitetura Spec-Driven e testes automatizados. O projeto foi construído em conformidade com as diretrizes e critérios de avaliação do edital.

Link do Projeto no GitHub: [Desafio Caixaverso 2026](https://github.com/martincLL/Desafio-Caixaverso-2026/tree/main)

---

## Stacks Tecnológicas Utilizadas

* **Linguagem:** Java 25
* **Framework:** Quarkus
* **Base de Dados:** H2 Database — Executado de forma 100% nativa, sem necessidade de Docker ou scripts SQL manuais.
* **Precisão Matemática:** Uso sistemático de BigDecimal para evitar erros de arredondamento em cálculos de juros compostos.
* **Testes & Cobertura:** JUnit 5, Mockito, REST Assured e Jacoco para relatórios de cobertura.
* **Documentação & Spec-Driven:** OpenAPI / SmallRye OpenAPI (Swagger UI).

---

## Seed Inicial de Dados Automático (Protegido contra Testes)

Para garantir que a API forneça dados imediatamente após a inicialização, o projeto implementa uma classe de carga automatizada (`SeedDadosIniciais`) acionada pelo evento `StartupEvent` do Quarkus.

Assim que o servidor de desenvolvimento é iniciado, o banco de dados H2 é populado automaticamente com 5 simulações completas e variadas, cobrindo diferentes cenários de negócios (valores, taxas e prazos distintos).

Isolamento de Testes (Sem interferência no clean verify): O seed utiliza a anotação `@UnlessBuildProfile("test")`. Isso significa que, quando o comando de testes e verificação (`mvnw clean verify`) é acionado, o Quarkus ativa o perfil de teste (`test`) e ignora completamente a execução do Seed. O banco de dados inicia perfeitamente limpo e controlado, impedindo que dados pré-existentes quebrem as asserções de contagem ou integridade dos testes de integração.

---

## Como Executar a Aplicação (Modo de Desenvolvimento)

A aplicação roda de maneira nativa localmente, dispensando o uso de contêineres Docker. Abra o terminal na raiz do projeto (onde está o arquivo `pom.xml`) e execute o comando correspondente ao seu sistema operacional:

### No Windows (PowerShell / CMD):
```powershell
.\mvnw.cmd quarkus:dev
```

Após o boot completo do framework, a mensagem confirmando a execução do seed de dados será exibida no terminal e as 5 simulações prontas estarão disponíveis para consulta.

---

## Documentação da API & Contratos (OpenAPI/Swagger UI)

Alinhado ao pilar de Spec-Driven Development, todos os contratos e payloads (sucessos 201/200 e erros 400/404) foram  documentados na classe de controller utilizando anotações nativas do MicroProfile OpenAPI.

Com a aplicação rodando em modo de desenvolvimento, você pode testar todas as rotas interativamente acessando a interface do Swagger pelo navegador:
[http://localhost:8080/q/swagger-ui](http://localhost:8080/q/swagger-ui)

### Endpoints Disponíveis:
* `POST /simulacoes` - Cria uma nova simulação (calcula juros compostos mês a mês e gera memória de cálculo).
* `GET /simulacoes` - Retorna a lista de todas as simulações cadastradas (incluindo as 5 geradas pelo Seed).
* `GET /simulacoes/{id}` - Retorna os detalhes e a evolução completa de uma simulação específica via parâmetro de rota.

---

## Como Executar a Suíte de Testes e Validar a Cobertura

O projeto conta com uma suíte de testes dividida em duas categorias:
1. **Testes Unitários:** Isolam a lógica matemática e os fluxos de serviço utilizando dublês de teste (`@InjectMock`).
2. **Testes de Integração:** Validam o fluxo completo de ponta a ponta (`@QuarkusTest` + `REST Assured`), interagindo diretamente com as rotas HTTP e persistindo no banco H2 real de testes (sem qualquer interferência do Seed).

Para executar todos os testes, gerar os relatórios de cobertura do Jacoco e certificar que a barreira eliminatória de 80% de cobertura foi superada, execute a verificação:

### No Windows (PowerShell / CMD):
`.\mvnw.cmd clean verify`


### Visualizando o Relatório do Jacoco:
### Estratégia de Cobertura (Jacoco)
O pacotes de configuração (`org.caixa.config`) foi intencionalmente excluído do relatório de cobertura via `application.properties`.

**Motivação:** A classe `SeedDadosIniciais` possui a anotação `@UnlessBuildProfile("test")`, impedindo-a de ser executada durante os testes de integração para não poluir o banco.
Após o término com sucesso do build (BUILD SUCCESS), o relatório visual detalhado pode ser visualizado abrindo o arquivo abaixo em qualquer navegador:
`target/jacoco-report/index.html`

---

## Organização de Código e Clean Code

O projeto segue o padrão arquitetural em camadas recomendado (Resource -> Service -> Repository) para evitar o acoplamento de lógica de negócios nos controladores:
* **Resource (Controller):** Expõe os endpoints REST e centraliza as anotações do Swagger.
* **Service:** Centraliza o motor de cálculo financeiro e as regras de negócio.
* **Repository:** Gerencia o acesso e persistência dos dados no H2.
* **ExceptionMapper:** Intercepta exceções globais e de validação, impedindo que erros internos de validação estourem HTTP 500 e garantindo o retorno limpo de payloads de erro estruturados com os status HTTP 400 ou HTTP 404.