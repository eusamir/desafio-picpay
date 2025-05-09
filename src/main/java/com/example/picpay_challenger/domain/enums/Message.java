package com.example.picpay_challenger.domain.enums;

import lombok.Getter;

@Getter
public enum Message {
    EMAIL_JA_EM_USO("E-mail já em uso."),
    CPF_JA_EM_USO("CPF já em uso."),
    USUARIO_NAO_ENCONTRADO("Usuário não encontrado."),
    LOGISTAS_NAO_PODEM_TRANSFERIR("Logistas não podem realizar transferências."),
    CARTEIRA_NAO_ENCONTRADA("Carteira não encontrada."),
    SALDO_INSUFICIENTE("Saldo insuficiente."),
    TRANSFERENCIA_NAO_AUTORIZADA("Transferência não autorizada pelo serviço externo."),

    ERRO_NAO_CATALOGADO("Erro não catalogado.");

    private final String message;

    Message(String message) {
        this.message = message;
    }
}
