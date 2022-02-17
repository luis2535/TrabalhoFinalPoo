package negocio;
import dados.*;
import persistencia.*;
import exceptions.*;
import java.util.*;
import java.sql.*;

public class CaixaDeMensagens {
	private List<Usuario> usuarios = new LinkedList<Usuario>();
	private UsuarioDAO usuarioDAO;
	private EmailDAO emailDAO;
	private Usuario logado = new Usuario();
	private Usuario destinatario = new Usuario();
	private static CaixaDeMensagens instance = null;
	
	public static CaixaDeMensagens getInstance() {
		if(instance == null) {
			try {
				instance = new CaixaDeMensagens();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SelectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}
	public CaixaDeMensagens() throws ClassNotFoundException, SQLException, SelectException {
			usuarioDAO = UsuarioDAO.getInstance();
			emailDAO = EmailDAO.getInstance();
		
	}
	public List<Usuario> retornaUsuarios() throws SelectException{
		return usuarioDAO.selectAll();
	}
	public Usuario getUsuarioLogado() {
		return logado;
	}
	public Usuario getUsuarioDestinatario() {
		return destinatario;
	}
	public void adicionaUsuario(Usuario usuario) throws UsuarioJaCadastradoException, InsertException, SelectException {
		if(!comparaUsuario(usuario)) {
			usuarioDAO.insert(usuario);
		}else {
			throw new UsuarioJaCadastradoException("Usuario ja cadastrado!\n");
			
		}
	}
	public Usuario logarUsuario(Usuario usuario) throws UsuarioInvalidoException, SelectException {
		Usuario login = new Usuario();
		login = null;
		usuarios = usuarioDAO.selectAll();
		for(Usuario u : usuarios) {
			if(u.getNome().equals(usuario.getNome())) {
				if(u.getSenha().equals(usuario.getSenha())) {
					System.out.println("Login realizado com sucesso!\n");
					login = usuarioDAO.select(usuario.getNome());
					this.logado = login;
					break;
				}
			}
		}
		if(login == null) {
			throw new UsuarioInvalidoException("Usuario ou senha incorreto!\n");
		}
		return login;
		
	}
	public void enviarEmail(Email mensagem) throws DestinatarioInexistenteException, DestinatarioInvalidoException, SelectException, InsertException {
		if(mensagem.getDestinatario() == null) {
			throw new DestinatarioInexistenteException("Destinatario não encontrado nos cadastros!");
		}else if(mensagem.getDestinatario().getNome().equals(mensagem.getRemetente().getNome())) {
			throw new DestinatarioInvalidoException("Não é possivel enviar um e-mail para si mesmo!");
		}else {
			String corpo = mensagem.getCorpo();
			int id = emailDAO.selectNewId();
			mensagem.setId(id);
			mensagem.setCorpo(codificaEmail(mensagem,corpo));
			
			emailDAO.insert(mensagem);
		}
		
	}	
	public void excluirEmail(Usuario usuario, int id) throws EmailInexistenteException, DeleteException, SelectException {
		Email excluir = new Email();
		if(compararId(usuario,id)) {
			excluir = emailSelecionado(usuario,id);
			emailDAO.delete(excluir);
		}else {
			throw new EmailInexistenteException("Esse e-mail não existe!");
		}
		
	}
	public Email responderEmail(Usuario usuario, int id) throws EmailInexistenteException, SelectException {
		Email resposta = new Email();
		if(compararId(usuario,id)) {
			Email selecionado = emailSelecionado(usuario, id);
			resposta.setDestinatario(selecionado.getRemetente());
			this.destinatario = resposta.getDestinatario();
			resposta.setRemetente(usuario);
		}else {
			resposta = null;
			throw new EmailInexistenteException("Esse e-mail não existe!");
		}
		return resposta;
	}
	public Usuario usuarioSelecionado(Usuario usuario) throws SelectException {
		Usuario selecionado = new Usuario();
		selecionado = null;
		usuarios = usuarioDAO.selectAll();
		for(Usuario u : usuarios) {
			if(u.getNome().equals(usuario.getNome())) {
				selecionado = u;
			}
		}
		return selecionado;
		
	}
	public boolean compararId(Usuario usuario, int id) throws SelectException {
		Email novo = new Email();
		List<Email> lista = emailDAO.select(usuario);
		novo.setId(id);
		if(!(lista.size()==0)) {
			for(Email e : lista) {
				if(e.getId() == novo.getId()) {
					return true;
				}
			}	
		}
		return false;
	}
	public List<Email> imprimirEmailLogin(Usuario login) throws SelectException, CaixaDeMensagensVaziaException {
		List<Email> lista = emailDAO.select(login);
		if(lista.size() == 0) {
			throw new CaixaDeMensagensVaziaException("Caixa de Mensagens vazia!");
		}
		return lista;
	}
	private Email emailSelecionado(Usuario usuario, int id) throws SelectException {
		Email novo = new Email();
		novo.setId(id);
		List<Email> emails = emailDAO.select(usuario);
		for(Email e : emails) {
			if(e.equals(novo)) {
				novo = e;
				break;
			}
		}
		return novo;
	}
	private String codificaEmail(Email email, String corpo) {
		char []corpoEmail = corpo.toCharArray();
		int cifra = email.getUnidadeData()+email.getUnidadeId();
		String corpoNovo = "";
		for(int i = 0; i <corpo.length();i++) {
			if(corpoEmail[i]>=65 && corpoEmail[i]<=90) {
				char c = (char) (corpoEmail[i] + cifra);
				if(c > 90) {
					corpoNovo += (char) (corpoEmail[i] - (26 - cifra));
				}else {
					corpoNovo += (char) (corpoEmail[i] + cifra);
				}
			}else if(corpoEmail[i] >= 97 && corpoEmail[i] <= 122) {
				char c = (char) (corpoEmail[i] + cifra);
				if(c > 122) {
					corpoNovo += (char) (corpoEmail[i] - (26 - cifra));
				}else {
					corpoNovo += (char) (corpoEmail[i] + cifra);
				}
				
			}else {
				corpoNovo += (char) (corpoEmail[i]);
			}
		}
		return corpoNovo;
	}
	private boolean comparaUsuario(Usuario usuario) throws SelectException {
		usuarios = usuarioDAO.selectAll();
		if(!(usuarios.size()==0)) {
			for(Usuario u : usuarios) {
				if(u.getNome().equals(usuario.getNome())) {
					return true;
				}
			}
		}
		return false;
		
	}
	

}
