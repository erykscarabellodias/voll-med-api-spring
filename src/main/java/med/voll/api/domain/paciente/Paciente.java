package med.voll.api.domain.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.paciente.dto.AtualizarPacienteDto;
import med.voll.api.domain.paciente.dto.CadastrarPacienteDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String cpf;
	private String telefone;
	private boolean ativo;

	@Embedded
	private Endereco endereco;

	public Paciente(CadastrarPacienteDto cadastrarPacienteDto) {
		this.nome = cadastrarPacienteDto.nome();
		this.email = cadastrarPacienteDto.email();
		this.cpf = cadastrarPacienteDto.cpf();
		this.telefone = cadastrarPacienteDto.telefone();
		this.ativo = true;
		this.endereco = new Endereco(cadastrarPacienteDto.endereco());
	}

	public void atualizarDados(AtualizarPacienteDto atualizarPacienteDto) {
		if (atualizarPacienteDto.nome() != null) {
			this.nome = atualizarPacienteDto.nome();
		}
		
		if (atualizarPacienteDto.telefone() != null) {
			this.nome = atualizarPacienteDto.telefone();
		}
		
		if (atualizarPacienteDto.endereco() != null) {
			this.endereco.atualizarDados(atualizarPacienteDto.endereco());
		}
	}

	public void desativar() {
		this.ativo = false;	
	}
}
