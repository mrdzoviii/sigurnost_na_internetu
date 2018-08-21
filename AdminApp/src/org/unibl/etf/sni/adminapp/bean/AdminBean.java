package org.unibl.etf.sni.adminapp.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.unibl.etf.sni.adminapp.mysql.dao.CategoryDao;
import org.unibl.etf.sni.adminapp.mysql.dao.DrivingLicenceDao;
import org.unibl.etf.sni.adminapp.mysql.dao.IdentityCardDao;
import org.unibl.etf.sni.adminapp.mysql.dao.PassportDao;
import org.unibl.etf.sni.adminapp.mysql.dao.TokenDao;
import org.unibl.etf.sni.adminapp.mysql.dao.UserDao;
import org.unibl.etf.sni.adminapp.mysql.dto.DrivingLicenceCategoryDto;
import org.unibl.etf.sni.adminapp.mysql.dto.DrivingLicenceDto;
import org.unibl.etf.sni.adminapp.mysql.dto.IdentityCardDto;
import org.unibl.etf.sni.adminapp.mysql.dto.PassportDto;
import org.unibl.etf.sni.adminapp.mysql.dto.TokenDto;
import org.unibl.etf.sni.adminapp.mysql.dto.UserDto;
import org.unibl.etf.sni.adminapp.util.ServiceUtility;

@ManagedBean
@SessionScoped
public class AdminBean implements Serializable {
	private static final long serialVersionUID = -6057902019315345496L;
	private static final String IDENTITY_CARD = ServiceUtility.bundle.getString("identityCard");
	private static final String PASSPORT = ServiceUtility.bundle.getString("passport");
	private static final String DRIVING_LICENNCE = ServiceUtility.bundle.getString("drivingLicence");
	private static final String PID_REGEX = ServiceUtility.bundle.getString("pidRegex");

	private String pid;
	
	private PassportDto passport;
	private IdentityCardDto identityCard;
	private DrivingLicenceDto drivingLicence;
	private Date today;
	private Date validFrom;
	private Date validUntil;
	private String type;
	private String code;
	private boolean pType = false;
	private boolean idType = true;
	private boolean dlType = false;

	// drivinglicence
	private Date a;
	private Date a1;
	private Date b;
	private Date b1;
	private Date c;
	private Date c1;
	private Date d;
	private Date d1;
	private Date be;
	private Date c1e;
	private Date ce;
	private Date de;

	@PostConstruct
	public void init() {
		clear();
		code = "";
	}

	public void onTypeChanged() {
		pType = PASSPORT.equals(type);
		idType = IDENTITY_CARD.equals(type);
		dlType = DRIVING_LICENNCE.equals(type);
	}

	public List<String> completePid(String query) {
		return UserDao.getAllPids(query);
	}
	
	private void clear() {
		type = IDENTITY_CARD;
		today = ServiceUtility.getToday();
		pid = "";
		pType = false;
		idType = true;
		dlType = false;
		passport = new PassportDto();
		identityCard = new IdentityCardDto();
		drivingLicence = new DrivingLicenceDto();
		validFrom = new Date();
		validUntil = null;
		a = null;
		a1 = null;
		b = null;
		b1 = null;
		c = null;
		c1 = null;
		d = null;
		d1 = null;
		be = null;
		ce = null;
		de = null;
		c1e = null;
	}

