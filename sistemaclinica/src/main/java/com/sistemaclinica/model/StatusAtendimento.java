package com.sistemaclinica.model;

public enum StatusAtendimento {
	
	AGENDADO("Agendado"),
	CONFIRMADO("Confirmado"),
	//AGUARDANDO("Aguardando Atendimento"), //quando o cliente confirma a consulta, automaticamente ele está aguardando atend.
	CANCELADO("Cancelado"),
	FINALIZADO("Finalizado");
	
	private String descricao;
	
	StatusAtendimento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}