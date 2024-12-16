package com.lucaticket.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lucaticket.compraservice.model.dto.DetailedEventResponse;

@FeignClient(name = "event")
public interface EventFeignClient {

	@GetMapping("/detail/{id}")
	public ResponseEntity<DetailedEventResponse> getDetail(@PathVariable long id);

}
