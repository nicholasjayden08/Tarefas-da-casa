package com.nicholas.casaemdia.repository;

import com.nicholas.casaemdia.model.Execucao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ExecucaoRepository extends JpaRepository<Execucao, Long> {

    boolean existsByTarefaIdAndDataHoraConclusaoBetween(
            Long tarefaId, LocalDateTime inicio, LocalDateTime fim);

    long countByTarefaIdAndDataHoraConclusaoBetween(
            Long tarefaId, LocalDateTime inicio, LocalDateTime fim);

    @Query("select e.dataHoraConclusao from Execucao e where e.tarefa.id = :tarefaId order by e.dataHoraConclusao desc")
    List<LocalDateTime> findDatasConclusao(@Param("tarefaId") Long tarefaId);

    void deleteByTarefaId(Long tarefaId);
}