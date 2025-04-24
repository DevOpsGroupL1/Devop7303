package com.boltonuni.devop7303;

import com.boltonuni.devop7303.configs.CorsConfig;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Devop7303Application {

	public static void main(String[] args) {
		SpringApplication.run(Devop7303Application.class, args);
	}

	@Bean
	public FilterRegistrationBean filterRegistration(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean((Filter) new CorsConfig(), new ServletRegistrationBean[0]);
		filterRegistrationBean.setName("Cors Config Reg");
//		filterRegistrationBean.setFilter(new CorsConfig());
		filterRegistrationBean.addUrlPatterns(new String[]{"/*"});
		filterRegistrationBean.setOrder(0);
		return filterRegistrationBean;
	}

}
