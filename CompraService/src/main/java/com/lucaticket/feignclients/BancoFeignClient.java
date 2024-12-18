package com.lucaticket.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.lucaticket.compraservice.config.FeignConfig;
import com.lucaticket.compraservice.model.dto.CompraRequest;
import com.lucaticket.compraservice.model.dto.ValidarCompraResponse;
import com.lucaticket.compraservice.model.dto.ValidarUserResponse;

@FeignClient(name = "bank", url = "http://banco.eu-west-3.elasticbeanstalk.com", configuration = FeignConfig.class)
public interface BancoFeignClient {

	@PostMapping("/pasarela/validaruser")
	ResponseEntity<ValidarUserResponse> validarUser(@RequestParam("user") String user,
			@RequestParam("password") String password);

	@PostMapping("/pasarela/validacion")
	ResponseEntity<ValidarCompraResponse> validarCompra(@RequestHeader("Authorization") String token, @RequestBody CompraRequest validarCompraRequest);
}
