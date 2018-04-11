package pl.bussystem.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class DefaultController {

  @GetMapping("/")
  public String greeting() {
    return "index";
  }

}