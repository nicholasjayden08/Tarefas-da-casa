package com.nicholas.casaemdia.service;

import com.nicholas.casaemdia.dto.EstatisticasResponse;
import com.nicholas.casaemdia.dto.EstatisticasResponse.PorTarefa;
import com.nicholas.casaemdia.repository.ExecucaoRepository;
import com.nicholas.casaemdia.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstatisticaService {

    private final TarefaRepository tarefaRepository;
    private final ExecucaoRepository execucaoRepository;

    public EstatisticasResponse gerar() {
        LocalDate hoje = LocalDate.now();
        LocalDateTime iniSemana = hoje.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime fimSemana = iniSemana.plusDays(7).minusNanos(1);
        LocalDateTime iniMes = hoje.withDayOfMonth(1).atStartOfDay();
        LocalDateTime fimMes = iniMes.plusMonths(1).minusNanos(1);

        List<PorTarefa> porTarefa = tarefaRepository.findByAtivaTrue().stream()
                .map(t -> new PorTarefa(
                        t.getId(),
                        t.getNome(),
                        execucaoRepository.countByTarefaIdAndDataHoraConclusaoBetween(t.getId(), iniSemana, fimSemana),
                        execucaoRepository.countByTarefaIdAndDataHoraConclusaoBetween(t.getId(), iniMes, fimMes),
                        sequenciaDias(t.getId(), hoje)))
                .toList();

        long totalSemana = porTarefa.stream().mapToLong(PorTarefa::vezesNaSemana).sum();
        long totalMes = porTarefa.stream().mapToLong(PorTarefa::vezesNoMes).sum();
        return new EstatisticasResponse(totalSemana, totalMes, porTarefa);
    }

    private int sequenciaDias(Long tarefaId, LocalDate hoje) {
        Set<LocalDate> dias = execucaoRepository.findDatasConclusao(tarefaId).stream()
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toSet());
        LocalDate cursor = dias.contains(hoje) ? hoje : hoje.minusDays(1);
        int sequencia = 0;
        while (dias.contains(cursor)) {
            sequencia++;
            cursor = cursor.minusDays(1);
        }
        return sequencia;
    }
}