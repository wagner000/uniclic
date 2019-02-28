package com.sistemaclinica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sistemaclinica.model.Convenio;
import com.sistemaclinica.repository.ConvenioDAO;
import com.sistemaclinica.util.jsf.FacesUtil;

/**
 * @author Wagner
 *
 */
@Named
@ViewScoped
public class PesquisaConvenioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ConvenioDAO convenioDAO;
	
	private Convenio convenioSelecionado;
	private List<Convenio> convenios;
	private String filtroNome;
	
	public PesquisaConvenioBean() {
		filtroNome = new String();
		convenioSelecionado = new Convenio();
	}

	public void init() {
		if(FacesUtil.isNotPostback()) {
			convenios = convenioDAO.todos();
		}
	}
	
	public void limpar() {
		convenioSelecionado = new Convenio();
		convenios = convenioDAO.todos();
	}
	
	public void novo() {
		convenioSelecionado = new Convenio();
	}
	
	public void salvar() {
		try {
			this.convenioSelecionado = convenioDAO.salvar(this.convenioSelecionado);
			limpar();
			
			FacesUtil.addInfoMessage("Convenio salvo com sucesso!");
		} catch (Exception ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void excluir() {
		convenioDAO.remover(convenioSelecionado);
		convenios.remove(convenioSelecionado);
		FacesUtil.addInfoMessage("Convenio "+convenioSelecionado.getDescricao()+" removido com sucesso.");
	}

	
	
	
	//===================================================================================
	
	public Convenio getConvenioSelecionado() {
		return convenioSelecionado;
	}

	public void setConvenioSelecionado(Convenio convenioSelecionado) {
		this.convenioSelecionado = convenioSelecionado;
	}

	public List<Convenio> getConvenios() {
		return convenios;
	}

	public void setConvenios(List<Convenio> convenios) {
		this.convenios = convenios;
	}

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}
	
}
