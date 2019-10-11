package intra.poleemploi.web;

import intra.poleemploi.dao.AppUserRepository;
import intra.poleemploi.entities.AppUser;
import intra.poleemploi.service.AccountService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping("/register")
    public AppUser register(@RequestBody UserForm userForm){ // données envoyées au format JSON
        return accountService.saveAppUser(userForm.getUsername(), userForm.getPassword(), userForm.getConfirmedPassword());
    }

    @GetMapping(value = "/profile/{username}")
    public AppUser appUserByUsername(@PathVariable(name="username") String username){
        return appUserRepository.findUserByUsername(username);
    };


}
@Data
class UserForm {
    private String username;
    private String password;
    private String confirmedPassword;
}