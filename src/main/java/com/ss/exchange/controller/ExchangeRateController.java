package com.ss.exchange.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ss.exchange.model.ExchangeRate;
import com.ss.exchange.model.Rates;
import com.ss.exchange.service.ExchangeRatesServiceProxy;

@Tag(name = "ExchangeRates", description = "the Exchange Rates API")
@Controller
public class ExchangeRateController {
	private static final int NUM_OF_MONTHS = 6;

	@Autowired
	ExchangeRatesServiceProxy ratesService;

	// REST API request methods

	@Operation(summary = "Find exchange rates of last six months for the same day.", description = "Current date is considered as current day.")
	@GetMapping("/api/rates")
	@ResponseBody
	public List<ExchangeRate> exchangeRateForLastSixMonthsJSON() throws Exception {
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return getExchangeRatesList(currentDate, NUM_OF_MONTHS);
	}

	@Operation(summary = "Find exchange rates of last six months for the same day.", description = "User provided date is considered as current day.")
	@GetMapping(path = "/api/rates/{date}", produces = "application/json")
	@ResponseBody
	public List<ExchangeRate> exchangeRateForLastNMonthsJSON(
			@PathVariable("date") @Parameter(description="User provided date in yyyy-MM-dd format.", required=true) String date) throws Exception {
		return getExchangeRatesList(date, NUM_OF_MONTHS);
	}

	@Operation(summary = "Find exchange rates of last N months for the same day.", description = "User provided date is considered as current day.")
	@GetMapping("/api/rates/{date}/{months}")
	@ResponseBody
	public List<ExchangeRate> exchangeRateForLastNMonthsJSON(
			@PathVariable("date") @Parameter(description="User provided date in yyyy-MM-dd format.", required=true) String date,
			@PathVariable("months") @Parameter(description="User provided number of months to see for.", required=true) int months) throws Exception {
		return getExchangeRatesList(date, months);
	}

	// UI request methods

	@GetMapping("/ui/rates")
	public ModelAndView exchangeRateForLastSixMonths() {
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return getExchangeRates(currentDate, NUM_OF_MONTHS);
	}

	@GetMapping("/ui/rates/{date}")
	public ModelAndView exchangeRateForLastNMonths(@PathVariable("date") String date) {
		return getExchangeRates(date, NUM_OF_MONTHS);
	}

	@GetMapping("/ui/rates/{date}/{months}")
	public ModelAndView exchangeRateForLastNMonths(@PathVariable("date") String date,
			@PathVariable("months") int months) {
		return getExchangeRates(date, months);
	}

	private ModelAndView getExchangeRates(String date, int numOfMonths) {
		ModelAndView mv = new ModelAndView();
		try {
			mv.addObject("exRates", getExchangeRatesList(date, numOfMonths));
			mv.setViewName("exrate");
		}catch (Exception e) {
			mv.addObject("msg", e.getMessage());
			mv.setViewName("error");
		}
		return mv;
	}

	private List<ExchangeRate> getExchangeRatesList(String date, int numOfMonths) throws Exception {
		List<ExchangeRate> rateList = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateToInitialize = dateFormat.parse(date);
		Calendar c = Calendar.getInstance();

		for (int i = 0; i < numOfMonths; i++) {
			c.setTime(dateToInitialize);
			c.add(Calendar.MONTH, -i);
			String sDate = dateFormat.format(c.getTime());
			ExchangeRate rate = ratesService.findGivenDateExchangeRates(sDate);
			if (sDate.equals(rate.getDate())) {
				rateList.add(rate);
			} else {
				rate.setDate(sDate);
				rate.setRates(new Rates());
				rateList.add(rate);
			}
		}
		return rateList;
	}
}
