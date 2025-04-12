package br.com.fiap.locatech.locatech.controllers;

import br.com.fiap.locatech.locatech.dtos.AluguelRequestDto;
import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.services.AluguelService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alugueis")
public class AluguelController {
    private static final Logger logger = LoggerFactory.getLogger(AluguelController.class);


    private final AluguelService aluguelService;
    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }


    // http://localhost:8080/pessoas?page=1&size=10
    @GetMapping
    public ResponseEntity<List<Aluguel>> findAllAlugueis(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        logger.info("Acessado o endpoint de alugueis /alugueis");
        var pessoas = this.aluguelService.findAllAluguel(page, size);
        return ResponseEntity.ok(pessoas);
    }

    // http://localhost:8080/pessoas/1
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Aluguel>> findAluguel(
            @PathVariable("id") Long id
    ) {
        logger.info("/alugueis/" + id);
        var pessoa = this.aluguelService.findAluguelById(id);
        return ResponseEntity.ok(pessoa);

    }

    @PostMapping
    public ResponseEntity<Void> saveAluguel(
           @Valid @RequestBody AluguelRequestDto aluguel
    ) {
        logger.info("POST -> /alugueis/");
        this.aluguelService.saveAluguel(aluguel);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAluguel(
            @PathVariable("id") Long id,
            @RequestBody Aluguel pessoa
    ){
        logger.info("PUT -> /alugueis/" + id);
        this.aluguelService.updateAluguel(pessoa, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluguel(
            @PathVariable("id") Long id
    ){
        logger.info("DELETE -> /alugueis/" + id);
        this.aluguelService.deleteAluguel(id);
        return ResponseEntity.ok().build();
    }
}
