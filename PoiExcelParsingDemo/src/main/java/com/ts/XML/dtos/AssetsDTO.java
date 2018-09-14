package com.ts.XML.dtos;


public class AssetsDTO {

	private String assetType;

	private Long assetId;

	private String number;

	private String details;

	private String status;

	private boolean deleted;

	public String getAssetType() {
		return assetType;
	}

	public Long getAssetId() {
		return assetId;
	}

	public String getNumber() {
		return number;
	}

	public String getDetails() {
		return details;
	}

	public String getStatus() {
		return status;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
