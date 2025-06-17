package br.com.trademapclone.service;

import br.com.trademapclone.modelo.Acao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FlaskApiService {
    private static final Logger logger = LoggerFactory.getLogger(FlaskApiService.class);

    private final RestTemplate restTemplate;
    private final MqttService mqttService;
    private final ObjectMapper objectMapper;
    
    @Value("${flask.api.url:http://localhost:5000}")
    private String flaskApiUrl;

    public FlaskApiService(RestTemplate restTemplate, MqttService mqttService, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.mqttService = mqttService;
        this.objectMapper = objectMapper;
    }

    public Acao consultarAcao(String codigo) {
        try {
            logger.info("Consultando ação: {}", codigo);
            String url = String.format("%s/acoes/%s", flaskApiUrl, codigo);
            
            ResponseEntity<Acao> response = restTemplate.getForEntity(url, Acao.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Acao acao = response.getBody();
                logger.info("Dados da ação {} obtidos com sucesso", codigo);
                
                // Publica os dados da ação no MQTT
                String payload = objectMapper.writeValueAsString(acao);
                mqttService.publicarAcao(payload);
                
                return acao;
            } else {
                logger.error("Erro ao consultar ação {}: {}", codigo, response.getStatusCode());
                throw new RuntimeException("Erro ao consultar ação: " + response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Erro ao consultar ação {}: {}", codigo, e.getMessage());
            throw new RuntimeException("Erro ao consultar ação: " + e.getMessage(), e);
        }
    }
} 