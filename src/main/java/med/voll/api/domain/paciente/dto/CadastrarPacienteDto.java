package med.voll.api.domain.paciente.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.dto.DadosEnderecoDto;

public record CadastrarPacienteDto(
		@NotBlank String nome, 
		@NotBlank @Email String email, 
		@NotBlank String telefone,
		@NotBlank @Pattern(regexp = "\\d{11}", message = "CPF inválido") String cpf,
		@Valid DadosEnderecoDto endereco
){}
