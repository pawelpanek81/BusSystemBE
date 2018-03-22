package pl.bussystem.bussystem.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bussystem.bussystem.domain.entity.AccountEntity;
import pl.bussystem.bussystem.domain.entity.AuthorityEntity;
import pl.bussystem.bussystem.domain.exceptions.AmbiguousRolesException;
import pl.bussystem.bussystem.domain.repository.AccountRepository;
import pl.bussystem.bussystem.domain.repository.AuthorityRepository;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
  private AccountRepository accountRepository;
  private AuthorityRepository authorityRepository;


  @Autowired
  public AccountServiceImpl(AccountRepository accountRepository, AuthorityRepository authorityRepository) {
    this.accountRepository = accountRepository;
    this.authorityRepository = authorityRepository;
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

  public String getUserType(String username) {
    List<AuthorityEntity> authorities = authorityRepository.findByAccountUsername(username);
    if (authorities.size() != 1)
      throw new AmbiguousRolesException("User should have exactly one role!");
    return authorities.get(0).getAuthority().substring(5);
  }
}
