package com.ss.exchange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.ss.exchange.controller.ExchangeRateController;
import com.ss.exchange.model.ExchangeRate;
import com.fasterxml.jackson.core.type.TypeReference;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ExchangeRateApplicationTests {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	ExchangeRateController exchangeRateController;
	
	@Test
	void contextLoads() {
		assertThat(exchangeRateController).isNotNull();
	}
	
	@Test
	public void testExchangeRateForLastNMonthsJSON() {
//		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/rates/2020-04-15", Object.class))
//		.toString().contains("1.0903");
		Object ob = restTemplate.getForObject("http://localhost:" + port + "/api/rates/2020-04-15", Object.class);
		System.out.println(ob.toString());
	}

}
