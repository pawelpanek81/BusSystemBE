package pl.bussystem.domain.user.rest;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.domain.user.service.AccountService;
import pl.bussystem.domain.user.model.dto.CheckUsernameFreeDTO;
import pl.bussystem.domain.user.model.dto.UserRegisterDTO;
import pl.bussystem.domain.user.model.dto.CheckEmailFreeDTO;
import pl.bussystem.rest.exception.ExceptionCodes;
import pl.bussystem.rest.exception.RestException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
class AccountController {
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
  public ResponseEntity<RestException> signUp(@RequestBody @Valid UserRegisterDTO account,
                                              HttpServletRequest request) {

    if (!accountService.isUsernameAndEmailAvailable(account.getUsername(), account.getEmail())) {
      RestException restException = new RestException(ExceptionCodes.USERNAME_TAKEN_OR_EMAIL_ALREADY_USED,
          "Username is taken or email is already used");
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
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

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/check-username-free")
  public ResponseEntity<RestException> checkUsername(@RequestBody @Valid CheckUsernameFreeDTO usernameDTO) {
    if (!accountService.existsByUsername(usernameDTO.getUsername())) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      RestException restException = new RestException(ExceptionCodes.USERNAME_TAKEN, "Username is taken");
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    }
  }


  @PostMapping("/check-email-free")
  public ResponseEntity<RestException> checkEmail(@RequestBody @Valid CheckEmailFreeDTO emailDTO) {
    if (!accountService.existsByEmail(emailDTO.getEmail())) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      RestException restException = new RestException(ExceptionCodes.EMAIL_ALREADY_USED, "Email is already used");
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    }
  }
}