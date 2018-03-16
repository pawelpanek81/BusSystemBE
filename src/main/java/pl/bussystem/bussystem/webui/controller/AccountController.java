package pl.bussystem.bussystem.webui.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bussystem.bussystem.domain.entity.AccountEntity;
import pl.bussystem.bussystem.domain.service.AccountService;
import pl.bussystem.bussystem.security.email.verification.OnRegistrationCompleteEvent;
import pl.bussystem.bussystem.webui.dto.UserRegisterDTO;
import pl.bussystem.bussystem.webui.dto.SingleUserAttributeDTO;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class AccountController {
  private AccountService accountService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private ApplicationEventPublisher eventPublisher;


  public AccountController(AccountService accountService,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           ApplicationEventPublisher applicationEventPublisher) {
    this.accountService = accountService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.eventPublisher = applicationEventPublisher;
  }

  @PostMapping("/sign-up")
  public ResponseEntity signUp(@RequestBody UserRegisterDTO account,
                       HttpServletRequest request) {


    // check if username is free
    // check if email is free
    if (    !accountService.isUsernameFree(account.getUsername()) ||
            !accountService.isEmailFree(account.getEmail())){
      return new ResponseEntity(HttpStatus.CONFLICT);
    }

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

    accountService.create(accountEntity);

//     here is logic to send mail
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String contextPath = request.getContextPath();  // includes leading forward slash

    String resultPath = scheme + "://" + serverName + ":" + serverPort + contextPath;
//    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(
//        accountEntity, request.getLocale(), resultPath
//    ));

    return new ResponseEntity(HttpStatus.OK);
  }

  @PostMapping("/check-username-free")
  public ResponseEntity checkUsername(@RequestBody SingleUserAttributeDTO usernameDTO,
                              HttpServletRequest request){
    if (accountService.isUsernameFree(usernameDTO.getValue())){
      return new ResponseEntity(HttpStatus.OK);
    }else{
      return new ResponseEntity(HttpStatus.CONFLICT);
    }
  }

  @PostMapping("/check-email-free")
  public ResponseEntity checkEmail(@RequestBody SingleUserAttributeDTO emailDTO,
                              HttpServletRequest request){
    if (accountService.isEmailFree(emailDTO.getValue())){
      return new ResponseEntity(HttpStatus.OK);
    }else{
      return new ResponseEntity(HttpStatus.CONFLICT);
    }
  }
}
