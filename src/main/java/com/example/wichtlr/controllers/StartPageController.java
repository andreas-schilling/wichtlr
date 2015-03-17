package com.example.wichtlr.controllers;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class StartPageController
{
  public StartPageController()
  {
    super();
  }
  
  @RequestMapping(value="/")
  public String index( final Locale locale, final Model model )
  {
    return "start";
  }

  @RequestMapping(value="/start", method = RequestMethod.GET)
  public String start( final Locale locale, final Model model )
  {
    return "start";
  }
}
