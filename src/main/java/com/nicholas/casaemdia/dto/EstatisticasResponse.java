package com.nicholas.casaemdia.dto;

import java.util.List;

public record EstatisticasResponse(
        long totalSemana,
        long totalMes,
        List<PorTarefa> tarefas
) {
    public record PorTarefa(
            Long tarefaId,
            String nome,
            long vezesNaSemana,
            long vezesNoMes,
            int sequenciaDias
    ) {
    }
}