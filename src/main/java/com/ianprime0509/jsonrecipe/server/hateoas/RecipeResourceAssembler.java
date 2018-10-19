package com.ianprime0509.jsonrecipe.server.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.ianprime0509.jsonrecipe.server.controllers.RecipeController;
import com.ianprime0509.jsonrecipe.server.data.RecipeDto;
import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Service;

@Service
public class RecipeResourceAssembler implements ResourceAssembler<RecipeDto, Resource<Recipe>> {
  @Override
  public Resource<Recipe> toResource(RecipeDto entity) {
    Link selfLink = linkTo(methodOn(RecipeController.class).findById(entity.getId())).withSelfRel();
    return new Resource<>(entity.getContents(), selfLink);
  }
}
