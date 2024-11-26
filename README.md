# API de Comunicação

## Endpoints Disponíveis

1. **Agendamento de Envio de Comunicação**: Endpoint para agendar um envio de comunicação, armazenando no banco de dados e na fila de mensageria.
2. **Consulta de Envio de Comunicação**: Endpoint para consultar o status de uma comunicação agendada.
3. **Cancelamento de Envio de Comunicação**: Endpoint para cancelar um agendamento de comunicação.

## Tecnologias Utilizadas

- **Linguagem**: Java
- **Banco de Dados**: PostgreSQL
- **Mensageria**: RabbitMQ
- **Modelo de API**: RESTful
- **Ferramenta de Teste**: Testes unitários usando `JUnit`
- **Contêinerização**: Docker e Docker Compose

## Como Rodar a Aplicação

### Configuração Local

1. **Clonar o Repositório**

2. **Instalar Dependências (se não estiver usando Docker)**

   A aplicação utiliza Java 17 LTS.

3. **Executar a API**

   Caso optar por não rodar utilizando docker compose pode subir o projeto normalmente com a IDE de sua preferência, o projeto utiliza maven.

### Rodando com Docker

Para rodar com Docker Compose, utilize o comando abaixo no diretório /docker:

```sh
docker-compose up -d
```

Isso configurará o banco de dados, a mensageria e a aplicação automaticamente na porta 8090.

## Endpoints

### 1. Verificar Saúde da Aplicação

- **URL**: `/api/actuator/health`
- **Método**: `GET`
- **Descrição**: Consulta se a api esta disponível para uso.

### 2. Agendamento de Envio de Comunicação

- **URL**: `/api/communications/scheduled`
- **Método**: `POST`
- **Dados de Entrada**:
  ```json
  {
  "type": "EMAIL",
  "destination": "usuario@exemplo.com",
  "scheduledDatetime": "2024-12-01T10:00:00Z",
  "message": "Olá, esta é uma mensagem agendada."
   }
  ```
- **Descrição**: Cria um agendamento e armazena no PostgreSQL e na fila do RabbitMQ.

### 3. Consulta de Comunicação

- **URL**: `/api/communications/scheduled/{id}`
- **Método**: `GET`
- **Descrição**: Consulta o agendamento de comunicação a partir de seu id.

### 4. Cancelamento de Agendamento

- **URL**: `/api/communications/scheduled/{id}/cancel`
- **Método**: `PATCH`
- **Descrição**: Cancela um agendamento de envio de comunicação.

## Testes

Para rodar a suite de testes pode ser utilizado o maven com o comando:

```sh
mvn test
```

## Licença

Este projeto está sob a licença MIT.
---