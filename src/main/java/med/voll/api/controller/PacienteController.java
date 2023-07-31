package med.voll.api.controller;

import java.net.URI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.paciente.dto.AtualizarPacienteDto;
import med.voll.api.domain.paciente.dto.CadastrarPacienteDto;
import med.voll.api.domain.paciente.dto.DetalharPacienteDto;
import med.voll.api.domain.paciente.dto.ListagemDePacientesMapperDto;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
	@Autowired
	private PacienteRepository repository;

	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid CadastrarPacienteDto cadastrarPacienteDto, UriComponentsBuilder uriComponentBuilder) {
		Paciente paciente = new Paciente(cadastrarPacienteDto);

		this.repository.save(paciente);
		
		URI uri = uriComponentBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DetalharPacienteDto(paciente));
	}

	@GetMapping
	public ResponseEntity<Page<ListagemDePacientesMapperDto>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		Page<ListagemDePacientesMapperDto> pacientes = this.repository.findAllByAtivoTrue(paginacao).map(ListagemDePacientesMapperDto::new);
		
		return ResponseEntity.ok(pacientes);
	}
	
	@PutMapping()
	@Transactional
	public ResponseEntity<DetalharPacienteDto> atualizar(@RequestBody AtualizarPacienteDto atualizarPacienteDto) {
		Paciente paciente = this.repository.getReferenceById(atualizarPacienteDto.id());
		
		paciente.atualizarDados(atualizarPacienteDto);
		
		return ResponseEntity.ok(new DetalharPacienteDto(paciente));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity desativar(@PathVariable Long id) {
		Paciente paciente = this.repository.getReferenceById(id);
		
		paciente.desativar();
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalharPacienteDto> detalhar (@PathVariable Long id) {
		Paciente paciente = this.repository.getReferenceById(id);
		
		return ResponseEntity.ok(new DetalharPacienteDto(paciente));
	}
	
	
	
	
	
	
	
	
	
}
