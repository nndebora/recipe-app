package com.dn.recipeapp.services;

import com.dn.recipeapp.commands.RecipeCommand;
import com.dn.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> listRecipes();
    Recipe findById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    RecipeCommand findCommandById(Long id);
    void deleteById(Long id);
}
