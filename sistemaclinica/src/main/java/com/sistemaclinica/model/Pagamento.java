package com.sistemaclinica.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="pagamento")
public class Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private BigDecimal valor = BigDecimal.ZERO;
	private Atendimento atendimento;
	private LocalDateTime data;
	
	
}
