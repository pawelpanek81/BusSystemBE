package pl.bussystem.domain.user.service;

import pl.bussystem.domain.user.persistence.entity.AccountEntity;

import java.security.Principal;

public interface AccountService {
  AccountEntity create(AccountEntity accountEntity);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean isUsernameAndEmailAvailable(String username, String email);

  AccountEntity findAccountByPrincipal(Principal principal);

  AccountEntity getUserByUsername(String username);

  AccountEntity findById(Integer id);

  AccountEntity updateAccount(AccountEntity accountEntity);

  String getUserType(String username);

}
