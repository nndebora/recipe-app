package com.dn.recipeapp.controllers;


import com.dn.recipeapp.commands.IngredientCommand;
import com.dn.recipeapp.commands.RecipeCommand;
import com.dn.recipeapp.commands.UnitOfMeasureCommand;
import com.dn.recipeapp.services.IngredientService;
import com.dn.recipeapp.services.RecipeService;
import com.dn.recipeapp.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IngredientsController {

    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitOfMeasureService uomService;


    public IngredientsController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService uomService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.uomService = uomService;
    }

    @GetMapping({"/recipe/{id}/ingredients"})
    public String listRecipeIngredients(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/ingredient/list";
    }


    @GetMapping({"/recipe/{recipeId}/ingredient/{ingredientId}/show"})
    public String showIngredientById(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId,Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));
        model.addAttribute("unitOfMeasureList",uomService.listUOMs());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        return "redirect:/recipe/"+savedCommand.getRecipeId()+"/ingredient/"+savedCommand.getId()+"/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId,Model model){
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("ingredient",ingredientCommand);
        model.addAttribute("unitOfMeasureList",uomService.listUOMs());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteRecipe(@PathVariable String recipeId,@PathVariable String ingredientId){
        ingredientService.deleteById(Long.valueOf(recipeId),Long.valueOf(ingredientId));
        return "redirect:/recipe/"+recipeId+"/ingredients";
    }

}
