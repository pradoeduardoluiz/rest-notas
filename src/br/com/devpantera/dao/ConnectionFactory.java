package br.com.devpantera.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Luiz Eduardo do Prado
 * @version 1.0
 * 
 *          Factory de conexoes com o banco de dados
 *
 */

public class ConnectionFactory {

	static final String DATABASE = "notas_db";
	static final String SERVER = "localhost:3306";
	static final String USER = "root";
	static final String PASSWORD = "root";
	static final String DRIVE = "com.mysql.jdbc.Driver";

	static final String URL = "jdbc:mysql://" + SERVER + "/" + DATABASE;

	static {
		try {
			Class.forName(DRIVE).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {

		return DriverManager.getConnection(URL, USER, PASSWORD);

	}

}
