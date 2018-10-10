package com.ianprime0509.jsonrecipe.server.services;

import java.util.Optional;
import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import com.ianprime0509.jsonrecipe.server.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RecipeService {
  private final RecipeRepository repository;

  public Page<Recipe> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Optional<Recipe> findById(String id) {
    return repository.findById(id);
  }

  public Recipe save(Recipe recipe) {
    return repository.save(recipe);
  }
}
