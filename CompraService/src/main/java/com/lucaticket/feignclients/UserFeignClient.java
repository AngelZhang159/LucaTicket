package com.lucaticket.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lucaticket.compraservice.model.dto.UserResponse;

@FeignClient(name = "user")
public interface UserFeignClient {

	@GetMapping("/user/{email}")
	public ResponseEntity<UserResponse> getUser(@PathVariable String email);
	
}
