package com.sistemaclinica.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.sistemaclinica.model.Grupo;
import com.sistemaclinica.util.jpa.Transacional;
import com.sistemaclinica.util.jsf.NegocioException;

public class GrupoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private EntityManager manager;
	
	public Grupo porId(Long id) {
		return manager.find(Grupo.class, id);
	}
	
	@Transacional
	public Grupo salvar(Grupo grupo) {
		return manager.merge(grupo);
	}
	
	@Transacional
	public void remover(Grupo grupo) {
		try {
			
			//TODO verificar se existe consultas com este grupo
			
			grupo = porId(grupo.getId());
			manager.remove(grupo);
			manager.flush();
		} catch (PersistenceException e) {
			try {
				throw new NegocioException("Este Grupo não pode ser removido");
			} catch (NegocioException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
public List<Grupo> todos(){
		
		return manager.createQuery("from Grupo",Grupo.class).getResultList();
	}
	
	
}
