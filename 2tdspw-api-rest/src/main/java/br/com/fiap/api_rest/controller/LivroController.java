package br.com.fiap.api_rest.controller;

import br.com.fiap.api_rest.dto.LivroRequest;
import br.com.fiap.api_rest.dto.LivroRequestDTO;
import br.com.fiap.api_rest.dto.LivroResponse;
import br.com.fiap.api_rest.model.Livro;
import br.com.fiap.api_rest.repository.LivroRepository;
import br.com.fiap.api_rest.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/livros")
public class LivroController {
    @Autowired
    private LivroService livroService;
    @Autowired
    private LivroRepository livroRepository;

    // CREATE, READ, UPDATE, DELETE
    // POST, GET, PUT, DELETE

    @PostMapping
    public ResponseEntity<Livro> createLivro(@RequestBody LivroRequestDTO livro) {
        Livro livroSalvo = livroRepository.save(livroService.recordToLivro(livro));
        return new ResponseEntity<>(livroSalvo,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Livro>> readLivros() {
        List<Livro> livros = livroRepository.findAll();
        return new ResponseEntity<>(livros,HttpStatus.OK);
    }

    //@PathVariable localhost:8080/livros/1
    //@RequestParams localhost:8080/livros/?id=1

    @GetMapping("/{id}")
    public ResponseEntity<List<LivroResponse>> readLivro() {
        List<Livro> livros = livroRepository.findAll();
        List<LivroResponse> listaLivros = new ArrayList<>();
        for (Livro livro : livros ) {
            listaLivros.add(livroService.livroResponse(livro));
        }
        return new ResponseEntity<>(listaLivros, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> updateLivro(@RequestBody Livro livro, @PathVariable Long id) {
        Optional<Livro> LivroExistente = livroRepository.findById(id);
        if(LivroExistente.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        };
        livro.setId(LivroExistente.get().getId());
        Livro livroSalvo = livroRepository.save(livro);
        return new ResponseEntity<>(livroSalvo,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        livroRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
