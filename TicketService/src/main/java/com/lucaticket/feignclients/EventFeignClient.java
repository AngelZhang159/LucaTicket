package com.lucaticket.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lucaticket.event.model.dto.DetailedEventResponse;

/**
 * @Author Yuji
 * **/

@FeignClient(name = "event")
public interface EventFeignClient {

	@GetMapping("/event/detail/{id}")
	public DetailedEventResponse getDetail(@PathVariable long id);

}
