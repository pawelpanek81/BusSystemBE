package pl.bussystem.bussystem.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.bussystem.bussystem.domain.entity.AuthorityEntity;
import pl.bussystem.bussystem.domain.repository.AccountRepository;
import pl.bussystem.bussystem.domain.repository.AuthorityRepository;

import java.util.List;

public class UserTypeRecognitionService {
  private AccountRepository accountRepository;
  private AuthorityRepository authorityRepository;

  @Autowired
  public UserTypeRecognitionService(AccountRepository accountRepository, AuthorityRepository authorityRepository) {
    this.accountRepository = accountRepository;
    this.authorityRepository = authorityRepository;
  }

  public Boolean isAdmin(String username) {
    return doesHaveAuthority(username, "LogAsAdmin");
  }

  public Boolean isDriver(String username) {
    return doesHaveAuthority(username, "LogAsDriver");
  }

  public Boolean isBOK(String username){
    return doesHaveAuthority(username, "LogAsBOK");
  }

  public Boolean isClient(String username){
    return doesHaveAuthority(username, "LogAsClient");
  }

  private Boolean doesHaveAuthority(String username, String authority) {
    Boolean have = false;
    List<AuthorityEntity> authorities = authorityRepository.findByAccountUsername(username);
    for (AuthorityEntity auth : authorities) {
      if (auth.getAuthority().equals(authority)) {
        have = true;
        break;
      }
    }
    return have;
  }
}
