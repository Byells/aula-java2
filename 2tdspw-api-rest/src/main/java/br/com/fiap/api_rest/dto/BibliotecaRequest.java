package br.com.fiap.api_rest.dto;


public class BibliotecaRequest {

    private String nome;
    private EnderecoRequest enderecoRequest;

    public EnderecoRequest getEnderecoRequest() {
        return enderecoRequest;
    }

    public void setEnderecoRequest(EnderecoRequest enderecoRequest) {
        this.enderecoRequest = enderecoRequest;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
