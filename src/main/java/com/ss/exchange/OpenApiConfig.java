package com.ss.exchange;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Exchange Rates API").description(
                        "Historical exchange rates for the past six months for Pound Sterling (GBP), the US Dollar (USD) and the Hong Kong Dollar (HKD) against the Euro (EUR) for the same day."));
    }
}
