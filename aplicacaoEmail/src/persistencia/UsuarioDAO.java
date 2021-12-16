package persistencia;

import java.sql.*;

import dados.Usuario;
import exceptions.*;
import java.util.*;

public class UsuarioDAO {
	private static UsuarioDAO instance = null;
	
	private PreparedStatement selectAll;
	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement select;
	public static UsuarioDAO getInstance() throws ClassNotFoundException, SQLException, SelectException{
		if(instance == null) {
			instance = new UsuarioDAO();
		}
		return instance;
	}
	private UsuarioDAO() throws ClassNotFoundException, SQLException, SelectException{
		Connection conexao = Conexao.getConexao();
		insert = conexao.prepareStatement("insert into usuario values(?,?)");
		delete = conexao.prepareStatement("delete from usuario where telefone = ?");
		selectAll = conexao.prepareStatement("select * from usuario");
		select = conexao.prepareStatement("select * from usuario where nome = ?");
	}
	
	public void insert(Usuario usuario) throws InsertException, SelectException{
		try {
			insert.setString(1, usuario.getNome());
			insert.setString(2,usuario.getSenha());
			insert.executeUpdate();
		}catch(SQLException e){
			throw new InsertException("Erro ao inserir novo Usuario");
			
		}
	}
	public void delete(Usuario usuario) throws DeleteException{
		try {
			delete.setString(1, usuario.getNome());
			delete.executeUpdate();
		}catch(SQLException e) {
			throw new DeleteException("Erro ao deletar contato");
		}
	}
	public List<Usuario> selectAll() throws SelectException {
		List<Usuario> lista = new LinkedList<Usuario>();
		try {
			ResultSet rs = selectAll.executeQuery();
			while(rs.next()) {
				String nome = rs.getString(1);
				String senha = rs.getString(2);
				Usuario usuario = new Usuario();
				usuario.setNome(nome);
				usuario.setSenha(senha);
				lista.add(usuario);
			}
		}catch(SQLException e) {
			throw new SelectException("Erro ao buscar usuarios");
		}
		return lista;
		
	}
	public Usuario select(String usuario) throws SelectException{
		try {
			select.setString(1, usuario);
			ResultSet rs = select.executeQuery();
			if(rs.next()) {
				String nome = rs.getString(1);
				String senha = rs.getString(2);
				return new Usuario(nome, senha);
			}
			
		} catch(SQLException e) {
			throw new SelectException("Erro ao buscar usuario");
		}
		return null;
	}
	

}