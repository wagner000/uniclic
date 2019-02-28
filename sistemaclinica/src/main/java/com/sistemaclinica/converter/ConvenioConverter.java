package com.sistemaclinica.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.sistemaclinica.model.Convenio;
import com.sistemaclinica.repository.ConvenioDAO;

@FacesConverter(forClass = Convenio.class)
public class ConvenioConverter implements Converter {
	
	@Inject
	private ConvenioDAO convenioDao;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Convenio e = null;
		if(StringUtils.isNotEmpty(arg2)) {

			e = this.convenioDao.porId(new Long(arg2));
		}
		return e;
	
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 != null) {
			return new Long( ((Convenio) arg2).getId()).toString();
		}
		return "";
	}

}
