package pl.bussystem.domain.user.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.bussystem.domain.user.service.AccountService;
import pl.bussystem.rest.exception.RestException;
import pl.bussystem.rest.exception.RestExceptionCodes;

@RestController
@RequestMapping("api/v1.0/emails")
public class EmailController {
  private AccountService accountService;

  @Autowired
  public EmailController(AccountService accountService) {
    this.accountService = accountService;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  ResponseEntity<RestException> checkEmail(@RequestParam("email") String email) {
    if (!accountService.existsByEmail(email)) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      RestException restException = new RestException(RestExceptionCodes.EMAIL_IS_ALREADY_USED, "Email is already used");
      return new ResponseEntity<>(restException, HttpStatus.NOT_FOUND);
    }
  }
}
