package pl.bussystem.security.email.verification.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bussystem.security.email.verification.service.VerificationTokenService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/api/v1.0")
public class VerificationController {
  private VerificationTokenService verificationTokenService;
  private Environment env;

  @Autowired
  public VerificationController(VerificationTokenService verificationTokenService,
                                Environment env) {
    this.verificationTokenService = verificationTokenService;
    this.env = env;
  }

  @RequestMapping(value = "confirmRegistration", method = RequestMethod.GET)
  public ResponseEntity confirmRegistration(@RequestParam("token") final String token,
                                            HttpServletResponse httpServletResponse) {
    String verificationResult = verificationTokenService.validateVerificationToken(token);

    env.getProperty("frontend.url");

    switch (verificationResult) {
      case VerificationTokenService.TOKEN_VALID:
        httpServletResponse.setHeader("Location", "http://localhost:8080/");
        return new ResponseEntity(HttpStatus.FOUND);

      case VerificationTokenService.TOKEN_EXPIRED:
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

      case VerificationTokenService.TOKEN_INVALID:
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

      default:
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

}
