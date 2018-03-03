package pl.bussystem.bussystem.webui.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bussystem.bussystem.domain.entity.AccountEntity;
import pl.bussystem.bussystem.domain.repository.AccountRepository;

@RestController
@RequestMapping("/users")
public class AccountController {
  private AccountRepository accountRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public AccountController(AccountRepository accountRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.accountRepository = accountRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @PostMapping("/sign-up")
  public void signUp(@RequestBody AccountEntity account) {
    account.setPassword(
        bCryptPasswordEncoder.encode(account.getPassword())
    );
    accountRepository.save(account);
  }
}
