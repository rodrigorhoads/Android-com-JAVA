package com.example.rodrigosilva.projetodoestudo;

/**
 * Created by rodrigo.silva on 28/03/2018.
 */

public class Tag {
    private String nome;
    private String negocioId;

    public Tag(String nome, String negocioId) {
        this.nome = nome;
        this.negocioId = negocioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNegocioId() {
        return negocioId;
    }

    public void setNegocioId(String negocioId) {
        this.negocioId = negocioId;
    }
}
