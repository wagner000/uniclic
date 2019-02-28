package com.sistemaclinica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sistemaclinica.model.Procedimento;
import com.sistemaclinica.repository.ProcedimentoDAO;
import com.sistemaclinica.util.jsf.FacesUtil;

/**
 * @author Wagner
 *
 */
@Named
@ViewScoped
public class PesquisaProcedimentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProcedimentoDAO procedimentoDAO;
	
	private Procedimento procedimentoSelecionado;
	private List<Procedimento> procedimentos;
	private String filtroNome;
	
	public PesquisaProcedimentoBean() {
		filtroNome = new String();
		procedimentoSelecionado = new Procedimento();
		System.out.println("**** CONTRUTOR *****");
	}

	public void init() {
		if(FacesUtil.isNotPostback()) {
			System.out.println("**** init *****");
			procedimentos = procedimentoDAO.todos();
		}
	}
	
	public void limpar() {
		procedimentoSelecionado = new Procedimento();
		procedimentos = procedimentoDAO.todos();
	}
	
	public void novo() {
		procedimentoSelecionado = new Procedimento();
	}
	
	public void salvar() {
		try {
			this.procedimentoSelecionado = procedimentoDAO.salvar(this.procedimentoSelecionado);
			limpar();
			
			FacesUtil.addInfoMessage("Procedimento salvo com sucesso!");
		} catch (Exception ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void excluir() {
		procedimentoDAO.remover(procedimentoSelecionado);
		procedimentos.remove(procedimentoSelecionado);
		FacesUtil.addInfoMessage("Procedimento "+procedimentoSelecionado.getDescricao()+" removido com sucesso.");
	}

	
	
	
	//===================================================================================
	
	
	public Procedimento getProcedimentoSelecionado() {
		return procedimentoSelecionado;
	}

	public void setProcedimentoSelecionado(Procedimento procedimentoSelecionado) {
		this.procedimentoSelecionado = procedimentoSelecionado;
	}

	public List<Procedimento> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(List<Procedimento> procedimentos) {
		this.procedimentos = procedimentos;
	}

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}
	
	
}
