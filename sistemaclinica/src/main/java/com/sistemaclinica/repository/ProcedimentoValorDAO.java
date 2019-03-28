package com.sistemaclinica.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.sistemaclinica.model.Medico;
import com.sistemaclinica.model.ProcedimentoValor;
import com.sistemaclinica.util.jpa.Transacional;
import com.sistemaclinica.util.jsf.NegocioException;

public class ProcedimentoValorDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private EntityManager manager;
	
	public ProcedimentoValor porId(Long id) {
		return manager.find(ProcedimentoValor.class, id);
	}
	
	@Transacional
	public ProcedimentoValor salvar(ProcedimentoValor procedimentoValor) {
		return manager.merge(procedimentoValor);
	}
	
	@Transacional
	public void remover(ProcedimentoValor procedimentoValor) {
		try {
			
			//TODO verificar se existe consultas com este ProcedimentoValor
			
			procedimentoValor = porId(procedimentoValor.getId());
			manager.remove(procedimentoValor);
			manager.flush();
		} catch (PersistenceException e) {
			try {
				throw new NegocioException("Este Procedimento não pode ser removido");
			} catch (NegocioException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public List<ProcedimentoValor> todos(){
		
		return manager.createQuery("from ProcedimentoValor",ProcedimentoValor.class).getResultList();
	}
	
	
	
	public List<ProcedimentoValor> byMedico(Medico medico){
		return manager.createQuery("from ProcedimentoValor p where p.medico.id = :id",ProcedimentoValor.class)
				.setParameter("id", medico.getId()).getResultList();
	}
	
}
