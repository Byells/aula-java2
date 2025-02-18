package br.com.fiap.api_rest.service;

import br.com.fiap.api_rest.dto.LivroRequest;
import br.com.fiap.api_rest.dto.LivroRequestDTO;
import br.com.fiap.api_rest.dto.LivroResponse;
import br.com.fiap.api_rest.model.Livro;

public class LivroService {
    public Livro requestToLivro(LivroRequest LivroRequest){
        Livro livro = new Livro();
        livro.setAutor(LivroRequest.getAutor());
        livro.setTitulo(LivroRequest.getTitulo());
        return livro;

    }

    public Livro recordToLivro(LivroRequestDTO LivroRecord){
        Livro livro = new Livro();
        livro.setAutor(LivroRecord.autor());
        livro.setTitulo(LivroRecord.titulo());
        return livro;
    }

    public LivroResponse livroResponse(Livro livro){
        return new LivroResponse(livro.getAutor() + " - " + livro.getAutor());

    }

}
