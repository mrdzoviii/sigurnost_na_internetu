package org.unibl.etf.sni.adminapp.bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class AdminBean implements Serializable {
	private static final long serialVersionUID = -6057902019315345496L;
	
	private String name;
	private String surname;
	private Date dateOfBirth;
	private String placeOfBirth;
	private String personId;
	private Date validFrom;
	private Date validUntil;
	private String serial;
	private String residence;
	private String type;
	
	//drivinglicence
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
	}
	
	public void saveDocument() {
		System.out.println("DOCUMENT SAVED");
	}
	
	private void clear() {
		name="";
		surname="";
		serial="";
		type="IDENTITY_CARD";
		personId="";
		dateOfBirth=null;
		placeOfBirth="";
		residence="";
		validFrom=new Date();
		validUntil=null;
		a=null;
		a1=null;
		b=null;
		b1=null;
		c=null;
		c1=null;
		d=null;
		d1=null;
		be=null;
		ce=null;
		de=null;
		c1e=null;
	}
	
	
	
	
	
	// getters and setters
	
	
	
	
	
	public String getName() {
		return name;
	}
	public AdminBean() {
		super();
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
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
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getType() {
		return type;
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
	
	
	 
	
	
	
	/*

	
	public void saveAd() {
		
		if (ad.getImage() == null && (ad.getText() == null || ad.getText().isEmpty())) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ad text or text image must be entered", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}
		if ("Free".equals(type)) {
			ad.setDateTo(ServiceUtility.getOffsetDate(1, ad.getDateFrom()));
		} else {
			ad.setDateTo(ServiceUtility.getOffsetDate(days, ad.getDateFrom()));
			if (ServiceUtility.differeceInDays(ad.getDateFrom(), ad.getDateTo()) < 1) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Ad date from and date to not valid", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}
			if (!ServiceUtility.checkEmail(account.getMail())) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"IpBanka don't recognize this email with any account", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}

			try {
				IpBankaSoapServiceServiceLocator locator = new IpBankaSoapServiceServiceLocator();
				IpBankaSoapService service = locator.getIpBankaSoapService();
				if (service.validateData(account.getMail(), account.getName(), account.getSurname(),
						account.getCardNumber(), account.getCardType(), account.getExpirationTime(), account.getCvc(),
						ServiceUtility.parseDouble(getPrice()))) {
					if(!service.payTotal(account.getMail(), account.getName(), account.getSurname(),
							account.getCardNumber(), account.getCardType(), account.getExpirationTime(),
							account.getCvc(), ServiceUtility.parseDouble(getPrice()))){
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eror during payment", "");
						FacesContext.getCurrentInstance().addMessage(null, message);
						return;
					}
				} else {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You enetered invalid data",
							"");
					FacesContext.getCurrentInstance().addMessage(null, message);
					return;
				}
			} catch (ServiceException e) {
				e.printStackTrace();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eror during payment", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			} catch (RemoteException e) {
				e.printStackTrace();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eror during payment", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}

		}

		if (AdDao.insertAd(ad)) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ad succesufully added", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			clear();
		}else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ad not succesufully added", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}

	


*/
	
	
	
}

