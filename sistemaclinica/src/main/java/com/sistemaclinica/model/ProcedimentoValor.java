package com.sistemaclinica.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="procedimento_medico_valor")
public class ProcedimentoValor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Procedimento procedimento;
	
	private Medico medico;
	
	private BigDecimal valor = BigDecimal.ZERO;

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_procedimento_valor")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_procedimento")
	public Procedimento getProcedimento() {
		return procedimento;
	}

	
	public void setProcedimento(Procedimento procedimento) {
		this.procedimento = procedimento;
	}

	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_medico")
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
