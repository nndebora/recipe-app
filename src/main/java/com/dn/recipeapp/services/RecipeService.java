package com.dn.recipeapp.services;

import com.dn.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> listRecipes();
    Recipe findById(Long id);
}
