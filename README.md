# 🧾 Tech Challenge - Sistema de Autoatendimento para Lanchonete

Este é um sistema de autoatendimento para uma lanchonete em expansão, desenvolvido como parte do **Tech Challenge da FIAP**, que integra conhecimentos de todas as disciplinas da fase. O projeto é essencial para automatizar e organizar o processo de pedidos, desde a escolha dos produtos até a entrega ao cliente.

---

## 📌 Objetivo

Criar uma aplicação de autoatendimento estilo fast-food que:

- Permita ao cliente realizar pedidos de forma autônoma.
- Integre pagamento via QRCode utilizando **Mercado Pago**.
- Acompanhe o status dos pedidos.
- Forneça ao administrador funcionalidades de gerenciamento de produtos, categorias e clientes.

---

## 🛠️ Funcionalidades

### Cliente

- ✅ Cadastro com nome, CPF e e-mail (opcional).
- ✅ Seleção de produtos com visualização de nome, descrição, imagem e preço.
- ✅ Montagem de pedidos com as categorias:
  - Lanche
  - Acompanhamento
  - Bebida
  - Sobremesa
- ✅ Pagamento via QRCode (Mercado Pago).
- ✅ Acompanhamento do pedido com os status:
  - Recebido
  - Em preparação
  - Pronto
  - Finalizado


## ⚙️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **MongoDB**
- **Mercado Pago SDK**
- **Docker**
- **Lombok**

---

## 📁 Estrutura do Projeto

- `adapter`: camada de interface (controllers, DTOs, handlers).
- `core`: domínio (casos de uso, entidades, interfaces).
- `application`: implementação dos casos de uso.
- `infrastructure`: configurações externas (ex: Mercado Pago).
- `util`: enums, exceptions e conversores.

## 🏗️ Arquitetura da Solução

### 🧱 Arquitetura Hexagonal (Ports and Adapters)

O projeto adota a arquitetura hexagonal para promover separação de responsabilidades, facilitar testes e permitir a substituição de tecnologias externas com baixo acoplamento.

- **Camada de entrada (Inbound Adapter)**: Controladores REST responsáveis por receber requisições HTTP e convertê-las para os casos de uso da aplicação.
- **Camada de aplicação (Use Cases)**: Contém a lógica central de negócios e orquestra as chamadas entre o domínio e os adaptadores.
- **Camada de saída (Outbound Adapter)**: Implementações de acesso a dados (MongoDB), integração com serviços externos (Mercado Pago), entre outros.
- **Banco de Dados**: MongoDB, utilizado para persistência dos dados de clientes, produtos, pedidos e pagamentos.
- **Pagamento**: Integração com a API do Mercado Pago utilizando QRCode.
- **Containers**: O MongoDB é executado em container Docker para facilitar o desenvolvimento e testes locais.

```
               +-------------------------+
               |   Interface do Cliente  |
               |     (HTTP REST API)     |
               +------------+------------+
                            |
            +---------------v---------------+
            |        Camada de Entrada      |
            |     (Controllers REST - API)  |
            +---------------+---------------+
                            |
            +---------------v---------------+
            |       Casos de Uso (Core)     |
            |   Regras de Negócio e Fluxos  |
            +---------------+---------------+
                            |
        +-------------------+-------------------+
        |                                       |
+-------v--------+                     +--------v--------+
| Banco de Dados |                     | Serviços Externos|
|   MongoDB      |                     |  Mercado Pago    |
+----------------+                     +------------------+
```


---

## 🚀 Como Executar Localmente

### Pré-requisitos

- Java 21
- Docker
- MongoDB (ou Docker Compose)
- Maven

### Passo a Passo

1. **Clone o repositório**
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd seu-repositorio

2. **Suba o MongoDB com Docker**
    ```bash
    docker compose up -d

3. **Configure variáveis de ambiente (exemplo já presente no application.properties):**

* mercadopago.access.token

* spring.data.mongodb.username, password, host, database

4. **Execute a aplicação**
    ```bash
    ./mvnw spring-boot:run

5. **Para acessar a aplicação:**

Swagger: http://localhost:8080/swagger-ui/index.html


## 📫 Endpoints Principais

**Clientes:**
| Método | Endpoint      | Descrição                |
| ------ | ------------- | ------------------------ |
| GET    | `/v1/clients` | Listar todos os clientes |
| POST   | `/v1/clients` | Criar um cliente         |




**Produtos:**
| Método | Endpoint                                   | Descrição                |
| ------ | ------------------------------------------ | ------------------------ |
| GET    | `/v1/products`                             | Listar todos os produtos |
| POST   | `/v1/products`                             | Criar um produto         |
| GET    | `/v1/products/category?category=SOBREMESA` | Filtrar por categoria    |

**Pedidos:**
| Método | Endpoint                                | Descrição                  |
| ------ | --------------------------------------- | -------------------------- |
| POST   | `/v1/orders`                            | Criar um pedido            |
| PATCH  | `/v1/orders/{id}?status=IN_PREPARATION` | Atualizar status do pedido |


**Pagamentos:**
| Método | Endpoint       | Descrição                     |
| ------ | -------------- | ----------------------------- |
| PATCH  | `/v1/payments` | Atualizar status do pagamento |


## 🙋‍♀️ Equipe

| Nome | RA      | Nome Discord                |
| ------ | ------------- | ------------------------ |
| Danilo Augusto Pereira     | 364411 | Danilo Augusto -  RM364411|
| Gabriela Trindade Ferreira   | 364756 | Gabriela Ferreira - RM364756|
| Guilherme Garcia Dos Santos Moraes   | 364613 | Guilherme Garcia - RM364613|
| Lucas Matheus Monteiro Machado   | 361059 | Lucas Machado - RM361059|
| Marjory Bispo Matos   | 361150 | Marjory Matos - RM361150|

