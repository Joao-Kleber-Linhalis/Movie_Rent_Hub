package com.jk.BackEndLocadora;

import com.jk.BackEndLocadora.service.IniciarAplicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class BackEndLocadoraApplication implements CommandLineRunner {

	@Autowired
	private IniciarAplicacaoService iniciarAplicacaoService;
	public static void main(String[] args) {
		SpringApplication.run(BackEndLocadoraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		iniciarAplicacaoService.IniciarAplicacao();
	}

}
