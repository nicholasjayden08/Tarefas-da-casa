package com.nicholas.casaemdia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    private String comodo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequencia frequencia;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "tarefa_dias_semana", joinColumns = @JoinColumn(name = "tarefa_id"))
    @Column(name = "dia_semana")
    private Set<DayOfWeek> diasDaSemana = new HashSet<>();

    private Integer diaDoMes;

    @Enumerated(EnumType.STRING)
    private PeriodoDoDia periodoDoDia;

    private LocalTime horario;

    private boolean ativa = true;
}
