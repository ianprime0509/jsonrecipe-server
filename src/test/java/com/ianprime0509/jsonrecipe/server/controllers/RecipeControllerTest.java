package com.ianprime0509.jsonrecipe.server.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ianprime0509.jsonrecipe.server.data.RecipeDto;
import com.ianprime0509.jsonrecipe.server.entities.Recipe;
import com.ianprime0509.jsonrecipe.server.hateoas.RecipeResourceAssembler;
import com.ianprime0509.jsonrecipe.server.services.RecipeResourceService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(RecipeController.class)
@ComponentScan(basePackages = "com.ianprime0509.jsonrecipe.server.hateoas")
@EnableSpringDataWebSupport
public class RecipeControllerTest {
  @Autowired private MockMvc mvc;

  @Autowired private RecipeResourceAssembler assembler;

  @MockBean private RecipeResourceService service;

  private Recipe dummyRecipe;

  private Recipe dummyRecipe2;

  private Resource<Recipe> dummyRecipeResource;

  private Resource<Recipe> dummyRecipeResource2;

  private String dummyRecipeJson;

  @Before
  public void setUp() {
    dummyRecipe =
        Recipe.builder()
            .title("Dummy recipe")
            .ingredients(new ArrayList<>())
            .directions(new ArrayList<>())
            .build();

    dummyRecipe2 =
        Recipe.builder()
            .title("Dummy recipe 2")
            .ingredients(new ArrayList<>())
            .directions(new ArrayList<>())
            .build();

    dummyRecipeResource = assembler.toResource(new RecipeDto("1", dummyRecipe));
    dummyRecipeResource2 = assembler.toResource(new RecipeDto("2", dummyRecipe2));

    dummyRecipeJson = "{\"title\": \"Dummy recipe\", \"ingredients\": [], \"directions\": []}";
  }

  @Test
  public void testFindAll() throws Exception {
    when(service.findAll(PageRequest.of(0, 5)))
        .thenReturn(
            new PagedResources<>(
                Arrays.asList(dummyRecipeResource, dummyRecipeResource2),
                new PagedResources.PageMetadata(5, 0, 2)));

    mvc.perform(get("/recipes").param("page", "0").param("size", "5"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.recipeList[0].title").value("Dummy recipe"))
        .andExpect(jsonPath("$._embedded.recipeList[1].title").value("Dummy recipe 2"));
  }

  @Test
  public void testFindById() throws Exception {
    when(service.findById("1")).thenReturn(Optional.of(dummyRecipeResource));

    mvc.perform(get("/recipes/{id}", "1"))
        .andExpect(status().isOk())
        .andExpect(content().json(dummyRecipeJson));
  }

  @Test
  public void testFindById_withNonExistentId_isNotFound() throws Exception {
    when(service.findById("3")).thenReturn(Optional.empty());

    mvc.perform(get("/recipes/{id}", "3")).andExpect(status().isNotFound());
  }

  @Test
  public void testAdd() throws Exception {
    final Resource<Recipe> newRecipeResource =
        assembler.toResource(new RecipeDto("3", dummyRecipe));
    final String newRecipeJson = dummyRecipeJson;
    when(service.add(dummyRecipe)).thenReturn(newRecipeResource);

    mvc.perform(
            post("/recipes").contentType(MediaType.APPLICATION_JSON_UTF8).content(newRecipeJson))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", newRecipeResource.getId().getHref()))
        .andExpect(content().json(newRecipeJson));
  }

  @Test
  public void testSave() throws Exception {
    when(service.save("1", dummyRecipe)).thenReturn(dummyRecipeResource);

    mvc.perform(
            put("/recipes/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(dummyRecipeJson))
        .andExpect(status().isOk())
        .andExpect(content().json(dummyRecipeJson));
  }

  @Test
  public void testDelete() throws Exception {
    mvc.perform(delete("/recipes/{id}", "1")).andExpect(status().isNoContent());

    verify(service).delete("1");
  }
}
