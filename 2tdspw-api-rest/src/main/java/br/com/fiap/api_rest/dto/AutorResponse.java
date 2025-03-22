package br.com.fiap.api_rest.dto;

public class AutorResponse {

    private Long id;

    private String nome;

    public AutorResponse(Long id, String nome){
        this.id = id;
        this.nome = nome;

    }

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
}
