package br.com.fiap.locatech.locatech.controllers;

import br.com.fiap.locatech.locatech.dtos.PessoaRequestDTO;
import br.com.fiap.locatech.locatech.entities.Pessoa;
import br.com.fiap.locatech.locatech.services.PessoaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/pessoas")
public class PessoaController {
    private static final Logger logger = LoggerFactory.getLogger(PessoaController.class);


    private final PessoaService pessoaService;
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }


    // http://localhost:8080/pessoas?page=1&size=10
    @GetMapping
    public ResponseEntity<List<Pessoa>> findAllPessoas(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        logger.info("Acessado o endpoint de pessoas /pessoas");
        var pessoas = this.pessoaService.findAllPessoa(page, size);
        return ResponseEntity.ok(pessoas);
    }

    // http://localhost:8080/pessoas/1
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Pessoa>> findPessoa(
            @PathVariable("id") Long id
    ) {
        logger.info("/pessoas/" + id);
        var pessoa = this.pessoaService.findPessoaById(id);
        return ResponseEntity.ok(pessoa);

    }

    @PostMapping
    public ResponseEntity<Void> savePessoa(
           @Valid @RequestBody PessoaRequestDTO pessoa
    ) {
        logger.info("POST -> /pessoas/");
        this.pessoaService.savePessoa(pessoa);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePessoa(
            @PathVariable("id") Long id,
            @RequestBody Pessoa pessoa
    ){
        logger.info("PUT -> /pessoas/" + id);
        this.pessoaService.updatePessoa(pessoa, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(
            @PathVariable("id") Long id
    ){
        logger.info("DELETE -> /pessoas/" + id);
        this.pessoaService.deletePessoa(id);
        return ResponseEntity.ok().build();
    }

}
