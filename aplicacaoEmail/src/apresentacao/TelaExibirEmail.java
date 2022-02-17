package apresentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import dados.Usuario;
import exceptions.DeleteException;
import exceptions.EmailInexistenteException;
import exceptions.SelectException;
import negocio.CaixaDeMensagens;

public class TelaExibirEmail extends JFrame {
	private CaixaDeMensagens sistema = CaixaDeMensagens.getInstance();
	private JPanel painel = new JPanel();
	private JPanel painelEntrada = new JPanel();
	private Usuario logado = sistema.getUsuarioLogado();
	JLabel infoCaixaTexto = new JLabel("Digite o ID do email:");
	private JTextField caixaID = new JTextField();
	private JButton botaoResponder = new JButton("Responder");
	private JButton botaoExcluir = new JButton("Excluir");
	private JScrollPane painelScrollTabelaEmail = new JScrollPane();
	private JTable tabelaEmail;
	private TabelaEmails emails = new TabelaEmails();
	
	public TelaExibirEmail() {
		setTitle("Aplicacao Email");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 750);
		setContentPane(painel);
		painel.setLayout(null);
		painelEntrada.setBounds(310,375,280,173);
		painelEntrada.setLayout(null);
		painel.add(painelEntrada);
		painel.add(painelEntrada);
		infoCaixaTexto.setBounds(30, 0, 200, 15);
		painelEntrada.add(infoCaixaTexto);
		caixaID.setBounds(30, 20, 241, 20);
		painelEntrada.add(caixaID);
		botaoResponder.setBounds(45, 64, 100, 25);
		painelEntrada.add(botaoResponder);
		botaoExcluir.setBounds(150,64,100,25);
		painelEntrada.add(botaoExcluir);
		painelScrollTabelaEmail.setBounds(150,10,600,300);
		painel.add(painelScrollTabelaEmail);
		tabelaEmail = new JTable(emails);
		painelScrollTabelaEmail.setViewportView(tabelaEmail);
		
		botaoExcluir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					sistema.excluirEmail(logado, Integer.parseInt(caixaID.getText()));
					JOptionPane.showMessageDialog(null,"Email excluido com sucesso","Email excluido com sucesso",JOptionPane.PLAIN_MESSAGE);
					caixaID.setText("");
					emails.atualizar();
					
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Digite algum valor!","Erro ao inserir valor", JOptionPane.ERROR_MESSAGE);
				} catch (EmailInexistenteException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Esse email n�o existe",JOptionPane.ERROR_MESSAGE);
				} catch (DeleteException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"DeleteException",JOptionPane.ERROR_MESSAGE);
				} catch (SelectException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"SelectException",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		botaoResponder.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					sistema.responderEmail(logado, Integer.parseInt(caixaID.getText()));
					caixaID.setText("");
					TelaResponderEmail telaResponderEmail = new TelaResponderEmail();
					telaResponderEmail.setVisible(true);
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Digite algum valor!","Erro ao inserir valor", JOptionPane.ERROR_MESSAGE);
				} catch (EmailInexistenteException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"Esse email n�o existe",JOptionPane.ERROR_MESSAGE);
				} catch (SelectException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage(),"SelectException",JOptionPane.ERROR_MESSAGE);
				}				
			}
			
		});
		
	}

}
