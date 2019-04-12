package com.sistemaclinica.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.sistemaclinica.model.FormaPagamento;
import com.sistemaclinica.util.jpa.Transacional;
import com.sistemaclinica.util.jsf.NegocioException;

public class FormaPagamentoDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public FormaPagamento porId(Long id) {
		return manager.find(FormaPagamento.class, id);
	}
	
	@Transacional
	public FormaPagamento salvar(FormaPagamento formaPag) {
		return manager.merge(formaPag);
	}
	
	@Transacional
	public void remover(FormaPagamento formaPag) {
		try {
			
			formaPag = porId(formaPag.getId());
			manager.remove(formaPag);
			manager.flush();
		} catch (PersistenceException e) {
			try {
				throw new NegocioException("Esta Forma de Pagamento não pode ser removida");
			} catch (NegocioException e1) {
				e1.printStackTrace();
			}
		}
	}
	
public List<FormaPagamento> todos(){
		
		return manager.createQuery("from FormaPagamento",FormaPagamento.class).getResultList();
	}
	
	
}
