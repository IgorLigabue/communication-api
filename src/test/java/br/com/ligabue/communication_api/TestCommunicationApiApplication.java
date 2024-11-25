package br.com.ligabue.communication_api;

import org.springframework.boot.SpringApplication;

public class TestCommunicationApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
