package pl.bussystem.bussystem.webui.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import pl.bussystem.bussystem.domain.entity.AccountEntity;
import pl.bussystem.bussystem.domain.repository.AccountRepository;
import pl.bussystem.bussystem.security.email.verification.OnRegistrationCompleteEvent;
import pl.bussystem.bussystem.webui.dto.UserRegisterDTO;

@RestController
@RequestMapping("/users")
public class AccountController {
  private AccountRepository accountRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private ApplicationEventPublisher eventPublisher;


  public AccountController(AccountRepository accountRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           ApplicationEventPublisher applicationEventPublisher) {
    this.accountRepository = accountRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.eventPublisher = applicationEventPublisher;
  }

  @PostMapping("/sign-up")
  public ResponseEntity signUp(@RequestBody UserRegisterDTO account,
                       WebRequest request) {
    account.setPassword(
        bCryptPasswordEncoder.encode(account.getPassword())
    );

    AccountEntity accountEntity = new AccountEntity(
        null,
        account.getUsername(),
        account.getName(),
        account.getSurname(),
        account.getPassword(),
        account.getEmail(),
        account.getPhone(),
        true,
        null
    );

    // here is logic to send mail
//    String appUrl = request.getContextPath();
//    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(
//        accountEntity, request.getLocale(), appUrl
//    ));


    accountRepository.save(accountEntity);
    return new ResponseEntity(HttpStatus.OK);
  }
}
