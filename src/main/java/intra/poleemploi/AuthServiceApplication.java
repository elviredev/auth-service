package intra.poleemploi;

import intra.poleemploi.entities.AppRole;
import intra.poleemploi.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(AccountService accountService){
		return args -> {
			// ajout de 2 roles
			accountService.saveAppRole(new AppRole(null, "USER"));
			accountService.saveAppRole(new AppRole(null, "ADMIN"));

			// ajout users
			Stream.of("user1", "user2", "user3", "admin").forEach(username -> {
				accountService.saveAppUser(username, "1234", "1234");
			});

			// ajout role ADMIN a l'admin
			accountService.addRoleToUser("admin", "ADMIN");


		};
	}

	// créer BCryptPasswordEncoder au démarrage de l'appli pour injection dans couche Service
	@Bean
	BCryptPasswordEncoder getBCPE(){
		return new BCryptPasswordEncoder();
	}

}
