package intra.poleemploi.service;

import intra.poleemploi.dao.AppRoleRepository;
import intra.poleemploi.dao.AppUserRepository;
import intra.poleemploi.entities.AppRole;
import intra.poleemploi.entities.AppUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // injection dependances via constructor
    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // Méthode saveUser()
    @Override
    public AppUser saveUser(String username, String password, String confirmedPassword) {
        AppUser user = appUserRepository.findUserByUsername(username);
        if(user != null) throw new RuntimeException("User already exist !");
        if(password != confirmedPassword) throw new RuntimeException("Please confirm your password !");
        // si user n'existe pas => on le créé
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        // cryptage du pwd
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        // save in the BDD
        appUserRepository.save(appUser);
        // user créé donc compte activé
        appUser.setActivated(true);
        // attribue role par défaut à user
        addRoleToUser(username, "USER");
        return appUser;
    }
    // Méthode saveRole()
    @Override
    public AppRole saveRole(AppRole role) {
        return appRoleRepository.save(role);
    }
    // Méthode loadUserByUsername()
    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findUserByUsername(username);
    }
    // Méthode addRoleToUser()
    @Override
    public void addRoleToUser(String username, String roleName) {
        // récupère user et role
        AppUser appUser = appUserRepository.findUserByUsername(username);
        AppRole appRole = appRoleRepository.findRoleByRoleName(roleName);
        // ajout role à user
        appUser.getRoles().add(appRole);
    }
}
