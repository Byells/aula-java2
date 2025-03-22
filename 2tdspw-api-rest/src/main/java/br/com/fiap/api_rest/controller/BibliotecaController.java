package br.com.fiap.api_rest.controller;

import br.com.fiap.api_rest.dto.BibliotecaRequest;
import br.com.fiap.api_rest.dto.BibliotecaResponseDTO;
import br.com.fiap.api_rest.model.Biblioteca;
import br.com.fiap.api_rest.repository.BibliotecaRepository;
import br.com.fiap.api_rest.service.BibliotecaService;
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
@RequestMapping(value = "/bibliotecas")
@Tag(name = "api-bibliotecas")
public class BibliotecaController {
    @Autowired
    private BibliotecaRepository bibliotecaRepository;
    @Autowired
    private BibliotecaService bibliotecaService;

    // CREATE, READ, UPDATE, DELETE
    // POST, GET, PUT, DELETE

    @Operation(summary = "Cria um novo biblioteca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Biblioteca criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Biblioteca.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros informados são inválidos",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<Biblioteca> createBiblioteca(@Valid @RequestBody BibliotecaRequest biblioteca) {
        Biblioteca bibliotecaSalvo = bibliotecaRepository.save(bibliotecaService.requestToBiblioteca(biblioteca));
        return new ResponseEntity<>(bibliotecaSalvo,HttpStatus.CREATED);
    }

    @Operation(summary = "Lista todos os bibliotecas por páginas")
    @GetMapping
    public ResponseEntity<Page<BibliotecaResponseDTO>> readBibliotecas(@RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest
                .of(pageNumber, 2, Sort.by("autor").ascending()
                        .and(Sort.by("titulo").ascending()));
        return new ResponseEntity<>(bibliotecaService.findAllDTO(pageable), HttpStatus.OK);
    }

    // @PathVariable localhost:8080/bibliotecas/1
    // @RequestParam localhost:8080/bibliotecas/?id=1
    @Operation(summary = "Retorna um biblioteca por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biblioteca encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BibliotecaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum biblioteca encontrado para o ID fornecido",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BibliotecaResponseDTO> readBiblioteca(@PathVariable Long id) {
        Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(id);
        if (biblioteca.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BibliotecaResponseDTO bibliotecaResponseDTO = bibliotecaService.bibliotecaToResponseDTO(biblioteca.get(), false);
        return new ResponseEntity<>(bibliotecaResponseDTO,HttpStatus.OK);
    }

    @Operation(summary = "Atualiza uma biblioteca existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Biblioteca atualizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Biblioteca.class))),
            @ApiResponse(responseCode = "400", description = "Nenhum biblioteca encontrado para o ID fornecido",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Biblioteca> updateBiblioteca(@PathVariable Long id,
                                             @RequestBody BibliotecaRequest biblioteca) {
        Optional<Biblioteca> bibliotecaExistente = bibliotecaRepository.findById(id);
        if (bibliotecaExistente.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Biblioteca bibliotecaConvertido = bibliotecaService.requestToBiblioteca(biblioteca);
        bibliotecaConvertido.setId(bibliotecaExistente.get().getId());
        Biblioteca bibliotecaSalvo = bibliotecaRepository.save(bibliotecaConvertido);
        return new ResponseEntity<>(bibliotecaSalvo,HttpStatus.CREATED);
    }

    @Operation(summary = "Exclui um biblioteca por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biblioteca excluída com sucesso",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Nenhuma biblioteca encontrado para o ID fornecido",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBiblioteca(@PathVariable Long id) {
        Optional<Biblioteca> bibliotecaExistente = bibliotecaRepository.findById(id);
        if (bibliotecaExistente.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        bibliotecaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}