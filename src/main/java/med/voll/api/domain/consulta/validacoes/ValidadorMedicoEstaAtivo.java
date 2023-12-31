package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsultaDTO;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoEstaAtivo implements ValidadorAgendamentoDeConsulta {
    @Autowired
    private MedicoRepository repository;
    public void validar(DadosAgendamentoConsultaDTO dados)  {
        if (dados.idMedico() == null) {
            return;
        }

        Boolean medicoAtivo = repository.findAtivoById(dados.idMedico());

        if (!medicoAtivo) {
            throw new ValidacaoException("Este médico não está ativo");
        }
    }
}
