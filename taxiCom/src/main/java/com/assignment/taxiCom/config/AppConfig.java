package com.assignment.taxiCom.config;


import com.assignment.taxiCom.entity.Invoice;
import com.assignment.taxiCom.entity.Customer;
import com.assignment.taxiCom.entity.Booking;
import com.assignment.taxiCom.entity.Car;
import com.assignment.taxiCom.entity.Driver;
import com.assignment.taxiCom.repository.DriverRepository;
import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Properties;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@Configuration
@EnableTransactionManagement
@EnableWebMvc
@EnableJpaRepositories("com.assignment.taxiCom.repository")
public class AppConfig {

    @Bean
    public Invoice invoice(){
        return new Invoice();
    }
    @Bean
    public Booking booking(){return new Booking();}
    
    @Bean
    public Car car() {return new Car();}

    @Bean
    public Customer customer() {return new Customer();}

    @Bean
    public Driver driver() {return new Driver();}


    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory(){
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");

        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/demo");
        dataSource.setUsername("postgres");
        dataSource.setPassword("minhkhoi");

        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setHibernateProperties(properties);
        sessionFactoryBean.setPackagesToScan("com.assignment.taxiCom.entity");

        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager tx = new HibernateTransactionManager(sessionFactory);

        return tx;
    }

}

