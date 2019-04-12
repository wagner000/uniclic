package com.sistemaclinica.model;

public enum FormaPagamentoOLD {

	DINHEIRO("Dinheiro"), 
	CARTAO_CREDITO("Cartão de crédito"), 
	CARTAO_DEBITO("Cartão de débito");
	
	private String descricao;
	
	FormaPagamentoOLD(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}