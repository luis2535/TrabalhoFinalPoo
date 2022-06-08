package apresentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dados.Email;
import dados.Usuario;
import exceptions.DestinatarioInexistenteException;
import exceptions.DestinatarioInvalidoException;
import exceptions.InsertException;
import exceptions.SelectException;
import negocio.CaixaDeMensagens;

public class TelaResponderEmail extends JFrame {
	private CaixaDeMensagens sistema = CaixaDeMensagens.getInstance();
	private JPanel painel = new JPanel();
	private Usuario logado = sistema.getUsuarioLogado();
	private JPanel painelEntrada = new JPanel();
	JLabel infoDestinatario = new JLabel("Destinatário: "+sistema.getUsuarioDestinatario().getNome());
	JLabel infoEnviar = new JLabel("Mensagem: ");
	private JButton botaoEnviar = new JButton("Enviar");
	private JButton botaoFechar = new JButton("Fechar");
	private JTextArea caixaMensagem = new JTextArea();
	
	
	public TelaResponderEmail() {
		setTitle("Aplicacao Email");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100,100,450,350);
		setContentPane(painel);
		painel.setLayout(null);
		painelEntrada.setBounds(60,20,300,275);
		setResizable(false);
		painelEntrada.setBorder(javax.swing.BorderFactory.createTitledBorder("Enviar Email"));
		painelEntrada.setLayout(null);
		painel.add(painelEntrada);
		infoDestinatario.setBounds(20,20,200,20);
		painelEntrada.add(infoDestinatario);
		infoEnviar.setBounds(20,50,200,20);
		painelEntrada.add(infoEnviar);
		caixaMensagem.setBounds(20,80,260,150);
		painelEntrada.add(caixaMensagem);
		botaoEnviar.setBounds(42,240,108,20);
		painelEntrada.add(botaoEnviar);
		botaoFechar.setBounds(155,240,108,20);
		painelEntrada.add(botaoFechar);
		
		botaoEnviar.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				Email email = new Email();
				Usuario usuario = new Usuario();
				email.setRemetente(logado);
				email.setCorpo(caixaMensagem.getText());
				email.setDestinatario(sistema.getUsuarioDestinatario());
				caixaMensagem.setText("");
				String data = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
				String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
				email.setData(data);
				email.setHora(hora);
				try {
					sistema.enviarEmail(email);
					JOptionPane.showMessageDialog(null,"Email respondido com sucesso","Email respondido com sucesso",JOptionPane.PLAIN_MESSAGE);
					dispose();
				} catch (DestinatarioInexistenteException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Destinatario não existe!",JOptionPane.ERROR_MESSAGE);
				} catch (DestinatarioInvalidoException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Não pode enviar email para si mesmo",JOptionPane.ERROR_MESSAGE);
				} catch (SelectException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"SelectException",JOptionPane.ERROR_MESSAGE);
				} catch (InsertException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"InsertException",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		botaoFechar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();				
			}
			
		});
		
	}
}