package com.lucaticket.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lucaticket.compraservice.config.FeignConfig;
import com.lucaticket.compraservice.model.dto.DetailedEventResponse;

@FeignClient(name = "event", configuration = FeignConfig.class)
public interface EventFeignClient {

	@GetMapping("/event/detail/{id}")
	public ResponseEntity<DetailedEventResponse> getDetail(@PathVariable long id);

}
