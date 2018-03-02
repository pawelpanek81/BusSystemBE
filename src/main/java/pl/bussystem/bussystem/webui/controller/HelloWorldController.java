package pl.bussystem.bussystem.webui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.bussystem.bussystem.domain.entity.AccountEntity;

@Controller
public class HelloWorldController {


  @RequestMapping(method = RequestMethod.GET, path = "/")
  @ResponseBody
  public Object helloGet() {
    return new AccountEntity(
        1,
        "panczo12d",
        "Paweł",
        "Panek",
        "5f4dcc3b5aa765d61d8327deb882cf99",
        "panczo12d@gmail.com",
        "795014696",
        Boolean.TRUE,
        "USER",
        null);
  }
}
