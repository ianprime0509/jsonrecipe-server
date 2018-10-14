package com.ianprime0509.jsonrecipe.server.controllers;

import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import com.ianprime0509.jsonrecipe.server.services.RecipeResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
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

  private final RecipeResourceService service;

  @GetMapping(produces = HAL_JSON)
  public PagedResources<Resource<Recipe>> findAll(Pageable pageable) {
    return service.findAll(pageable);
  }

  @GetMapping(path = "/{id}", produces = HAL_JSON)
  public ResponseEntity<Resource<Recipe>> findById(@PathVariable String id) {
    return service.findById(id) //
        .map(ResponseEntity::ok) //
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping(consumes = JSON, produces = HAL_JSON)
  public ResponseEntity<Resource<Recipe>> add(@RequestBody Recipe recipe) {
    Resource<Recipe> saved = service.save(recipe);
    Link selfLink = saved.getLink(Link.REL_SELF);
    return ResponseEntity.created(selfLink.getTemplate().expand()).body(saved);
  }
}
