package com.sistemaclinica.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.sistemaclinica.model.Usuario;
import com.sistemaclinica.repository.UsuarioDAO;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Usuario e = new Usuario();
		if(StringUtils.isNotEmpty(arg2)) {

			e = this.usuarioDAO.porId(new Long(arg2));
		}
		return e;
	}

	
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 != null) {
			Usuario Usuario = (Usuario) arg2;
			if(Usuario.getId() == null) {
				return null;
			}else {
				return Usuario.getId().toString();
			}
		}else {
			return "";
		}
	}

}
