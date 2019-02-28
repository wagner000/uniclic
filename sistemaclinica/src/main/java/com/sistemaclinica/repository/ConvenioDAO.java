package com.sistemaclinica.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.sistemaclinica.model.Convenio;
import com.sistemaclinica.service.NegocioException;
import com.sistemaclinica.util.jpa.Transacional;

public class ConvenioDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private EntityManager manager;
	
	public Convenio porId(Long id) {
		return manager.find(Convenio.class, id);
	}
	
	@Transacional
	public Convenio salvar(Convenio convenio) {
		return manager.merge(convenio);
	}
	
	@Transacional
	public void remover(Convenio convenio) {
		try {
			
			//TODO verificar se existe consultas com este convenio
			
			convenio = porId(convenio.getId());
			manager.remove(convenio);
			manager.flush();
		} catch (PersistenceException e) {
			try {
				throw new NegocioException("Este Convenio não pode ser removido");
			} catch (NegocioException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public List<Convenio> todos(){
		
		return manager.createQuery("from Convenio",Convenio.class).getResultList();
	}
	
	
}