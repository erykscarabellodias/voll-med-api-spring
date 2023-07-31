package med.voll.api.domain.medico.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.endereco.dto.DadosEnderecoDto;
import med.voll.api.domain.medico.Especialidade;

public record CadastrarMedicoDto(

		@NotBlank String nome,

		@NotBlank @Email String email,
		
		@NotBlank String telefone,

		@NotBlank @Pattern(regexp = "\\d{6,8}", message = "CRM inválido") String crm,

		Especialidade especialidade,

		@Valid DadosEnderecoDto endereco
		) {
}
