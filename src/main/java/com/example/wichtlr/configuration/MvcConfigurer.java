package com.example.wichtlr.configuration;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@EnableWebMvc
public class MvcConfigurer extends WebMvcConfigurerAdapter
{

  @Override
  public void addViewControllers( final ViewControllerRegistry registry )
  {
    registry.addViewController( "/" ).setViewName( "start" );
    registry.addViewController( "/participants" ).setViewName( "participants" );
    registry.addViewController( "/constraints" ).setViewName( "constraints" );
    registry.addViewController( "/sessions" ).setViewName( "sessions" );
    registry.addViewController( "/session" ).setViewName( "session" );
  }


  @Override
  public void configureContentNegotiation( final ContentNegotiationConfigurer configurer )
  {
    configurer.favorPathExtension( true ).favorParameter( true ).parameterName( "mediaType" ).ignoreAcceptHeader( true )
            .useJaf( false ).defaultContentType( MediaType.TEXT_HTML ).mediaType( "xml", MediaType.APPLICATION_XML )
            .mediaType( "json", MediaType.APPLICATION_JSON ).mediaType( "html", MediaType.TEXT_HTML );
  }
}
