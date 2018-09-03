package org.unibl.etf.sni.adminapp.util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.unibl.etf.sni.adminapp.mysql.dao.TokenDao;
import org.unibl.etf.sni.adminapp.mysql.dao.UserDao;
import org.unibl.etf.sni.adminapp.mysql.dto.TokenDto;
import org.unibl.etf.sni.adminapp.mysql.dto.UserDto;

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
		String address = "403.xhtml?faces-redirect=true";
		// String loginURL = "https://desktop-k7km0nm:9443/cas/login";
		String username = req.getRemoteUser();
		UserDto user = UserDao.getByUsername(username);
		System.out.println("ADMIN:" + req.getRequestURI());
		String referrer = cxt.getExternalContext().getRequestHeaderMap().get("referer");
		System.out.println("ADMIN ref:" + referrer);
		if (req.getRequestURI().contains(";jsessionid=")) {
			if (user != null) {
				session.setAttribute("user", user);
				TokenDto token = TokenDao.getByUserId(user.getId());
				if (user.getAdmin()) {
					if (referrer != null && referrer.contains("cas/login")) {
						address = "verify.xhtml?faces-redirect=true";
						if(token!=null) {
						token.setSso(false);
						TokenDao.update(token);
						}
					} else {
						if (token!=null && token.getSso()) {
							address = "index.xhtml?faces-redirect=true";
						} else {
							address = "verify.xhtml?faces-redirect=true";
						}
					}
				} else {
					address = "403.xhtml?faces-redirect=true";
				}
				if (!resp.isCommitted()) {
					cxt.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(),
							null, address);
					cxt.responseComplete();
				}
			}
		} else if (req.getRequestURI().endsWith("/") || req.getRequestURI().contains("index.xhtml")) {
			if (user != null) {
				session.setAttribute("user", user);
				TokenDto token = TokenDao.getByUserId(user.getId());
				if (user.getAdmin()) {
					if (token!=null && token.getSso()) {
						if(req.getRequestURI().contains("index.xhtml"))
							return;
						address = "index.xhtml?faces-redirect=true";
					}
					else
						address = "verify.xhtml?faces-redirect=true";
				} else {
					address = "403.xhtml?faces-redirect=true";
				}
			}
			if (!resp.isCommitted()) {
				cxt.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,
						address);
				cxt.responseComplete();
			}
		}

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
