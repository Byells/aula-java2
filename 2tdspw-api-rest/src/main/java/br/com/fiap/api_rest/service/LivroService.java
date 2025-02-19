package br.com.fiap.api_rest.service;

import br.com.fiap.api_rest.dto.LivroRequest;
import br.com.fiap.api_rest.dto.LivroRequestDTO;
import br.com.fiap.api_rest.dto.LivroResponse;
import br.com.fiap.api_rest.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivroService {
    public Livro requestToLivro(LivroRequest LivroRequest){
        Livro livro = new Livro();
        livro.setAutor(LivroRequest.getAutor());
        livro.setTitulo(LivroRequest.getTitulo());
        livro.setPreco(LivroRequest.getPreco());
        livro.setCategoria(LivroRequest.getCategoria());
        livro.setIsbn(LivroRequest.getIsbn());
        return livro;

    }

    public Livro recordToLivro(LivroRequestDTO LivroRecord){
        Livro livro = new Livro();
        livro.setAutor(LivroRecord.autor());
        livro.setTitulo(LivroRecord.titulo());
        return livro;
    }

    public LivroResponse livroToResponse(Livro livro){
        return new LivroResponse(livro.getAutor() + " - " + livro.getAutor());

    }

    public List<LivroResponse> livrosToResponse(List<Livro> livros){
        List<LivroResponse> listaLivros = new ArrayList<>();
        for(Livro livro:  livros){
            listaLivros.add(livroToResponse(livro));
        }
        return listaLivros;
    }

}
