package com.nicholas.casaemdia.repository;

import com.nicholas.casaemdia.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByAtivaTrue();
}
