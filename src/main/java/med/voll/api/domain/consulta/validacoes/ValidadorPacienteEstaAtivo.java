package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsultaDTO;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteEstaAtivo implements ValidadorAgendamentoDeConsulta {
    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsultaDTO dados) {
        boolean pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());

        if (!pacienteEstaAtivo) {
            throw new ValidacaoException("Este paciente não está ativo");
        }
    }
}
