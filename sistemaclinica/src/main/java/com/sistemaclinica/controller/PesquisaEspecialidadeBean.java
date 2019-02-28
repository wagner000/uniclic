package com.sistemaclinica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sistemaclinica.model.Especialidade;
import com.sistemaclinica.repository.EspecialidadeDAO;
import com.sistemaclinica.util.jsf.FacesUtil;

/**
 * @author Wagner
 *
 */
@Named
@ViewScoped
public class PesquisaEspecialidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EspecialidadeDAO especialidadeDAO;
	
	private Especialidade especialidadeSelecionado;
	private List<Especialidade> especialidades;
	private String filtroNome;
	
	public PesquisaEspecialidadeBean() {
		filtroNome = new String();
		especialidadeSelecionado = new Especialidade();
	}

	public void init() {
		if(FacesUtil.isNotPostback()) {
			especialidades = especialidadeDAO.todas();
		}
	}
	
	public void limpar() {
		especialidadeSelecionado = new Especialidade();
		especialidades = especialidadeDAO.todas();
	}
	
	public void novo() {
		especialidadeSelecionado = new Especialidade();
	}
	
	public void salvar() {
		try {
			this.especialidadeSelecionado = especialidadeDAO.salvar(this.especialidadeSelecionado);
			limpar();
			
			FacesUtil.addInfoMessage("Especialidade salvo com sucesso!");
		} catch (Exception ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void excluir() {
		especialidadeDAO.remover(especialidadeSelecionado);
		especialidades.remove(especialidadeSelecionado);
		FacesUtil.addInfoMessage("Especialidade "+especialidadeSelecionado.getDescricao()+" removido com sucesso.");
	}

	
	
	
	//===================================================================================
	
	public Especialidade getEspecialidadeSelecionado() {
		return especialidadeSelecionado;
	}

	public void setEspecialidadeSelecionado(Especialidade especialidadeSelecionado) {
		this.especialidadeSelecionado = especialidadeSelecionado;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}
	
}
