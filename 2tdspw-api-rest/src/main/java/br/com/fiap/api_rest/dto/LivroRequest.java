package br.com.fiap.api_rest.dto;

import br.com.fiap.api_rest.model.Categoria;
import jakarta.validation.constraints.*;

public class LivroRequest {
    @NotBlank(message = "O titulo não pode estar vazio")
    @Size(min = 3, max= 254, message = "O Titulo deve ter entre 3 e 150 caracteres")
    private String titulo;

    @NotBlank(message = "O titulo não pode estar vazio")
    @Size(min = 3, max= 254, message = "O Titulo deve ter entre 3 e 150 caracteres")
    private String autor;
    @Min(value= 1, message= "O preço deve ser no mínimo 1")
    @Max(value = 99, message = "O preço deve ser no máximo 99")
    private int preco;
    @NotNull(message = "A categoria é obrigatória")
    private Categoria categoria;
    @Pattern(regexp = "^970\\d{7}$|^970\\d{10}$", message = "O ISBN deve começar com 970 e ter 10 ou 13 dígitos")
    private String isbn;

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() { return autor; }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
