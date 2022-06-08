package apresentacao;
import negocio.CaixaDeMensagens;
import dados.Email;
import dados.Usuario;
import exceptions.DeleteException;
import exceptions.EmailInexistenteException;
import exceptions.SelectException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class TabelaEmails extends AbstractTableModel {
	private String[] colunas = {"ID","Remetente","Data","Hora"};
	private CaixaDeMensagens email = CaixaDeMensagens.getInstance();
	private Usuario logado = email.getUsuarioLogado();
	

	
	public String getColumnName(int column) {
		return colunas[column];
	}
	@Override
	public int getRowCount() {
		int tamanho = 0;
		try {
			tamanho = email.imprimirEmailLogin(logado).size();
		} catch (SelectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tamanho;
	}

	@Override
	public int getColumnCount() {
		return  colunas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			List<Email> lista = email.imprimirEmailLogin(logado);
			if(lista.size()!=0) {
				switch(columnIndex) {
				case 0:
					return lista.get(rowIndex).getId();
				case 1:
					return lista.get(rowIndex).getRemetente().getNome();
				case 2:
					return lista.get(rowIndex).getData();
				case 3:
					return lista.get(rowIndex).getHora();
	
				}
				
			}
		} catch (SelectException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"SelectException",JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	public Class<?> getColumnClass(int col){
		if(col == 0) {
			return JButton.class;
		}else {
			return String.class;
		}
	}
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	public void atualizar() {
		fireTableStructureChanged();
	}
	

}
