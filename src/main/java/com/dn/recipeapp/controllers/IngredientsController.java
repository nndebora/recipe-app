package com.dn.recipeapp.controllers;


import com.dn.recipeapp.services.IngredientService;
import com.dn.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientsController {

    private RecipeService recipeService;
    private IngredientService ingredientService;

    public IngredientsController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping({"/recipe/{id}/ingredients"})
    public String listRecipeIngredients(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/ingredient/list";
    }


    @GetMapping
    @RequestMapping({"/recipe/{recipeId}/ingredient/{ingredientId}/show"})
    public String showIngredientById(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));
        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients/{ingredientId}")
    public String updateRecipe(@PathVariable String recipeId, @PathVariable String ingredientId,Model model){
   //     Recipe recipe = recipeService.findById(Long.valueOf(recipeId));
     //   IngredientCommand command = converter.convert(recipe.get)
       // model.addAttribute("ingredient",recipeService.findByRecipeIdAndIngredientId(Long.valueOf(id)));
        return "recipe/ingredient/ingredientform";
    }

}
