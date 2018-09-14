package com.ts.XML.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ts.XML.model.Assets;

public class EmployeeDTO {

	private Long empId;

	private String name;

	private Long contactNumber;

	private String account;

	private List<Assets> assetList;

	private Boolean status;

	private LocalDate dateOfBirth;

	private LocalDateTime lastModified;
	
	
	public EmployeeDTO() {};
	

	public EmployeeDTO(Long empId, String name, Long contactNumber, String account, List<Assets> assetList,
			Boolean status, LocalDate dateOfBirth, LocalDateTime lastModified) {
		super();
		this.empId = empId;
		this.name = name;
		this.contactNumber = contactNumber;
		this.account = account;
		this.assetList = assetList;
		this.status = status;
		this.dateOfBirth = dateOfBirth;
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [empId=" + empId + ", name=" + name + ", contactNumber=" + contactNumber + ", account="
				+ account + ", assetList=" + assetList + ", status=" + status + ", dateOfBirth=" + dateOfBirth
				+ ", lastModified=" + lastModified + "]";
	}


	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public List<Assets> getAssetList() {
		return assetList;
	}

	public void setAssetList(List<Assets> assetList) {
		this.assetList = assetList;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}


}
