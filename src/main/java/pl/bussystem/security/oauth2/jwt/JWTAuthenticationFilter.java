package pl.bussystem.security.oauth2.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.bussystem.domain.user.persistence.entity.AccountEntity;
import pl.bussystem.domain.user.service.AccountService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static pl.bussystem.security.oauth2.jwt.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;
  private AccountService accountService;

  JWTAuthenticationFilter(AuthenticationManager authenticationManager, AccountService accountService) {
    this.authenticationManager = authenticationManager;
    this.accountService = accountService;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req,
                                              HttpServletResponse res) throws AuthenticationException {
    try {
      AccountEntity creds = new ObjectMapper()
          .readValue(req.getInputStream(), AccountEntity.class);

      Authentication authenticate = null;
      try {
        authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                creds.getUsername(),
                creds.getPassword(),
                new ArrayList<>()
            )
        );
      } catch (BadCredentialsException e) {
        res.setStatus(HttpStatus.NOT_FOUND.value());
      }

      return authenticate;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req,
                                          HttpServletResponse res,
                                          FilterChain chain,
                                          Authentication auth) throws IOException, ServletException {

    String username = ((User) auth.getPrincipal()).getUsername();
    String token = Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
        .claim("ut", accountService.getUserType(username))
        .claim("id", accountService.getIdByUsername(username))
        .compact();
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
  }
}
