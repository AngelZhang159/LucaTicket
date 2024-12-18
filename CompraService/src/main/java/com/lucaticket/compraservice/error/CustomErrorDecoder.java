package com.lucaticket.compraservice.error;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import com.lucaticket.compraservice.error.exception.BancoNoDisponibleException;
import com.lucaticket.compraservice.error.exception.CuentaNoRegistradaException;
import com.lucaticket.compraservice.error.exception.DatosCompraInvalidosException;
import com.lucaticket.compraservice.error.exception.EventoNotFoundException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
	private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
    	log.error(response.body().toString() + " | == | " + methodKey);
    	String bodyContent = comprobadorCodigoError400(response.body());
        switch (response.status()) {
            case 404:
                if(methodKey.contains("UserFeignClient#getUser") || methodKey.contains("BancoFeignClient#validarCompra")) {
                	return new CuentaNoRegistradaException("El email introducido no coincide con ninguna cuenta registrada en nuestra base de datos, "
                			+ "por favor, regístrese para poder disfrutar de nuestro servicio de compra");
                } if(methodKey.contains("EventFeignClient#getDetail")) {
                	return new EventoNotFoundException("El id de evento introducido no coincide con ningun evento registrado en nuestra base de datos,"
                			+ " por favor, introduzca un id de evento válido");
                } 
            case 400: 
            	if(bodyContent.contains("400.0001")) {
            		return new DatosCompraInvalidosException("No hay fondos suficientes en la cuenta");
            	} if(bodyContent.contains("400.0002")) {
            		return new DatosCompraInvalidosException("No se encuentran los datos del cliente ");
            	} if(bodyContent.contains("400.0003")) {
            		return new DatosCompraInvalidosException("El número de la tarjeta no es válido");
            	} if(bodyContent.contains("400.0004")) {
            		return new DatosCompraInvalidosException("El formato del cvv  no es válido");
            	} if(bodyContent.contains("400.0005")) {
            		return new DatosCompraInvalidosException("El mes (caducidad) no es correcto ");
            	} if(bodyContent.contains("400.0006")) {
            		return new DatosCompraInvalidosException("El año (caducidad) no es correcto");
            	} if(bodyContent.contains("400.0007")) {
            		return new DatosCompraInvalidosException("La fecha de caducidad debe ser posterior al día actual");
            	} if(bodyContent.contains("400.0008")) {
            		return new DatosCompraInvalidosException("El formato del nombre no es correcto");
            	}
            case 500:
            	return new BancoNoDisponibleException("El sistema se encuentra inestable ");
            case 503: 
            	return new HttpServerErrorException(HttpStatus.valueOf(response.status()), "El servidor User no esta disponible en estos momentos, por favor, intentelo más tarde");
            default:
                return defaultDecoder.decode(methodKey, response);
        }
    }
    
    private String decodeInputStreamBody(Response.Body body) throws IOException {
        if (body == null) {
            return null;
        }

        try (InputStream inputStream = body.asInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            return responseBuilder.toString();
        }
    }
    
    private String comprobadorCodigoError400(Response.Body body) {
    	try {
			return decodeInputStreamBody(body);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "nada";
    }

}
