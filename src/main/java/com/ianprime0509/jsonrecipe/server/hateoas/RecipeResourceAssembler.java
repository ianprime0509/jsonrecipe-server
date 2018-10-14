package com.ianprime0509.jsonrecipe.server.hateoas;

import com.ianprime0509.jsonrecipe.server.controllers.RecipeController;
import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class RecipeResourceAssembler extends ResourceAssemblerSupport<Recipe, Resource<Recipe>> {
  @SuppressWarnings("unchecked")
  public RecipeResourceAssembler() {
    super(RecipeController.class, (Class<Resource<Recipe>>) (Class<?>) Resource.class);
  }

  @Override
  public Resource<Recipe> toResource(Recipe entity) {
    return createResourceWithId(entity.getId(), entity);
  }
}
