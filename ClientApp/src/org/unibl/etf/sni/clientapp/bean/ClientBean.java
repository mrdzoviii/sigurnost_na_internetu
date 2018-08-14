package org.unibl.etf.sni.clientapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
		documents.clear();
		documents=data.stream().filter(d->d.getUser().getUsername().toLowerCase().contains(username.toLowerCase()) && 
				d.getUser().getPid().contains(pid)).collect(Collectors.toList());
	if(issueDate!=null) {
				documents=documents.stream().filter(doc->(doc.getValidFrom().equals(issueDate))).collect(Collectors.toList());
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

