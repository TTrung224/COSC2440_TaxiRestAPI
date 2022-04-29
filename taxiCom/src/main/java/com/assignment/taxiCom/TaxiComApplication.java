package com.assignment.taxiCom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class TaxiComApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiComApplication.class, args);
	}

}
