package pl.bussystem.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.user.exception.AmbiguousRolesException;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.domain.user.persistence.entity.AuthorityEntity;
import pl.bussystem.domain.user.persistence.repository.AccountRepository;
import pl.bussystem.domain.user.persistence.repository.AuthorityRepository;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
    AccountEntity account = accountRepository.save(accountEntity);
    AuthorityEntity authority = AuthorityEntity.builder()
        .account(account)
        .authority("ROLE_USER")
        .build();
    authorityRepository.save(authority);
    return account;
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
  public AccountEntity readById(Integer id) {
    return accountRepository.findById(id).orElseThrow(bind(UsernameNotFoundException::new, "user with id" + id + " not found"));
  }

  @Override
  public AccountEntity updateAccount(AccountEntity accountEntity) {
    return accountRepository.save(accountEntity);
  }

  @Override
  public Integer getIdByUsername(String username) {
    AccountEntity account = accountRepository.findByUsername(username);
    if (account != null) {
      return account.getId();
    }
    return null;
  }

  private static <T, R> Supplier<R> bind(Function<T, R> fn, T val) {
    return () -> fn.apply(val);
  }

  public String getUserType(String username) {
    List<AuthorityEntity> authorities = authorityRepository.findByAccountUsername(username);
    if (authorities.size() != 1)
      throw new AmbiguousRolesException("User should have exactly one role!");
    return authorities.get(0).getAuthority().substring(5);
  }

  @Override
  public List<AccountEntity> readByUserType(String userType) {
    Map<String, String> rolesMap = new HashMap<String, String>() {
      {
        put("user", "ROLE_USER");
        put("driver", "ROLE_DRIVER");
        put("bok", "ROLE_BOK");
        put("admin", "ROLE_ADMIN");
      }
    };

    userType = rolesMap.get(userType);

    List<AuthorityEntity> authorities = authorityRepository.findByAuthority(userType);
    return authorities.stream()
        .map(AuthorityEntity::getAccount)
        .collect(Collectors.toList());
  }
}
