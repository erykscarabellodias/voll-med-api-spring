package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {
    @Autowired
    private ConsultaRepository repository;
    public void validar(DadosAgendamentoConsultaDTO dados) {
        boolean medicoPossuiConsultaNoMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());

        if (medicoPossuiConsultaNoMesmoHorario) {
            throw new ValidacaoException("Este médico já tem uma consulta neste horário");
        }
    }
}
