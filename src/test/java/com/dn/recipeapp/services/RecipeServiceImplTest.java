package com.dn.recipeapp.services;

import com.dn.recipeapp.domain.Recipe;
import com.dn.recipeapp.repositories.RecipeRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;
    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listRecipesTest() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeService.listRecipes()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.listRecipes();
        assertEquals(recipesData,recipes);
        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    public void getRecipesBtIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);
        assertNotNull("Null recipe returned",recipeReturned);
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,never()).findAll();
    }
}