package app.polld.worker.models;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BillDetail {
	
	@JsonProperty
	String type;
	
	@JsonProperty
	String portfolio;
	
	@JsonProperty
	String status;
	
	@JsonProperty
	String sponsor;
	
	@JsonProperty
	String summary;
	
	@JsonProperty
	String permaLink;
	
	@JsonProperty
	String originHouse;
	
	@JsonProperty
	boolean isAssented = false;
	
	@JsonProperty
	Date assentDate;
	
	@JsonProperty
	List<BillProgress> progress;
	
	public BillDetail() {}

	@JsonGetter
	public String getType() {
		return type;
	}
	
	@JsonSetter
	public void setType(String type) {
		this.type = type;
	}

	@JsonGetter
	public String getPortfolio() {
		return portfolio;
	}

	@JsonSetter
	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}

	@JsonGetter
	public String getSponsor() {
		return sponsor;
	}

	@JsonSetter
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	@JsonGetter
	public boolean isAssented() {
		return isAssented;
	}

	@JsonSetter
	public void setAssented(boolean isAssented) {
		this.isAssented = isAssented;
	}

	@JsonGetter
	public List<BillProgress> getProgress() {
		return progress;
	}

	@JsonSetter
	public void setProgress(List<BillProgress> progress) {
		this.progress = progress;
	}

	@JsonGetter
	public String getStatus() {
		return status;
	}

	@JsonSetter
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonGetter
	public String getSummary() {
		return summary;
	}

	@JsonSetter
	public void setSummary(String summary) {
		this.summary = summary;
	}

	@JsonGetter
	public String getPermaLink() {
		return permaLink;
	}

	@JsonSetter
	public void setPermaLink(String permaLink) {
		this.permaLink = permaLink;
	}

	@JsonGetter
	public String getOriginHouse() {
		return originHouse;
	}

	@JsonSetter
	public void setOriginHouse(String originHouse) {
		this.originHouse = originHouse;
	}

	@JsonGetter
	public Date getAssentDate() {
		return assentDate;
	}

	@JsonSetter
	public void setAssentDate(Date assentDate) {
		this.assentDate = assentDate;
	}
}
