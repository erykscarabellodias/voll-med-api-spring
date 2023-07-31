package med.voll.api.infra.exception;

import java.util.List;

import jakarta.validation.ValidationException;
import med.voll.api.domain.ValidacaoException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity tratarErro404() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity <List<BeanValidationErrorDto>> tratarErroDoBeanValidation(MethodArgumentNotValidException ex) {
		List<FieldError> fieldErrors = ex.getFieldErrors();
		
		List<BeanValidationErrorDto> errosConvertidos = fieldErrors.stream().map(BeanValidationErrorDto::new).toList();
		
		return ResponseEntity.badRequest().body(errosConvertidos);
	}

	@ExceptionHandler (ValidacaoException.class)
	public ResponseEntity tratarErroDeValidacao(ValidacaoException ex) {
		return ResponseEntity.badRequest().body(new BadRequestResponseDto(ex.getMessage()));
	}
	
	private record BeanValidationErrorDto(String campo, String mensagem) {
		public BeanValidationErrorDto(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());			
		}
	}

	private record BadRequestResponseDto(String message) {
	}
}
