package com.ss.exchange.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ss.exchange.model.ExchangeRate;

@FeignClient(name="exchange-rates", url = "https://api.exchangeratesapi.io")
public interface ExchangeRatesServiceProxy {
	   @RequestMapping("/api/latest")
	   ResponseEntity<ExchangeRate> findLatestDateExchangeRates();

	   @RequestMapping("/api/{date}")
	   ExchangeRate findGivenDateExchangeRates(@PathVariable("date") String date);
}
