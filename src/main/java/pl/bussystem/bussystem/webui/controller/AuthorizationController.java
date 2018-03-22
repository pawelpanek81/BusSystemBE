package pl.bussystem.bussystem.webui.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

  @RequestMapping(method = RequestMethod.GET, path = "/a")
  @ResponseBody
  @PreAuthorize("hasAuthority('group:Admin')")
  public String isAdmin(Principal principal) {
    return principal.getName();
  }

  @RequestMapping(method = RequestMethod.GET, path = "/b")
  @ResponseBody
  @PreAuthorize("hasAuthority('group:BOK')")
  public String isBok(Principal principal) {
    return principal.getName();
  }

  @RequestMapping(method = RequestMethod.GET, path = "/d")
  @ResponseBody
  @PreAuthorize("hasAuthority('group:Driver')")
  public String isDriver(Principal principal) {
    return principal.getName();
  }

  @RequestMapping(method = RequestMethod.GET, path = "/u")
  @ResponseBody
  @PreAuthorize("hasAuthority('group:User')")
  public String isUser(Principal principal) {
    return principal.getName();
  }

}
