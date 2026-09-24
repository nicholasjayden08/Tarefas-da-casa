package com.nicholas.casaemdia.dto;

import com.nicholas.casaemdia.model.Frequencia;
import com.nicholas.casaemdia.model.PeriodoDoDia;
import jakarta.validation.constraints.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public record TarefaRequest(
        @NotBlank String nome,
        String descricao,
        String comodo,
        @NotNull Frequencia frequencia,
        Set<DayOfWeek> diasDaSemana,
        @Min(1) @Max(31) Integer diaDoMes,
        PeriodoDoDia periodoDoDia,
        LocalTime horario
) {
}
