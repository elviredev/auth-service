package intra.poleemploi.service;

import intra.poleemploi.entities.AppRole;
import intra.poleemploi.entities.AppUser;

public interface AccountService {
    AppUser saveAppUser(String username, String password, String confirmedPassword);
    void saveAppRole(AppRole role);
    AppUser loadAppUserByUsername(String username);
    void addRoleToUser(String username, String roleName);
}
