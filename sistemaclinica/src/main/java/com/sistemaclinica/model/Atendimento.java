package com.sistemaclinica.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	private List<Pagamento> pagamentos = new ArrayList<Pagamento>();
	private String observacoes;
	private BigDecimal desconto = BigDecimal.ZERO;
	
	
	public Atendimento() {
		this.medico = new Medico();
		this.data = new Date();
		this.pagamentos = new ArrayList<Pagamento>();
		this.desconto = BigDecimal.ZERO;
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
	
	
	
	public void adcionarItemVazio() {
			
		if(this.isEditavel()) {
			Pagamento p = new Pagamento();
			p.setAtendimento(this);
			
			this.getPagamentos().add(0, p); //sempre que chamar esse metodo ele adiciona a primeira linha
		}
	}
	
	public void removerItemVazio() {
		Pagamento primeiroItem = this.getPagamentos().get(0);
		
		if(primeiroItem.isNotOk()) {
			this.getPagamentos().remove(0);
		}
	}
	
	
	@Transient
	public BigDecimal getValorTotal() {
		
		if(getValor() != null ) {
			
			if(getDesconto() !=null) {
				BigDecimal total = getValor().subtract(getDesconto());
				return total;
			}else
				return getValor();
			
		}else
			return BigDecimal.ZERO;
	}
	
	@Transient
	public BigDecimal getValorPago() {
		BigDecimal valorPago = BigDecimal.ZERO;
		
		for(Pagamento p : getPagamentos()) {
			valorPago = valorPago.add(p.getValor());
		}return valorPago;
	}
	
	@Transient
	public BigDecimal getValor() {
		
		if(getConvenio() !=null && getProcedimento() !=null) {
			
			for(ConvenioValor valor :this.convenio.getValores()) {
				if(valor.getProcedimento().equals(getProcedimento())) {
					return valor.getValor();
				}	
			}
		}else {
			return BigDecimal.ZERO;
		}
		return BigDecimal.ZERO;
	}
	
	@Transient
	public boolean isAgendado() {
		return ( StatusAtendimento.AGENDADO.equals(this.getStatus()) && this.getId()!=null );
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
		return StatusAtendimento.FINALIZADO.equals(this.getStatus());
	}
	
	@Transient
	public boolean isEditavel() {
		if(isCancelado() || isFinalizado()) {
			return false;
		}else
			return true;
	}
	
	@Transient
	public boolean isCancelavel() {
		
		if(this.isAgendado() || this.isConfirmado()) {
			return true;
		}else{
			return false;
		}
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

	
	@OneToMany(mappedBy = "atendimento", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}
	
	@Column(name="desconto")
	public BigDecimal getDesconto() {
		return this.desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	
	
}
