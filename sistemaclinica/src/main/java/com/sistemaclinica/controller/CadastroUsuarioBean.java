package com.sistemaclinica.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sistemaclinica.model.Grupo;
import com.sistemaclinica.model.Usuario;
import com.sistemaclinica.repository.GrupoDAO;
import com.sistemaclinica.repository.UsuarioDAO;
import com.sistemaclinica.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	@Inject
	private UsuarioDAO usuarioService;

	private Grupo grupo;
	@Inject
	private GrupoDAO grupoDAO;
	private List<Grupo> grupos = new ArrayList<Grupo>();

	public CadastroUsuarioBean() {
		usuario = new Usuario();
	}

	public void init() {
		if (FacesUtil.isNotPostback()) {
			grupos = grupoDAO.todos();

			if (usuario.getId() != null) {
				this.grupo = usuario.getGrupos().get(0);
			}
		}
	}

	public void salvar() {
		try {
			this.usuario.getGrupos().add(0, this.grupo);
			this.usuario = usuarioService.salvar(this.usuario);
			limpar();
			FacesUtil.addInfoMessage("usuario salvo com sucesso!");
		} catch (Exception ne) {
			ne.printStackTrace();
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	// limpa todos os campos
	public void limpar() {
		usuario = new Usuario();
		grupo = new Grupo();
	}

	public boolean isEditando() {
		return this.usuario.getId() != null;
	}

	// ============================== GETTERS AND SETTERS
	// ===============================================

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

}
