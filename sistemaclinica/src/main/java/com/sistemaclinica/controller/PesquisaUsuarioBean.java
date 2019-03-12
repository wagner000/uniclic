package com.sistemaclinica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sistemaclinica.model.Usuario;
import com.sistemaclinica.repository.UsuarioDAO;
import com.sistemaclinica.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	private List<Usuario> usuarios;
	private String filtroNome;
	private String filtroCpf;
	private Usuario usuarioSelecionado;
	
	public PesquisaUsuarioBean() {
		filtroNome = new String();
		filtroCpf = new String();
	}

	public void filtrar() {
		//usuarios = usuarioDAO.filtrar(filtroNome, filtroCpf);
		usuarios = usuarioDAO.todos();
	}
	
	public void excluir() {
		usuarioDAO.remover(usuarioSelecionado);
		usuarios.remove(usuarioSelecionado);
		FacesUtil.addInfoMessage("Usuario "+usuarioSelecionado.getId()+" removido com sucesso.");
	}
	
	//===================================================================================
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}

	public String getFiltroCpf() {
		return filtroCpf;
	}

	public void setFiltroCpf(String filtroCpf) {
		this.filtroCpf = filtroCpf;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
	
}
