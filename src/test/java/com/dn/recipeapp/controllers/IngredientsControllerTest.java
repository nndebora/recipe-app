package com.dn.recipeapp.controllers;

import com.dn.recipeapp.commands.IngredientCommand;
import com.dn.recipeapp.commands.RecipeCommand;
import com.dn.recipeapp.commands.UnitOfMeasureCommand;
import com.dn.recipeapp.domain.UnitOfMeasure;
import com.dn.recipeapp.services.IngredientService;
import com.dn.recipeapp.services.RecipeService;
import com.dn.recipeapp.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class IngredientsControllerTest {

    IngredientsController controller;
    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureService uomService;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new IngredientsController(recipeService, ingredientService, uomService);
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

        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void showIngredientTest() throws Exception {
        IngredientCommand command = new IngredientCommand();
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());

    }

    @Test
    public void updateViewTest() throws Exception {
        IngredientCommand command = new IngredientCommand();
        command.setId(2L);
        UnitOfMeasureCommand uom1 = new UnitOfMeasureCommand();
        uom1.setId(1L);
        UnitOfMeasureCommand uom2 = new UnitOfMeasureCommand();
        uom1.setId(2L);
        Set<UnitOfMeasureCommand> uomCommands = new HashSet<>();
        uomCommands.add(uom1);
        uomCommands.add(uom2);
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);
        when(uomService.listUOMs()).thenReturn(uomCommands);
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitOfMeasureList"));
    }

    @Test
    public void postNewIngredientFormTest() throws Exception {
        IngredientCommand command = new IngredientCommand();
        command.setId(2L);
        command.setRecipeId(1L);

        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe/1/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient/2/show"));
    }

    @Test
    public void newIngredientTest() throws Exception {

        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitOfMeasureList"));
    }

    @Test
    public void deleteActionTest() throws Exception {
        mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredients"));

        verify(ingredientService, times(1)).deleteById(anyLong(), anyLong());

    }
}