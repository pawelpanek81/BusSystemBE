package pl.bussystem.bussystem.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.bussystem.bussystem.domain.entity.AuthorityEntity;
import pl.bussystem.bussystem.domain.repository.AccountRepository;
import pl.bussystem.bussystem.domain.repository.AuthorityRepository;
import pl.bussystem.bussystem.webui.dto.exception.ExceptionCodes;

import java.util.List;

public class UserTypeRecognitionService {

  // useless class since we have:
  //   @PreAuthorize("hasAuthority('read:contacts')")
  private AuthorityRepository authorityRepository;

  @Autowired
  public UserTypeRecognitionService(AccountRepository accountRepository, AuthorityRepository authorityRepository) {
    this.authorityRepository = authorityRepository;
  }


  public String getUserType(String username) throws Exception {
    String type;

    // it would be better to have user account types as constants
    if (isAdmin(username)) {
      type = "Admin";
    } else if (isBOK(username)) {
      type = "BOK";
    } else if (isDriver(username)) {
      type = "Driver";
    } else if (isClient(username)) {
      type = "Client";
    } else {
      throw new Exception("user does not have type!");
    }

    return type;
  }

  public Boolean isAdmin(String username) {
    return doesHaveAuthority(username, "LogAsAdmin");
  }

  public Boolean isDriver(String username) {
    return doesHaveAuthority(username, "LogAsDriver");
  }

  public Boolean isBOK(String username) {
    return doesHaveAuthority(username, "LogAsBOK");
  }

  public Boolean isClient(String username) {
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
