package med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import med.voll.api.domain.usuario.Usuario;

@Service
public class TokenService {
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String gerarTokenJwt(Usuario usuario) {
		try {
			Algorithm algoritmo = Algorithm.HMAC256(this.secret);
			
			String token = JWT.create()
					.withIssuer("voll.med")
					.withSubject(usuario.getUsername())
					.withClaim("id", usuario.getId())
					.withExpiresAt(this.dataExpiracao())
					.sign(algoritmo);
			
			return token;
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Erro ao gerar token JWT", exception);
		}
	}
	
	public String getSubject(String token) {
		try {
			Algorithm algoritmo = Algorithm.HMAC256(this.secret);
			
			var subject = JWT.require(algoritmo)
					.withIssuer("voll.med")
					.build()
					.verify(token)
					.getSubject();
			
			return subject;			
		} catch (JWTVerificationException exception) {
			System.out.println(exception);
			throw new RuntimeException("Token inválido ou expirado");
		}
	}
	
	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
