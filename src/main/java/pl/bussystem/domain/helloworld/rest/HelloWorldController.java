package pl.bussystem.domain.helloworld.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.domain.user.persistence.entity.AuthorityEntity;
import pl.bussystem.domain.user.persistence.repository.AccountRepository;
import pl.bussystem.domain.user.persistence.repository.AuthorityRepository;

import java.security.Principal;
import java.util.List;

@Controller
@Deprecated() // should be removed in the future
public class HelloWorldController {
  private AccountRepository accountRepository;
  private AuthorityRepository authorityRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public HelloWorldController(AccountRepository accountRepository,
                              AuthorityRepository authorityRepository,
                              BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.accountRepository = accountRepository;
    this.authorityRepository = authorityRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @RequestMapping(method = RequestMethod.GET, path = "/")
  @ResponseBody
  public Object helloGet() {
    return new AccountEntity(
        1,
        "panczo12d",
        "Paweł",
        "Panek",
        bCryptPasswordEncoder.encode("password"),
        "panczo12d@gmail.com",
        "795014696",
        Boolean.TRUE,
        null);
  }

    @RequestMapping(method = RequestMethod.GET, path = "/l")
    @ResponseBody
    @PreAuthorize("hasAuthority('group:Admin')")
    public String helloL(Principal principal) {
      return principal.getName(); //this return AccountEntity.username
    }

  @RequestMapping(method = RequestMethod.GET, path = "/users")
  @ResponseBody
  public List<AccountEntity> users() {
    return accountRepository.findAll();
  }

  @RequestMapping(method = RequestMethod.GET, path = "/auths")
  @ResponseBody
  public List<AuthorityEntity> auths() {
    return authorityRepository.findAll();
  }

  @RequestMapping(method = RequestMethod.GET, path = "/data")
  @ResponseBody
  public String fillData() {
    AccountEntity accountEntity = new AccountEntity(
        1,
        "panczo12d",
        "Paweł",
        "Panek",
        bCryptPasswordEncoder.encode("password"),
        "panczo12d@gmail.com",
        "795014696",
        Boolean.TRUE,
        null);
    accountRepository.save(accountEntity);

    AccountEntity accountEntity2 = new AccountEntity(
        2,
        "admin",
        "adm",
        "admin",
        bCryptPasswordEncoder.encode("password"),
        "admmin@gmail.com",
        "795014696",
        Boolean.TRUE,
        null);
    accountRepository.save(accountEntity2);
    return "OK";
  }

}
