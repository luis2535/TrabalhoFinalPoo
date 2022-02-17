package apresentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dados.Usuario;
import exceptions.InsertException;
import exceptions.SelectException;
import exceptions.UsuarioInvalidoException;
import exceptions.UsuarioJaCadastradoException;
import negocio.CaixaDeMensagens;

public class Tela extends JFrame {
	private CaixaDeMensagens sistema = CaixaDeMensagens.getInstance();
	private JPanel painel = new JPanel();
	JLabel infoUsuario = new JLabel("Usuario: ");
	JLabel infoSenha = new JLabel("Senha: ");
	private JTextField caixaUsuario = new JTextField();
	private JTextField caixaSenha = new JTextField();
	private JButton botaoLogin = new JButton("Login");
	private JButton botaoCadastro = new JButton("Cadastrar");
	
	public Tela() {
		setTitle("Aplicacao Email");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,570,300);
		setContentPane(painel);
		painel.setLayout(null);
		
		login();
	}
	public void login() {
		JPanel painelLogin = new JPanel();
		painelLogin.setLayout(null);
		painelLogin.setBounds(100,40,325,180);
		setResizable(false);
		painelLogin.setBorder(javax.swing.BorderFactory.createTitledBorder("Login"));
		add(painelLogin);
		infoUsuario.setBounds(20, 60, 200, 20);
		painelLogin.add(infoUsuario);
		infoSenha.setBounds(20, 85, 200, 20);
		painelLogin.add(infoSenha);
		caixaUsuario.setBounds(80,60,200,20);
		painelLogin.add(caixaUsuario);
		caixaSenha.setBounds(80,85,200,20);
		painelLogin.add(caixaSenha);
		botaoLogin.setBounds(40, 125, 117, 20);
		painelLogin.add(botaoLogin);
		botaoCadastro.setBounds(175, 125, 117, 20);
		painelLogin.add(botaoCadastro);
		
		botaoLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Usuario usuario = new Usuario();
				Usuario login = null;
				usuario.setNome(caixaUsuario.getText());
				usuario.setSenha(caixaSenha.getText());
				caixaUsuario.setText("");
				caixaSenha.setText("");
				try {
					login = sistema.logarUsuario(usuario);
					TelaPrincipal telaPrincipal = new TelaPrincipal();
					JOptionPane.showMessageDialog(null,"Login realizado com sucesso!","Login realizado com sucesso!",JOptionPane.PLAIN_MESSAGE);
					telaPrincipal.setVisible(true);
					dispose();
				} catch (UsuarioInvalidoException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Usuario ou senha incorreto!",JOptionPane.ERROR_MESSAGE);
				} catch (SelectException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"SelectException",JOptionPane.ERROR_MESSAGE);
				}
			}
			
					
			
		});
		botaoCadastro.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Usuario usuario = new Usuario();
				usuario.setNome(caixaUsuario.getText());
				usuario.setSenha(caixaSenha.getText());
				caixaUsuario.setText("");
				caixaSenha.setText("");
				try {
					sistema.adicionaUsuario(usuario);
					JOptionPane.showMessageDialog(null,"Cadastro bem sucedido!","Usuario registrado com sucesso!",JOptionPane.PLAIN_MESSAGE);
				} catch (UsuarioJaCadastradoException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Usuario ja cadastrado!",JOptionPane.ERROR_MESSAGE);

				} catch (InsertException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"InsertException",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (SelectException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"SelectException",JOptionPane.ERROR_MESSAGE);
				}
			}
			
				
			
		});
		
	}

}
