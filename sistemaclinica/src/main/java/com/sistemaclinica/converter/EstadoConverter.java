package com.sistemaclinica.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.sistemaclinica.model.Estado;
import com.sistemaclinica.repository.EstadoDAO;

@FacesConverter(forClass = Estado.class)
public class EstadoConverter implements Converter {
	
	@Inject
	private EstadoDAO estadoDao;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Estado e = null;
		if(StringUtils.isNotEmpty(arg2)) {

			e = this.estadoDao.porId(new Long(arg2).intValue());
		}
		return e;
	
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 != null) {
			return new Long( ((Estado) arg2).getId()).toString();
		}
		return "";
	}

}
