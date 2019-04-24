package com.sistemaclinica.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.sistemaclinica.model.Atendimento;
import com.sistemaclinica.model.StatusAtendimento;
import com.sistemaclinica.util.jpa.Transacional;
import com.sistemaclinica.util.jsf.NegocioException;

public class AtendimentoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	@Inject
	private ConvenioDAO conDao;

	public Atendimento porId(Long id) {
		Atendimento atendimento = manager.find(Atendimento.class, id);
		atendimento.getConvenio().setValores(conDao.valores(atendimento.getConvenio()));
		return atendimento;
	}

	@Transacional
	public Atendimento salvar(Atendimento atendimento) {
		return manager.merge(atendimento);
	}

	@Transacional
	public void remover(Atendimento atendimento) {
		try {

			// TODO verificar se existe consultas com este atendimento

			atendimento = porId(atendimento.getId());
			manager.remove(atendimento);
			manager.flush();
		} catch (PersistenceException e) {
			try {
				throw new NegocioException("Este Atendimento não pode ser removido");
			} catch (NegocioException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@Transacional
	public Atendimento cancelar(Atendimento atendimento){
			if(atendimento.isCancelado()) {
				throw new NegocioException("Este atendimento já está Cancelado!");
			}
			if(atendimento.isFinalizado()) {
				throw new NegocioException("Este atendimento já foi Finalizado!");
			}
			atendimento = this.salvar(atendimento);
			atendimento.setStatus(StatusAtendimento.CANCELADO);
			atendimento = this.salvar(atendimento);
			return atendimento;
		
	}

	public List<Atendimento> todos() {

		return manager.createQuery("from Atendimento", Atendimento.class).getResultList();
	}
	
	public List<Atendimento> atendimentosDoDia() throws Exception{
		Date d1 = new Date();
		d1.setHours(0);
		d1.setMinutes(0);
		d1.setSeconds(0);
		Date d2 = new Date();
		d2.setHours(23);
		d2.setMinutes(59);
		d2.setSeconds(59);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String inicio = format.format(d1);
		String fim = format.format(d2);
		d1 = format.parse(inicio);
		d2 = format.parse(fim);
		
		Query query = manager.createQuery("select a from Atendimento a where a.data >= :d1 and a.data <= :d2", Atendimento.class);
		query.setParameter("d1", d1);
		query.setParameter("d2", d2);
		
		return query.getResultList();
		
	}

}
