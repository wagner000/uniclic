package com.sistemaclinica.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import com.sistemaclinica.model.Atendimento;
import com.sistemaclinica.model.Convenio;
import com.sistemaclinica.model.FormaPagamento;
import com.sistemaclinica.model.Medico;
import com.sistemaclinica.model.Paciente;
import com.sistemaclinica.model.Pagamento;
import com.sistemaclinica.model.Procedimento;
import com.sistemaclinica.model.StatusAtendimento;
import com.sistemaclinica.repository.AtendimentoDAO;
import com.sistemaclinica.repository.ConvenioDAO;
import com.sistemaclinica.repository.FormaPagamentoDAO;
import com.sistemaclinica.repository.MedicoDAO;
import com.sistemaclinica.repository.PacienteDAO;
import com.sistemaclinica.repository.ProcedimentoDAO;
import com.sistemaclinica.util.jsf.FacesUtil;
import com.sistemaclinica.util.jsf.NegocioException;

@Named
@ViewScoped
public class CadastroAtendimentoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AtendimentoDAO atendimentoDAO;
	@Inject
	private ProcedimentoDAO procedimentoDAO;
	@Inject
	private ConvenioDAO convenioDAO;
	@Inject
	private PacienteDAO pacienteDAO;
	@Inject
	private MedicoDAO medicoDao;
	@Inject
	private FormaPagamentoDAO formaPagDAO;
	private List<FormaPagamento> formasPagamento;
	
	private Atendimento atendimento;
	private List<Atendimento> atendimentos;
	private List<Atendimento> atendimentosDia;
	private List<Procedimento> procedimentos;
	private List<Convenio> convenios;
	private List<Paciente> pacientes;
	private List<Medico> medicos;
	
	private Pagamento novoPagamento;
	
	private ScheduleModel schedule;
	
	private boolean diaAtivo = false;

	public void init() {
		if(FacesUtil.isNotPostback()) {
			
			atendimento = new Atendimento();
			atendimentos = atendimentoDAO.todos();
			procedimentos = procedimentoDAO.todos();
			formasPagamento = formaPagDAO.todos();
			convenios = convenioDAO.todos();
			pacientes = pacienteDAO.todos();
			//medicos = medicoDao.medicosHoje();
			medicos = new ArrayList<Medico>();
			
			carregarListaHoje();
			carregarSchedule();
		}
	}
	
	public void carregarListaHoje() {
		try {
			atendimentosDia = atendimentoDAO.atendimentosDoDia();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void carregarSchedule() {
		atendimentos = atendimentoDAO.todos();
		schedule = new DefaultScheduleModel();
		
		//para cada atendimento criamos um schedule event
		for(Atendimento at : atendimentos) {
			DefaultScheduleEvent event = new DefaultScheduleEvent();
			event.setStartDate(at.getData());
			event.setEndDate(at.getData());
			event.setTitle(at.getPaciente().getNome());
			event.setData(at.getId());
			schedule.addEvent(event);
		}
	}
	
	public void dateSelect(SelectEvent evento) {
		atendimento = new Atendimento();
		atendimento.setData((Date)evento.getObject());
		//medicos = medicoDao.medicosNoDia(atendimento.getData());
		medicos = medicoDao.todos();
		
		Date hoje = new Date();
		hoje.setHours(0);
		hoje.setMinutes(0);
		hoje.setSeconds(0);

		//NÃO PERMITE AGENDAMENTOS COM DATA RETROATIVA
		if(atendimento.getData().getYear() == hoje.getYear() && atendimento.getData().getMonth() == hoje.getMonth()) {
			if(atendimento.getData().getDate() >= hoje.getDate()) {
				RequestContext.getCurrentInstance().execute("PF('atendDialog').show();");
			}
		}
	}
	
	public void eventSelect(SelectEvent selectEvent) {
		
		//limparForm();
		DefaultScheduleEvent event = (DefaultScheduleEvent) selectEvent.getObject();
		
		if(medicos == null || medicos.size() == 0 ) {
			//medicos = medicoDao.medicosNoDia(event.getStartDate());
			medicos = medicoDao.todos();
		}
		
		for(Atendimento at : atendimentos) {
			if(at.getId() == (Long) event.getData()) {
				atendimento = atendimentoDAO.porId(at.getId());

				if(atendimento.isEditavel()) {
					atendimento.setDesconto(BigDecimal.ZERO);
					atualizaPagamentos(null, 0);
				}
				return;
			}
		}
	}
	
	//-------
	//TODO fazer teste se o medico atende no novo dia
	//-----------
	public void eventMove(ScheduleEntryMoveEvent scheduleEntryMoveEvent){
		Atendimento a = atendimentoDAO.porId((Long) scheduleEntryMoveEvent.getScheduleEvent().getData());
		
		if(!a.isEditavel()) {
			throw new NegocioException("Atendimento "+a.getStatus().getDescricao()+"!");
		}else {
			java.util.Calendar newCal = new GregorianCalendar();
			newCal.setTime(a.getData());
			newCal.add(Calendar.DATE, (scheduleEntryMoveEvent.getDayDelta()));
			newCal.add(Calendar.MINUTE, (scheduleEntryMoveEvent.getMinuteDelta()));
			a.setData(newCal.getTime());
			
			atendimentoDAO.salvar(a);
			carregarSchedule();
			carregarListaHoje();
			FacesUtil.addInfoMessage("Agendamento atualizado.");
		}
	 }
	
	public void salvar() {
		try {
			atendimento.removerItemVazio();
			this.atendimento = atendimentoDAO.salvar(atendimento);
			carregarSchedule();
			carregarListaHoje();
			FacesUtil.addInfoMessage("Agendamento salvo.");
			RequestContext.getCurrentInstance().execute("PF('atendDialog').hide();");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excluir() {
		try {
			atendimento.removerItemVazio();
			atendimentoDAO.remover(atendimento);
			this.atendimento = new Atendimento();
			carregarSchedule();
			carregarListaHoje();
			FacesUtil.addInfoMessage("Agendamento Excluído.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NegocioException("Erro ao excluir!");
		}
	}
	
	public void cancelarAtendimento() {
		this.atendimento = atendimentoDAO.cancelar(this.atendimento);
		
	}
	
	public void finalizarAtendimento() {
		this.atendimento = atendimentoDAO.finalizar(this.atendimento);
		FacesUtil.addInfoMessage("Agendamento Finalizado com sucesso.");
	}
	
	public void limparForm() {
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		List<String> clientIds = new ArrayList<String>();
		clientIds.add("frmAtendimento:status");
		clientIds.add("frmAtendimento:procedimento");
		clientIds.add("frmAtendimento:pacienteComplete");
		clientIds.add("frmAtendimento:medico");
		clientIds.add("frmAtendimento:convenio");
		clientIds.add("frmAtendimento:pagamento");
		clientIds.add("frmAtendimento:dataAtendimento");
		
		UIViewRoot view = ctx.getViewRoot();
		view.resetValues(ctx, clientIds); // reseta os componentes da lista
	}
	
	public void carregarAtendimento(int linha) {
		this.atendimento = atendimentosDia.get(linha);
	}
	
	public List<Paciente> carregaPacientes(){
		if(pacientes != null) {
			return this.pacientes;
		}
		return pacienteDAO.todos();
	}
	
	public List<Medico> carregaMedicos(){
		if(medicos!=null) {
			return medicos;
		}
		return medicoDao.todos();
	}
	
	public void atualizaPagamentos(Pagamento p, int linha) {
		if(p!=null) {
			if(p.isNotOk() && linha != 0) {
				atendimento.getPagamentos().remove(linha);
			}
		}
		
		if(atendimento.getPagamentos().isEmpty()) {
			atendimento.adcionarItemVazio();
			return;
		}
		
		if(atendimento.getPagamentos().get(0).isOk() &&
				atendimento.getValorPago().compareTo(atendimento.getValorTotal()) < 0) {
			atendimento.adcionarItemVazio();
		}
	}
	
	//=================================================================================================
	//==================GETTERS AND SETTERS ==============
	//===================================================================================================
	
	public StatusAtendimento[] getStatus() {
		return StatusAtendimento.values();
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public List<Procedimento> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(List<Procedimento> procedimentos) {
		this.procedimentos = procedimentos;
	}

	public List<Convenio> getConvenios() {
		return convenios;
	}

	public void setConvenios(List<Convenio> convenios) {
		this.convenios = convenios;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public List<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}


	public ScheduleModel getSchedule() {
		return schedule;
	}


	public void setSchedule(ScheduleModel schedule) {
		this.schedule = schedule;
	}


	public List<Atendimento> getAtendimentos() {
		return atendimentos;
	}


	public void setAtendimentos(List<Atendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}

	public List<Atendimento> getAtendimentosDia() {
		return atendimentosDia;
	}

	public void setAtendimentosDia(List<Atendimento> atendimentosDia) {
		this.atendimentosDia = atendimentosDia;
	}

	public boolean isDiaAtivo() {
		return diaAtivo;
	}

	public void setDiaAtivo(boolean diaAtivo) {
		this.diaAtivo = diaAtivo;
	}

	public Pagamento getNovoPagamento() {
		return novoPagamento;
	}

	public void setNovoPagamento(Pagamento novoPagamento) {
		this.novoPagamento = novoPagamento;
	}

	public List<FormaPagamento> getFormasPagamento() {
		return formasPagamento;
	}

	public void setFormasPagamento(List<FormaPagamento> formasPagamento) {
		this.formasPagamento = formasPagamento;
	}

}
