package com.sistemaclinica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sistemaclinica.model.FormaPagamento;
import com.sistemaclinica.repository.FormaPagamentoDAO;
import com.sistemaclinica.util.jsf.FacesUtil;

/**
 * @author Wagner
 *
 */
@Named
@ViewScoped
public class FormaPagamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FormaPagamentoDAO formaPagamentoDAO;
	
	private FormaPagamento formaPagamentoSelecionado;
	private List<FormaPagamento> formaPagamentos;
	private String filtroNome;
	
	public FormaPagamentoBean() {
		filtroNome = new String();
		formaPagamentoSelecionado = new FormaPagamento();
	}

	public void init() {
		if(FacesUtil.isNotPostback()) {
			formaPagamentos = formaPagamentoDAO.todos();
		}
	}
	
	public void limpar() {
		formaPagamentoSelecionado = new FormaPagamento();
		formaPagamentos = formaPagamentoDAO.todos();
	}
	
	public void novo() {
		formaPagamentoSelecionado = new FormaPagamento();
	}
	
	public void salvar() {
		try {
			this.formaPagamentoSelecionado = formaPagamentoDAO.salvar(this.formaPagamentoSelecionado);
			limpar();
			
			FacesUtil.addInfoMessage("Forma de Pagamento salvo com sucesso!");
		} catch (Exception ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void excluir() {
		formaPagamentoDAO.remover(formaPagamentoSelecionado);
		formaPagamentos.remove(formaPagamentoSelecionado);
		FacesUtil.addInfoMessage("Forma de Pagamento "+formaPagamentoSelecionado.getDescricao()+" removido com sucesso.");
	}

	
	
	
	//===================================================================================
	
	
	public FormaPagamento getFormaPagamentoSelecionado() {
		return formaPagamentoSelecionado;
	}

	public void setFormaPagamentoSelecionado(FormaPagamento formaPagamentoSelecionado) {
		this.formaPagamentoSelecionado = formaPagamentoSelecionado;
	}

	public List<FormaPagamento> getFormaPagamentos() {
		return formaPagamentos;
	}

	public void setFormaPagamentos(List<FormaPagamento> formaPagamentos) {
		this.formaPagamentos = formaPagamentos;
	}

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}
	
	
}
