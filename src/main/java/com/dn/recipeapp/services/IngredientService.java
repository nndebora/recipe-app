package com.dn.recipeapp.services;

import com.dn.recipeapp.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
//    Ingredient findById(Long id);

}
