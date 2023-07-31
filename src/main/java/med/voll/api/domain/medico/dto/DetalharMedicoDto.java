package med.voll.api.domain.medico.dto;

import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;

public record DetalharMedicoDto(Long id, String nome, String crm, String email, String telefone, Especialidade especialidade, Endereco endereco) {
	public DetalharMedicoDto(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getCrm(), medico.getEmail(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
	}
}
