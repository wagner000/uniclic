package com.sistemaclinica.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.sistemaclinica.model.Procedimento;
import com.sistemaclinica.service.NegocioException;
import com.sistemaclinica.util.jpa.Transacional;

public class ProcedimentoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private EntityManager manager;
	
	public Procedimento porId(Long id) {
		return manager.find(Procedimento.class, id);
	}
	
	@Transacional
	public Procedimento salvar(Procedimento procedimento) {
		return manager.merge(procedimento);
	}
	
	@Transacional
	public void remover(Procedimento procedimento) {
		try {
			
			//TODO verificar se existe consultas com este procedimento
			
			procedimento = porId(procedimento.getId());
			manager.remove(procedimento);
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
	
public List<Procedimento> todos(){
		
		return manager.createQuery("from Procedimento",Procedimento.class).getResultList();
	}
	
	
}
