package br.com.devpantera.rest;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.devpantera.dao.NotaDAO;
import br.com.devpantera.model.NotaMO;

@Path("/nota")
public class NotaService {

	private NotaDAO notaDAO;

	@PostConstruct
	private void init() {
		notaDAO = new NotaDAO();
	}

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<NotaMO> list() {

		List<NotaMO> lista = null;

		try {
			lista = notaDAO.list();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

}
