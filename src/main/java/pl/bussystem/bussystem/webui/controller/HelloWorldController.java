package pl.bussystem.bussystem.webui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.bussystem.bussystem.domain.entity.AccountEntity;
import pl.bussystem.bussystem.domain.entity.AuthorityEntity;
import pl.bussystem.bussystem.domain.repository.AccountRepository;
import pl.bussystem.bussystem.domain.repository.AuthorityRepository;

import java.util.List;

@Controller
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
