package com.ianprime0509.jsonrecipe.server.repositories;

import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, String> {
}
