package pl.bussystem.domain.user.service;

import pl.bussystem.domain.user.persistence.entity.AccountEntity;

import java.security.Principal;
import java.util.List;

public interface AccountService {
  AccountEntity create(AccountEntity accountEntity);

  AccountEntity readById(Integer id);

  String getUserType(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean isUsernameAndEmailAvailable(String username, String email);

  AccountEntity findAccountByPrincipal(Principal principal);

  AccountEntity getUserByUsername(String username);

  AccountEntity updateAccount(AccountEntity accountEntity);

  Integer getIdByUsername(String username);

  List<AccountEntity> readByUserType(String userType);
}
