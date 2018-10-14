package com.ianprime0509.jsonrecipe.server.services;

import java.util.Optional;
import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import com.ianprime0509.jsonrecipe.server.hateoas.RecipeResourceAssembler;
import com.ianprime0509.jsonrecipe.server.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RecipeResourceService {
  private final RecipeRepository repository;
  private final RecipeResourceAssembler resourceAssembler;
  private final PagedResourcesAssembler<Recipe> pagedResourcesAssembler;

  public PagedResources<Resource<Recipe>> findAll(Pageable pageable) {
    return pagedResourcesAssembler.toResource(repository.findAll(pageable), resourceAssembler);
  }

  public Optional<Resource<Recipe>> findById(String id) {
    return repository.findById(id).map(resourceAssembler::toResource);
  }

  public Resource<Recipe> save(Recipe recipe) {
    return resourceAssembler.toResource(repository.save(recipe));
  }
}
