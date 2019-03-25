package com.sistemaclinica.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.sistemaclinica.model.Especialidade;
import com.sistemaclinica.util.jpa.Transacional;
import com.sistemaclinica.util.jsf.NegocioException;

public class EspecialidadeDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private EntityManager manager;
	
	public Especialidade porId(Long id) {
		return manager.find(Especialidade.class, id);
	}
	
	public List<Especialidade> todas(){
		
		return manager.createQuery("from Especialidade",Especialidade.class).getResultList();
	}
	
	@Transacional
	public Especialidade salvar(Especialidade especialidade) {
		return manager.merge(especialidade);
	}
	
	@Transacional
	public void remover(Especialidade especialidade) {
		try {
			
			//TODO verificar se existe consultas com este especialidade
			
			especialidade = porId(especialidade.getId());
			manager.remove(especialidade);
			manager.flush();
		} catch (PersistenceException e) {
			try {
				throw new NegocioException("Este Especialidade não pode ser removido");
			} catch (NegocioException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
