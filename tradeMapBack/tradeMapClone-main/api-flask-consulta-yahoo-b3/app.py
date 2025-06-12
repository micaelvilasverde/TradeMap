from flask import Flask, jsonify
from yahooquery import Ticker
from datetime import datetime
import pytz
import random
import json
import os
from kafka import KafkaProducer

app = Flask(__name__)
TOPICO_KAFKA = 'acao.b3.dados'

def criar_produtor():
    return KafkaProducer(
        bootstrap_servers='kafka:9092',
        value_serializer=lambda v: json.dumps(v).encode('utf-8')
    )

@app.route('/')
def index():
    return "API para gerar valores de ações do B3."

@app.route('/acoes/<codigo_acao>', methods=['GET'])
def gerar_valor_acao(codigo_acao):
    ticker_acao = Ticker(codigo_acao)
    resultado = ticker_acao.history(period="7d", interval="5m")

    if resultado.empty:
        return jsonify({"erro": "Não foi possível obter dados para o código informado."}), 404

    resultado.reset_index(inplace=True)
    index = random.randrange(0, resultado.shape[0])
    resultado = resultado.iloc[[index]]

    retorno = {
        'codigo': codigo_acao,
        'horario': datetime.now(pytz.timezone('America/Sao_Paulo')).isoformat(),
        'valor': float(resultado['close'].values[0])
    }

    try:
        producer = criar_produtor()
        producer.send(TOPICO_KAFKA, retorno)
        producer.flush()
    except Exception as e:
        print("Erro ao enviar para o Kafka:", e)

    return jsonify(retorno), 200

if __name__ == "__main__":
    port = int(os.environ.get('PORT', 5000))
    app.run(host='0.0.0.0', port=port)
