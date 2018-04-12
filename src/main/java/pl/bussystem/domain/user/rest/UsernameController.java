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
@RequestMapping("api/v1.0/usernames")
public class UsernameController {
  private AccountService accountService;

  @Autowired
  public UsernameController(AccountService accountService) {
    this.accountService = accountService;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  ResponseEntity<RestException> checkUsername(@RequestParam("username") String username) {
    if (!accountService.existsByUsername(username)) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      RestException restException = new RestException(RestExceptionCodes.USERNAME_IS_TAKEN, "Username is taken");
      return new ResponseEntity<>(restException, HttpStatus.OK);
    }
  }
}
