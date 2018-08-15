package org.unibl.etf.sni.clientapp.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	private List<Document> documents=new ArrayList<>();
	private List<Document> data;
	private String pid;
	private Date issueDate;
	private String username;
	private String code;
	
	@PostConstruct
	public void init() {
		reset();
	}
	
	public List<String> completePid(String query){
		return UserDao.getAllPids(query);
	}
	
	public List<String> completeUsername(String query){
		return UserDao.getAllUsername(query);
	}
	
	public void filterDocs() {
		System.out.println(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteUser());
		System.out.println(((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("edu.yale.its.tp.cas.client.filter.user"));
		documents.clear();
		documents=data.stream().filter(d->d.getUser().getUsername().toLowerCase().contains(username.toLowerCase()) && 
				d.getUser().getPid().contains(pid)).collect(Collectors.toList());
	if(issueDate!=null) {
				documents=documents.stream().filter(doc->(doc.getValidFrom().equals(issueDate))).collect(Collectors.toList());
		}
	}
	
	public void verify() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		UserDto user=(UserDto) session.getAttribute("user");
		TokenDto token=TokenDao.getByUserId(user.getId());
		if(token==null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Get verification code from android app", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}
		System.out.println(token.getToken());
		if(token.getToken().equals(code)) {
			token.setSso(true);
			TokenDao.update(token);
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
			.handleNavigation(FacesContext.getCurrentInstance(), null, "index.xhtml?faces-redirect=true");
			return;
		}
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Wrong code", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
	}
	
	
	public void logout() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("https://desktop-k7km0nm:9443/cas/logout");
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			UserDto user=(UserDto) session.getAttribute("user");
			TokenDto token=TokenDao.getByUserId(user.getId());
			token.setSso(false);
			TokenDao.update(token);
			session.removeAttribute("user");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


	public void reset() {
		data=ServiceUtility.getAll();
		documents.clear();
		documents.addAll(data);
		pid="";
		username="";
		issueDate=null;
	}


	
	
	//getters setters
	
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
	
	
}

