package com.dn.recipeapp.controllers;

import com.dn.recipeapp.commands.IngredientCommand;
import com.dn.recipeapp.commands.RecipeCommand;
import com.dn.recipeapp.services.IngredientService;
import com.dn.recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class IngredientsControllerTest {

    IngredientsController controller;
    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new IngredientsController(recipeService,ingredientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void listIngredientsTest() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService,times(1)).findCommandById(anyLong());
    }

    @Test
    public void showIngredientTest() throws Exception {
        IngredientCommand command = new IngredientCommand();
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
        verify(ingredientService,times(1)).findByRecipeIdAndIngredientId(anyLong(),anyLong());

    }
}