package br.com.devpantera.rest;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.devpantera.dao.NotaDAO;
import br.com.devpantera.model.NotaMO;

@Path("/nota")
public class NotaService {

	private NotaDAO notaDAO;
	private static final String CHARSET_UTF8 = ";charset=utf-8";

	@PostConstruct
	private void init() {
		notaDAO = new NotaDAO();
	}

	@GET
	@Path("/get/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public NotaMO getById(@PathParam("id") int idNota) {

		NotaMO nota = null;

		try {
			nota = notaDAO.get(idNota);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nota;
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

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String add(NotaMO nota) {

		String msg = "";
		int idGerado = 0;

		if (nota.getTitulo().isEmpty()) {
			msg = "Título inválido";
		}

		if (nota.getDescricao().isEmpty()) {
			msg = "Descrição inválida";
		}

		if (msg.isEmpty()) {
			try {

				if (notaDAO.isExistTitulo(nota.getTitulo())) {
					msg = "Já existe nota com este título";
					return msg;
				}

				idGerado = notaDAO.add(nota);
				msg = String.valueOf(idGerado);

			} catch (SQLException e) {
				e.printStackTrace();
				msg = "Ocorreu um erro ao executar o processo no banco de dados! Erro: "
						+ e.getMessage();
			}
		}

		return msg;

	}

	@PUT
	@Path("/edit/{id}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String edit(NotaMO nota, @PathParam("id") int idNota) {

		String msg = "";

		if (nota.getTitulo().isEmpty()) {
			msg = "Título inválido";
		}

		if (nota.getDescricao().isEmpty()) {
			msg = "Descrição inválida";
		}

		if (msg.isEmpty()) {
			try {

				notaDAO.update(nota);
				msg = "Nota editada com sucesso!";

			} catch (SQLException e) {
				e.printStackTrace();
				msg = "Ocorreu um erro ao executar o processo no banco de dados! Erro: "
						+ e.getMessage();
			}
		}

		return msg;

	}

	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String delete(NotaMO nota, @PathParam("id") int idNota) {

		String msg = "";

		try {

			notaDAO.delete(idNota);
			msg = "Nota deletada com sucesso!";

		} catch (SQLException e) {
			e.printStackTrace();
			msg = "Ocorreu um erro ao executar o processo no banco de dados! Erro: "
					+ e.getMessage();
		}

		return msg;

	}

}
