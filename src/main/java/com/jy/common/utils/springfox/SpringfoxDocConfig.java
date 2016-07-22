package com.jy.common.utils.springfox;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by DCLab on 12/17/2015.
 */
@Configuration
@EnableWebMvc //NOTE: Only needed in a non-springboot application
@EnableSwagger2 //Enable swagger 2.0 spec
@ComponentScan("com.jy.controller")
public class SpringfoxDocConfig {

  @Bean
  public Docket petApi() {
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName("full-petstore")
            .apiInfo(apiInfo())
            .forCodeGeneration(true)
            .select()
            .paths(petstorePaths())
            .build();
  }

  private Predicate<String> petstorePaths() {
    return or(
            regex("/api/pet.*"),
            regex("/api/user.*"),
            regex("/api/store.*")
    );
  }

  @Bean
  public Docket adminApi() {
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName("admins")
            .apiInfo(apiInfo())
            .forCodeGeneration(true)
            .select()
            .paths(regex("/admins.*"))
            .build();
  }


  @Bean
  public Docket userApi() {
    AuthorizationScope[] authScopes = new AuthorizationScope[1];
    authScopes[0] = new AuthorizationScopeBuilder()
            .scope("read")
            .description("read access")
            .build();
    SecurityReference securityReference = SecurityReference.builder()
            .reference("test")
            .scopes(authScopes)
            .build();

    ArrayList<SecurityContext> securityContexts = newArrayList(SecurityContext.builder().securityReferences
            (newArrayList(securityReference)).build());
    return new Docket(DocumentationType.SWAGGER_2)
            .securitySchemes(newArrayList(new BasicAuth("test")))
            .securityContexts(securityContexts)
            .groupName("user")
            .apiInfo(apiInfo())
            .select()
            .paths(userOnlyEndpoints())
            .build();
  }

  private Predicate<String> userOnlyEndpoints() {
    return new Predicate<String>() {
      @Override
      public boolean apply(String input) {
        return input.contains("user");
      }
    };
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
            .title("Springfox REST API")
            .description("Descriptions.")
            .termsOfServiceUrl("http://springfox.io")
            .contact("springfox")
            .license("Apache License Version 2.0")
            .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
            .version("2.0")
            .build();
  }

  @Bean
  public Docket configSpringfoxDocket_all() {
    return new Docket(DocumentationType.SWAGGER_2)
            .produces(Sets.newHashSet("application/json"))
            .consumes(Sets.newHashSet("application/json"))
            .protocols(Sets.newHashSet("http", "https"))
            .forCodeGeneration(true)
            .select().paths(regex(".*"))
            .build();
  }

  @Bean
  public Docket configSpringfoxDocket_foo() {
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName("foo")
            .produces(Sets.newHashSet("application/json"))
            .consumes(Sets.newHashSet("application/json"))
            .protocols(Sets.newHashSet("http", "https"))
            .forCodeGeneration(true)
            .select().paths(regex(".*foo.*"))
            .build();
  }
}