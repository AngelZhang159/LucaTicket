package com.lucaticket.ticketservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucaticket.ticketservice.model.dto.DetailedTicketResponse;
import com.lucaticket.ticketservice.model.dto.TicketRequest;
import com.lucaticket.ticketservice.model.dto.TicketResponse;
import com.lucaticket.ticketservice.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
@Slf4j
public class TicketController {

	private final TicketService ticketService;

	/**
	 * @author Raul
	 * @param un objeto con los parametros necesarios para guardar un ticket
	 * @return una entidad de respuesta con un codigo http y el objeto ticket que se
	 *         ha guardado
	 */
	@PostMapping
	@Operation(description = "Guarda un ticket en la base de datos a partir de una peticion ticketRequest con todos los datos necesarios")
	ResponseEntity<TicketResponse> save(@RequestBody @Valid TicketRequest ticketRequest ) {
		log.info("Controller: Guardando nuevo ticket: " + ticketRequest.toString());
		return ticketService.save(ticketRequest);
	}

	/**
	 * @author Raul
	 * @return una lista con todos los tickets registrados
	 */
	@GetMapping
	@Operation(description = "Lista todos los tickets guardados en la base de datos")
	public
	ResponseEntity<List<TicketResponse>> listTickets() {
		log.info("Controller: Listando todos los tickets:");
		return ticketService.listTickets();
	}
	
	/**
	 * @author Alberto de la Blanca
	 * obtiene una lista de tickets asociados a un correo.
	 * 
	 * @param mail Correo electronico del usuario.
	 * @return una lista de objetos DetailedTicketResponse
	 */
	
	@GetMapping("/list/mail/{mail}")
	@Operation(description = "Lista todos los tickets guardados en la base de datos filtrando por el email")
	public ResponseEntity<List<DetailedTicketResponse>> getTickets(@PathVariable String mail){
		log.info("Controller: Obteniendo nuevo ticket para el correo: " + mail);
		return ticketService.listTicketsByEmail(mail);
	}


	@Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job processTicketsJob;

    @GetMapping("/start-job")
    public String startJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // Parámetro único basado en el tiempo
                    .addString("uniqueId", UUID.randomUUID().toString()) // Agrega un identificador único
                    .toJobParameters();
            JobExecution execution = jobLauncher.run(processTicketsJob, params);
            return "Job Status: " + execution.getStatus();
        } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobRestartException e) {
            return "Job failed: " + e.getMessage();
        }
    }

}
