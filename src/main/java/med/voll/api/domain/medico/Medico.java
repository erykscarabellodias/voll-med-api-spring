package med.voll.api.domain.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.dto.CadastrarMedicoDto;

@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String crm;
	private boolean ativo;

	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;

	@Embedded
	private Endereco endereco;

	public Medico(CadastrarMedicoDto dadosDeCadastro) {
		this.nome = dadosDeCadastro.nome();
		this.email = dadosDeCadastro.crm();
		this.crm = dadosDeCadastro.crm();
		this.especialidade = dadosDeCadastro.especialidade();
		this.telefone = dadosDeCadastro.telefone();
		this.ativo = true;
		this.endereco = new Endereco(dadosDeCadastro.endereco());
	}

	public void atualizarDados(AtualizarMedicoDto atualizarMedicoDto) {
		if (atualizarMedicoDto.nome() != null) {
			this.nome = atualizarMedicoDto.nome();
		}
		
		if (atualizarMedicoDto.telefone() != null) {
			this.telefone = atualizarMedicoDto.nome();
		}
		
		if (atualizarMedicoDto.endereco() != null) {
			this.endereco.atualizarDados(atualizarMedicoDto.endereco());
		}
	}

	public void desativar() {
		this.ativo = false;
	}

}
