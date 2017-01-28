/*
 * Demo Project for Fresh Grade Rest API test
 * By: Mark Vejvoda
 */

package com.freshgrade.studentmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
@RestController
public class StudentManagerApplication {

  /**
   * <a href="https://cloud.google.com/appengine/docs/flexible/java/how-instances-are-managed#health_checking">
   * App Engine health checking</a> requires responding with 200 to {@code /_ah/health}.
   */
  @RequestMapping("/_ah/health")
  public String healthy() {
    // Message body required though ignored
    return "Still surviving.";
  }

  public static void main(String[] args) {
    SpringApplication.run(StudentManagerApplication.class, args);
  }
  
  @Bean
  public Docket newsApi() {
      return new Docket(DocumentationType.SWAGGER_2)
              .groupName("student-manager")
              .apiInfo(apiInfo())
              .select()
              .paths(regex("/v1/student-manager.*"))
              .build();
  }
   
  private ApiInfo apiInfo() {
      return new ApiInfoBuilder()
              .title("Spring Boot Student App REST API with Swagger")
              .description("Spring Boot Student App REST API with Swagger")
              //.termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
              .contact("Mark Vejvoda")
              .license("Apache License Version 2.0")
              //.licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
              .version("1.0")
              .build();
  }  
}

