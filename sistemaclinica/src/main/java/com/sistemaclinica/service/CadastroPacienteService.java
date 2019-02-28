package com.sistemaclinica.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.sistemaclinica.model.Paciente;
import com.sistemaclinica.repository.PacienteDAO;
import com.sistemaclinica.util.jpa.Transacional;

public class CadastroPacienteService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PacienteDAO pacienteDAO;
	
	@Transacional
	public Paciente salvar(Paciente paciente) {
	
		//TODO implementar: Teste unicidade do numero de CPF
		return pacienteDAO.salvar(paciente);
	}
	
	
}
