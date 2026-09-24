package com.nicholas.casaemdia.dto;

import java.util.List;

public record HojeResponse(
        List<TarefaResponse> manha,
        List<TarefaResponse> tarde,
        List<TarefaResponse> noite,
        List<TarefaResponse> semPeriodo
) {
}