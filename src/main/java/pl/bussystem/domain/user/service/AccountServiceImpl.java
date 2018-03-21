package pl.bussystem.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.domain.user.persistence.repository.AccountRepository;

import java.security.Principal;

@Service
class AccountServiceImpl implements AccountService {
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

  @Override
  public AccountEntity findAccountByPrincipal(Principal principal) {
    if (principal == null) {
      throw new NullPointerException("Cannot find user, because principal == null (user is not logged in)");
    }
    return accountRepository.findByUsername(principal.getName());
  }


}
