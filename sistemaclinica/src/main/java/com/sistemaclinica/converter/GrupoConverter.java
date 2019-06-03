package com.sistemaclinica.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.sistemaclinica.model.Grupo;
import com.sistemaclinica.repository.GrupoDAO;

@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter {
	
	@Inject
	private GrupoDAO grupoDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Grupo e = new Grupo();
		if(StringUtils.isNotEmpty(arg2)) {

			e = this.grupoDAO.porId(new Long(arg2));
		}
		return e;
	}

	
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 != null) {
			Grupo Grupo = (Grupo) arg2;
			if(Grupo.getId() == null) {
				return null;
			}else {
				return Grupo.getId().toString();
			}
		}else {
			return "";
		}
	}

}
