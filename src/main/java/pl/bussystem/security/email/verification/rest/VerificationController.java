package pl.bussystem.security.email.verification.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bussystem.security.email.verification.service.VerificationTokenService;

@Controller
@RequestMapping(value = "/api/v1.0")
public class VerificationController {
  private VerificationTokenService verificationTokenService;

  @Autowired
  public VerificationController(VerificationTokenService verificationTokenService) {
    this.verificationTokenService = verificationTokenService;
  }

  @RequestMapping(value = "confirmRegistration", method = RequestMethod.GET)
  public ResponseEntity confirmRegistration(@RequestParam("token") final String token) {
    String verificationResult = verificationTokenService.validateVerificationToken(token);
    switch (verificationResult) {
      case VerificationTokenService.TOKEN_VALID:
        return new ResponseEntity(HttpStatus.OK);

      case VerificationTokenService.TOKEN_EXPIRED:
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

      case VerificationTokenService.TOKEN_INVALID:
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

      default:
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

}
