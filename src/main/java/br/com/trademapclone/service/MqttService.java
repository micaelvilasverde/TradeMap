package br.com.trademapclone.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.messaging.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MqttService {
    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);

    private final MessageHandler mqttOutbound;

    @Value("${mqtt.topic.acoes}")
    private String topicAcoes;

    public MqttService(MessageHandler mqttOutbound) {
        this.mqttOutbound = mqttOutbound;
    }

    public void publicarAcao(String payload) {
        try {
            logger.info("Publicando mensagem no tópico {}: {}", topicAcoes, payload);
            
            Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader(MqttHeaders.TOPIC, topicAcoes)
                .build();

            mqttOutbound.handleMessage(message);
            logger.info("Mensagem publicada com sucesso");
        } catch (Exception e) {
            logger.error("Erro ao publicar mensagem MQTT: {}", e.getMessage());
            throw new RuntimeException("Erro ao publicar mensagem MQTT", e);
        }
    }
} 