package br.com.fiap.streamfiap.exception;
 
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
 
import java.util.Map;
 
@RestControllerAdvice

public class GlobalExceptionHandler {
 
    // Bug 09: Tratamento da exceção de classificação indicativa

    @ExceptionHandler(ClassificacaoIndicativaException.class)

    public ResponseEntity<Map<String, String>> handleClassificacaoIndicativa(ClassificacaoIndicativaException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)

                .body(Map.of("erro", ex.getMessage()));

    }
 
    // Bug 10: Retorno estruturado de erros genéricos de aluguel

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})

    public ResponseEntity<Map<String, String>> handleErrosAluguel(RuntimeException ex) {

        // Clean Code 05: Padronização na resposta dos erros da API

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)

                .body(Map.of("erro", ex.getMessage()));

    }

}
 