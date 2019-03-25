package com.sistemaclinica.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.sistemaclinica.model.Paciente;
import com.sistemaclinica.util.jpa.Transacional;
import com.sistemaclinica.util.jsf.NegocioException;

public class PacienteDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private EntityManager manager;
	
	public Paciente porId(Long id) {
		return manager.find(Paciente.class, id);
	}
	
	public List<Paciente> todos(){
		
		return manager.createQuery("from Paciente",Paciente.class).getResultList();
	}
	
	@Transacional
	public Paciente salvar(Paciente paciente) {
		//TODO implementar: Teste unicidade do numero de CPF
		return manager.merge(paciente);
	}
	
	@Transacional
	public void remover(Paciente paciente) {
		try {
			paciente = porId(paciente.getId());
			manager.remove(paciente);
			manager.flush();
		} catch (PersistenceException e) {
			try {
				throw new NegocioException("Este paciente não pode ser removido");
			} catch (NegocioException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public List<Paciente> filtrar(String filtroNome, String filtroCpf){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Paciente> criteriaQuery = builder.createQuery(Paciente.class);
		List<Predicate> predicates = new ArrayList<>();
		Root<Paciente> pacienteRoot = criteriaQuery.from(Paciente.class);
		
		if(StringUtils.isNotBlank(filtroCpf)) {
			predicates.add(builder.equal(pacienteRoot.get("cpf"), filtroCpf));
		}
		if(StringUtils.isNotBlank(filtroNome)) {
			predicates.add(builder.like(builder.lower(pacienteRoot.get("nome")),
					"%"+ filtroNome.toLowerCase()+"%"));
		}
		
		criteriaQuery.select(pacienteRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(pacienteRoot.get("nome")));
		
		TypedQuery<Paciente> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	
}
