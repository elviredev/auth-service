package intra.poleemploi.service;

import intra.poleemploi.entities.AppRole;
import intra.poleemploi.entities.AppUser;
import intra.poleemploi.entities.AppProduct;

public interface AccountService {
    // enregistrer un utilisateur
    public AppUser saveUser(String username, String password, String confirmedPassword);
    // enregistrer un role
    public AppRole saveRole(AppRole role);
    // enregistrer un produit
    public AppProduct saveProduct(AppProduct product);
    // charger un utilisateur via son username
    public AppUser loadUserByUsername(String username);
    // ajouter un role à un utilisateur
    public void addRoleToUser(String username, String roleName);
    // ajouter un produit à un utilisateur
    public void addProductToUser(String username, String productName);
}
