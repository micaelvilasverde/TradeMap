from flask import Flask, jsonify
import requests
from datetime import datetime
import pytz
import random
import json
import os
import logging
from kafka import KafkaProducer

# Configuração de logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = Flask(__name__)
TOPICO_KAFKA = 'acao.b3.dados'
ALPHA_VANTAGE_API_KEY = 'MJG3MN81R8MCMD8F'
ALPHA_VANTAGE_BASE_URL = 'https://www.alphavantage.co/query'

def criar_produtor():
    try:
        config = {
            'bootstrap_servers': ['kafka:19092'],
            'value_serializer': lambda v: json.dumps(v).encode('utf-8'),
            'request_timeout_ms': 30000,
            'api_version': (2, 4, 0),
            'client_id': 'flask-producer',
            'retries': 3,
            'retry_backoff_ms': 1000,
            'max_block_ms': 60000,
            'connections_max_idle_ms': 540000,
            'security_protocol': 'PLAINTEXT',
            'metadata_max_age_ms': 300000,
            'acks': 1
        }
        app.logger.info(f"Tentando criar produtor Kafka com configuração: {config}")
        producer = KafkaProducer(**config)
        app.logger.info("Produtor Kafka criado com sucesso")
        return producer
    except Exception as e:
        app.logger.error(f"Erro ao criar produtor Kafka: {str(e)}")
        return None

@app.route('/')
def index():
    return "API para gerar valores de ações do B3."

@app.route('/acoes/<acao>')
def consultar_acao(acao):
    try:
        app.logger.info(f"Recebida requisição para ação: {acao}")
        
        # Consulta Alpha Vantage
        app.logger.info(f"Consultando Alpha Vantage para: {acao}.SA")
        response = requests.get(
            f'https://www.alphavantage.co/query',
            params={
                'function': 'GLOBAL_QUOTE',
                'symbol': f'{acao}.SA',
                'apikey': ALPHA_VANTAGE_API_KEY
            },
            timeout=10
        )
        
        if response.status_code != 200:
            app.logger.error(f"Erro na consulta Alpha Vantage: {response.status_code}")
            return jsonify({'erro': 'Erro na consulta Alpha Vantage'}), 500
            
        data = response.json()
        app.logger.info(f"Dados obtidos com sucesso para: {acao}.SA")
        
        # Envia para o Kafka
        producer = criar_produtor()
        if producer:
            try:
                app.logger.info("Tentando enviar mensagem para o Kafka")
                # Garante que os valores numéricos sejam convertidos para float
                if 'Global Quote' in data:
                    quote = data['Global Quote']
                    for key in quote:
                        try:
                            quote[key] = float(quote[key].replace(',', ''))
                        except (ValueError, AttributeError):
                            pass
                
                future = producer.send('acao.b3.dados', value=data)
                app.logger.info("Aguardando confirmação do Kafka")
                future.get(timeout=10)
                app.logger.info("Mensagem enviada com sucesso para o Kafka")
            except Exception as e:
                app.logger.error(f"Erro ao enviar mensagem para o Kafka: {str(e)}")
            finally:
                producer.close()
        
        return jsonify(data)
        
    except Exception as e:
        app.logger.error(f"Erro ao processar requisição: {str(e)}")
        return jsonify({'erro': str(e)}), 500

if __name__ == "__main__":
    port = int(os.environ.get('PORT', 5000))
    app.run(host='0.0.0.0', port=port)
