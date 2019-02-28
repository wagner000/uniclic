package com.sistemaclinica.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import com.sistemaclinica.model.AgendaMedico;
import com.sistemaclinica.model.Especialidade;
import com.sistemaclinica.model.Medico;
import com.sistemaclinica.repository.EspecialidadeDAO;
import com.sistemaclinica.service.CadastroMedicoService;
import com.sistemaclinica.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroMedicoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Medico medico;
	private Especialidade especSelected;
	private List<Especialidade> especialidades;
	@Inject
	private EspecialidadeDAO especialidadeRepositoy;
	
	@Inject
	private CadastroMedicoService medicoService;
	
	private ScheduleModel scheduleMedico;
	
	public CadastroMedicoBean () {//construtor
		medico = new Medico();
	}
	
	public void init() {
		if(FacesUtil.isNotPostback()) {
			especialidades = especialidadeRepositoy.todas();
			scheduleMedico = new DefaultScheduleModel();
			carregarSchedule();
		}
	}
	
	
	public void carregarSchedule() {
		scheduleMedico = new DefaultScheduleModel();
		
		//para cada atendimento criamos um schedule event
		for(AgendaMedico am : medico.getAgenda()) {
			DefaultScheduleEvent event = new DefaultScheduleEvent();
			event.setStartDate(am.getData());
			event.setEndDate(am.getData());
			event.setTitle(am.getMedico().getNome());
			event.setData(am.getId());
			scheduleMedico.addEvent(event);
		}
	}
	
	
	public void dateSelect(SelectEvent evento) {
		
		for(AgendaMedico ag : medico.getAgenda()) {
			if(ag.getData().compareTo( (Date)evento.getObject() ) == 0){
				medico.getAgenda().remove(ag);
				medicoService.removerAgenda(ag);
				carregarSchedule();
				return;
			}
		}
		
		AgendaMedico agenda = new AgendaMedico();
		agenda.setMedico(medico);
		agenda.setData((Date)evento.getObject());
		medico.getAgenda().add(agenda);
		this.medico = medicoService.salvar(medico);
		carregarSchedule();
	}
	
	
	public void salvar() {
		try {
			this.medico = medicoService.salvar(this.medico);
			limpar();
			
			FacesUtil.addInfoMessage("Medico salvo com sucesso!");
		} catch (Exception ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void retirarEspecialidade() {
		
			System.out.println("*** "+especSelected.getDescricao());
			medico.getEspecialidades().remove(especSelected);
		
	}
	
	
	//limpa todos os campos
	public void limpar() {
		medico = new Medico();
	}
	
	public boolean isEditando(){
		return this.medico.getId() != null;
	}
	
	
	
	//============================== GETTERS AND SETTERS ===============================================
	public Medico getMedico() {
		return medico;
	}
	public List<Especialidade> getEspecialidades(){
		return especialidades;
	}

	public Especialidade getEspecSelected() {
		return especSelected;
	}

	public void setEspecSelected(Especialidade especSelected) {
		this.especSelected = especSelected;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public ScheduleModel getScheduleMedico() {
		return scheduleMedico;
	}

	public void setScheduleMedico(ScheduleModel scheduleMedico) {
		this.scheduleMedico = scheduleMedico;
	}
	
	
	
	
}