	public void saveDocument() {
		UserDto user = UserDao.getByPid(pid);
		if (pid.equals("") || !pid.matches(PID_REGEX) || validFrom == null || validUntil == null
				|| validFrom.before(today) || validUntil.before(today) || user == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input data not valid", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}

		if (IDENTITY_CARD.equals(type)) {
			identityCard.setSerial(ServiceUtility.serialGenerator());
			identityCard.setStatus(true);
			identityCard.setUserId(user.getId());
			identityCard.setValidFrom(validFrom);
			identityCard.setValidUntil(validUntil);
			if (IdentityCardDao.update(user.getId()) && IdentityCardDao.insert(identityCard)) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Document created", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
				clear();
				return;
			}
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Document not created", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		} else if (PASSPORT.equals(type)) {
			passport.setSerial(ServiceUtility.serialGenerator());
			passport.setStatus(true);
			passport.setUserId(user.getId());
			passport.setValidFrom(validFrom);
			passport.setValidUntil(validUntil);
			if (PassportDao.update(user.getId()) && PassportDao.insert(passport)) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Document created", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
				clear();
				return;
			}
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Document not created", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		} else {
			drivingLicence.setSerial(ServiceUtility.serialGenerator());
			drivingLicence.setStatus(true);
			drivingLicence.setUserId(user.getId());
			drivingLicence.setValidFrom(validFrom);
			drivingLicence.setValidUntil(validUntil);
			List<DrivingLicenceCategoryDto> categories = new ArrayList<>();
			if (a != null && a.compareTo(today)<=0) {
				categories.add(new DrivingLicenceCategoryDto(null, CategoryDao.getByName("A").getId(), a, false, null));
			}
			if (a1 != null && a1.compareTo(today)<=0) {
				categories
						.add(new DrivingLicenceCategoryDto(null, CategoryDao.getByName("A1").getId(), a1, false, null));
			}
			if (b != null && b.compareTo(today)<=0) {
				categories.add(new DrivingLicenceCategoryDto(null, CategoryDao.getByName("B").getId(), b, false, null));
			}
			if (b1 != null && b1.compareTo(today)<=0) {
				categories
						.add(new DrivingLicenceCategoryDto(null, CategoryDao.getByName("B1").getId(), b1, false, null));
			}
			if (c != null && c.compareTo(today)<=0) {
				categories.add(new DrivingLicenceCategoryDto(null, CategoryDao.getByName("C").getId(), c, false, null));
			}
			if (c1 != null && c1.compareTo(today)<=0) {
				categories
						.add(new DrivingLicenceCategoryDto(null, CategoryDao.getByName("C1").getId(), c1, false, null));
			}
			if (d != null && d.compareTo(today)<=0) {
				categories.add(new DrivingLicenceCategoryDto(null, CategoryDao.getByName("D").getId(), d, false, null));
			}
			if (d1 != null && d1.compareTo(today)<=0) {
				categories
						.add(new DrivingLicenceCategoryDto(null, CategoryDao.getByName("D1").getId(), d1, false, null));
			}
			if (be != null && be.compareTo(today)<=0) {
				categories
						.add(new DrivingLicenceCategoryDto(null, CategoryDao.getByName("BE").getId(), be, false, null));
			}
			if (ce != null && ce.compareTo(today)<=0) {
				categories
						.add(new DrivingLicenceCategoryDto(null, CategoryDao.getByName("CE").getId(), ce, false, null));
			}
			if (c1e != null && c1e.compareTo(today)<=0) {
				categories.add(
						new DrivingLicenceCategoryDto(null, CategoryDao.getByName("C1E").getId(), c1e, false, null));
			}
			if (de != null && de.compareTo(today)<=0) {
				categories
						.add(new DrivingLicenceCategoryDto(null, CategoryDao.getByName("DE").getId(), de, false, null));
			}
			
			drivingLicence.setCategories(categories);

			if (DrivingLicenceDao.update(user.getId()) && DrivingLicenceDao.insert(drivingLicence)) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Document created", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
				clear();
				return;
			}
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Document not created", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}
	}

	// getters and setters
	public void verify() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (code.matches("^[0-9A-Z]{8}$")) {
			
			UserDto user = (UserDto) session.getAttribute("user");
			TokenDto token = TokenDao.getByUserId(user.getId());
			if (token == null || token.getValidUntil().before(new Date(System.currentTimeMillis()))) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Get verification code from android app", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}
			if (token.getToken().equals(code)) {
				token.setSso(true);
				TokenDao.update(token);
				FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
						.handleNavigation(FacesContext.getCurrentInstance(), null, "index.xhtml?faces-redirect=true");
				return;
			}
		}
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong code", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
		return;
	}

	public void logout() {
		try {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			UserDto user = getUser();
			if (user != null) {
				session.removeAttribute("user");
				TokenDto token=TokenDao.getByUserId(user.getId());
				token.setSso(false);
				TokenDao.update(token);
			}
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("https://desktop-k7km0nm:9443/cas/logout");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getType() {
		return type;
	}

	public boolean ispType() {
		return pType;
	}

	public void setpType(boolean pType) {
		this.pType = pType;
	}

	public boolean isIdType() {
		return idType;
	}

	public void setIdType(boolean idType) {
		this.idType = idType;
	}

	public boolean isDlType() {
		return dlType;
	}

	public void setDlType(boolean dlType) {
		this.dlType = dlType;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}

	public AdminBean() {
		super();
	}

	public UserDto getUser() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		UserDto user = (UserDto) session.getAttribute("user");
		return user;
	}

	public PassportDto getPassport() {
		return passport;
	}

	public void setPassport(PassportDto passport) {
		this.passport = passport;
	}

	public IdentityCardDto getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(IdentityCardDto identityCard) {
		this.identityCard = identityCard;
	}

	public DrivingLicenceDto getDrivingLicence() {
		return drivingLicence;
	}

	public void setDrivingLicence(DrivingLicenceDto drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getA() {
		return a;
	}

	public void setA(Date a) {
		this.a = a;
	}

	public Date getA1() {
		return a1;
	}

	public void setA1(Date a1) {
		this.a1 = a1;
	}

	public Date getB() {
		return b;
	}

	public void setB(Date b) {
		this.b = b;
	}

	public Date getB1() {
		return b1;
	}

	public void setB1(Date b1) {
		this.b1 = b1;
	}

	public Date getC() {
		return c;
	}

	public void setC(Date c) {
		this.c = c;
	}

	public Date getC1() {
		return c1;
	}

	public void setC1(Date c1) {
		this.c1 = c1;
	}

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
		this.d = d;
	}

	public Date getD1() {
		return d1;
	}

	public void setD1(Date d1) {
		this.d1 = d1;
	}

	public Date getBe() {
		return be;
	}

	public void setBe(Date be) {
		this.be = be;
	}

	public Date getC1e() {
		return c1e;
	}

	public void setC1e(Date c1e) {
		this.c1e = c1e;
	}

	public Date getCe() {
		return ce;
	}

	public void setCe(Date ce) {
		this.ce = ce;
	}

	public Date getDe() {
		return de;
	}

	public void setDe(Date de) {
		this.de = de;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
