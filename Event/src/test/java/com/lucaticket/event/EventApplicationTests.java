package com.lucaticket.event;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EventApplicationTests {

	@Autowired
	private DataSource dataSource;

	// @Olivord
	@Test
	void configureDatabaseConnection_shouldSucceed() {
		// Verifica que el DataSource no sea nulo
		assertNotNull(dataSource, "La conexión a la base de datos debería estar configurada correctamente.");

		try (Connection connection = dataSource.getConnection()) {
			// Verifica que la conexión se establece correctamente
			assertNotNull(connection, "La conexión con la base de datos debería haberse establecido.");

			// Extrae metadatos para verificar que la conexión apunta a la base de datos
			// correcta
			DatabaseMetaData metaData = connection.getMetaData();
			String databaseUrl = metaData.getURL();
			String databaseUser = metaData.getUserName();

			// Valida que el URL y usuario coincidan con la configuración proporcionada
			assertEquals(
					"jdbc:mysql://junction.proxy.rlwy.net:25537/lucatickect?useSSL=false&serverTimezone=UTC",
					databaseUrl,
					"El URL de la base de datos debería coincidir con la configuración proporcionada.");
			assertEquals(
					"root",
					databaseUser,
					"El usuario de la base de datos debería coincidir con la configuración proporcionada.");
		} catch (Exception e) {
			throw new AssertionError("No se pudo conectar con la base de datos.", e);
		}
	}

}
