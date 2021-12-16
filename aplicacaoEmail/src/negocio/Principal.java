package negocio;
import dados.*;
import exceptions.*;

import java.util.*;
import java.sql.SQLException;
import java.text.*;

public class Principal {
	static CaixaDeMensagens sistema;
	static Usuario login = new Usuario();
	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) throws ClassNotFoundException, SQLException, SelectException {
		sistema = new CaixaDeMensagens();
		int opcao = -1;
		while(opcao != 0 ) {
			opcao = menuCadastro();
			switch(opcao) {
			case 1:
				adicionarUsuario();
				break;
			case 2:
				Usuario novo = new Usuario();
				System.out.print("Nome:");
				novo.setNome(scan.nextLine());
				System.out.print("Senha:");
				novo.setSenha(scan.nextLine());
				System.out.println("");
				login = logarUsuario(novo);
				if(login != null) {					
					System.out.println("Bem vindo "+login.getNome());
					int escolha = -1;
					while(escolha != 0) {
						escolha = menuEmail();
						switch(escolha) {
						case 1:
							imprimirEmails();
							break;
						case 2:
							enviarEmail();
							break;
						case 3:
							responderEmail();
							break;
						case 4:
							excluirEmail();
							break;
						case 0:
							System.out.println("");
							login = null;
							System.out.println("Logout realizado com sucesso!");
							System.out.println("");
							break;
						default:
							System.out.println("");
							System.out.println("Opção invalida, digite novamente!");
							System.out.println("");
						}
					}
				}
				break;
			case 3:
				imprimirUsuario();
				break;
			case 0:
				System.out.println("");
				System.out.println("Menu finalizado!");
				System.out.println("");
				break;
			default:
				System.out.println("");
				System.out.println("Opção invalida, digite novamente!");
				System.out.println("");
			}
		}
	}
	public static int menuCadastro() {
		System.out.println("|=====================|");
		System.out.println("|     Menu Inicial    |");
		System.out.println("|=====================|");
		System.out.println("|1 - Cadastro Usuario |");
		System.out.println("|2 - Login Usuario    |");
		System.out.println("|3 - Imprimir Usuario |");
		System.out.println("|0 - Fechar Programa  |");
		System.out.println("|=====================|");
		System.out.println("");
		int opcao;
		opcao = Integer.parseInt(scan.nextLine());
		return opcao;
	}
	public static int menuEmail() {
		System.out.println("|=====================|");
		System.out.println("|    Caixa de Email   |");
		System.out.println("|=====================|");
		System.out.println("| 1 - Exibir Emails   |");
		System.out.println("| 2 - Enviar Email    |");
		System.out.println("| 3 - Responder Email |");
		System.out.println("| 4 - Excluir Email   |");
		System.out.println("| 0 - Fazer Logout    |");
		System.out.println("|=====================|");
		System.out.println("");
		int opcao;
		opcao = Integer.parseInt(scan.nextLine());
		return opcao;
	}
	public static Usuario criaUsuario() {
		Usuario usuario = new Usuario();
		System.out.print("Nome:");
		usuario.setNome(scan.nextLine());
		System.out.print("Senha:");
		usuario.setSenha(scan.nextLine());
		System.out.println("");
		return usuario;
	}
	public static void adicionarUsuario() {
		Usuario usuario = new Usuario();
		usuario = criaUsuario();
		try {
			sistema.adicionaUsuario(usuario);
			} catch (InsertException e) {
				System.err.println(e.getMessage());
			} catch (SelectException e) {
				System.err.println(e.getMessage());
			} catch (UsuarioJaCadastradoException e) {
			System.err.println(e.getMessage());
			
		}
	}
	@SuppressWarnings("finally")
	public static Usuario logarUsuario(Usuario usuario) {
		Usuario login = null;
		try {
			login = sistema.logarUsuario(usuario);
		}
		catch(UsuarioInvalidoException e){
			System.err.println(e.getMessage());
		}
		finally{
			return login;
		}	
	}
	public static void imprimirEmails() {
		List<Email>emails = new LinkedList<Email>();
		try {
			emails = sistema.imprimirEmailLogin(login);
			System.out.println("");
			System.out.println("Caixa de Entrada:");
			System.out.println("");
			for(Email e : emails) {
				System.out.println(e+"\n");
			}
		} catch (CaixaDeMensagensVaziaException e) {
			System.err.println(e.getMessage());
		} catch (SelectException e) {
			System.err.println(e.getMessage());
			
		}
	}
	public static void enviarEmail() {
		Email email = new Email();
		Usuario usuario = new Usuario();
		email.setRemetente(login);
		System.out.println("");
		System.out.print("Destinatario:");
		usuario.setNome(scan.nextLine());
		System.out.println("");
		try {
			email.setDestinatario(sistema.usuarioSelecionado(usuario));
		} catch (SelectException e) {
			System.err.println(e.getMessage());
		}
		String data = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
		String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		email.setData(data);
		email.setHora(hora);
		System.out.println("Mensagem:");
		String corpo = scan.nextLine();
		email.setCorpo(corpo);
		try {
			sistema.enviarEmail(email);
			System.out.println("");
			System.out.println("E-mail enviado com sucesso!");
			System.out.println("");
		} catch (DestinatarioInexistenteException e) {
			System.err.println(e.getMessage());
		} catch (DestinatarioInvalidoException e) {
			System.err.println(e.getMessage());
		} catch(SelectException e) {
			System.err.println(e.getMessage());			
		} catch(InsertException e) {
			System.err.println(e.getMessage());
		}
		
	}		
		
	public static void responderEmail() {
		List<Email> emails = new LinkedList<Email>();
		try {
			emails = sistema.imprimirEmailLogin(login);
			System.out.println("");
			System.out.println("Digite o id do e-mail que deseja responder:");
			imprimirEmailID();
			System.out.print("ID: ");
			int escolha;
			escolha = Integer.parseInt(scan.nextLine());
			Email novo = sistema.responderEmail(login, escolha);
			String data = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
			String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
			novo.setData(data);
			novo.setHora(hora);
			System.out.println("Mensagem:");
			String corpo = scan.nextLine();
			novo.setCorpo(corpo);
			sistema.enviarEmail(novo);
			System.out.println("");
			System.out.println("E-mail enviado com sucesso!");
			System.out.println("");
			
		} catch (CaixaDeMensagensVaziaException e) {
			System.err.println(e.getMessage());
		} catch (EmailInexistenteException e) {
			System.err.println(e.getMessage());
		} catch (DestinatarioInvalidoException e) {
			System.err.println(e.getMessage());			
		} catch(DestinatarioInexistenteException e) {
			System.err.println(e.getMessage());
		} catch (SelectException e) {
			System.err.println(e.getMessage());	
		} catch (InsertException e) {
			System.err.println(e.getMessage());	
		}
	}
	public static void excluirEmail() {
		List<Email> emails = new LinkedList<Email>();
		try {
			emails = sistema.imprimirEmailLogin(login);
			System.out.println("");
			System.out.println("Digite o ID do e-mail que deseja excluir:");
			imprimirEmailID();
			System.out.print("ID: ");
			int escolha;
			escolha = Integer.parseInt(scan.nextLine());
			sistema.excluirEmail(login,escolha);
			System.out.println("");
			System.out.println("Email removido com sucesso!");
			System.out.println("");
		} catch (CaixaDeMensagensVaziaException e) {
			System.err.println(e.getMessage());
		} catch (EmailInexistenteException e) {
			System.err.println(e.getMessage());
		} catch (SelectException e) {
			System.err.println(e.getMessage());
		} catch (DeleteException e) {
			System.err.println(e.getMessage());
		}
		
	
		
	}
	public static void imprimirUsuario() {
		List<Usuario> lista;
		try {
			lista = sistema.retornaUsuarios();
			for(Usuario u : lista) {
				System.out.println(u);
			}
		} catch (SelectException e) {
			System.err.println(e.getMessage());
		}
		
	}
	public static void imprimirEmailID() {
		List<Email> emails = new LinkedList<Email>();
		try {
			emails = sistema.imprimirEmailLogin(login);
			System.out.println("");
			for(int i = 0; i <emails.size();i++) {
				Email e = emails.get(i);
				System.out.println("ID: "+e.getId()+" Remetente: "+e.getRemetente()+" Data: "+e.getData()+" Hora: "+e.getHora());
			}
		} catch (CaixaDeMensagensVaziaException e) {
			System.err.println(e.getMessage());
		} catch (SelectException e) {
			System.err.println(e.getMessage());
		}
		
	}
}
