package com.sistemaclinica.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="medico_has_procedimentos")
public class ProcedimentoValor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Procedimento procedimento;
	
	private Medico medico;
	
	private BigDecimal valor;

	
	
	public Procedimento getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(Procedimento procedimento) {
		this.procedimento = procedimento;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
