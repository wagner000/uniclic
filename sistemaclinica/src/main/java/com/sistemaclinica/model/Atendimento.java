package com.sistemaclinica.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="atendimento")
public class Atendimento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private StatusAtendimento status = StatusAtendimento.AGENDADO;
	private Paciente paciente;
	private Medico medico;
	private Convenio convenio;
	private Date data;
	private Procedimento procedimento;
	private FormaPagamento formaPagamento;
	private String observacoes;
	
	
	public Atendimento() {
		this.medico = new Medico();
		this.data = new Date();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_atendimento")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name= "status", nullable = false, length = 50)
	public StatusAtendimento getStatus() {
		return status;
	}
	public void setStatus(StatusAtendimento statusAtend) {
		this.status = statusAtend;
	}
	
	
	@NotNull
	@ManyToOne
	@JoinColumn(name= "id_paciente", nullable = false)
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name= "id_medico", nullable = false)
	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name= "id_convenio", nullable = false)
	public Convenio getConvenio() {
		return convenio;
	}
	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}
	
	@NotNull
	@Column(name = "data_atendimento", nullable = false)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name= "id_procedimento", nullable = false)
	public Procedimento getProcedimento() {
		return procedimento;
	}
	public void setProcedimento(Procedimento procedimento) {
		this.procedimento = procedimento;
	}
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name= "forma_pagamento", nullable = false, length = 20)
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	
	@Transient
	public boolean isAgendado() {
		return StatusAtendimento.AGENDADO.equals(this.getStatus());
	}
	
	@Transient
	public boolean isConfirmado() {
		return StatusAtendimento.CONFIRMADO.equals(this.getStatus());
	}
	
	@Transient
	public boolean isCancelado() {
		return StatusAtendimento.CANCELADO.equals(this.getStatus());
	}
	
	@Transient
	public boolean isFinalizado() {
		return StatusAtendimento.CONFIRMADO.equals(this.getStatus());
	}
	
	//====================================
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Atendimento other = (Atendimento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Column(length = 200)
	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	
	
}
