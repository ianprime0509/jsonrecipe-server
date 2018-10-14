package com.ianprime0509.jsonrecipe.server.hateoas;

import com.ianprime0509.jsonrecipe.server.controllers.RecipeController;
import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.stereotype.Service;

@Service
public class RecipeResourceAssembler implements ResourceAssembler<Recipe, Resource<Recipe>> {
  @Override
  public Resource<Recipe> toResource(Recipe entity) {
    Link selfLink = linkTo(methodOn(RecipeController.class).findById(entity.getId())).withSelfRel();
    return new Resource<>(entity, selfLink);
  }
}
