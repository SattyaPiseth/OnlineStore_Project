package co.devkh.onlinestore.reviewonlinestore.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class CredentialException extends RuntimeException {
	private final HttpStatus status; 
	private final String message;
}
