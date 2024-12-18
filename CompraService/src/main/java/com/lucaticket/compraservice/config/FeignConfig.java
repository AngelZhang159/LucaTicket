package com.lucaticket.compraservice.config;

import org.springframework.context.annotation.Bean;

import com.lucaticket.compraservice.error.CustomErrorDecoder;

import feign.codec.ErrorDecoder;

/**
 * @author Raul
 */
public class FeignConfig {
	 @Bean
	    public ErrorDecoder errorDecoder() {
	        return new CustomErrorDecoder();
	    }
}
