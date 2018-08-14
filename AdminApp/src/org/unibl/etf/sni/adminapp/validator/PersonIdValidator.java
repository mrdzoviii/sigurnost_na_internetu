package org.unibl.etf.sni.adminapp.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.unibl.etf.sni.adminapp.mysql.dao.UserDao;
import org.unibl.etf.sni.adminapp.mysql.dto.UserDto;

@FacesValidator("org.unibl.etf.sni.adminapp.validator.PersonIdValidator")
public class PersonIdValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String pid = value.toString().trim();
		if(!pid.matches("^[0-9]{13}$")) {
			FacesMessage msg = new FacesMessage();
			msg = new FacesMessage("Person id must be 13 characters long and only can contains digits");
			throw new ValidatorException(msg);
		}
		UserDto user=UserDao.getByPid(pid);
		if(user==null) {
			FacesMessage msg = new FacesMessage();
			msg = new FacesMessage("Person with this person id doesn't exist");
			throw new ValidatorException(msg);
		}
	}
}