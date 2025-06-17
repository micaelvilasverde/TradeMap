package br.com.trademapclone.resource;

import br.com.trademapclone.modelo.Acao;
import br.com.trademapclone.service.FlaskApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/acoes")
@CrossOrigin(origins = "*")
public class AcaoResource {
    private static final Logger logger = LoggerFactory.getLogger(AcaoResource.class);

    private final FlaskApiService flaskApiService;

    public AcaoResource(FlaskApiService flaskApiService) {
        this.flaskApiService = flaskApiService;
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Acao> consultarAcao(@PathVariable String codigo) {
        try {
            logger.info("Recebida requisição para consultar ação: {}", codigo);
            Acao acao = flaskApiService.consultarAcao(codigo);
            return ResponseEntity.ok(acao);
        } catch (Exception e) {
            logger.error("Erro ao processar requisição para ação {}: {}", codigo, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
} 