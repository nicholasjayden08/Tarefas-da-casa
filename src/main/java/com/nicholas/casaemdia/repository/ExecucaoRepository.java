package com.nicholas.casaemdia.repository;

import com.nicholas.casaemdia.model.Execucao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ExecucaoRepository extends JpaRepository<Execucao, Long> {

    boolean existsByTarefaIdAndDataHoraConclusaoBetween(
            Long tarefaId, LocalDateTime inicio, LocalDateTime fim);

    void deleteByTarefaId(Long tarefaId);
}
