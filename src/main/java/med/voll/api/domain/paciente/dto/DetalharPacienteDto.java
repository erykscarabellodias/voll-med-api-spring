package med.voll.api.domain.paciente.dto;

import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.paciente.Paciente;

public record DetalharPacienteDto(Long id, String nome, String telefone, String email, String cpf, Endereco endereco) {
	public DetalharPacienteDto(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getTelefone(), paciente.getEmail(), paciente.getCpf(), paciente.getEndereco());
	}
}
