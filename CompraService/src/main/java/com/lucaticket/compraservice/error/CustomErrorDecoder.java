package com.lucaticket.compraservice.error;

import com.lucaticket.compraservice.error.exception.CuentaNoRegistradaException;
import com.lucaticket.compraservice.error.exception.EventoNotFoundException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
	private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
//            case 400:
//                return new BadRequestException("Bad Request: " + response.request().url());
            case 404:
                return new EventoNotFoundException("El evento o el usuario no existen");
            case 500: 
                return new CuentaNoRegistradaException("El usuario no existe en la base de datos");
            default:
                return defaultDecoder.decode(methodKey, response);
        }
    }

}
