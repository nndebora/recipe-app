package com.dn.recipeapp.services;

import com.dn.recipeapp.commands.IngredientCommand;
import com.dn.recipeapp.commands.UnitOfMeasureCommand;
import com.dn.recipeapp.converters.IngredientCommandToIngredient;
import com.dn.recipeapp.converters.IngredientToIngredientCommand;
import com.dn.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.dn.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.dn.recipeapp.domain.Ingredient;
import com.dn.recipeapp.domain.Recipe;
import com.dn.recipeapp.domain.UnitOfMeasure;
import com.dn.recipeapp.repositories.RecipeRepository;
import com.dn.recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository uomRepository;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(recipeRepository,uomRepository,ingredientToIngredientCommand,ingredientCommandToIngredient);

    }

    @Test
    public void findByRecipeIdAndIngredientIdTest() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void saveIngredientIdTest() throws Exception {
        //given

        IngredientCommand ingredientToSave = new IngredientCommand();
        ingredientToSave.setRecipeId(2L);
        ingredientToSave.setId(3L);
        Recipe recipe = new Recipe();
        recipe.setId(2L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //then
        IngredientCommand ingredientCommand = ingredientService.saveIngredientCommand(ingredientToSave);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
    @Test
    public void deletByIdTest() {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        ingredient.setRecipe(recipe);
        recipe.addIngredient(ingredient);

        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteById(1L,3L);

        //then
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(any(Recipe.class));

    }
}


