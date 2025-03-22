package br.com.fiap.api_rest.dto;

public class BibliotecaResponse {

    private Long id;
    private String nome;
    private EnderecoResponse enderecoResponse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EnderecoResponse getEnderecoResponse() {
        return enderecoResponse;
    }

    public void setEnderecoResponse(EnderecoResponse enderecoResponse) {
        this.enderecoResponse = enderecoResponse;
    }
}
