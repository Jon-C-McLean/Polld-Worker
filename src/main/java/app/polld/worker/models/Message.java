package app.polld.worker.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Message {

	@JsonProperty
	private String url;
	
	@JsonProperty
	private Long listed;
	
	@JsonProperty
	private Long billId;
	
	public Message() {}
	
	@JsonGetter
	public String getUrl() {
		return url;
	}

	@JsonSetter
	public void setUrl(String url) {
		this.url = url;
	}

	@JsonGetter
	public Long getListed() {
		return listed;
	}
	
	@JsonSetter
	public void setListed(Long listed) {
		this.listed = listed;
	}

	@JsonGetter
	public Long getBillId() {
		return billId;
	}

	@JsonSetter
	public void setBillId(Long billId) {
		this.billId = billId;
	}		
}
