package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsultaDTO;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {
    public void validar(DadosAgendamentoConsultaDTO dados) {
        LocalDateTime data = dados.data();
        LocalDateTime agora = LocalDateTime.now();

        Long diferencaEmMinutos = Duration.between(agora, data).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidacaoException("A consulta deve ser agenda com antecedência mínima de 30 minutos");
        }
    }
}
