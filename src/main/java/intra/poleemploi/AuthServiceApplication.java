package intra.poleemploi;

import intra.poleemploi.entities.AppProduct;
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
			accountService.saveRole(new AppRole(null, "USER"));
			accountService.saveRole(new AppRole(null, "ADMIN"));

			// ajout users
			Stream.of("user1", "user2", "user3", "admin").forEach(username -> {
				accountService.saveUser(username, "1234", "1234");
			});

			// ajout products
			Stream.of("Profil de compétences", "MRS Digitale", "MAP Vue DE", "BEN Pe.fr", "AUDE Presta").forEach(productName -> {
				accountService.saveProduct(new AppProduct(null, productName));
			});

			// ajout role ADMIN a l'admin
			accountService.addRoleToUser("admin", "ADMIN");

			// ajout products à user1
			accountService.addProductToUser("user1", "Profil de compétences");
			accountService.addProductToUser("user1", "MAP Vue DE");

			// ajout products à user2
			accountService.addProductToUser("user2", "MRS Digitale");
		};
	}

	// créer BCryptPasswordEncoder au démarrage de l'appli pour injection dans couche Service
	@Bean
	BCryptPasswordEncoder getBCPE(){
		return new BCryptPasswordEncoder();
	}

}
