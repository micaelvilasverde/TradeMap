# TradeMapClone API

API Rest para o app TradeMapClone destinado à Mentoria da Digital Innovation One. Esta API simula funcionalidades do TradeMap com integração ao Kafka para mostrar em tempo real valores das ações da B3.

## 🚀 Tecnologias

### API Rest
- **Java** com **Spring Boot 2**
- **Spring Data**
- **Spring for Kafka**
- **PostgreSQL**

### Coleta de Dados
- **Python** com **Flask**
- **Yahoo Finance API** - [https://finance.yahoo.com/](https://finance.yahoo.com/)

### Infraestrutura
- **Docker** e **Docker Compose**
- **Apache Kafka**
- **Kafka Connect**
- **MQTT**

## 📋 Aplicações Envolvidas

1. **API Rest**: Escrita em Java e Spring Boot com endpoints para simular funcionalidades do TradeMap
2. **API de Coleta B3**: Escrita em Python com Flask utilizando a biblioteca do Yahoo Finance
3. **Infraestrutura Docker**: Arquivos docker-compose para subir:
  - Broker Kafka
  - PostgreSQL
  - API Flask

## 🏗️ Arquitetura do Projeto

O projeto utiliza uma arquitetura baseada em microsserviços com comunicação via Kafka para processamento de dados em tempo real das ações da B3, integrado com MQTT para comunicação eficiente com aplicações móveis.

## ⚙️ Como Executar a Aplicação

### 1. Broker do Kafka
```bash
# Dentro da pasta arquivos-docker
docker-compose -f docker-compose-kafka.yml up -d
```

### 2. PostgreSQL
```bash
# Dentro da pasta arquivos-docker
docker-compose -f docker-compose-postgres.yml up -d
```

### 3. API Flask com Yahoo Finance
```bash
# Dentro da pasta api-flask-consulta-yahoo-b3
docker-compose up -d
```

### 4. Teste do Consumer Kafka
```bash
# Dentro da pasta api-flask-consulta-yahoo-b3
python3 consumer-teste-kafka.py
```

## 📱 Integração MQTT com Kafka Connect

Para que o aplicativo móvel receba as informações da API em tempo real, utilizamos o **MQTT**, priorizando velocidade e economia de recursos na troca de informações.

O **Kafka Connect** automatiza a transição das informações do Kafka para o Broker MQTT.

### Configuração do Kafka Connect MQTT

#### 1. Acessar o Container do Kafka Connect
```bash
docker exec -it kafka-connect bash
```

#### 2. Instalar o Kafka Connect MQTT
```bash
confluent-hub install confluentinc/kafka-connect-mqtt:latest
```
> **Nota**: A instalação deve ser realizada no path `/etc/kafka-connect/jars`

#### 3. Reiniciar o Container
```bash
# Sair do container
exit

# Reiniciar o container
docker restart kafka-connect
```

#### 4. Configurar o Connector
Com o auxílio do **Postman** (ou ferramenta similar), fazer uma requisição **POST** no endpoint:

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d @arquivos-docker/config-kafka-connect-mqtt.json \
  http://localhost:8083/connectors
```

> **Observação**: O tópico do MQTT será igual ao do Kafka configurado no JSON do connect.

## 📁 Estrutura do Projeto

```
TradeMapClone/
├── arquivos-docker/
│   ├── docker-compose-kafka.yml
│   ├── docker-compose-postgres.yml
│   └── config-kafka-connect-mqtt.json
├── api-flask-consulta-yahoo-b3/
│   ├── docker-compose.yml
│   └── consumer-teste-kafka.py
└── README.md
```

## 🔧 Endpoints da API

A API Rest fornece endpoints para simular as principais funcionalidades do TradeMap, incluindo:
- Consulta de ações em tempo real
- Histórico de preços
- Análise de mercado

## 📊 Monitoramento

O sistema permite monitoramento em tempo real através do consumer de teste do Kafka, facilitando o debug e acompanhamento dos dados das ações da B3.