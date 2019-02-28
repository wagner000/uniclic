package com.sistemaclinica.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.sistemaclinica.model.Municipio;
import com.sistemaclinica.repository.MunicipioDAO;

@FacesConverter(forClass = Municipio.class)
public class MunicipioConverter implements Converter {
	
	@Inject
	private MunicipioDAO municipioDAO;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Municipio e = null;
		
		if(StringUtils.isNotEmpty(arg2)) {
			e = this.municipioDAO.porId(new Long(arg2).intValue());
		}
		return e;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 != null) {
			return new Long( ((Municipio) arg2).getId()).toString();
		}
		return "";
	}
}
