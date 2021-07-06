package com.bdf;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bdf.service._BootstrapService;

@SpringBootApplication
public class Application {

	@Autowired
	private _BootstrapService apiService;

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void init() {
		apiService.startThread();
	}
}