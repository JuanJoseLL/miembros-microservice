package com.gym.members_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.gym.members_microservice.model.Miembro;
import com.gym.members_microservice.service.MiembroService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner seedData(MiembroService miembroService) {
		return args -> {
			if (miembroService.findAll().size() >= 4) {
				return;
			}

			miembroService.save(Miembro.builder().nombre("Ana Perez").email("ana@example.com").build());
			miembroService.save(Miembro.builder().nombre("Luis Gomez").email("luis@example.com").build());
			miembroService.save(Miembro.builder().nombre("Maria Lopez").email("maria@example.com").build());
			miembroService.save(Miembro.builder().nombre("Carlos Diaz").email("carlos@example.com").build());
		};
	}
}
