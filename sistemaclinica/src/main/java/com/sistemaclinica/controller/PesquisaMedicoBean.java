package com.sistemaclinica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sistemaclinica.model.Medico;
import com.sistemaclinica.repository.MedicoDAO;
import com.sistemaclinica.util.jsf.FacesUtil;

/**
 * @author Wagner
 *
 */
@Named
@ViewScoped
public class PesquisaMedicoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MedicoDAO medicoDAO;
	
	private Medico medicoSelecionado;
	private List<Medico> medicos;
	private String filtroNome;
	private String filtroConselho;
	
	public PesquisaMedicoBean() {
		filtroNome = new String();
		filtroConselho = new String();
	}

	public void filtrar() {
		medicos = medicoDAO.filtrar(filtroNome, filtroConselho);
	}
	
	public void excluir() {
		medicoDAO.remover(medicoSelecionado);
		medicos.remove(medicoSelecionado);
		FacesUtil.addInfoMessage("Médico "+medicoSelecionado.getNumeroConselho()+" removido com sucesso.");
	}
	
	
	
	//===================================================================================
	public List<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}

	public String getFiltroConselho() {
		return filtroConselho;
	}

	public void setFiltroConselho(String filtroConselho) {
		this.filtroConselho = filtroConselho;
	}

	public Medico getMedicoSelecionado() {
		return medicoSelecionado;
	}

	public void setMedicoSelecionado(Medico medicoSelecionado) {
		this.medicoSelecionado = medicoSelecionado;
	}
	
}
