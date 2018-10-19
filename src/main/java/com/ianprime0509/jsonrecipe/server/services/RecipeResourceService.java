package com.ianprime0509.jsonrecipe.server.services;

import com.ianprime0509.jsonrecipe.server.data.RecipeDto;
import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import com.ianprime0509.jsonrecipe.server.hateoas.RecipeResourceAssembler;
import com.ianprime0509.jsonrecipe.server.repositories.RecipeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

/** A service for retrieving recipe data in the form of a resource. */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RecipeResourceService {
  private final RecipeRepository repository;
  private final RecipeResourceAssembler resourceAssembler;
  private final PagedResourcesAssembler<RecipeDto> pagedResourcesAssembler;

  /**
   * Finds all recipes in the database.
   *
   * @param pageable a {@link Pageable} object, containing directions for paging and sorting
   * @return the recipes on the requested page of results
   */
  public PagedResources<Resource<Recipe>> findAll(Pageable pageable) {
    return pagedResourcesAssembler.toResource(repository.findAll(pageable), resourceAssembler);
  }

  /**
   * Finds a single recipe by ID.
   *
   * @param id the ID of the recipe to find
   * @return the recipe with the given ID
   */
  public Optional<Resource<Recipe>> findById(String id) {
    return repository.findById(id).map(resourceAssembler::toResource);
  }

  /**
   * Adds a new recipe to the database.
   *
   * @param recipe the recipe to add
   * @return the recipe that was added
   */
  public Resource<Recipe> add(Recipe recipe) {
    return resourceAssembler.toResource(repository.save(new RecipeDto(recipe)));
  }

  /**
   * Replaces an existing recipe in the database.
   *
   * @param id the ID of the recipe to replace
   * @param recipe the recipe to save with the given ID
   * @return the recipe that was saved
   */
  public Resource<Recipe> save(String id, Recipe recipe) {
    return resourceAssembler.toResource(repository.save(new RecipeDto(id, recipe)));
  }

  /**
   * Deletes a recipe by its ID.
   *
   * @param id the ID of the recipe to delete
   */
  public void delete(String id) {
    repository.deleteById(id);
  }
}
