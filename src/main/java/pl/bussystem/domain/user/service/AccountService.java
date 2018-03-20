package pl.bussystem.domain.user.service;

import pl.bussystem.domain.user.persistence.entity.AccountEntity;

public interface AccountService {
  AccountEntity create(AccountEntity accountEntity);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean isUsernameAndEmailAvailable(String username, String email);
}
