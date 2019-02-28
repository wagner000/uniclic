package com.sistemaclinica.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.sistemaclinica.model.Medico;
import com.sistemaclinica.repository.MedicoDAO;

@FacesConverter(forClass = Medico.class)
public class MedicoConverter implements Converter {
	
	@Inject
	private MedicoDAO medicoDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Medico e = new Medico();
		if(StringUtils.isNotEmpty(arg2)) {

			e = this.medicoDAO.porId(new Long(arg2));
		}
		return e;
	}

	
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 != null) {
			Medico medico = (Medico) arg2;
			if(medico.getId() == null) {
				return null;
			}else {
				return medico.getId().toString();
			}
		}else {
			return "";
		}
	}

}
