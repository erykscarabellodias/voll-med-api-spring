package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.dto.DadosEnderecoDto;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
	private String logradouro;
	private String numero;
	private String bairro;
	private String cidade;
	private String uf;
	private String complemento;
	private String cep;

	public Endereco(DadosEnderecoDto dadosEndereco) {
		this.logradouro = dadosEndereco.logradouro();
		this.numero = dadosEndereco.numero();
		this.bairro = dadosEndereco.bairro();
		this.cidade = dadosEndereco.cidade();
		this.uf = dadosEndereco.uf();
		this.complemento = dadosEndereco.complemento();
		this.cep = dadosEndereco.cep();
	}

	public void atualizarDados(DadosEnderecoDto dadosEndereco) {
		if (dadosEndereco != null) {
			this.logradouro = dadosEndereco.logradouro();
		}

		if (dadosEndereco != null) {
			this.numero = dadosEndereco.numero();
		}

		if (dadosEndereco != null) {
			this.bairro = dadosEndereco.bairro();
		}

		if (dadosEndereco != null) {
			this.cidade = dadosEndereco.cidade();
		}

		if (dadosEndereco != null) {
			this.uf = dadosEndereco.uf();
		}

		if (dadosEndereco != null) {
			this.complemento = dadosEndereco.complemento();
		}

		if (dadosEndereco != null) {
			this.cep = dadosEndereco.cep();
		}
	}
}
