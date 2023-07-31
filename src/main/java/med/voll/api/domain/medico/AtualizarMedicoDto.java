package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.dto.DadosEnderecoDto;

public record AtualizarMedicoDto(
	@NotNull
	Long id,
	String nome,
	String telefone,
	
	@Valid
	DadosEnderecoDto endereco
){}
