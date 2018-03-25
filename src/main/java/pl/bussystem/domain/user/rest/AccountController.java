package pl.bussystem.domain.user.rest;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.user.model.dto.*;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.domain.user.service.AccountService;
import pl.bussystem.rest.exception.ExceptionCodes;
import pl.bussystem.rest.exception.RestException;
import pl.bussystem.security.email.verification.async.OnRegistrationCompleteEvent;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1.0/users")
class AccountController {
  private AccountService accountService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private ApplicationEventPublisher eventPublisher;


  AccountController(AccountService accountService,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           ApplicationEventPublisher applicationEventPublisher) {
    this.accountService = accountService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.eventPublisher = applicationEventPublisher;
  }

  @PostMapping("/sign-up")
  ResponseEntity<RestException> signUp(@RequestBody @Valid UserRegisterDTO account,
                                              HttpServletRequest request) {

    if (!accountService.isUsernameAndEmailAvailable(account.getUsername(), account.getEmail())) {
      RestException restException = new RestException(
          ExceptionCodes.USERNAME_TAKEN_OR_EMAIL_ALREADY_USED,
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
  ResponseEntity<RestException> checkUsername(@RequestBody @Valid CheckUsernameFreeDTO usernameDTO) {
    if (!accountService.existsByUsername(usernameDTO.getUsername())) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      RestException restException = new RestException(ExceptionCodes.USERNAME_TAKEN, "Username is taken");
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    }
  }

  @PostMapping("/check-email-free")
  ResponseEntity<RestException> checkEmail(@RequestBody @Valid CheckEmailFreeDTO emailDTO) {
    if (!accountService.existsByEmail(emailDTO.getEmail())) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      RestException restException = new RestException(ExceptionCodes.EMAIL_ALREADY_USED, "Email is already used");
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/userData")
  @Secured(value = { "ROLE_USER", "ROLE_ADMIN", "ROLE_BOK", "ROLE_DRIVER"})
  ResponseEntity getAccountInfo(Principal principal) {
    AccountEntity accountEntity = accountService.findAccountByPrincipal(principal);

    AccountInfoDTO accountInfoDTO = new AccountInfoDTO(
        accountEntity.getId(),
        accountEntity.getUsername(),
        accountEntity.getName(),
        accountEntity.getSurname(),
        accountEntity.getEmail(),
        accountEntity.getPhone(),
        accountEntity.getActive(),
        accountEntity.getPhoto()
    );

    return new ResponseEntity<>(accountInfoDTO, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/updateData")
  @Secured(value = { "ROLE_USER", "ROLE_ADMIN", "ROLE_BOK", "ROLE_DRIVER"})
  ResponseEntity updateAccountInfo(Principal principal,
                                   @RequestBody AccountUpdateDTO accountUpdateDTO) {
    AccountEntity prevAccData = accountService.findAccountByPrincipal(principal);

    if (!prevAccData.getUsername().equals(accountUpdateDTO.getUsername()) &&
        accountService.existsByUsername(accountUpdateDTO.getUsername())) {
      RestException restException = new RestException(ExceptionCodes.USERNAME_TAKEN,
          "Username is taken");
      return new ResponseEntity<>(restException, HttpStatus.OK);

    }

    String newUsername =  accountUpdateDTO.getUsername()  == null ? prevAccData.getUsername() : accountUpdateDTO.getUsername();
    String newName =      accountUpdateDTO.getName()      == null ? prevAccData.getName()     : accountUpdateDTO.getName();
    String newSurName =   accountUpdateDTO.getSurname()   == null ? prevAccData.getSurname()  : accountUpdateDTO.getSurname();
    String newPhone =     accountUpdateDTO.getPhone()     == null ? prevAccData.getPhone()    : accountUpdateDTO.getPhone();
    String newPhoto =     accountUpdateDTO.getPhoto()     == null ? prevAccData.getPhoto()    : accountUpdateDTO.getPhoto();

    AccountEntity newAccData = AccountEntity.builder()
        .id(prevAccData.getId())
        .username(newUsername)
        .name(newName)
        .surname(newSurName)
        .password(prevAccData.getPassword())
        .email(prevAccData.getEmail())
        .phone(newPhone)
        .active(prevAccData.getActive())
        .photo(newPhoto)
        .build();
    accountService.updateAccount(newAccData);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
