package med.voll.api.domain.medico.dto;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;

public record ListagemDeMedicosMapperDto(Long id, String nome, String telefone, String email, Especialidade especialidade) {
	public ListagemDeMedicosMapperDto(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getTelefone(), medico.getEmail(), medico.getEspecialidade());
	}
}
