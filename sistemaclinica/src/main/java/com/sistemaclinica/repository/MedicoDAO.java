package com.sistemaclinica.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.sistemaclinica.model.AgendaMedico;
import com.sistemaclinica.model.Medico;
import com.sistemaclinica.service.NegocioException;
import com.sistemaclinica.util.jpa.Transacional;

public class MedicoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private EntityManager manager;
	
	public Medico porId(Long id) {
		return manager.find(Medico.class, id);
	}
	
	public List<Medico> todos(){
		
		return manager.createQuery("from Medico",Medico.class).getResultList();
	}
	
	public List<Medico> medicosHoje(){
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date fromDate = calendar.getTime();

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date toDate = calendar.getTime();

		TypedQuery<Medico> query = manager.createQuery("SELECT ag.medico FROM AgendaMedico ag"
				+ " WHERE ag.data >= :fromDate"
				+ " AND ag.data <= :toDate", Medico.class);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
	
		return query.getResultList();
		
	}
	
public List<Medico> medicosNoDia(Date dia){
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dia);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date fromDate = calendar.getTime();

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date toDate = calendar.getTime();

		TypedQuery<Medico> query = manager.createQuery("SELECT ag.medico FROM AgendaMedico ag"
				+ " WHERE ag.data >= :fromDate"
				+ " AND ag.data <= :toDate", Medico.class);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
	
		return query.getResultList();
		
	}
	
	public List<Medico> porEspecialidade(Long especialidadeId) {
		return manager.createQuery("select medico from Medico medico JOIN FETCH especialidades"
				+ " JOIN especialidades espec"
				+ " WHERE espec.id = :especialidadeId", Medico.class)
				.setParameter("especialidadeId", especialidadeId).getResultList();
	}
	
	public List<Medico> porEspecialidade2(Long especialidadeId) {
		return manager.createQuery("select medico from Medico medico"
				+ " JOIN especialidades espec"
				+ " WHERE espec.id = :especialidadeId", Medico.class)
				.setParameter("especialidadeId", especialidadeId).getResultList();
	}
	
	
	public List<Medico> filtrar(String filtroNome, String filtroConselho){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Medico> criteriaQuery = builder.createQuery(Medico.class);
		List<Predicate> predicates = new ArrayList<>();
		Root<Medico> medicoRoot = criteriaQuery.from(Medico.class);
		
		if(StringUtils.isNotBlank(filtroConselho)) {
			predicates.add(builder.equal(medicoRoot.get("numeroConselho"), filtroConselho));
		}
		if(StringUtils.isNotBlank(filtroNome)) {
			predicates.add(builder.like(builder.lower(medicoRoot.get("nome")),
					"%"+ filtroNome.toLowerCase()+"%"));
		}
		
		criteriaQuery.select(medicoRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(medicoRoot.get("nome")));
		
		TypedQuery<Medico> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	
	@Transacional
	public Medico salvar(Medico medico) {
		
		return manager.merge(medico);
	}
	
	@Transacional
	public void removerAgenda(AgendaMedico ag) {
		try {
			ag = manager.find(AgendaMedico.class, ag.getId());
			manager.remove(ag);
			manager.flush();
		} catch (PersistenceException e) {
			//e.printStackTrace();
			try {
				throw new NegocioException("Este agendamento não pode ser removido");
			} catch (NegocioException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@Transacional
	public void remover(Medico medico) {
		try {
			medico = porId(medico.getId());
			manager.remove(medico);
			manager.flush();
		} catch (PersistenceException e) {
			//e.printStackTrace();
			try {
				throw new NegocioException("Este médico não pode ser removido");
			} catch (NegocioException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
