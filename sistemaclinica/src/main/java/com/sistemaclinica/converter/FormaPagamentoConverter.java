package com.sistemaclinica.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.sistemaclinica.model.FormaPagamento;
import com.sistemaclinica.repository.FormaPagamentoDAO;

@FacesConverter(forClass = FormaPagamento.class)
public class FormaPagamentoConverter implements Converter {
	
	@Inject
	private FormaPagamentoDAO formaPagDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		FormaPagamento e = null;
		if(StringUtils.isNotEmpty(arg2)) {

			e = this.formaPagDAO.porId(new Long(arg2));
		}
		return e;
	
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 != null) {
			FormaPagamento pag = (FormaPagamento) arg2;
			if(pag.getId() == null) {
				return null;
			}else {
				return pag.getId().toString();
			}
		}else {
			return "";
		}
	}

}
