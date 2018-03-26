package pl.bussystem.domain.user.rest;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.bussystem.domain.user.mapper.UserMapper;
import pl.bussystem.domain.user.model.dto.CreateUserDTO;
import pl.bussystem.domain.user.model.dto.ReadUserDTO;
import pl.bussystem.domain.user.model.dto.UpdateUserDTO;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.domain.user.service.AccountService;
import pl.bussystem.rest.exception.RestException;
import pl.bussystem.rest.exception.RestExceptionCodes;

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

  @PostMapping("/")
  ResponseEntity<RestException> signUp(@RequestBody @Valid CreateUserDTO account,
                                       HttpServletRequest request) {

    if (!accountService.isUsernameAndEmailAvailable(account.getUsername(), account.getEmail())) {
      RestException restException = new RestException(
          RestExceptionCodes.USERNAME_IS_TAKEN_OR_EMAIL_IS_ALREADY_USED,
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
//    eventPublisher.publishEvent(new RegistrationCompleteEvent(
//        accountEntity, request.getLocale(), resultPath
//    ));

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  @Secured(value = {"ROLE_USER", "ROLE_ADMIN", "ROLE_BOK", "ROLE_DRIVER"})
  ResponseEntity readById(@PathVariable Integer id,
                          Principal principal) {
    AccountEntity accountEntity = accountService.findAccountByPrincipal(principal);

    if (!accountEntity.getId().equals(id)) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    ReadUserDTO readUserDTO = UserMapper.mapToReadUserDTO.apply(accountEntity);

    return new ResponseEntity<>(readUserDTO, HttpStatus.OK);
  }

  @RequestMapping(value = "/availability/username", method = RequestMethod.GET)
  ResponseEntity<RestException> checkUsername(@RequestParam("username") String username) {
    if (!accountService.existsByUsername(username)) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      RestException restException = new RestException(RestExceptionCodes.USERNAME_IS_TAKEN, "Username is taken");
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    }
  }

  @RequestMapping(value = "/availability/email", method = RequestMethod.GET)
  ResponseEntity<RestException> checkEmail(@RequestParam("email") String email) {
    if (!accountService.existsByEmail(email)) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      RestException restException = new RestException(RestExceptionCodes.EMAIL_IS_ALREADY_USED, "Email is already used");
      return new ResponseEntity<>(restException, HttpStatus.CONFLICT);
    }
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
  @Secured(value = {"ROLE_USER", "ROLE_ADMIN", "ROLE_BOK", "ROLE_DRIVER"})
  ResponseEntity updateById(@PathVariable Integer id,
                            Principal principal,
                            @Valid @RequestBody UpdateUserDTO dto) {
    AccountEntity prevAccData = accountService.findAccountByPrincipal(principal);

    if (!prevAccData.getId().equals(id)) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    if (!prevAccData.getUsername().equals(dto.getUsername()) &&
        accountService.existsByUsername(dto.getUsername())) {
      RestException restException = new RestException(RestExceptionCodes.USERNAME_IS_TAKEN,
          "Username is taken");
      return new ResponseEntity<>(restException, HttpStatus.OK);
    }

    AccountEntity newAccData = UserMapper.mapToAccountEntity(prevAccData, dto);
    accountService.updateAccount(newAccData);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
