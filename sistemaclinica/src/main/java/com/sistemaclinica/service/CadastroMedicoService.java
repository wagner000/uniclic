package com.sistemaclinica.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.sistemaclinica.model.AgendaMedico;
import com.sistemaclinica.model.Medico;
import com.sistemaclinica.repository.MedicoDAO;
import com.sistemaclinica.util.jpa.Transacional;

public class CadastroMedicoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MedicoDAO medicoDAO;
	
	@Transacional
	public Medico salvar(Medico medico) {
	
		//TODO implementar: Teste unicidade do numero de conselho "CRM"
		
		return medicoDAO.salvar(medico);
	}
	
	public Medico porId(Long id) {
		return medicoDAO.porId(id);
	}
	
	public void removerAgenda(AgendaMedico ag) {
		medicoDAO.removerAgenda(ag);
	}
	
}
