package exceptions;

public class EmailInexistenteException extends Exception {

	public EmailInexistenteException() {
		
	}
	public EmailInexistenteException(String mensagem) {
		super(mensagem);
	} 

} 