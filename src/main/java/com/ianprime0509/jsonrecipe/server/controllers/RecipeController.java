package com.ianprime0509.jsonrecipe.server.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import com.ianprime0509.jsonrecipe.server.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RecipeController {
  private static final String HAL_JSON = "application/hal+json";
  private static final String JSON = MediaType.APPLICATION_JSON_UTF8_VALUE;

  private final RecipeService service;

  @GetMapping(path = "/{id}", produces = HAL_JSON)
  public ResponseEntity<Recipe> findById(@PathVariable("id") String id) {
    return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping(consumes = JSON, produces = HAL_JSON)
  public ResponseEntity<Recipe> add(@RequestBody Recipe recipe) {
    Recipe saved = service.save(recipe);
    Link selfLink =
        linkTo(methodOn(RecipeController.class).findById(saved.getRecipeId())).withSelfRel();
    saved.add(selfLink);
    return ResponseEntity.created(selfLink.getTemplate().expand()).body(saved);
  }
}
