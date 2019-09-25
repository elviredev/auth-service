package intra.poleemploi.service;

import intra.poleemploi.entities.AppRole;
import intra.poleemploi.entities.AppUser;

public interface AccountService {
    // enregistrer un utilisateur
    public AppUser saveUser(String username, String password, String confirmedPassword);
    // enregistrer un role
    public AppRole saveRole(AppRole role);
    // charger un utilisateur via son username
    public AppUser loadUserByUsername(String username);
    // ajouter un role à un utilisateur
    public void addRoleToUser(String username, String roleName);
}
