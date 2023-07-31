package med.voll.api.domain.endereco.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEnderecoDto(@NotBlank String logradouro,

		String numero,

		@NotBlank String bairro,

		@NotBlank String cidade,

		@NotBlank String uf,

		String complemento,

		@NotBlank @Pattern(regexp = "\\d{8}") String cep) {
}
