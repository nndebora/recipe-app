package com.dn.recipeapp.services;

import com.dn.recipeapp.domain.Recipe;
import com.dn.recipeapp.repositories.RecipeRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void listRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeService.listRecipes()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.listRecipes();
        assertEquals(recipesData,recipes);
        verify(recipeRepository,times(1)).findAll();
    }
}