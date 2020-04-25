package com.ss.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rates {
	public Float GBP;
	public Float HKD;
	public Float USD;

	@Override
	public String toString() {
		return "Rates [GBP=" + GBP + ", HKD=" + HKD + ", USD=" + USD + "]";
	}

}
