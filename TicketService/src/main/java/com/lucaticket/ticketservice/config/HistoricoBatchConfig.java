package com.lucaticket.ticketservice.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.lucaticket.ticketservice.model.Historico;
import com.lucaticket.ticketservice.model.Ticket;

@Configuration
public class HistoricoBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final JdbcTemplate jdbcTemplate;

    public HistoricoBatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            JdbcTemplate jdbcTemplate) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public Step processTicketsStep(ItemReader<List<Ticket>> reader,
            ItemProcessor<List<Ticket>, Historico> processor,
            ItemWriter<Historico> writer) {
        return new StepBuilder("processTicketsStep", jobRepository)
                .<List<Ticket>, Historico>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job processTicketsJob(Step processTicketsStep) {
        return new JobBuilder("processTicketsJob", jobRepository)
                // .start(cleanTableStep(jobRepository, transactionManager))
                .start(processTicketsStep) // Luego procesa los tickets
                .build();
    }

    @Bean
    public Step cleanTableStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("cleanTableStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    // Lógica para limpiar la tabla
                    jdbcTemplate.execute("TRUNCATE TABLE historico");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
