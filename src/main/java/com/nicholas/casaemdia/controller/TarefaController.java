package com.nicholas.casaemdia.controller;

import com.nicholas.casaemdia.dto.TarefaRequest;
import com.nicholas.casaemdia.dto.TarefaResponse;
import com.nicholas.casaemdia.service.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.nicholas.casaemdia.dto.HojeResponse;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService service;

    @GetMapping
    public List<TarefaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/hoje")
    public HojeResponse hoje() {
        return service.hoje();
    }

    @GetMapping("/{id}")
    public TarefaResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TarefaResponse criar(@Valid @RequestBody TarefaRequest req) {
        return service.criar(req);
    }

    @PutMapping("/{id}")
    public TarefaResponse atualizar(@PathVariable Long id, @Valid @RequestBody TarefaRequest req) {
        return service.atualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }

    @PostMapping("/{id}/concluir")
    public TarefaResponse concluir(@PathVariable Long id) {
        return service.concluir(id);
    }

    @DeleteMapping("/{id}/concluir")
    public TarefaResponse desfazer(@PathVariable Long id) {
        return service.desfazer(id);
    }
}
