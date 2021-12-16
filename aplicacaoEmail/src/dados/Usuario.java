package dados;
import java.util.List;
import java.util.LinkedList;

public class Usuario {
	String nome;
	String senha;
	List<Email> listaDeEmails = new LinkedList<Email>();
	
	public Usuario() {	
	}
	public Usuario(String nome, String senha) {
		this.nome = nome;
		this.senha = senha;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String toString() {
		return nome;
	}
	
	
}
