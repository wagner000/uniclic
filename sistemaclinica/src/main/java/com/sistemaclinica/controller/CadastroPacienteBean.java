package com.sistemaclinica.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sistemaclinica.model.Estado;
import com.sistemaclinica.model.Municipio;
import com.sistemaclinica.model.Paciente;
import com.sistemaclinica.repository.EstadoDAO;
import com.sistemaclinica.repository.MunicipioDAO;
import com.sistemaclinica.service.CadastroPacienteService;
import com.sistemaclinica.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroPacienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Paciente paciente;
	private List<Estado> estados;
	private List<Municipio> cidades;
	@Inject
	private EstadoDAO estadoDAO;
	@Inject
	private MunicipioDAO cidadeDAO;
	@Inject
	private CadastroPacienteService pacienteService;
	
	
	public CadastroPacienteBean () {
		paciente = new Paciente();
	}
	
	public void init() {
		if(FacesUtil.isNotPostback()) {
			estados = estadoDAO.todos();
			
			if(paciente.getEstado() != null) {
				carregarCidades();
			}
		}
	}
	
	
	public void salvar() {
		try {
			this.paciente = pacienteService.salvar(this.paciente);
			limpar();
			FacesUtil.addInfoMessage("paciente salvo com sucesso!");
		} catch (Exception ne) {
			System.out.println(ne.getMessage());
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	//limpa todos os campos
		public void limpar() {
			paciente = new Paciente();
			cidades = null;
		}
		
		public void carregarCidades() {
			cidades = cidadeDAO.porEstado(paciente.getEstado().getUf());
		}
		
		public boolean isEditando() {
			return this.paciente.getId() != null;
		}
		
		//============================== GETTERS AND SETTERS ===============================================
		
		public Paciente getPaciente() {
			return paciente;
		}

		public void setPaciente(Paciente paciente) {
			this.paciente = paciente;
		}

		public List<Estado> getEstados() {
			return estados;
		}

		public void setEstados(List<Estado> estados) {
			this.estados = estados;
		}

		public List<Municipio> getCidades() {
			return cidades;
		}

		public void setCidades(List<Municipio> cidades) {
			this.cidades = cidades;
		}

}
