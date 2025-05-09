package com.example.picpay_challenger.controller;

import com.example.picpay_challenger.domain.model.ErrorResponse;
import com.example.picpay_challenger.suport.expection.AuthenticationException;
import com.example.picpay_challenger.suport.expection.BadGatewayException;
import com.example.picpay_challenger.suport.expection.BadRequestException;
import com.example.picpay_challenger.suport.expection.NotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonMappingException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestController
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @JsonFormat
    ErrorResponse badRequestException(final BadRequestException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({BadGatewayException.class})
    @ResponseStatus(value = HttpStatus.BAD_GATEWAY)
    @JsonFormat
    ErrorResponse badGatewayException(final BadGatewayException e) {
        return new ErrorResponse(HttpStatus.BAD_GATEWAY.value(), e.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @JsonFormat
    ErrorResponse forbiddenException(final AccessDeniedException e) {
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ExceptionHandler({JsonMappingException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleJsonMappingError(final JsonMappingException e) {
        // Formatar a mensagem de erro com base na exceção
        String mensagemErro = "Erro ao processar sua solicitação. Verifique os dados enviados.";

        // Caso o erro tenha relação com o enum
        if (e.getMessage().contains("Cannot deserialize value")) {
            mensagemErro = "O valor fornecido para o campo é inválido. Verifique os valores permitidos para o campo.";
        }

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                mensagemErro
        );
    }
    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @JsonFormat
    ErrorResponse authenticationException(final AuthenticationException e) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @JsonFormat
    ErrorResponse userNotFoundException(final NotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(FeignException.Forbidden.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleFeignForbidden(FeignException.Forbidden ex) {
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Autorização negada: " + extractFeignMessage(ex));
    }

    private String extractFeignMessage(FeignException ex) {
        try {
            return ex.contentUTF8();
        } catch (Exception ignored) {
            return ex.getMessage();
        }
    }
}
