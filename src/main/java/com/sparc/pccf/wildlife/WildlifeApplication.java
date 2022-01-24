package com.sparc.pccf.wildlife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

import com.sparc.pccf.wildlife.uploadImage.property.FileStorageProperties;

@SpringBootApplication
@ComponentScan(basePackages="com.sparc.pccf.wildlife.*")
@EnableJpaRepositories(basePackages="com.sparc.pccf.wildlife.*")
@EntityScan(basePackages="com.sparc.pccf.wildlife.*")
@RestController
public class WildlifeApplication extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(WildlifeApplication.class, args);
	}
}
