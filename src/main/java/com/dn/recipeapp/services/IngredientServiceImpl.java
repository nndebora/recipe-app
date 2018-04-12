package com.dn.recipeapp.services;

import com.dn.recipeapp.commands.IngredientCommand;
import com.dn.recipeapp.converters.IngredientToIngredientCommand;
import com.dn.recipeapp.domain.Recipe;
import com.dn.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService{

    private RecipeRepository recipeRepository;
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    //    @Override
 //   public Ingredient findById(Long id) {
  //      Optional<Ingredient> recipeOptional = repository.findById(id);
   //     if (recipeOptional.isPresent()) {
    //        return recipeOptional.get();
     //   } else {
      //      throw new RuntimeException("Ingredient not found");
       // }
   // }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
       Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
       if (!recipeOptional.isPresent()){
           //todo impl error handling
           log.error("Recipe not found. ID: "+recipeId);
       }
       Recipe recipe = recipeOptional.get();

       Optional<IngredientCommand> ingredientOptional =
       recipe.getIngredients().stream()
               .filter(ingredient -> ingredient.getId().equals(ingredientId))
               .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

       if(!ingredientOptional.isPresent()){
           //todo impl error handliing
           log.error("Ingredient not found. ID: "+ingredientId);
       }
        return ingredientOptional.get();
    }
}
