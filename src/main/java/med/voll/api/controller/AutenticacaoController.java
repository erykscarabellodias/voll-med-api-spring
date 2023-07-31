package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import jakarta.validation.Valid;
import med.voll.api.infra.security.DadosTokenJwt;
import med.voll.api.infra.security.TokenService;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.dto.DadosAutenticacao;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		// DTO de autenticação do Spring
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dados.usuario(), dados.senha());
		
		// Dispara a autenticação
		Authentication authentication = this.manager.authenticate(token);
		
		String tokenJwt = tokenService.gerarTokenJwt((Usuario)authentication.getPrincipal());
		return ResponseEntity.ok(new DadosTokenJwt(tokenJwt));
	}
}
