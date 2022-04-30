package com.assignment.taxiCom;

import com.assignment.taxiCom.config.AppConfig;
import com.assignment.taxiCom.entity.Booking;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.time.LocalDateTime;

@SpringBootApplication
public class TaxiComApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaxiComApplication.class, args);
	}
}
