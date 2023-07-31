package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsultaDTO;

public interface ValidadorAgendamentoDeConsulta {
    void validar (DadosAgendamentoConsultaDTO dados);
}
