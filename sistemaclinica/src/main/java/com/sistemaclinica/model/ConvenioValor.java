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
@Table(name="convenio_tbpreco")
public class ConvenioValor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Convenio convenio;
	private Procedimento procedimento;
	private BigDecimal valor = BigDecimal.ZERO;
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tbpreco")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_convenio")
	public Convenio getConvenio() {
		return convenio;
	}
	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_procedimento")
	public Procedimento getProcedimento() {
		return procedimento;
	}
	public void setProcedimento(Procedimento procedimento) {
		this.procedimento = procedimento;
	}
	
	
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	
	
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
		ConvenioValor other = (ConvenioValor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
