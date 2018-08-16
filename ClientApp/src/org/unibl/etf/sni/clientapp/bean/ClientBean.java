package org.unibl.etf.sni.clientapp.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.unibl.etf.sni.clientapp.mysql.dao.PassportDao;
import org.unibl.etf.sni.clientapp.mysql.dao.TokenDao;
import org.unibl.etf.sni.clientapp.mysql.dto.TokenDto;
import org.unibl.etf.sni.clientapp.mysql.dto.UserDto;
import org.unibl.etf.sni.clientapp.mysql.dao.UserDao;
import org.unibl.etf.sni.clientapp.mysql.dto.Document;
import org.unibl.etf.sni.clientapp.util.ServiceUtility;

@ManagedBean
@SessionScoped
public class ClientBean implements Serializable {
	private static final long serialVersionUID = -6057902019315345496L;

	private List<Document> documents = new ArrayList<>();
	private List<Document> data;
	private String pid;
	private Date issueDate;
	private String username;
	private String serial;
	private String code;
	private Document selectedDocument;

	@PostConstruct
	public void init() {
		System.out.println("INIT");
		pid = "";
		serial = "";
		username = "";
		issueDate = null;
	}

	public List<String> completePid(String query) {
		return UserDao.getAllPids(query);
	}

	public List<String> completeUsername(String query) {
		return UserDao.getAllUsername(query);
	}

	public List<String> completeSerial(String query) {
		return PassportDao.getAllSerials(query);
	}

	public void filterDocs() {
		if (!pid.matches("^[0-9]{0,13}$") || !username.matches("^[0-9a-zA-z]+$")
				|| !serial.matches("^[0-9a-zA-z]{0,9}$") || !serial.matches("^[0-9a-zA-z]{0,30}$")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong input data...", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			reset();
			return;
		}
		documents.clear();
		documents = data.stream()
				.filter(d -> d.getUser().getUsername().toLowerCase().contains(username.toLowerCase())
						&& d.getUser().getPid().contains(pid)
						&& d.getSerial().toLowerCase().contains(serial.toLowerCase()))
				.collect(Collectors.toList());
		if (issueDate != null) {
			documents = documents.stream().filter(doc -> (doc.getValidFrom().equals(issueDate)))
					.collect(Collectors.toList());
		}
	}

	public void verify() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		if (code.matches("^[0-9A-Z]{8}$")) {
			UserDto user = (UserDto) session.getAttribute("user");
			TokenDto token = TokenDao.getByUserId(user.getId());
			if (token == null || token.getValidUntil().before(new Date(System.currentTimeMillis()))) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Get verification code from android app", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}
			System.out.println(token.getToken());
			if (token.getToken().equals(code)) {
				token.setSso(true);
				TokenDao.update(token);
				FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
						.handleNavigation(FacesContext.getCurrentInstance(), null, "index.xhtml?faces-redirect=true");
				// this.user=user;
				return;

			}
		}
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong code", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
		return;
	}

	public void logout() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("https://desktop-k7km0nm:9443/cas/logout");
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			UserDto user = (UserDto) session.getAttribute("user");
			TokenDto token = TokenDao.getByUserId(user.getId());
			token.setSso(false);
			TokenDao.update(token);
			session.removeAttribute("user");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getDocument() {
		System.out.println("SELECTED:" + selectedDocument.getSerial());
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();

		byte[] pdf = ServiceUtility.getPdf(selectedDocument);

		ec.responseReset();
		ec.setResponseContentType("application/pdf");
		ec.setResponseContentLength(pdf.length);
		ec.setResponseHeader("Content-Disposition",
				"attachment; filename=\"" + selectedDocument.getSerial() + ".pdf" + "\"");
		ec.setResponseHeader("Cache-Control", "no-cache, no-store, must-revalidate");

		try {
			OutputStream output = ec.getResponseOutputStream();
			output.write(pdf);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(pdf.length);
		fc.responseComplete();
	}

	public Document getSelectedDocument() {
		return selectedDocument;
	}

	public void setSelectedDocument(Document selectedDocument) {
		this.selectedDocument = selectedDocument;
	}

	public void onload() {
		System.out.println("ON LOAD");
		UserDto user = getUser();
		System.out.println(user);
		if (user != null) {
			if (user.getAdmin()) {
				data = ServiceUtility.getAll();
			} else {
				data = ServiceUtility.getAllByPid(user.getPid());
			}
			documents.clear();
			documents.addAll(data);
		}
		// data=ServiceUtility.getAll();

	}

	public void reset() {
		onload();
		pid = "";
		username = "";
		issueDate = null;
		serial = "";
	}

	public UserDto getUser() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		UserDto user = (UserDto) session.getAttribute("user");
		return user;
	}

	// getters setters

	public List<Document> getData() {
		return data;
	}

	public void setData(List<Document> data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ClientBean() {
		super();
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

}
