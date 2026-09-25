package com.nicholas.casaemdia.dto;

import com.nicholas.casaemdia.model.Frequencia;
import com.nicholas.casaemdia.model.PeriodoDoDia;
import com.nicholas.casaemdia.model.Tarefa;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public record TarefaResponse(
        Long id,
        String nome,
        String descricao,
        String comodo,
        Frequencia frequencia,
        Set<DayOfWeek> diasDaSemana,
        Integer diaDoMes,
        PeriodoDoDia periodoDoDia,
        LocalTime horario,
        boolean ativa,
        boolean feitaHoje,
        boolean atrasada
) {
    public static TarefaResponse de(Tarefa t, boolean feitaHoje) {
        boolean atrasada = !feitaHoje && t.getHorario() != null
                && t.getHorario().isBefore(java.time.LocalTime.now());
        return new TarefaResponse(t.getId(), t.getNome(), t.getDescricao(), t.getComodo(),
                t.getFrequencia(), Set.copyOf(t.getDiasDaSemana()), t.getDiaDoMes(),
                t.getPeriodoDoDia(), t.getHorario(), t.isAtiva(), feitaHoje, atrasada);
    }
}
