package med.voll.api.controller;

import java.net.URI;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.AtualizarMedicoDto;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.medico.dto.CadastrarMedicoDto;
import med.voll.api.domain.medico.dto.DetalharMedicoDto;
import med.voll.api.domain.medico.dto.ListagemDeMedicosMapperDto;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
	@Autowired
	private MedicoRepository repository;

	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid CadastrarMedicoDto cadastrarMedicoDto, UriComponentsBuilder uriComponentBuilder) {
		Medico medico = new Medico(cadastrarMedicoDto);

		this.repository.save(medico);
		
		URI uri = uriComponentBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DetalharMedicoDto(medico));
	}

	@GetMapping
	public ResponseEntity<Page<ListagemDeMedicosMapperDto>> listar(@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
		Page<ListagemDeMedicosMapperDto> medicos =  this.repository.findAllByAtivoTrue(paginacao).map(ListagemDeMedicosMapperDto::new);
		
		return ResponseEntity.ok(medicos);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DetalharMedicoDto> atualizar(@RequestBody @Valid AtualizarMedicoDto atualizarMedicoDto) {
		Medico medico = this.repository.getReferenceById(atualizarMedicoDto.id());
		
		medico.atualizarDados(atualizarMedicoDto);
		
		return ResponseEntity.ok(new DetalharMedicoDto(medico));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity desativar(@PathVariable Long id) {
		Medico medico = this.repository.getReferenceById(id);
		
		medico.desativar();
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalharMedicoDto> detalhar (@PathVariable Long id) {
		Medico medico = this.repository.getReferenceById(id);
		
		return ResponseEntity.ok(new DetalharMedicoDto(medico));
	}
}
