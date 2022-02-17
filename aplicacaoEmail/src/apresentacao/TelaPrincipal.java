package apresentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dados.Usuario;
import negocio.CaixaDeMensagens;

public class TelaPrincipal extends JFrame {
	private JPanel painel = new JPanel();
	private JPanel painelEntrada = new JPanel();
	private CaixaDeMensagens sistema = CaixaDeMensagens.getInstance();
	private Usuario logado = sistema.getUsuarioLogado();
	JLabel infoUsuario = new JLabel("Bem vindo(a) "+logado.getNome());
	private JButton botaoExibirEmail = new JButton("Exibir Emails");
	private JButton botaoEnviarEmail = new JButton("Enviar Emails");
	private JButton botaoLogout = new JButton("Logout");
	
	
	public TelaPrincipal() {
		setBounds(100,100,450,300);
		setTitle("Aplicacao Email");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setContentPane(painel);
		painel.setLayout(null);
		painelEntrada.setBounds(80,20,300, 200);
		painelEntrada.setLayout(null);
		painelEntrada.setBorder(javax.swing.BorderFactory.createTitledBorder("Menu"));
		painel.add(painelEntrada);
		infoUsuario.setBounds(20,20,200,20);
		painelEntrada.add(infoUsuario);
		botaoExibirEmail.setBounds(50, 60, 200, 20);
		painelEntrada.add(botaoExibirEmail);
		botaoEnviarEmail.setBounds(50, 90, 200, 20);
		painelEntrada.add(botaoEnviarEmail);
		botaoLogout.setBounds(50, 120, 200, 20);
		painelEntrada.add(botaoLogout);
		botaoExibirEmail.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TelaExibirEmail telaExibirEmail = new TelaExibirEmail();
				telaExibirEmail.setVisible(true);
				
			}
			
		});
		botaoEnviarEmail.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TelaEnviarEmail telaEnviarEmail = new TelaEnviarEmail();
				telaEnviarEmail.setVisible(true);
				
			}
			
		});
		botaoLogout.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Tela tela = new Tela();
		        tela.setVisible(true);
		        dispose();
			}
			
		});
		
	}

}
