package org.unibl.etf.sni.clientapp.util;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.unibl.etf.sni.clientapp.mysql.dao.TokenDao;
import org.unibl.etf.sni.clientapp.mysql.dao.UserDao;
import org.unibl.etf.sni.clientapp.mysql.dto.TokenDto;
import org.unibl.etf.sni.clientapp.mysql.dto.UserDto;

public class AccessListener implements PhaseListener {

	private static final long serialVersionUID = 3971334959325942875L;

	@Override
	public void afterPhase(PhaseEvent arg0) {
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		FacesContext cxt = arg0.getFacesContext();
		HttpServletRequest req = (HttpServletRequest) cxt.getExternalContext().getRequest();
		HttpServletResponse resp = (HttpServletResponse) cxt.getExternalContext().getResponse();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		String address = "error.xhtml?faces-redirect=true";
		String username = req.getRemoteUser();
		UserDto user = UserDao.getByUsername(username);
		System.out.println(req.getRequestURI());
		if (req.getRequestURI().contains("jsessionid") || req.getRequestURI().endsWith("/")) {
			if (user != null) {
				session.setAttribute("user",user);
				TokenDto token=TokenDao.getByUserId(user.getId());
					if(token!=null && token.getSso()) {
					address = "index.xhtml?faces-redirect=true";
					}else {
					
						address="verify.xhtml?faces-redirect=true";
					}		
			}
		
			if (!resp.isCommitted()) {
				cxt.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(),
						null, address);
				cxt.responseComplete();
			}
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
