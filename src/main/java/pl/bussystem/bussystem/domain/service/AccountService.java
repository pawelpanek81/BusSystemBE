package pl.bussystem.bussystem.domain.service;

import pl.bussystem.bussystem.domain.entity.AccountEntity;

public interface AccountService {
  AccountEntity create(AccountEntity accountEntity);
  Boolean isUsernameFree(String username);
  Boolean isEmailFree(String email);
}
