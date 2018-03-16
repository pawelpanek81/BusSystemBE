package pl.bussystem.bussystem.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bussystem.bussystem.domain.entity.AccountEntity;
import pl.bussystem.bussystem.domain.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
  private AccountRepository accountRepository;

  @Autowired
  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public AccountEntity create(AccountEntity accountEntity) {
    return accountRepository.save(accountEntity);
  }

  @Override
  public Boolean existsByUsername(String username) {
    return accountRepository.existsByUsername(username);
  }

  @Override
  public Boolean existsByEmail(String email) {
    return accountRepository.existsByEmail(email);
  }

  @Override
  public Boolean isUsernameAndEmailAvailable(String username, String email) {
    return (!existsByUsername(username) && !existsByEmail(email));
  }
}
