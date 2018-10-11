package com.ianprime0509.jsonrecipe.server;

import com.ianprime0509.jsonrecipe.server.json.RecipeJacksonModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }

  // By declaring the JSONRecipe Jackson module here as a bean, Spring will register it
  // automatically with the ObjectMapper.
  @Bean
  public RecipeJacksonModule recipeJacksonModule() {
    return new RecipeJacksonModule();
  }
}
