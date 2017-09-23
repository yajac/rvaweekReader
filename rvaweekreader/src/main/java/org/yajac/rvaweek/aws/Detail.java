package org.yajac.rvaweek.aws;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Detail {

	@JsonProperty("detail")
	String detail;

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
