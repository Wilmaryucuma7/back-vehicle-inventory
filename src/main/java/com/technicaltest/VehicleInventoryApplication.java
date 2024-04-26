package com.technicaltest;


import com.technicaltest.models.UserEntity;
import com.technicaltest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class VehicleInventoryApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRespository;

	public static void main(String[] args) {
		SpringApplication.run(VehicleInventoryApplication.class, args);

	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedHeaders("*").allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH").exposedHeaders("*");
			}
		};
	}

	@Bean
	CommandLineRunner init(){
		return args -> {
			UserEntity userEntity = UserEntity.builder()
					.email("wil@gmail.com")
					.username("wil")
					.password(passwordEncoder.encode("1234"))
					.build();
			try {
				userRespository.save(userEntity);
			} catch (Exception ignored){}
		};
	}

}
