package br.com.devpantera.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.devpantera.model.NotaMO;

public class NotaDAO {

	private Connection connection;

	public NotaMO get(int id) throws SQLException {

		StringBuilder query = new StringBuilder(
				"SELECT id, titulo, descricao FROM TB_NOTA WHERE 'a' = 'a' ");

		if (id > 0) {
			query.append("AND id = '" + id + "'");
		}

		Statement stmt = null;
		ResultSet rs = null;

		connection = ConnectionFactory.getConnection();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(query.toString());

		if (rs.next()) {

			NotaMO objeto = new NotaMO();
			objeto.setId(rs.getInt("id"));
			objeto.setTitulo(rs.getString("titulo"));
			objeto.setDescricao(rs.getString("descricao"));

			return objeto;

		}
		rs.close();
		stmt.close();

		return null;
	}

	public int insert(NotaMO objeto) throws SQLException {

		String query = "INSERT INTO TB_NOTA (titulo, descricao) VALUES (?,?)";

		PreparedStatement ps = null;

		connection = ConnectionFactory.getConnection();
		ps = connection
				.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		ps.setString(1, objeto.getTitulo());
		ps.setString(2, objeto.getDescricao());
		ps.executeUpdate();

		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			return rs.getInt(1);
		}
		ps.close();

		return 0;

	}

	public void update(NotaMO objeto) throws SQLException {

		String query = "UPDATE TB_NOTA set titulo = ?, descricao = ? WHERE id = ?";

		PreparedStatement ps = null;

		connection = ConnectionFactory.getConnection();
		ps = connection.prepareStatement(query);

		ps.setString(1, objeto.getTitulo());
		ps.setString(2, objeto.getDescricao());
		ps.setInt(3, objeto.getId());

		ps.executeUpdate();
		ps.close();

	}

	public void delete(int id) {

	}

	public List<NotaMO> list() throws SQLException {

		List<NotaMO> notas = new ArrayList<NotaMO>();

		connection = ConnectionFactory.getConnection();

		String query = "SELECT * FROM TB_NOTA";

		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			NotaMO nota = new NotaMO();
			nota.setId(rs.getInt("id_nota"));
			nota.setTitulo(rs.getString("titulo"));
			nota.setDescricao(rs.getString("descricao"));
			notas.add(nota);
		}

		return notas;

	}

}
