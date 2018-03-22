package pl.bussystem.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.user.exception.AmbiguousRolesException;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.domain.user.persistence.entity.AuthorityEntity;
import pl.bussystem.domain.user.persistence.repository.AccountRepository;
import pl.bussystem.domain.user.persistence.repository.AuthorityRepository;

import java.security.Principal;
import java.util.List;

@Service
class AccountServiceImpl implements AccountService {
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

  @Override
  public AccountEntity findAccountByPrincipal(Principal principal) {
    if (principal == null) {
      throw new NullPointerException("Cannot find user, because principal == null (user is not logged in)");
    }
    return accountRepository.findByUsername(principal.getName());
  }

  @Override
  public AccountEntity getUserByUsername(String username) {
    return accountRepository.findByUsername(username);
  }

  @Override
  public String getUserType(String username) {
    List<AuthorityEntity> authorities = authorityRepository.findByAccountUsername(username);
    if (authorities.size() != 1)
      throw new AmbiguousRolesException("User should have exactly one role!");
    return authorities.get(0).getAuthority().substring(5);
  }
}
