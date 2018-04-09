package com.dn.recipeapp.controllers;

import com.dn.recipeapp.domain.Recipe;
import com.dn.recipeapp.services.RecipeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {


    IndexController indexController;
    @Mock
    RecipeService recipeService;
    @Mock
    Model model;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testMockMvc() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }



    @Test
    public void getIndexPage() {

        //given
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(new Recipe());
        Recipe recipe =new Recipe();
        recipe.setServings(1);
        recipesData.add(recipe);
        when(recipeService.listRecipes()).thenReturn(recipesData);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        //when
        String indexView = indexController.getIndexPage(model);

        assertEquals("index", indexView);
        verify(recipeService, times(1)).listRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2,setInController.size());
     }
}