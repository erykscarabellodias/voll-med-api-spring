package med.voll.api.domain.paciente.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.dto.DadosEnderecoDto;

public record AtualizarPacienteDto(
		@NotNull
		Long id,
		String nome,
		String telefone,
		
		@Valid
		DadosEnderecoDto endereco
){}
