package com.lucaticket.compraservice.error;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Raul, Angel, Alberto, Yuji
 */
@Component
@Slf4j
public class CustomErrorAttributes extends DefaultErrorAttributes {

//	<-- DATOS --> 
	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	/**
	 * @author Raul
	 * Sobreescribimos el método de error de atributos, formateando la fecha, 
	 * quitando la traza, añadiendo un mensaje con la version de java utilizada y
	 * un mensaje informando del error
	 */
	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		log.info("------ getErrorAttributes(): " + options);
		Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
		log.info("------ getErrorAttributes(): " + options);		

		Object timestamp = errorAttributes.get("timestamp");
		if (timestamp == null) {
			errorAttributes.put("timestamp", dateFormat.format(new Date()));
		} else {
			errorAttributes.put("timestamp", dateFormat.format((Date) timestamp));
		}

		errorAttributes.remove("trace");
		errorAttributes.put("jdk", System.getProperty("java.version"));

		return errorAttributes;
	}

}

