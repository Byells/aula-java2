package br.com.fiap.api_rest.controller;


import br.com.fiap.api_rest.model.Autor;
import br.com.fiap.api_rest.repository.AutorRepository;
import br.com.fiap.api_rest.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/autores")
@Tag(name = "api-autores")
public class AutorController {
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private AutorService autorService;

    // CREATE, READ, UPDATE, DELETE
    // POST, GET, PUT, DELETE

    @Operation(summary = "Cria um novo autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Autor.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros informados são inválidos",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<Autor> createAutor(@Valid @RequestBody AutorRequest autor) {
        Autor autorSalvo = autorRepository.save(autorService.requestToAutor(autor));
        return new ResponseEntity<>(autorSalvo,HttpStatus.CREATED);
    }

    @Operation(summary = "Lista todos os autors por páginas")
    @GetMapping
    public ResponseEntity<Page<AutorResponseDTO>> readAutors(@RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest
                .of(pageNumber, 2, Sort.by("autor").ascending()
                        .and(Sort.by("titulo").ascending()));
        return new ResponseEntity<>(autorService.findAllDTO(pageable), HttpStatus.OK);
    }

    // @PathVariable localhost:8080/autors/1
    // @RequestParam localhost:8080/autors/?id=1
    @Operation(summary = "Retorna um autor por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AutorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum autor encontrado para o ID fornecido",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> readAutor(@PathVariable Long id) {
        Optional<Autor> autor = autorRepository.findById(id);
        if (autor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AutorResponseDTO autorResponseDTO = autorService.autorToResponseDTO(autor.get(), false);
        return new ResponseEntity<>(autorResponseDTO,HttpStatus.OK);
    }

    @Operation(summary = "Atualiza um autor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Autor.class))),
            @ApiResponse(responseCode = "400", description = "Nenhum autor encontrado para o ID fornecido",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Autor> updateAutor(@PathVariable Long id,
                                             @RequestBody AutorRequest autor) {
        Optional<Autor> autorExistente = autorRepository.findById(id);
        if (autorExistente.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Autor autorConvertido = autorService.requestToAutor(autor);
        autorConvertido.setId(autorExistente.get().getId());
        Autor autorSalvo = autorRepository.save(autorConvertido);
        return new ResponseEntity<>(autorSalvo,HttpStatus.CREATED);
    }

    @Operation(summary = "Exclui um autor por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor excluído com sucesso",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Nenhum autor encontrado para o ID fornecido",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        Optional<Autor> autorExistente = autorRepository.findById(id);
        if (autorExistente.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        autorRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}