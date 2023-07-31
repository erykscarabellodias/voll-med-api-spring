package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsultaDTO;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {
    public void validar(DadosAgendamentoConsultaDTO dados) {
        LocalDateTime data = dados.data();

        boolean diaEhDomingo = data.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        boolean clinicaAindaNaoAbriu = data.getHour() < 7;

        boolean clinicaJaFechou = data.getHour() > 18;

        if (diaEhDomingo || clinicaAindaNaoAbriu || clinicaJaFechou) {
            throw new ValidacaoException("Consulta fora do horário de atendimento da clínica");
        }
    }
}
