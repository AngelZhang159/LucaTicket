package com.lucaticket.ticketservice.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job processTicketsJob;

    @Scheduled(fixedRate = 20000) // Ejecuta cada 60 segundos
    public void scheduleJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // Parámetro único basado en el tiempo
                    .addString("uniqueId", UUID.randomUUID().toString()) // Identificador único
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(processTicketsJob, params);
            System.out.println("Job Status: " + execution.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Job failed: " + e.getMessage());
        }
    }
}
