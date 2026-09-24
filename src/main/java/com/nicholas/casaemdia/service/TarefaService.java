package com.nicholas.casaemdia.service;

import com.nicholas.casaemdia.dto.TarefaRequest;
import com.nicholas.casaemdia.dto.TarefaResponse;
import com.nicholas.casaemdia.model.Execucao;
import com.nicholas.casaemdia.model.Tarefa;
import com.nicholas.casaemdia.repository.ExecucaoRepository;
import com.nicholas.casaemdia.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final ExecucaoRepository execucaoRepository;

    public List<TarefaResponse> listar() {
        return tarefaRepository.findByAtivaTrue().stream().map(this::paraResponse).toList();
    }

    public TarefaResponse buscar(Long id) {
        return paraResponse(buscarEntidade(id));
    }

    @Transactional
    public TarefaResponse criar(TarefaRequest req) {
        Tarefa tarefa = new Tarefa();
        aplicar(tarefa, req);
        return paraResponse(tarefaRepository.save(tarefa));
    }

    @Transactional
    public TarefaResponse atualizar(Long id, TarefaRequest req) {
        Tarefa tarefa = buscarEntidade(id);
        aplicar(tarefa, req);
        return paraResponse(tarefaRepository.save(tarefa));
    }

    @Transactional
    public void remover(Long id) {
        Tarefa tarefa = buscarEntidade(id);
        execucaoRepository.deleteByTarefaId(id);
        tarefaRepository.delete(tarefa);
    }

    @Transactional
    public TarefaResponse concluir(Long id) {
        Tarefa tarefa = buscarEntidade(id);
        if (feitaHoje(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tarefa já concluída hoje");
        }
        Execucao execucao = new Execucao();
        execucao.setTarefa(tarefa);
        execucao.setDataHoraConclusao(LocalDateTime.now());
        execucaoRepository.save(execucao);
        return paraResponse(tarefa);
    }

    private Tarefa buscarEntidade(Long id) {
        return tarefaRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));
    }

    private void aplicar(Tarefa t, TarefaRequest r) {
        t.setNome(r.nome());
        t.setDescricao(r.descricao());
        t.setComodo(r.comodo());
        t.setFrequencia(r.frequencia());
        t.setDiasDaSemana(r.diasDaSemana() == null ? new HashSet<>() : new HashSet<>(r.diasDaSemana()));
        t.setDiaDoMes(r.diaDoMes());
        t.setPeriodoDoDia(r.periodoDoDia());
        t.setHorario(r.horario());
    }

    private boolean feitaHoje(Long tarefaId) {
        LocalDate hoje = LocalDate.now();
        return execucaoRepository.existsByTarefaIdAndDataHoraConclusaoBetween(
                tarefaId, hoje.atStartOfDay(), hoje.atTime(LocalTime.MAX));
    }

    private TarefaResponse paraResponse(Tarefa t) {
        return TarefaResponse.de(t, feitaHoje(t.getId()));
    }
}
