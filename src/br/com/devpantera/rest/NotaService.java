package br.com.devpantera.rest;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String add(NotaMO nota) {

		String msg = "";

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

				notaDAO.insert(nota);
				msg = "Nota adicionado com sucesso!";

			} catch (SQLException e) {
				e.printStackTrace();
				msg = "Ocorreu um erro ao executar o processo no banco de dados! Erro: "
						+ e.getMessage();
			}
		}

		return msg;

	}

}
