package com.sistemaclinica.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.sistemaclinica.model.Paciente;
import com.sistemaclinica.repository.PacienteDAO;

@FacesConverter(forClass = Paciente.class)
public class PacienteConverter implements Converter {
	
	@Inject
	private PacienteDAO pacienteDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Paciente e = new Paciente();
		if(StringUtils.isNotEmpty(arg2)) {

			e = this.pacienteDAO.porId(new Long(arg2));
		}
		return e;
	}

	
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 != null) {
			Paciente Paciente = (Paciente) arg2;
			if(Paciente.getId() == null) {
				return null;
			}else {
				return Paciente.getId().toString();
			}
		}else {
			return "";
		}
	}

}
