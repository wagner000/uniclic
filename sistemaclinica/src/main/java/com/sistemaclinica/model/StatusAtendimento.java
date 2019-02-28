package com.sistemaclinica.model;

public enum StatusAtendimento {
	
	//agendado / confirmado / aguardando atendimento / não compareceu / atendido
	
	AGENDADO("Agendado"),
	CONFIRMADO("Confirmado"),
	AGUARDANDO("Aguardando Atendimento"),
	NAO_COMPARECEU("Não Compareceu"),
	ATENDIDO("Atendido");
	
	private String descricao;
	
	StatusAtendimento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}