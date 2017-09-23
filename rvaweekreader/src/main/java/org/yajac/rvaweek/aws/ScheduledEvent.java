package org.yajac.rvaweek.aws;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScheduledEvent {

	@JsonProperty("account")
	String account;
	@JsonProperty("region")
	String region;
	@JsonProperty("detail")
	Detail detail;
	@JsonProperty("detail-type")
	String detailType;
	@JsonProperty("source")
	String source;
	@JsonProperty("time")
	String time;
	@JsonProperty("id")
	String id;
	@JsonProperty("resources")
	String[] resources;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getResources() {
		return resources;
	}

	public void setResources(String[] resources) {
		this.resources = resources;
	}


}
