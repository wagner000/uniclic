package com.sistemaclinica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sistemaclinica.model.Paciente;
import com.sistemaclinica.repository.PacienteDAO;
import com.sistemaclinica.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaPacienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PacienteDAO pacienteDAO;
	private List<Paciente> pacientes;
	private String filtroNome;
	private String filtroCpf;
	private Paciente pacienteSelecionado;
	
	public PesquisaPacienteBean() {
		filtroNome = new String();
		filtroCpf = new String();
	}

	public void filtrar() {
		pacientes = pacienteDAO.filtrar(filtroNome, filtroCpf);
	}
	
	public void excluir() {
		pacienteDAO.remover(pacienteSelecionado);
		pacientes.remove(pacienteSelecionado);
		FacesUtil.addInfoMessage("Paciente "+pacienteSelecionado.getId()+" removido com sucesso.");
	}
	
	//===================================================================================
	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}

	public String getFiltroCpf() {
		return filtroCpf;
	}

	public void setFiltroCpf(String filtroCpf) {
		this.filtroCpf = filtroCpf;
	}

	public Paciente getPacienteSelecionado() {
		return pacienteSelecionado;
	}

	public void setPacienteSelecionado(Paciente pacienteSelecionado) {
		this.pacienteSelecionado = pacienteSelecionado;
	}
	
}
