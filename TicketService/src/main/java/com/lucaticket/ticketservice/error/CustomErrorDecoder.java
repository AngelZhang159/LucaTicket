package com.lucaticket.ticketservice.error;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
	
	private final ErrorDecoder defaultDecoder = new Default();

	@Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
//            case 400:
//                return new BadRequestException("Bad Request: " + response.request().url());
//            case 404:
//                return new UserNotFoundException("User not found: " + response.request().url());
//            case 500:
//                return new InternalServerException("Internal Server Error: " + response.request().url());
            default:
                return defaultDecoder.decode(methodKey, response);
        }
    }

}
