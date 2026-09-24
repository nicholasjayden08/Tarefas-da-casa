package com.nicholas.casaemdia.controller;

import com.nicholas.casaemdia.dto.EstatisticasResponse;
import com.nicholas.casaemdia.service.EstatisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatisticas")
@RequiredArgsConstructor
public class EstatisticaController {

    private final EstatisticaService service;

    @GetMapping
    public EstatisticasResponse gerar() {
        return service.gerar();
    }
}