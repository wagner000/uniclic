package com.sistemaclinica.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.sistemaclinica.model.Procedimento;
import com.sistemaclinica.repository.ProcedimentoDAO;

@FacesConverter(forClass = Procedimento.class)
public class ProcedimentoConverter implements Converter {
	
	@Inject
	private ProcedimentoDAO procedimentoDao;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Procedimento e = null;
		if(StringUtils.isNotEmpty(arg2)) {

			e = this.procedimentoDao.porId(new Long(arg2));
		}
		return e;
	
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 != null) {
			return new Long( ((Procedimento) arg2).getId()).toString();
		}
		return "";
	}

}
