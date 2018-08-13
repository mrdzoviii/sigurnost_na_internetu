package org.unibl.etf.sni.rest.beans;

import java.io.Serializable;
import java.util.List;


import org.unibl.etf.sni.model.DrivingLicenceDto;
import org.unibl.etf.sni.model.IdentityCardDto;
import org.unibl.etf.sni.model.PassportDto;


public class DocumentsBean implements Serializable {
	private static final long serialVersionUID = 5990901783215035972L;
	private List<PassportDto> passports;
	private List<IdentityCardDto> identityCards;
	private List<DrivingLicenceDto> drivingLicences;
	public List<PassportDto> getPassports() {
		return passports;
	}
	public void setPassports(List<PassportDto> passports) {
		this.passports = passports;
	}
	public List<IdentityCardDto> getIdentityCards() {
		return identityCards;
	}
	public void setIdentityCards(List<IdentityCardDto> identityCards) {
		this.identityCards = identityCards;
	}
	public List<DrivingLicenceDto> getDrivingLicences() {
		return drivingLicences;
	}
	public void setDrivingLicences(List<DrivingLicenceDto> drivingLicences) {
		this.drivingLicences = drivingLicences;
	}
	
	
}
