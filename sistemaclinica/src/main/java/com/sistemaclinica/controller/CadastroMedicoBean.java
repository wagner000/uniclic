package com.sistemaclinica.controller;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.sistemaclinica.model.Procedimento;
import com.sistemaclinica.model.ProcedimentoValor;
import com.sistemaclinica.repository.EspecialidadeDAO;
import com.sistemaclinica.repository.ProcedimentoDAO;
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
	
	@Inject
	private ProcedimentoDAO procedimentoDAO;
	private List<Procedimento> procedimentos;
	private List<Procedimento> proSelecionados;
	
	private List<ProcedimentoValor> proValors;
	private ProcedimentoValor procSelected;
	
	private ScheduleModel scheduleMedico;
	
	public CadastroMedicoBean () {//construtor
		medico = new Medico();
	}
	
	public void init() {
		if(FacesUtil.isNotPostback()) {
			especialidades = especialidadeRepositoy.todas();
			procedimentos = procedimentoDAO.todos();
			proSelecionados = new ArrayList<Procedimento>();
			
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
			//limpar();
			
			FacesUtil.addInfoMessage("Medico salvo com sucesso!");
		} catch (Exception ne) {
			ne.printStackTrace();
			FacesUtil.redirect("/Erro.xhtml");
		}
	}
	
	
	  public void retirarEspecialidade() {
	  
	  medico.getEspecialidades().remove(especSelected);
	  }
	 
	
	public void adicionarProcedimentos() {
		for(Procedimento p : proSelecionados) {
			ProcedimentoValor proValor = new ProcedimentoValor();
			proValor.setMedico(medico);
			proValor.setProcedimento(p);
			medico.getProcedimentos().add(proValor);
		}
	}
	
	public void retirarProcedimento() {
		
		medico.getProcedimentos().remove(procSelected);
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

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public ScheduleModel getScheduleMedico() {
		return scheduleMedico;
	}

	public void setScheduleMedico(ScheduleModel scheduleMedico) {
		this.scheduleMedico = scheduleMedico;
	}

	public List<Procedimento> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(List<Procedimento> procedimentos) {
		this.procedimentos = procedimentos;
	}

	public List<ProcedimentoValor> getProValors() {
		return proValors;
	}

	public void setProValors(List<ProcedimentoValor> proValors) {
		this.proValors = proValors;
	}

	public List<Procedimento> getProSelecionados() {
		return proSelecionados;
	}

	public void setProSelecionados(List<Procedimento> proSelecionados) {
		this.proSelecionados = proSelecionados;
	}

	public ProcedimentoValor getProcSelected() {
		return procSelected;
	}

	public void setProcSelected(ProcedimentoValor procSelected) {
		this.procSelected = procSelected;
	}

	public Especialidade getEspecSelected() {
		return especSelected;
	}

	public void setEspecSelected(Especialidade especSelected) {
		this.especSelected = especSelected;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}
	
	
	
	
}
