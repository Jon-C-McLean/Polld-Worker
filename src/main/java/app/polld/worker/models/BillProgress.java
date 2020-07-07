package app.polld.worker.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BillProgress {
	
	@JsonProperty
	int processOrder;
	
	@JsonProperty
	String desc;
	
	@JsonProperty
	Date date;
	
	@JsonProperty
	Long billId;
	
	public BillProgress() {}

	@JsonGetter
	public int getProcessOrder() {
		return processOrder;
	}

	@JsonSetter
	public void setProcessOrder(int processOrder) {
		this.processOrder = processOrder;
	}

	@JsonGetter
	public String getDesc() {
		return desc;
	}

	@JsonSetter
	public void setDesc(String desc) {
		this.desc = desc;
	}

	@JsonGetter
	public Date getDate() {
		return date;
	}

	@JsonSetter
	public void setDate(Date date) {
		this.date = date;
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
