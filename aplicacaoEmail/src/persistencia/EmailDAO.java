package persistencia;
import java.sql.*;
import dados.*;
import exceptions.*;
import java.util.*;


public class EmailDAO {
	private static EmailDAO instance = null;
	
	private PreparedStatement selectNewId;
	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement select;
	private PreparedStatement selectAll;
	
	public static EmailDAO getInstance() throws ClassNotFoundException, SQLException, SelectException{
		if(instance == null) {
			instance = new EmailDAO();
		}
		return instance;
	}
	private EmailDAO() throws ClassNotFoundException, SQLException, SelectException{
		Connection conexao = Conexao.getConexao();
		selectNewId = conexao.prepareStatement("select nextval('id_email')");
		insert = conexao.prepareStatement("insert into email values(?,?,?,?,?,?)");
		delete = conexao.prepareStatement("delete from email where id = ?");
		select = conexao.prepareStatement("select * from email where nome_destinatario = ?");
	
		selectAll = conexao.prepareStatement("select * from email");
	}
	public int selectNewId() throws SelectException{
		try {
			ResultSet rs = selectNewId.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		}catch (SQLException e) {
			throw new SelectException("Erro ao gerar novo id da tabela email");
		}
		return 0;
	}
	public void insert(Email email) throws InsertException, SelectException{
		try {
			insert.setInt(1, email.getId());
			insert.setString(2, email.getCorpo());
			insert.setString(3, email.getRemetente().getNome());
			insert.setString(4, email.getDestinatario().getNome());
			insert.setString(5, email.getData());
			insert.setString(6, email.getHora());
			insert.executeUpdate();
			
		} catch(SQLException e) {
			throw new InsertException("Erro ao inserir novo email");
			
		}
	}
	public void delete(Email email) throws DeleteException{
		try {
			delete.setInt(1, email.getId());
			delete.executeUpdate();
		} catch(SQLException e) {
			throw new DeleteException("Erro ao deletar email");
		}
	}
	public List<Email> select(Usuario destinatario) throws SelectException{
		List<Email> lista = new LinkedList<Email>();
		try {
			select.setString(1, destinatario.getNome());
			ResultSet rs = select.executeQuery();
			while(rs.next()) {
				int id = rs.getInt(1);
				String corpo = rs.getString(2);
				String nomeRemetente = rs.getString(3);
				String nomeDestinatario = rs.getString(4);
				String data = rs.getString(5);
				String hora = rs.getString(6);
				Usuario remetente = new Usuario();
				remetente.setNome(nomeRemetente);
				destinatario.setNome(nomeDestinatario);
				Email novo = new Email(remetente, destinatario, corpo, data, hora, id);
				lista.add(novo);
								
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar emails");
		}
		return lista;
	}
	public List<Email> selectAll() throws SelectException{
		List<Email> lista = new LinkedList<Email>();
		try{
			ResultSet rs = selectAll.executeQuery();
			while(rs.next()) {
				int id = rs.getInt(1);
				String corpo = rs.getString(2);
				String nomeRemetente = rs.getString(3);
				String nomeDestinatario = rs.getString(4);
				String data = rs.getString(5);
				String hora = rs.getString(6);
				Usuario remetente = new Usuario();
				remetente.setNome(nomeRemetente);
				Usuario destinatario = new Usuario();
				destinatario.setNome(nomeDestinatario);
				Email novo = new Email(remetente, destinatario, corpo, data, hora, id);
				lista.add(novo);
				
			}
			
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar emails");
		}
		return lista;
		
	}
}
