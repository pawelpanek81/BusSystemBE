package pl.bussystem.bussystem.domain.service;

import pl.bussystem.bussystem.domain.entity.AccountEntity;

public interface AccountService {
  AccountEntity create(AccountEntity accountEntity);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean isUsernameAndEmailAvailable(String username, String email);
}
