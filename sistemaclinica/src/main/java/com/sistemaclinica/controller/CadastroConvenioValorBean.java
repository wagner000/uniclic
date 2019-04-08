package com.sistemaclinica.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sistemaclinica.model.Convenio;
import com.sistemaclinica.model.ConvenioValor;
import com.sistemaclinica.model.Procedimento;
import com.sistemaclinica.repository.ConvenioDAO;
import com.sistemaclinica.repository.ProcedimentoDAO;
import com.sistemaclinica.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroConvenioValorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ConvenioValor conValorSelected;
	private Convenio convenio;
	private List<Procedimento> procedimentos;
	private List<Procedimento> proSelecionados;

	@Inject
	private ProcedimentoDAO procedimentoDAO;
	@Inject
	private ConvenioDAO convenioDAO;

	public void init() {
		if (convenio == null || convenio.getId() == null) {
			FacesUtil.redirect("/Erro.xhtml");

		}
		if (FacesUtil.isNotPostback()) {
			procedimentos = procedimentoDAO.todos();
			System.out.println("** procedimentos.size: " + procedimentos.size());
			proSelecionados = new ArrayList<Procedimento>();

			for (ConvenioValor c : convenio.getValores()) {
				procedimentos.remove(c.getProcedimento());
			}
		}
	}

	public void salvar() {
		try {
			this.convenio = convenioDAO.salvar(this.convenio);

			FacesUtil.addInfoMessage("Convênio salvo com sucesso!");
		} catch (Exception ne) {
			ne.printStackTrace();
			FacesUtil.redirect("/Erro.xhtml");
		}
	}

	public void adicionarProcedimentos() {

		for(Procedimento p : proSelecionados) {
			
			ConvenioValor valor = new ConvenioValor();
			valor.setConvenio(this.convenio);
			valor.setProcedimento(p);
			convenio.getValores().add(valor);
			
			procedimentos.remove(p);
		}
		proSelecionados = new ArrayList<Procedimento>();
		this.convenio = convenioDAO.salvar(this.convenio);
	}

	public void retirarProcedimento() {
		procedimentos.add(conValorSelected.getProcedimento());
		convenio.getValores().remove(conValorSelected);
		this.convenio = convenioDAO.salvar(this.convenio);
	}

	public ConvenioValor getConValorSelected() {
		return conValorSelected;
	}

	public void setConValorSelected(ConvenioValor conValor) {
		this.conValorSelected = conValor;
	}

	public Convenio getConvenio() {
		return convenio;
	}

	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	public List<Procedimento> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(List<Procedimento> procedimentos) {
		this.procedimentos = procedimentos;
	}

	public List<Procedimento> getProSelecionados() {
		return proSelecionados;
	}

	public void setProSelecionados(List<Procedimento> proSelecionados) {
		this.proSelecionados = proSelecionados;
	}

}
