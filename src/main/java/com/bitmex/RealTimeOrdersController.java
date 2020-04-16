package com.bitmex;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RealTimeOrdersController {

  @GetMapping
  public ModelAndView orders() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("orders");
    return modelAndView;
  }

}
