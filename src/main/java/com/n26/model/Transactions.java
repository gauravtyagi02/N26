package com.n26.model;

import java.io.Serializable;
import java.util.TimeZone;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;


@Component

@Validated
public class Transactions implements Serializable {

	private static final long serialVersionUID = -5074165522709256779L;

	@NotEmpty
	
	private String amount;
	
	
	
	@NotEmpty
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private String timestamp;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Transactions [amount=" + amount + ", timestamp=" + timestamp + "]";
	}

}
