package com.lucaticket.compraservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lucaticket.compraservice.config.FeignConfig;
import com.lucaticket.compraservice.model.dto.UserResponse;

/**
 * @author Raul
 */
@FeignClient(name = "user", configuration = FeignConfig.class)
public interface UserFeignClient {

	@GetMapping("/user/{email}")
	public ResponseEntity<UserResponse> getUser(@PathVariable String email);
	
}
